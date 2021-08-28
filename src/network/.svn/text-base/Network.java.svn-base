package network;

import java.io.*;
import java.util.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

public class Network extends DirectedSparseGraph<Long, Long> implements Serializable {

	HashMap<Long, User> idToUser = new HashMap<Long, User>();
	HashMap<Long, Relation> idToRelation = new HashMap<Long, Relation>();

	private static final long serialVersionUID = -1154292807862420036L;

	public boolean isConverged() {
		return this.isConverged(0);
	}
	
	public boolean isConverged(int changeThreshold) {
		int change = 0;
		for(long id : this.getVertices()) {
			User u = this.getUser(id);
			if(u.getState() != u.getPreviouslyState()) {
				change++;
				if(change > changeThreshold) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void addUser(User u) {
		this.idToUser.put(u.getId(), u);		
	}
	
	public int getUserCount() {
		return this.idToUser.size();
	}
	
	public int getRelationCount() {
		return this.idToRelation.size();
	}
	
	public User getUser(long id) {
		return this.idToUser.get(id);
	}

	public void addRelation(Relation r) {
		this.idToRelation.put(r.getId(), r);		
	}
	
	public Relation getRelation(long id) {
		return this.idToRelation.get(id);
	}
	
	public int discountedDegree(long id) {
		int count = 0;
		if(this.getNeighbors(id) != null) {
			for(long v : this.getNeighbors(id)) {
				if(this.getUser(v).getState() == User.STATE_INACTIVATED) {
					count++;
				}
			}
		}
		return count;
	}

	public int discountedInDegree(long id) {
		int count = 0;
		if(this.getInEdges(id) != null) {
			for(long e : this.getInEdges(id)) {
				long v = this.getSource(e);
				if(this.getUser(v).getState() == User.STATE_INACTIVATED) {
					count++;
				}
			}
		}
		return count;
	}

	public int discountedOutDegree(long id) {
		int count = 0;
		if(this.getOutEdges(id) != null) {
			for(long e : this.getOutEdges(id)) {
				long v = this.getDest(e);
				if(this.getUser(v).getState() == User.STATE_INACTIVATED) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int commonNeighborPlusOne(long id1, long id2) {
		return (this.commonNeighbor(id1, id2) + 1);
	}
	
	public int commonNeighbor(long id1, long id2) {
		if(!this.containsVertex(id1) || !this.containsVertex(id2)) {
			return 0;
		}
		Collection<Long> neighbors = this.getNeighbors(id1);
		int count = 0;
		for(long v : this.getNeighbors(id2)) {
			if(neighbors.contains(v)) {
				count++;
			}
		}
		return count;
	}
	
	public void resetStates() {
		for(long v : this.getVertices()) {
			this.getUser(v).setState(User.STATE_INACTIVATED);
		}
	}
	
	public double inDegreePlusOne(long v) {
		if(!this.containsVertex(v)) {
			return 1.0;
		}
		else {
			return (double)this.inDegree(v) + 1.0;
		}
	}
	
	public double outDegreePlusOne(long v) {
		if(!this.containsVertex(v)) {
			return 1.0;
		}
		else {
			return (double)this.outDegree(v) + 1.0;
		}
	}

	public double degreePlusOne(long v) {
		if(!this.containsVertex(v)) {
			return 1.0;
		}
		else {
			return (double)this.degree(v) + 1.0;
		}
	}
}
