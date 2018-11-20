package score;

import network.*;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.*;

public class PathMaker{
	MessageManager m_m ;
	ResponseManager r_m;
	Network LBIPaths ;
	Map<Long, Long> LBILink2Time;
	Map<Long, Long> LBISourceTimes;
	Network UBIPaths ;
	Map<Long, Long> UBILink2Time;
	Map<Long, Long> UBISourceTimes;

	public Map<Long,Long> getLBISourceTimes(){ return LBISourceTimes;}
	public Map<Long,Long> getUBISourceTimes(){ return UBISourceTimes;}

	public Network getLBIPaths(){
		return LBIPaths;
	}
//	public Map<Long, Long> getLBIUser2Time(){
//		return LBIUser2Time ;
//	}
	/**
	 *	@return map linkId to response time in LBI path
	 * */
	public Map<Long,Long> getLBILink2Time(){
		return LBILink2Time;
	}
	public Network getUBIPaths(){
		return UBIPaths ;
	}
//	public Map<Long, Long> getUBIUser2Time(){
//		return UBIUser2Time;
//	}
	/**
	 * 	@return map linkId to response time in UBI path
	 * */
	public Map<Long,Long> getUBILink2Time(){
		return UBILink2Time ;
	}
	// for facebook data
	public PathMaker( String messageFileName , String responseFileName  ) throws Exception{
		// Encoding
		String encoding = "UTF-8";
		
		m_m = new MessageManager( messageFileName , encoding);
		r_m = new ResponseManager( responseFileName , encoding);

		LBIPaths = null;
		UBIPaths = null;
	}
	// for plurk data?
	public  PathMaker( String messageFileName , String messageQualifierFileName , String messageLangFileName , String responseFileName, String responseQualifierFileName, String responseLangFileName )throws Exception {
		// Encoding
		String encoding = "UTF-8";
		
		m_m = new MessageManager( messageFileName, messageQualifierFileName , messageLangFileName , encoding);
		r_m = new ResponseManager( responseFileName, responseQualifierFileName, responseLangFileName, encoding);
		LBIPaths  =null;
		UBIPaths = null;
	} 
	/**
	 *	construct UBI and LBI path , and the link2time which is millis-second based
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access", "unused" })
	public void constructPaths(){
		//preprocessing messages
		Vector<Message> messages = m_m.getMessages();
		Vector<Response> responses = r_m.getResponses();
		// step 1, sort  meessages and responseby timestamp( asc)
		Comparator timeComparator = new Comparator(){
			public int compare( Object o1 , Object o2  ){
				Message m1 = (Message) o1 ;
				Message m2 = (Message) o2 ;
				Long t1 =(long) m1.getTimestamp();
				Long t2 =(long) m2.getTimestamp();
				if( t1 < 0 || t2 < 0 ){ 
					t1 = m1.getTime().getTimeInMillis();
					t2 = m2.getTime().getTimeInMillis();
				}
				return (int)(t1 - t2) ;
			} 	
			public boolean equals( Object obj ){
				return false;
			} 
		};
		Collections.sort( messages, timeComparator );
		Comparator responseTimeComparator = new Comparator(){
			public int compare( Object o1 , Object o2  ){
				Response r1 = (Response) o1 ;
				Response r2 = (Response) o2 ;
				Long t1 =(long) r1.getTimestamp();
				Long t2 =(long) r2.getTimestamp();
				if( t1 < 0 || t2 < 0 ){
					t1 = r1.getTime().getTimeInMillis();
					t2 = r2.getTime().getTimeInMillis();
				}
				return (int)( t1 - t2) ;
			} 	
			public boolean equals( Object obj ){
				return false;
			} 
		};
		Collections.sort( messages, timeComparator );	
		Collections.sort( responses, responseTimeComparator );	
		// step 2, for each user, retain the first message and response only
		Set<Long> userSet = new HashSet<Long>();
		Set<Message> removedSet = new HashSet<Message>();
		for( int i = 0; i < messages.size(); i++){
			Long uid = messages.get(i).getUserId();
			if( userSet.contains( uid )){
				removedSet.add( messages.get(i)); 	
			}else{
				userSet.add( uid );
			}
		}
		messages.removeAll( removedSet );
		Set<Long> tmpResponseUserSet = new HashSet<Long>();
		Set<Response> removedResponseSet = new HashSet<Response>();
		for( int i =0 ; i < responses.size() ; i++){
			Long uid = responses.get(i).getUserId();
			if( tmpResponseUserSet.contains( uid )){
				removedResponseSet.add( responses.get(i));
			}else{
				tmpResponseUserSet.add(uid) ;
			}
		}
		responses.removeAll( removedResponseSet) ;
		// step 2.5, if response time > message time , remove response 
		removedResponseSet.clear() ;
		for( Message m : messages ){
			for( Response r : r_m.getResponsesByUser( m.getUserId() ) ){
				Long m_time =(long) m.getTimestamp();
				Long r_time =(long) r.getTimestamp();
				if( m_time < 0 || r_time < 0 ){
					m_time = m.getTime().getTimeInMillis();
					r_time = r.getTime().getTimeInMillis();
				}
				if( r_time > m_time ){ removedResponseSet.add( r ) ;}
			} 
		}		
		responses.removeAll( removedResponseSet );
		// step 3, add user into paths
		Network lbiPaths = new Network();
		Map<Long, Long> user2time = new HashMap<Long,Long>();
		for( Message m : messages){
			Long uid = m.getUserId();
			Long time = (long)  m.getTimestamp();
			if( time < 0 ){
				time = (Long) (m.getTime().getTimeInMillis());
			}
			lbiPaths.addVertex( uid );
			lbiPaths.addUser( UserFactory.getUser( uid )); 
			user2time.put( uid, time ) ; 
		}
		// step 4 , for each poUser, concate them by po-response relationship
		RelationFactory rf = new RelationFactory();
			//map link -> receive time
		Map<Long,Long> link2time = new HashMap<Long,Long>();  
		for( Message m : messages ){
			Vector<Response> responsesForM = r_m.getResponsesByMessage( m.getId());
			Long poId = m.getUserId();
			Long poTime = (long) 1000 * m.getTimestamp();
			if( poTime < 0){
				poTime = (Long) (m.getTime().getTimeInMillis());
			}
			for( Response response : responsesForM ){
				Long receiverId = response.getUserId() ;
				if( !userSet.contains( receiverId ) ){
					continue;
				}else{
					//avoid self-loop
					if( receiverId.equals(poId) ){ continue ;};
					
					Long receivedTime = (long) 1000 * response.getTimestamp();
					if( receivedTime < 0){
						receivedTime = (Long) (response.getTime().getTimeInMillis());
					}
					Long receiverPoTime = user2time.get( receiverId ) ; 
					if( !(  poTime < receivedTime &&  receivedTime < receiverPoTime)   ){ continue;}
					// add lbi link
					Relation r = rf.getRelation() ;
					//System.out.println("p:"+poId +"\tr:"+receiverId  );
					lbiPaths.addEdge( r.getId() , poId , receiverId ); 
					lbiPaths.addRelation( r );
					link2time.put( r.getId() , receivedTime );  	
				}
			}
		}
		// Step5, delete node if degree(node) == 0
		Set<Long> removedUid = new HashSet<Long>(); 
		for( Long uid : lbiPaths.getVertices() ){
			if( lbiPaths.degree( uid ) == 0 ){
				removedUid.add( uid );
			}
		}
		for( Long uid : removedUid ){
			lbiPaths.removeVertex( uid );	
		}
		// Step6, for each initial nodes, set time
		this.LBISourceTimes = new HashMap<Long,Long >();
		for( Long uid: lbiPaths.getVertices() ){
			if( lbiPaths.inDegree( uid ) == 0 ){
				LBISourceTimes.put( uid, user2time.get( uid ) );  
			}
		}
		LBIPaths = lbiPaths;
		LBILink2Time = link2time ;
		// construct ubi path
		// step 1, clone lbi path
		// clone LBIPath to UBIPaths
		UBIPaths = new Network();
		for( Long uid : LBIPaths.getVertices() ){
			UBIPaths.addVertex( uid );
			UBIPaths.addUser( LBIPaths.getUser( uid )); 
		}
		for( Long linkId : LBIPaths.getEdges()) {
			Pair<Long> endpoints = LBIPaths.getEndpoints( linkId );
		//	UBIPaths.addEdge( linkId, endpoints.getFirst() , endpoints.getSecond() );
			UBIPaths.addEdge( linkId , LBIPaths.getSource( linkId ), LBIPaths.getDest( linkId ) );
			UBIPaths.addRelation( LBIPaths.getRelation( linkId ) );
		}
		// clone LBIUser2Time -> UBIUser2Time
		UBILink2Time = new HashMap<Long,Long>();
		for( Long key : LBILink2Time.keySet() ){
			UBILink2Time.put( key, LBILink2Time.get( key ) );	
		} 
		// step 2, attach response to lbi path
		for( Message m : messages){
			Vector<Response> responsesForM = r_m.getResponsesByMessage( m.getId());
			Long poId = m.getUserId();
			Long poTime =(long) 1000* m.getTimestamp();
			if( poTime < 0 ){
				poTime =(Long )( m.getTime().getTimeInMillis());
			}


			for( Response response : responsesForM ){
				Long receiverId = response.getUserId() ;
				Long receivedTime =  (long) 1000* response.getTimestamp();
				if( receivedTime < 0 ){
					receivedTime = (Long)(response.getTime().getTimeInMillis());
				} 
				// avoid self -loop
				if( receiverId.equals( poId) ){ continue ; }
				// avoid loop
				//if( userSet.contains( receiverId)) { continue; }
				if( LBIPaths.containsVertex( receiverId ) ){ continue;}
				if( receivedTime < poTime ){ System.out.println("Time Erorr");
					System.out.println( poTime); 
					System.out.println( receivedTime);
					continue;}
				//if( UBIPaths.findEdge( poId , receiverId ) != null ){continue;}
				// add ubi link

				Relation r = rf.getRelation() ;
				UBIPaths.addEdge( r.getId() , poId , receiverId ); 
				UBIPaths.addRelation( r );
				UBILink2Time.put( r.getId() , receivedTime );  	
			}			
		}
		// Step3, for each initial nodes, set time
		this.UBISourceTimes = new HashMap<Long,Long >();
		for( Long uid: UBIPaths.getVertices() ){
			if( UBIPaths.inDegree( uid ) == 0 ){
				UBISourceTimes.put( uid, user2time.get( uid ) );  
			}
		}
		
		
	}
	/**
	 * @return the split forest( start time <= time < end time ).
	 * */
	public static Network splitForestByTime( 
			Network forest ,
			Map<Long,Long> link2time ,
			Long starttime , Long endtime ){
		
		Network ret = new Network();
		for( Long eid : forest.getEdges() ){
			Long time = (Long) link2time.get( eid);
			//System.out.print( "starttime: "+starttime +" time: "+ time + " endtime: " +endtime+"\r" );  
			if( starttime <= time && time < endtime ){
				Long source = forest.getSource( eid );
				Long dest = forest.getDest( eid );
			
				if( !ret.containsVertex( source ) ){ ret.addVertex( source ) ;} 
				if( !ret.containsVertex( dest ) ){ ret.addVertex( dest ) ;} 

				ret.addEdge( eid , source, dest ); 

			}
		}
		return ret ;
	}
}



