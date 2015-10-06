package model;

import java.util.*;
import network.*;

public class SIR_Model  extends Model{

	double deathRate ;

	//int node_count;
	//int edge_count;
	int infected;
	int suscepted;
	int recovered;
	static int MAX_ROUND = 1000;
	static HashMap<Long, Long> UseridToArrayid = new HashMap<Long, Long>();
	Collection<Long> a0 ;
	LinkedList<Long> infectedList;

	
	public void setDeathRate( double deathRate ){ this.deathRate = deathRate ;}
	public void setA0( Collection<Long> a0 ){ this.a0 = a0;}

	public SIR_Model(){
		this.deathRate = 0.01; 
	}

	public void initial( Network network , Network o_n ){
		User user;
		//Relation relation;
		// set infected linklist
		if( this.infectedList == null){
			infectedList = new LinkedList<Long>();
		}else{ infectedList.clear();}
		
		// set inactive initially 
		for(Long user_id: network.getVertices()){
			user = network.getUser(user_id);
			user.setState(User.STATE_INACTIVATED);
		}
		// set active
		for( Long uid : a0 ){
			user= network.getUser( uid );
			user.setState( User.STATE_ACTIVATED );
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
		HashSet<Long> toBeRemoved = new HashSet<Long>();

		for( Long infectedId : this.infectedList ){
			// activate neighbors
			for( Long neighborId : network.getSuccessors( infectedId) ){
				User u = network.getUser( neighborId);
				if( u.getState() == User.STATE_RECOVERED || u.getState() == User.STATE_ACTIVATED ){continue;}
				long relationId = network.findEdge( infectedId , neighborId );
				Relation relation = network.getRelation( relationId );
				double birth_rate = relation.getSirBirthRate();
				if( rand.nextDouble() <= birth_rate ){
					u.setState(  User.STATE_ACTIVATED) ;
					toBeAdded.add( neighborId );
					if( !o_n.containsVertex( infectedId)){o_n.addVertex(infectedId);}  
					if( !o_n.containsVertex( neighborId)){o_n.addVertex(neighborId);}
					o_n.addEdge( relationId , infectedId, neighborId);
					link2time.put( relationId , timestamp );
				} 
			}
			//if recovered
			User infectedUser = network.getUser( infectedId );
			if( rand.nextDouble() <= infectedUser.getSirDeathRate() ){
				toBeRemoved.add( infectedId );
				infectedUser.setState( User.STATE_RECOVERED );
			}
		}
		this.infectedList.removeAll( toBeRemoved );
		this.infectedList.addAll(  toBeAdded );
	} 

	public int getInfectedCount(){
		return infected;
	}

	public int getSusceptedCount(){
		return suscepted;
	}

	public int getRecoveredCount(){
		return recovered;
	}

	public String information(){
		return ("suscepted = " + getSusceptedCount() + "\tinfected = " + getInfectedCount() + "\trecovered = " + getRecoveredCount());
	}
/*
	public boolean isConverged(){
		return network.isConverged();
	}
*/
}
