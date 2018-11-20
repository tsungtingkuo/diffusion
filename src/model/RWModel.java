package model;

import utilities.*;
import network.*;

import java.util.*;

public class RWModel extends Model {

	public Collection<Long> A0 ;
	public int activateNumber = 0; 

	public void setA0( Collection<Long> A0 ){ this.A0 = A0;}
	
	/**
	 * @param	n	input network
	 * @param	o_n	output network
	 * @param	link2time	map link to time
	 * */
	public void run( Network n , Network o_n , Map<Long,Long> link2time   ){
			
		int level =1;
		Vector<Long> activated = new Vector<Long>();
		Vector<Long> queue = new Vector<Long>();
		for( Long uid : n.getVertices() ){ 
			n.getUser(uid).setState( User.STATE_INACTIVATED );
		}	
		
		//init
		Collection<Long> notFoundUsers = new HashSet<Long>();
		for( Long uid : A0 ){
			if( n.getUser(uid ) == null){
				System.out.println("NO USER FOUND");
				notFoundUsers.add( uid) ;
			}
			else {
				activated.add(uid);
				queue.add(uid);
				n.getUser(uid).setState( User.STATE_ACTIVATED );
				o_n.addVertex( uid ) ;
			}
		}
		A0.removeAll( notFoundUsers );
		notFoundUsers.clear(); 
		try{
			IOUtility.writeUserSet( "NotFoundUserSet", notFoundUsers );
		}catch( Exception e ){
			System.out.println("ERROR");
		}
		Random random = new Random();

		// start 
		System.out.println("Original = " + queue.size());
		
		int finalActivated = queue.size() + this.activateNumber;
//		if(finalActivated > n.getVertexCount()-queue.size()) {
//			finalActivated = n.getVertexCount()-queue.size();
//		}
		if(finalActivated > n.getVertexCount()) {
			finalActivated = n.getVertexCount();
		}
		
		HashSet<Long> removing = new HashSet<Long>();
		int count = 0;
		while(activated.size() < finalActivated){
			if(count == finalActivated) {
				Vector<Long> tempQueue = new Vector<Long>();
				for(long uid : queue) {
					if(!removing.contains(uid)) {
						tempQueue.add(uid);
					}
				}
				System.out.println("Final = " + finalActivated + ", activated = " + activated.size() + ", queue = " + queue.size() + ", removed = " + tempQueue.size());
				queue = new Vector<Long>(tempQueue);
				removing.clear();
				count = 0;
			}
			Long uid1 = queue.get(random.nextInt(queue.size()));
			Vector<Long> candidates = getCandidates(n, uid1);
			if(candidates.size() > 0) {
				level += 1;
				Long uid2 = candidates.get(random.nextInt(candidates.size()));
				User u2 = n.getUser( uid2 );
				u2.setState( User.STATE_ACTIVATED );
				Relation r = n.getRelation( n.findEdge( uid1, uid2 ) );
				o_n.addVertex( uid2) ;
				o_n.addEdge( r.getId() , uid1, uid2 );
				link2time.put( r.getId() ,(long) level );
				//System.out.println("User activated = " + uid2);
								
				activated.add(uid2);
				
				if(hasCandidates(n, uid2)) {
					queue.add(uid2);
				}
				
				if(candidates.size() == 1) {
					removing.add(uid1);
				}
			}
			count++;
		}
		System.out.println("Count = " + count); 
	}
	
	public boolean hasCandidates(Network n, long uid) {
		for( Long id :  n.getSuccessors(uid)){
			User u = n.getUser( id );
			if(u.getState()==User.STATE_INACTIVATED) {
				return true;
			}
		}
		return false;
	}
	
	public Vector<Long> getCandidates(Network n, long uid) {
		Vector<Long> candidates = new Vector<Long>();
		for( Long id :  n.getSuccessors(uid)){
			User u = n.getUser( id );
			if(u.getState()==User.STATE_INACTIVATED) {
				candidates.add(id);
			}
		}
		return candidates;
	}

	/**
	 * @return the activateNumber
	 */
	public int getActivateNumber() {
		return activateNumber;
	}

	/**
	 * @param activateNumber the activateNumber to set
	 */
	public void setActivateNumber(int activateNumber) {
		this.activateNumber = activateNumber;
	} 
} 
