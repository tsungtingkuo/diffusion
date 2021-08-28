package predictor;

import java.util.Vector;

import network.*;

public class DoNothingPredictor {
	
	DiffusionNetwork dn = null;
	DiffusionNetwork dnt = null;
	CascadesManager cm = null;

	public DoNothingPredictor(DiffusionNetwork dn, DiffusionNetwork dnt) throws Exception {
		this.dn = dn;
		this.dnt = dnt;
	}
	
	public DiffusionNetwork predict(DiffusionNetwork result, int concept, long[] aggresives) throws Exception {
		return result;
	}
	
	public DiffusionNetwork predict(int concept, long[] aggresives) throws Exception {
		DiffusionNetwork result = this.setAggresives(aggresives);
		result = this.predict(result, concept, aggresives);
		return result;
	}
	
	public DiffusionNetwork predict(DiffusionNetwork result, int concept, DiffusionNetwork testdn) throws Exception {
		return result;
	}
	
	public DiffusionNetwork predict(int concept, DiffusionNetwork testdn) throws Exception {
		DiffusionNetwork result = new DiffusionNetwork();
		result = this.predict(result, concept, testdn);
		return result;
	}
	
	DiffusionNetwork setAggresives(long[] aggresives) {
		DiffusionNetwork result = new DiffusionNetwork();
		for(long aggresive : aggresives) {
			result.addVertex(aggresive);
		}
		return result;
	}
	
	DiffusionNetwork activate(DiffusionNetwork result, long uid1, long uid2, boolean predicted, double score) {
		if(!result.containsVertex(uid2)) {
			result.addVertex(uid2) ;
		}
		if(result.findEdge(uid1, uid2) == null) {
			Long e = this.dn.findEdge(uid1, uid2);
			Relation r = this.dn.getRelation(e);
			r.setPredicted(predicted);
			r.setScore(score);
			result.addEdge(r.getId(), uid1, uid2);
			result.addRelation(r);
		}
		return result;
	}
	
	public boolean hasCandidates(long uid) {
		if(getCandidates(uid).size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Vector<Long> getCandidates(long uid) {
		Vector<Long> candidates = new Vector<Long>();
		for(long id :  this.dn.getSuccessors(uid)){
			candidates.add(id);
		}
		return candidates;
	}
}
