package network;

import java.util.Collection;

public class DiffusionNetwork extends Network {

	private static final long serialVersionUID = 6172277467854661071L;
	
	public double getTotalWeight() {
		double tw = 0.0;
		for(long e : this.getEdges()) {
			tw += this.getRelation(e).getWeight();
		}
		return tw;
	}
	
	public double getOutLengthPlusOne(long sourceId, long destinationId) {
		return this.getOutLength(sourceId, destinationId) + 1;
	}

	public double getOutLength(long sourceId, long destinationId) {
		Long edge = this.findEdge(sourceId, destinationId);
		if(edge == null) {
			return 0.0;
		}
		else {
			return this.getRelation(edge).getLegnth();
		}
	}
	
	public double getOutHrefPlusOne(long sourceId, long destinationId) {
		return this.getOutHref(sourceId, destinationId) + 1;
	}

	public double getOutHref(long sourceId, long destinationId) {
		Long edge = this.findEdge(sourceId, destinationId);
		if(edge == null) {
			return 0.0;
		}
		else {
			return this.getRelation(edge).getHref();
		}
	}
	
	public double getOutImgPlusOne(long sourceId, long destinationId) {
		return this.getOutImg(sourceId, destinationId) + 1;
	}

	public double getOutImg(long sourceId, long destinationId) {
		Long edge = this.findEdge(sourceId, destinationId);
		if(edge == null) {
			return 0.0;
		}
		else {
			return this.getRelation(edge).getImg();
		}
	}
	
	public double getWeightPlusOne(long sourceId, long destinationId) {
		return (this.getWeight(sourceId, destinationId) + (double)1);
	}

	public double getWeight(long sourceId, long destinationId) {
		return this.getInWeight(sourceId, destinationId) + this.getOutWeight(sourceId, destinationId);
	}
	
	public double getInWeightPlusOne(long sourceId, long destinationId) {
		return (this.getInWeight(sourceId, destinationId) + (double)1);
	}

	public double getInWeight(long sourceId, long destinationId) {
		Long edge = this.findEdge(destinationId, sourceId);
		if(edge == null) {
			return 0.0;
		}
		else {
			return this.getRelation(edge).getWeight();
		}
	}
	
	public double getOutWeightPlusOne(long edge) {
		return this.getOutWeightPlusK(edge, 1.0);
	}

	public double getOutWeightPlusK(long edge, double k) {
		return (this.getOutWeight(edge) + k);
	}

	public double getOutWeight(long edge) {
		Relation r = this.getRelation(edge);
		if(r == null) {
			return 0.0;
		}
		else {
			return r.getWeight();
		}
	}
	
	public double getOutWeightPlusOne(long sourceId, long destinationId) {
		return this.getOutWeightPlusK(sourceId, destinationId, 1.0);
	}

	public double getOutWeightPlusK(long sourceId, long destinationId, double k) {
		return (this.getOutWeight(sourceId, destinationId) + k);
	}

	public double getOutWeight(long sourceId, long destinationId) {
		Long edge = this.findEdge(sourceId, destinationId);
		if(edge == null) {
			return 0.0;
		}
		else {
			return this.getRelation(edge).getWeight();
		}
	}
	
	public double weightedDegreePlusOne(long vertex) {
		return (this.weightedDegree(vertex) + (double)1);
	}

	public double weightedDegree(long vertex) {
		double wd = 0.0;
		if(this.containsVertex(vertex)) {
			for(long e : this.getIncidentEdges(vertex)) {
				wd += this.getRelation(e).getWeight();
			}
		}
		return wd;
	}

	public double weightedOutDegreePlusOne(long vertex) {
		return (this.weightedOutDegree(vertex) + (double)1);
	}

	public double weightedOutDegree(long vertex) {
		double wd = 0.0;
		if(this.containsVertex(vertex)) {
			for(long e : this.getOutEdges(vertex)) {
				wd += this.getRelation(e).getWeight();
			}
		}
		return wd;
	}
	
	public double weightedInDegreePlusOne(long vertex) {
		return (this.weightedInDegree(vertex) + (double)1);
	}

	public double weightedInDegree(long vertex) {
		double wd = 0.0;
		if(this.containsVertex(vertex)) {
			for(long e : this.getInEdges(vertex)) {
				wd += this.getRelation(e).getWeight();
			}
		}
		return wd;
	}
	
	public double weightedCommonNeighborPlusOne(long id1, long id2) {
		return (this.weightedCommonNeighbor(id1, id2) + 1.0);
	}
	
	public double weightedCommonNeighbor(long id1, long id2) {
		if(!this.containsVertex(id1) || !this.containsVertex(id2)) {
			return 0;
		}
		Collection<Long> neighbors = this.getNeighbors(id1);
		double wcn = 0.0;
		for(long v : this.getNeighbors(id2)) {
			if(neighbors.contains(v)) {
				wcn += this.getWeight(id1, v);
				wcn += this.getWeight(id2, v);
			}
		}
		return wcn;
	}	
}
