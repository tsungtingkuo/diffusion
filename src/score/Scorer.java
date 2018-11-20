package score ;

import java.util.*;
import network.* ;
public class Scorer{
	/**
	 * @param	path	influence path
	 * @param	source	source user
	 */
	public static double getInfluenceScore( Network path  , Long sourceId  ){
		double ret = 0.0;
		Queue<Long> queue = new LinkedList<Long>();
		queue.offer( sourceId);
		while( queue.peek() != null ){
			Long uid  = queue.poll();
			ret += 1.0;
			for( Long successorId : path.getSuccessors( uid )){
				queue.offer( successorId );
				//System.out.println("if now in :"+ uid +", successor: "+successorId ) ;
			}
		}
		queue.clear();
		queue = null;
		ret -= 1.0;// reduce self
		return ret ;
	} 
	/**
	 * @param	path	influence path
	 * @param	source	source user
	 * @param	interval time interval
	 * @param	timeMap mapping the link to time stamp
	 */
	public static double getSpeed( Network path , Long sourceId ,Long sourceTime, int interval , Map<Long, Long> timeMap   ){
		double ret = 0.0;
		Queue<Long> queue = new LinkedList<Long>();
		queue.offer( sourceId);
		Vector<Long> timeVector = new Vector<Long>(); 
		// calculate time distribution
		Map<Long,Integer> timeDistribution = new HashMap<Long,Integer>(); 
		while( queue.peek() != null ){
			Long uid  = queue.poll();
			
			for( Long successorId : path.getSuccessors( uid )){
				Long rid = path.findEdge(uid ,successorId);	
				Long timestamp = timeMap.get( rid );
				timeVector.add( timestamp);

				queue.offer( successorId );
			}
		}
		queue.clear();
		queue =null;
		// get time distribution curve
		timeDistribution.put( (long)0 , 0  );
		for( int i = 0 ;  i < timeVector.size() ;i++){
			Long tmpTime = timeVector.get(i);
			//System.out.println(" tmp Time: "+tmpTime );
			//System.out.println(" sourceTime: " + sourceTime );	
			Long timeSector = (tmpTime - sourceTime) / (long) interval;
			if( timeDistribution.get( timeSector ) == null){
				timeDistribution.put( timeSector , 1 );
			}else{
				Integer tmpCount = timeDistribution.get( timeSector);
				timeDistribution.put( timeSector , tmpCount +1 );
			}
		}
		timeVector.clear();
		timeVector = null;
		ArrayList<Long> timeSectors = new ArrayList<Long>( timeDistribution.keySet() );
		Collections.sort( timeSectors );

		// accumulated curve 
		Map<Long,Integer> timeCDistribution = timeDistribution ;
		for( int i  = 0 ; i < timeSectors.size() -1 ; i++){
			Integer first = timeCDistribution.get( timeSectors.get(i) );
			Integer second = timeCDistribution.get( timeSectors.get(i + 1) );
			timeCDistribution.put( timeSectors.get(i+1 ) , first + second );
		}
		// cal accumulated influence area 
		for(int i = 0 ; i <timeSectors.size() -1 ; i++){
			Integer first = timeCDistribution.get( timeSectors.get(i ));
			Integer second = timeCDistribution.get( timeSectors.get(i+1) );
			Long deltaTime = timeSectors.get(i+1) - timeSectors.get(i ) ;
			ret += (double)(( first + second ) * deltaTime) / 2.0 ;
		}
		timeSectors.clear();
		timeSectors = null;
		timeDistribution.clear();
		timeDistribution = null;
		timeCDistribution.clear();
		timeCDistribution =null;

		return ret ;
	} 

	/**
	 * @param network 
	 * @param sourceId
	 * @param user2distance 
	 * Not Implement 
	 * */
	public static double getGeoScore( Network path , Long sourceId  , Map<Long,Double> user2distance    ){
		double ret = 0.0;
		Queue<Long> queue = new LinkedList<Long>();
		queue.offer( sourceId);
		while( queue.peek() != null ){
			Long uid  = queue.poll();
			ret += user2distance.get( uid ) ;
			for( Long successorId : path.getSuccessors( uid )){
				queue.offer( successorId );		
			}
		}
		return ret ;
	}

	public  double getScore(Network path  , Long sourceId ){System.out.println("U Must IMPELEMENT"); return -1;}
	
	public void setLink2time( Map<Long,Long> link2time ){;}
} 
