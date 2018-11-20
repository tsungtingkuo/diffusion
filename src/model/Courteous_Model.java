package model;

import java.util.*;
import network.*;

public class Courteous_Model  extends Model{

	double T_threshold ;
	double likeQ;
	double C_threshold;
	
	public static final int USER_SEEN_LIKE = 0;
	public static final int USER_SEEN_UNLIKE = 1;
	public static final int USER_UNSEEN_LIKE = 2;
	public static final int USER_UNSEEN_UNLIKE = 3;

	//int node_count;
	//int edge_count;
	static int MAX_ROUND = 1000;
	Collection<Long> a0 ;
	LinkedList<Long> infectedList;
	HashMap<Long, Integer> userPreference = new HashMap<Long, Integer>();
	HashMap<Long, Boolean> userStop = new HashMap<Long, Boolean>();

	public void setParameter( double t_threshold, double c_threshold, double like){ 
		this.T_threshold = t_threshold ;
		this.C_threshold = c_threshold ;
		this.likeQ = like;
	}

	public void setA0( Collection<Long> a0 ){ 
		this.a0 = a0;
	}

	public Courteous_Model(){
		this.C_threshold = 0.25;
		this.likeQ = 0.5;
	}

	public void initial( Network network , Network o_n ){
		User user;
		//Relation relation;
		// set infected linklist
		if( this.infectedList == null){
			infectedList = new LinkedList<Long>();
		}else{ infectedList.clear();}

		
		// set inactive initially 
		Random rand = new Random();
		for(Long user_id: network.getVertices()){
			user = network.getUser(user_id);
			user.setState(User.STATE_INACTIVATED);

			/* Set User Preference */
			if(rand.nextDouble() <= likeQ)
				userPreference.put(user_id, USER_UNSEEN_LIKE);
			else
				userPreference.put(user_id, USER_UNSEEN_UNLIKE);

			userStop.put(user_id, false);
		}
		// set active
		for( Long uid : a0 ){
			user= network.getUser( uid );
			user.setState( User.STATE_ACTIVATED );
			if(userPreference.get(uid) == USER_UNSEEN_LIKE)
				userPreference.put(uid, USER_SEEN_LIKE);
			else if(userPreference.get(uid) == USER_UNSEEN_UNLIKE)
				userPreference.put(uid, USER_SEEN_UNLIKE);
			userStop.put(uid, true);
			o_n.addVertex( uid );
		}
	
		infectedList.addAll( a0 );
	}

	public void run( Network n, Network o_n, Map<Long, Long > link2time ){
		this.initial( n , o_n );
	
		for( int i = 0 ; i < MAX_ROUND && !n.isConverged() ; i++){
			NewRunIteration(n , o_n , link2time ,(long) i );	
		}
	}  

	public void NewRunIteration( Network network , Network o_n , Map<Long,Long> link2time, long timestamp ){
		Random rand = new Random();
		HashSet<Long> toBeAdded = new HashSet<Long>();
		Vector<Long> isInfected = new Vector<Long>();
		HashMap<Long, Boolean> userDone = new HashMap<Long, Boolean>();
	
		for(Long user_id: network.getVertices()){
			User u = network.getUser(user_id);
			u.setState(u.getState());
			userDone.put(user_id, false);
		}
		
		for( Long infectedId : this.infectedList ){
			for( Long neighborId : network.getSuccessors( infectedId) ){
				if(userDone.get(neighborId) == true)
					continue;
				
				userDone.put(neighborId, true);
				
				if(userStop.get(neighborId) == true)
					continue;
				
				User u = network.getUser(neighborId);
				if( u.getState() == User.STATE_ACTIVATED ){
					u.setState(User.STATE_ACTIVATED);
					userStop.put(neighborId, true);
					continue;
				}
				
				int num_Neighbor = network.getSuccessors(neighborId).size();
				if(num_Neighbor == 0){
					userStop.put(neighborId, true);
					continue;
				}
				
				isInfected.clear();
				for(Long tempneighborid: network.getPredecessors(neighborId)){
					User neighbor_u = network.getUser(tempneighborid);
					if(neighbor_u.getState() == User.STATE_ACTIVATED)
						isInfected.add(tempneighborid);
				}
				if(isInfected.size() == 0)
					continue;
				
				int num_NeighborUnseen = 0;
				int num_LikeUnseen = 0;
				for(Long tempneighborid: network.getSuccessors(neighborId)){
					int templike = userPreference.get(tempneighborid);
					if(templike == USER_UNSEEN_LIKE || templike == USER_UNSEEN_UNLIKE){
						num_NeighborUnseen++;
						if(templike == USER_UNSEEN_LIKE)
							num_LikeUnseen++;
					}
				}
				
				int num_Seen = num_Neighbor - num_NeighborUnseen;
				
				if(num_Seen == 0)
					continue;
				
				if(num_NeighborUnseen == 0){
					userStop.put(neighborId, true);
					continue;
				}
			
				if((num_Seen / num_Neighbor <= C_threshold) && (num_LikeUnseen / num_NeighborUnseen >= T_threshold)){
					toBeAdded.add(neighborId);
					u.setState(User.STATE_ACTIVATED);
					
					int templike = userPreference.get(neighborId);
					if(templike == USER_UNSEEN_LIKE)
						userPreference.put(neighborId, USER_SEEN_LIKE);
					else if(templike == USER_UNSEEN_UNLIKE)
						userPreference.put(neighborId, USER_SEEN_UNLIKE);
					
					userStop.put(neighborId, true);

					int n = rand.nextInt(isInfected.size());
					Long tempneighborid = isInfected.get(n);
					Long relationId = network.findEdge(tempneighborid, neighborId);

					if( !o_n.containsVertex(neighborId))
						o_n.addVertex(neighborId); 
					if( !o_n.containsVertex(tempneighborid))
						o_n.addVertex(tempneighborid);
					o_n.addEdge( relationId, tempneighborid, neighborId);
					link2time.put( relationId , timestamp );	
				}
				else{
					u.setState(User.STATE_INACTIVATED);
					if(num_Seen / num_Neighbor > C_threshold)
						userStop.put(neighborId, true);
				}
			}
		}
		this.infectedList.addAll(  toBeAdded );
	} 

/*
	public boolean isConverged(){
		return network.isConverged();
	}
*/
}
