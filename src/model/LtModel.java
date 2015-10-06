package model;

import java.util.*;
import network.*;

public class LtModel extends Model{
	public static long sepId = -99;
	public Collection<Long> A0;
	public void setA0( Collection<Long> A0 ){ this.A0 = A0; }
	/**
	 * @param	n	input network
	 * @param	o_n	output network
	 * @param	link2time	map link to generating time
	 * */
	public void run( Network n  , Network o_n, Map<Long,Long> link2time ){
		//init 
		int level = 1;
		Long sep = sepId ;
		Queue<Long> queue = new LinkedList<Long>() ;		

		for( Long uid : n.getVertices() ){ n.getUser(uid).setState( User.STATE_INACTIVATED );}
		for( Long uid : n.getVertices()){ n.getUser( uid ).setLtValue( 0.0 );	}

		Collection<Long> notFoundUsers = new HashSet<Long>();
		for( Long uid : A0 ){ 
			if( n.getUser(uid ) == null){
				System.out.println("NO USER FOUND");
				notFoundUsers.add( uid) ;
				continue;
			}

			queue.offer( uid );
			n.getUser(uid).setState( User.STATE_ACTIVATED );
			o_n.addVertex( uid );
			//o_n.addUser( UserFactory.getUser( uid ));
		}
		A0.removeAll( notFoundUsers );
		notFoundUsers.clear();
		notFoundUsers =null;

		queue.offer( sep );
		//
		while( queue.peek() != null ) {

			Long uid1 = queue.poll();
			//User u1 = n.getUser( uid1 ) ;
			if( uid1 == sepId && queue.peek() !=null ){
				level += 1;
				queue.offer( sep );
				//System.out.print("Lt: level=" + level +" size="+ queue.size() +"\r");
				continue;
			}
			if( uid1 != sepId ){
				for( Long uid2 : n.getSuccessors( uid1 ) ){
					User u2 = n.getUser( uid2 ) ; 
					//	if( u2.getState() == User.STATE_ACTIVATED ){ continue;}
					Relation r = n.getRelation(  n.findEdge( uid1, uid2 ) );
					u2.setLtValue( u2.getLtValue() + r.getLtWeight() );
					if(  u2.getLtValue() >= u2.getLtThreshold()  && u2.getState() == User.STATE_INACTIVATED ){
						// u2 is activated, add into next round 
						queue.offer( uid2 );
						u2.setState( User.STATE_ACTIVATED);
						o_n.addVertex( uid2) ;
						o_n.addEdge( r.getId( ) , uid1, uid2 );
						link2time.put( r.getId() , (long)level );	
					}
				}
			}


		}
		queue.clear();
		queue=null;
	}
}
