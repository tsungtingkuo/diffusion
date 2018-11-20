package model;

import utilities.*;
import network.*;
import java.util.*;

public class IcModel extends Model{

	public static long  sepId = -99;
	public Collection<Long> A0 ;

	public void setA0( Collection<Long> A0 ){ this.A0 = A0;}
	
	//The model will use id=-99 User as the seperator 
	/**
	 * @param	n	input network
	 * @param	o_n	output network
	 * @param	link2time	map link to time
	 * */
	public void run( Network n , Network o_n , Map<Long,Long> link2time   ){
			
		int level =1;

		Queue<Long> queue = new LinkedList<Long>() ;		
		for( Long uid : n.getVertices() ){//n.getUser(uid).setActivated( false); 
			n.getUser(uid).setState( User.STATE_INACTIVATED );
		}	
		
		//init
		Collection<Long> notFoundUsers = new HashSet<Long>();
		for( Long uid : A0 ){
			if( n.getUser(uid ) == null){
				System.out.println("NO USER FOUND");
				notFoundUsers.add( uid) ;
				continue;
			}
	
			queue.offer( uid ); //n.getUser(uid).setActivated( true ); }
			n.getUser(uid).setState( User.STATE_ACTIVATED );
			o_n.addVertex( uid ) ;
		}
		A0.removeAll( notFoundUsers );
		notFoundUsers.clear(); 
		try{
			IOUtility.writeUserSet( "NotFoundUserSet", notFoundUsers );
		}catch( Exception e ){
			System.out.println("ERROR");
		}
		queue.offer( sepId );
		Random random =new Random();

		// start 
		while( queue.peek() != null ){
			Long uid1 = queue.poll();
			//User u1 = n.getUser( uid1 );
			if( uid1 == sepId && queue.peek() !=null ){
				level += 1;
				queue.offer( sepId );
				continue;
			}
			if( uid1 != sepId){
				for( Long uid2 : n.getSuccessors( uid1 ) ){
					User u2 = n.getUser( uid2 );
					if( /*u2.isActivated()*/ u2.getState()==User.STATE_ACTIVATED /*|| u2.getState() == User.STATE_RECOVERED */){ continue;}
					
					Relation r = n.getRelation( n.findEdge( uid1, uid2 ) );
					if( random.nextFloat() <= r.getIcProbability() ){
						// u2 is activated, add into next round 
						queue.offer( uid2 );
						u2.setState( User.STATE_ACTIVATED );
						o_n.addVertex( uid2) ;
						o_n.addEdge( r.getId() , uid1, uid2 );
						link2time.put( r.getId() ,(long) level );	
					}/*else{
						u2.setState( User.STATE_RECOVERED );	
					}*/
				}
			}
		}  
		queue.clear();
		queue= null;
	} 
} 
