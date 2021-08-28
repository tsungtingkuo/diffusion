package predictor;

import java.util.*;
import utility.*;
import network.*;

public class GuidedKNeighborsPredictor extends DoNothingPredictor {
	
	int k = 1;
	int function = 1;
	Random r = new Random();

	public double score(long sourceId, long destinationId) {
		double value = this.r.nextDouble();
		switch(function){
		case 1:	value = this.dnt.getOutWeightPlusOne(sourceId, destinationId); break;
		case 2:	value = this.dnt.getOutLengthPlusOne(sourceId, destinationId); break;
		case 3:	value = this.dnt.getOutHrefPlusOne(sourceId, destinationId); break;
		case 4:	value = this.dnt.getOutImgPlusOne(sourceId, destinationId); break;
		case 5: value = this.dnt.weightedInDegreePlusOne(destinationId); break;
		case 6: value = (double)this.dnt.commonNeighborPlusOne(sourceId, destinationId); break;
		case 7: value = this.dnt.weightedCommonNeighborPlusOne(sourceId, destinationId); break;
		default: break;
		}
		return value;
	}
	
	public TreeMap<Long, Double> selectTopK(Vector<Long> candidates, long sourceId, int k) {
		TreeMap<Long, Double> tm = new TreeMap<Long, Double>();
		for(long destinationId : candidates) {
			tm.put(destinationId, this.score(sourceId, destinationId));
		}
		return Utility.getTopValuedLongToDoubleTreeMap(tm, k, true);
	}
	
	public DiffusionNetwork predictGuided(DiffusionNetwork result, int concept, long[] aggresives) throws Exception {
		for(long uid1 : aggresives) {
			Vector<Long> candidates = getCandidates(uid1);
			int select = k;
			if(select==-1 || select>candidates.size()) {
				select = candidates.size();
			}
			TreeMap<Long, Double> tm = this.selectTopK(candidates, uid1, select);
			for(long uid2 : candidates) {
				if(tm.containsKey(uid2)) {
					result = this.activate(result, uid1, uid2, true, tm.get(uid2));
				}
				else {
					result = this.activate(result, uid1, uid2, false, 0.0);
				}
			}
		}		
		return result;
	}
	
	// k is not used
	public DiffusionNetwork predictGuided(DiffusionNetwork result, int concept, DiffusionNetwork testdn) throws Exception {
		for(long e : testdn.getEdges()) {
			long source = testdn.getSource(e);
			long dest = testdn.getDest(e);
			result = this.activate(result, source, dest, true, this.score(source, dest));
		}		
		return result;
	}
	
	public DiffusionNetwork predict(DiffusionNetwork result, int concept, long[] aggresives) throws Exception {
		return predictGuided(result, concept, aggresives);
	}

	public DiffusionNetwork predict(DiffusionNetwork result, int concept, DiffusionNetwork testdn) throws Exception {
		return predictGuided(result, concept, testdn);
	}

	public GuidedKNeighborsPredictor(DiffusionNetwork dn, DiffusionNetwork dnt) throws Exception {
		super(dn, dnt);
	}

	/**
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * @param k the k to set
	 */
	public void setK(int k) {
		this.k = k;
	}

	/**
	 * @return the function
	 */
	public int getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(int function) {
		this.function = function;
	}
}
