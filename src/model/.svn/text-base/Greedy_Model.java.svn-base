package model;

import java.util.*;
import network.*;

public class Greedy_Model  extends Model{

	double T_threshold ;
	double likeQ;

	//int node_count;
	//int edge_count;
	static int MAX_ROUND = 1000;
	Collection<Long> a0 ;
	LinkedList<Long> infectedList;
	HashMap<Long, Boolean> userPreference = new HashMap<Long, Boolean>();
	HashMap<Long, Boolean> userStop = new HashMap<Long, Boolean>();

	public void setParameter( double t_threshold, double like){ 
		this.T_threshold = t_threshold ;
		this.likeQ = like;
	}

	public void setA0( Collection<Long> a0 ){ 
		this.a0 = a0;
	}

	public Greedy_Model(){
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
				userPreference.put(user_id, true);
			else
				userPreference.put(user_id, false);

			userStop.put(user_id, false);
		}
		// set active
		for( Long uid : a0 ){
			user= network.getUser( uid );
			user.setState( User.STATE_ACTIVATED );
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
	
		for(Long user_id: network.getVertices()){
			User u = network.getUser(user_id);
			u.setState(u.getState());
		}
		
		for( Long infectedId : this.infectedList ){
			for( Long neighborId : network.getSuccessors( infectedId) ){
				if(userStop.get(neighborId) == true)
					continue;
				
				User u = network.getUser(neighborId);
				if( u.getState() == User.STATE_ACTIVATED ){
					u.setState(User.STATE_ACTIVATED);
					continue;
				}
				
				int num_Like = 0;
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
				
				for(Long tempneighborid: network.getSuccessors(neighborId)){
					if(userPreference.get(tempneighborid) == true)
						num_Like++;
				}
				
				if(num_Like / num_Neighbor <= T_threshold){
					u.setState(User.STATE_INACTIVATED);
					userStop.put(neighborId, true);
					continue;
				}

				toBeAdded.add(neighborId);
				u.setState(User.STATE_ACTIVATED);
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
		}
		
		this.infectedList.addAll(  toBeAdded );
	} 

/*
	public boolean isConverged(){
		return network.isConverged();
	}
*/
}
