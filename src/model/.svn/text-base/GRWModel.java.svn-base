package model;

import utilities.*;
import network.*;
import java.util.*;

import edu.uci.ics.jung.algorithms.scoring.PageRank;

import utility.*;

public class GRWModel extends Model {

	public static final int RANDOM = 0;
	public static final int DEGREE = 1;
	public static final int IN_DEGREE = 2;
	public static final int PAGERANK = 3;
	public static final int INVERSE_DEGREE = 4;
	public static final int INVERSE_IN_DEGREE = 5;
	public static final int INVERSE_PAGERANK = 6;
	public static final int DN_WEIGHTED_DEGREE = 7;
	public static final int DN_INVERSE_WEIGHTED_DEGREE = 8;
	public static final int DN_LINK_OUT_WEIGHT = 9;
	public static final int DN_LINK_INVERSE_OUT_WEIGHT = 10;
	public static final int DN_LINK_LOYALTY = 11;
	public static final int DN_LINK_RESPONSIVENESS = 12;
	public static final int DN_LINK_LOYALTY_RESPONSIVENESS = 13;
	public static final int DN_LINK_INVERSE_LOYALTY = 14;
	public static final int DN_LINK_INVERSE_RESPONSIVENESS = 15;
	public static final int DN_LINK_INVERSE_LOYALTY_RESPONSIVENESS = 16;
	public static final int OUT_DEGREE = 17;
	public static final int INVERSE_OUT_DEGREE = 18;
	public static final int DN_WEIGHTED_IN_DEGREE = 19;
	public static final int DN_INVERSE_WEIGHTED_IN_DEGREE = 20;
	public static final int DN_WEIGHTED_OUT_DEGREE = 21;
	public static final int DN_INVERSE_WEIGHTED_OUT_DEGREE = 22;
	public static final int DN_LINK_IN_LOYALTY = 23;
	public static final int DN_LINK_IN_RESPONSIVENESS = 24;
	public static final int DN_LINK_IN_LOYALTY_RESPONSIVENESS = 25;
	public static final int DN_LINK_INVERSE_IN_LOYALTY = 26;
	public static final int DN_LINK_INVERSE_IN_RESPONSIVENESS = 27;
	public static final int DN_LINK_INVERSE_IN_LOYALTY_RESPONSIVENESS = 28;
	public static final int DN_LINK_OUT_LOYALTY = 29;
	public static final int DN_LINK_OUT_RESPONSIVENESS = 30;
	public static final int DN_LINK_OUT_LOYALTY_RESPONSIVENESS = 31;
	public static final int DN_LINK_INVERSE_OUT_LOYALTY = 32;
	public static final int DN_LINK_INVERSE_OUT_RESPONSIVENESS = 33;
	public static final int DN_LINK_INVERSE_OUT_LOYALTY_RESPONSIVENESS = 34;
	public static final int AVERAGE_OD_IOD = 35;
	public static final int DN_LINK_OUT_WEIGHT_MULTIPLY_OD = 36;
	public static final int DN_LINK_OUT_WEIGHT_MULTIPLY_IOD = 37;
	public static final int MULTIPLY_OD_IOD = 38;
	public static final int SN_OUT_DEGREE = 39;
	public static final int SN_INVERSE_OUT_DEGREE = 40;
	public static final int SN_DEGREE = 41;
	public static final int SN_IN_DEGREE = 42;
	public static final int SN_PAGERANK = 43;
	public static final int SN_INVERSE_DEGREE = 44;
	public static final int SN_INVERSE_IN_DEGREE = 45;
	public static final int SN_INVERSE_PAGERANK = 46;
	public static final int DISCOUNTED_DEGREE = 47;
	public static final int DISCOUNTED_IN_DEGREE = 48;
	public static final int DISCOUNTED_OUT_DEGREE = 49;
	public static final int DISCOUNTED_INVERSE_DEGREE = 50;
	public static final int DISCOUNTED_INVERSE_IN_DEGREE = 51;
	public static final int DISCOUNTED_INVERSE_OUT_DEGREE = 52;
	public static final int SN_DISCOUNTED_DEGREE = 53;
	public static final int SN_DISCOUNTED_IN_DEGREE = 54;
	public static final int SN_DISCOUNTED_OUT_DEGREE = 55;
	public static final int SN_DISCOUNTED_INVERSE_DEGREE = 56;
	public static final int SN_DISCOUNTED_INVERSE_IN_DEGREE = 57;
	public static final int SN_DISCOUNTED_INVERSE_OUT_DEGREE = 58;
	public static final int DN_LINK_COMMON_NEIGHBOR = 59;
	public static final int SN_LINK_COMMON_NEIGHBOR = 60;
	public static final int DN_LINK_INVERSE_COMMON_NEIGHBOR = 61;
	public static final int SN_LINK_INVERSE_COMMON_NEIGHBOR = 62;
	
	public Collection<Long> A0 ;
	public int activateNumber = 0; 
	Random random = new Random();
	int nodeSelectionMethod = RANDOM;
	int linkSelectionMethod = RANDOM;
	PageRank<Long, Long> pr = null;
	DiffusionNetwork diffusionNetwork = null;
	Network sn = null;
	String userFileName = null;
	String relationFileName = null;

	// Guided selection predictor for link
	public long selectLink(Network n, Vector<Long> candidates, int method, long sourceId) {
		TreeMap<Long, Double> idPrior = new TreeMap<Long, Double>();
		switch(method) {
		case DN_LINK_OUT_WEIGHT:
			for(long id : candidates) {
				idPrior.put(id, this.diffusionNetwork.getOutWeightPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_OUT_WEIGHT_MULTIPLY_OD:
			for(long id : candidates) {
				double low = this.diffusionNetwork.getOutWeightPlusOne(sourceId, id);
				double od = (double)n.outDegree(id);
				idPrior.put(id, low*od);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_OUT_WEIGHT_MULTIPLY_IOD:
			for(long id : candidates) {
				double low = this.diffusionNetwork.getOutWeightPlusOne(sourceId, id);
				double od = (double)n.outDegree(id);
				double iod = 0.0;
				if(od != 0) {
					iod = (double)1/(double)od;
				}
				idPrior.put(id, low*iod);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_OUT_WEIGHT:
			for(long id : candidates) {
				idPrior.put(id, (double)1/(double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(id);
				idPrior.put(id, l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(sourceId);
				idPrior.put(id, r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(sourceId);
				idPrior.put(id, l*r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(id);
				idPrior.put(id, (double)1/(double)l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)(l*r));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_IN_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(id);
				idPrior.put(id, l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_IN_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(sourceId);
				idPrior.put(id, r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_IN_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(sourceId);
				idPrior.put(id, l*r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_IN_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(id);
				idPrior.put(id, (double)1/(double)l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_IN_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_IN_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedInDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)(l*r));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_OUT_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(id);
				idPrior.put(id, l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_OUT_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(sourceId);
				idPrior.put(id, r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_OUT_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(sourceId);
				idPrior.put(id, l*r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_OUT_LOYALTY:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(id);
				idPrior.put(id, (double)1/(double)l);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_OUT_RESPONSIVENESS:
			for(long id : candidates) {
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)r);
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_OUT_LOYALTY_RESPONSIVENESS:
			for(long id : candidates) {
				double l = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(id);
				double r = (double)this.diffusionNetwork.getOutWeightPlusOne(sourceId, id)/(double)this.diffusionNetwork.weightedOutDegreePlusOne(sourceId);
				idPrior.put(id, (double)1/(double)(l*r));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_COMMON_NEIGHBOR:
			for(long id : candidates) {
				idPrior.put(id, (double)this.diffusionNetwork.commonNeighborPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case SN_LINK_COMMON_NEIGHBOR:
			for(long id : candidates) {
				idPrior.put(id, (double)this.sn.commonNeighborPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case DN_LINK_INVERSE_COMMON_NEIGHBOR:
			for(long id : candidates) {
				idPrior.put(id, (double)1/(double)this.diffusionNetwork.commonNeighborPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		case SN_LINK_INVERSE_COMMON_NEIGHBOR:
			for(long id : candidates) {
				idPrior.put(id, (double)1/(double)this.sn.commonNeighborPlusOne(sourceId, id));
			}
			return Utility.randomSelectionWithPrior(idPrior, random);
		default:
			return select(n, candidates, method);
		}
	}
	
	// Guided selection predictor for node or link
	public long select(Network n, Vector<Long> candidates, int method) {
		TreeMap<Long, Double> idPrior = new TreeMap<Long, Double>();
		TreeMap<Long, Double> idPrior1 = new TreeMap<Long, Double>();
		TreeMap<Long, Double> idPrior2 = new TreeMap<Long, Double>();
		for(long id : candidates) {
			if(!n.containsVertex(id)) {
				idPrior.put(id, 0.0);
			}
			else {
				switch(method) {
				case RANDOM:
					idPrior.put(id, 1.0);
					break;
				case DEGREE:
					idPrior.put(id, (double)n.degree(id));
					break;
				case IN_DEGREE:
					idPrior.put(id, (double)n.inDegree(id));
					break;
				case OUT_DEGREE:
					idPrior.put(id, (double)n.outDegree(id));
					break;
				case SN_PAGERANK:
				case PAGERANK:
					idPrior.put(id, (double)this.pr.getVertexScore(id));
					break;
				case INVERSE_DEGREE:
					int d = n.degree(id);
					if(d != 0) {
						idPrior.put(id, (double)1/(double)d);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case INVERSE_IN_DEGREE:
					int ind = n.inDegree(id);
					if(ind != 0) {
						idPrior.put(id, (double)1/(double)ind);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case INVERSE_OUT_DEGREE:
					int outd = n.outDegree(id);
					if(outd != 0) {
						idPrior.put(id, (double)1/(double)outd);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_INVERSE_PAGERANK:
				case INVERSE_PAGERANK:
					double p = this.pr.getVertexScore(id);
					if(p > 0) {
						idPrior.put(id, (double)1/(double)p);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case DN_WEIGHTED_DEGREE:
					idPrior.put(id, this.diffusionNetwork.weightedDegreePlusOne(id));
					break;
				case DN_INVERSE_WEIGHTED_DEGREE:
					idPrior.put(id, (double)1/(double)this.diffusionNetwork.weightedDegreePlusOne(id));
					break;
				case DN_WEIGHTED_IN_DEGREE:
					idPrior.put(id, this.diffusionNetwork.weightedInDegreePlusOne(id));
					break;
				case DN_INVERSE_WEIGHTED_IN_DEGREE:
					idPrior.put(id, (double)1/(double)this.diffusionNetwork.weightedInDegreePlusOne(id));
					break;
				case DN_WEIGHTED_OUT_DEGREE:
					idPrior.put(id, this.diffusionNetwork.weightedOutDegreePlusOne(id));
					break;
				case DN_INVERSE_WEIGHTED_OUT_DEGREE:
					idPrior.put(id, (double)1/(double)this.diffusionNetwork.weightedOutDegreePlusOne(id));
					break;
				case AVERAGE_OD_IOD:
				case MULTIPLY_OD_IOD:
					idPrior1.put(id, (double)n.outDegree(id));
					int outdegree = n.outDegree(id);
					if(outdegree != 0) {
						idPrior2.put(id, (double)1/(double)outdegree);
					}
					else {
						idPrior2.put(id, 0.0);
					}
					break;		
				case SN_OUT_DEGREE:
					idPrior.put(id, (double)this.sn.outDegree(id));
					break;
				case SN_INVERSE_OUT_DEGREE:
					int outdeg = this.sn.outDegree(id);
					if(outdeg != 0) {
						idPrior.put(id, (double)1/(double)outdeg);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_DEGREE:
					idPrior.put(id, (double)this.sn.degree(id));
					break;
				case SN_IN_DEGREE:
					idPrior.put(id, (double)this.sn.inDegree(id));
					break;
				case SN_INVERSE_DEGREE:
					int deg = this.sn.degree(id);
					if(deg != 0) {
						idPrior.put(id, (double)1/(double)deg);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_INVERSE_IN_DEGREE:
					int indeg = this.sn.inDegree(id);
					if(indeg != 0) {
						idPrior.put(id, (double)1/(double)indeg);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case DISCOUNTED_DEGREE:
					idPrior.put(id, (double)n.discountedDegree(id));
					break;
				case DISCOUNTED_IN_DEGREE:
					idPrior.put(id, (double)n.discountedInDegree(id));
					break;
				case DISCOUNTED_OUT_DEGREE:
					idPrior.put(id, (double)n.discountedOutDegree(id));
					break;
				case DISCOUNTED_INVERSE_DEGREE:
					int dd = n.discountedDegree(id);
					if(dd != 0) {
						idPrior.put(id, (double)1/(double)dd);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case DISCOUNTED_INVERSE_IN_DEGREE:
					int did = n.discountedInDegree(id);
					if(did != 0) {
						idPrior.put(id, (double)1/(double)did);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case DISCOUNTED_INVERSE_OUT_DEGREE:
					int dod = n.discountedOutDegree(id);
					if(dod != 0) {
						idPrior.put(id, (double)1/(double)dod);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_DISCOUNTED_DEGREE:
					idPrior.put(id, (double)this.sn.discountedDegree(id));
					break;
				case SN_DISCOUNTED_IN_DEGREE:
					idPrior.put(id, (double)this.sn.discountedInDegree(id));
					break;
				case SN_DISCOUNTED_OUT_DEGREE:
					idPrior.put(id, (double)this.sn.discountedOutDegree(id));
					break;
				case SN_DISCOUNTED_INVERSE_DEGREE:
					int sdd = this.sn.discountedDegree(id);
					if(sdd != 0) {
						idPrior.put(id, (double)1/(double)sdd);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_DISCOUNTED_INVERSE_IN_DEGREE:
					int sdid = this.sn.discountedInDegree(id);
					if(sdid != 0) {
						idPrior.put(id, (double)1/(double)sdid);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				case SN_DISCOUNTED_INVERSE_OUT_DEGREE:
					int sdod = this.sn.discountedOutDegree(id);
					if(sdod != 0) {
						idPrior.put(id, (double)1/(double)sdod);
					}
					else {
						idPrior.put(id, 0.0);
					}
					break;
				}
			}
		}
		if(method == AVERAGE_OD_IOD) {
			TreeMap<Long, Double> normalized1 = Utility.normalizePrior(idPrior1);
			TreeMap<Long, Double> normalized2 = Utility.normalizePrior(idPrior2);
			for(long id : normalized1.keySet()) {
				double prior1 = normalized1.get(id);
				double prior2 = normalized2.get(id);
				idPrior.put(id, (double)(prior1+prior2)/(double)2);
			}
		}
		if(method == MULTIPLY_OD_IOD) {
			TreeMap<Long, Double> normalized1 = Utility.normalizePrior(idPrior1);
			TreeMap<Long, Double> normalized2 = Utility.normalizePrior(idPrior2);
			for(long id : normalized1.keySet()) {
				double prior1 = normalized1.get(id);
				double prior2 = normalized2.get(id);
				idPrior.put(id, prior1*prior2);
			}
		}
		return Utility.randomSelectionWithPrior(idPrior, random);
	}
	
	public GRWModel(int nodeSelectionMethod, int linkSelectionMethod) {
		super();
		this.nodeSelectionMethod = nodeSelectionMethod;
		this.linkSelectionMethod = linkSelectionMethod;
	}

	public void setA0( Collection<Long> A0 ){ this.A0 = A0;}
	
	/**
	 * @param	n	input network
	 * @param	o_n	output network
	 * @param	link2time	map link to time
	 * */
	public void run( Network n , Network o_n , Map<Long,Long> link2time ) throws Exception {

		if(this.nodeSelectionMethod==PAGERANK
				||this.linkSelectionMethod==PAGERANK
				||this.nodeSelectionMethod==INVERSE_PAGERANK
				||this.linkSelectionMethod==INVERSE_PAGERANK
				) {
			System.out.print("Running PageRank ... ");
			this.pr = new PageRank<Long, Long>(n, 0.15);
			this.pr.evaluate();
			System.out.println("done.");
		}

		if(this.nodeSelectionMethod==SN_OUT_DEGREE
				||this.linkSelectionMethod==SN_OUT_DEGREE
				||this.nodeSelectionMethod==SN_INVERSE_OUT_DEGREE
				||this.linkSelectionMethod==SN_INVERSE_OUT_DEGREE
				||this.nodeSelectionMethod==SN_DEGREE
				||this.linkSelectionMethod==SN_DEGREE
				||this.nodeSelectionMethod==SN_IN_DEGREE
				||this.linkSelectionMethod==SN_IN_DEGREE
				||this.nodeSelectionMethod==SN_PAGERANK
				||this.linkSelectionMethod==SN_PAGERANK
				||this.nodeSelectionMethod==SN_INVERSE_DEGREE
				||this.linkSelectionMethod==SN_INVERSE_DEGREE
				||this.nodeSelectionMethod==SN_INVERSE_IN_DEGREE
				||this.linkSelectionMethod==SN_INVERSE_IN_DEGREE
				||this.nodeSelectionMethod==SN_INVERSE_PAGERANK
				||this.linkSelectionMethod==SN_INVERSE_PAGERANK
				||this.nodeSelectionMethod==SN_DISCOUNTED_OUT_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_OUT_DEGREE
				||this.nodeSelectionMethod==SN_DISCOUNTED_INVERSE_OUT_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_INVERSE_OUT_DEGREE
				||this.nodeSelectionMethod==SN_DISCOUNTED_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_DEGREE
				||this.nodeSelectionMethod==SN_DISCOUNTED_IN_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_IN_DEGREE
				||this.nodeSelectionMethod==SN_DISCOUNTED_INVERSE_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_INVERSE_DEGREE
				||this.nodeSelectionMethod==SN_DISCOUNTED_INVERSE_IN_DEGREE
				||this.linkSelectionMethod==SN_DISCOUNTED_INVERSE_IN_DEGREE
				||this.linkSelectionMethod==SN_LINK_COMMON_NEIGHBOR
				||this.linkSelectionMethod==SN_LINK_INVERSE_COMMON_NEIGHBOR
				) {
			System.out.print("Loading social network ... ");
			this.sn = NetworkFactory.getNetwork(true, this.userFileName, this.relationFileName);
			System.out.println("done, node = " + this.sn.getVertexCount() + ", link = " + this.sn.getEdgeCount());
		}

		if(this.nodeSelectionMethod==SN_PAGERANK
				||this.linkSelectionMethod==SN_PAGERANK
				||this.nodeSelectionMethod==SN_INVERSE_PAGERANK
				||this.linkSelectionMethod==SN_INVERSE_PAGERANK
				) {
			System.out.print("Running PageRank ... ");
			this.pr = new PageRank<Long, Long>(this.sn, 0.15);
			this.pr.evaluate();
			System.out.println("done.");
		}

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

		// start 
		System.out.println("Original = " + queue.size());
		
		int finalActivated = queue.size() + this.activateNumber;
		if(finalActivated > n.getVertexCount()) {
			finalActivated = n.getVertexCount();
		}
		
		HashSet<Long> removing = new HashSet<Long>();
		int count = 0;
		int countRepeat = 0;
		while(activated.size() < finalActivated){
			if(count%100 == 0) {
				System.out.print(count + ".");
				if(count == 0) {
					countRepeat++;
					if(countRepeat > 100) {
						break;
					}
				}
			}
			
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
				countRepeat = 0;
			}
			
			// Guided random selection of a node
			Long uid1 = this.select(n, queue, this.nodeSelectionMethod);

			if(uid1 != -1) {
				Vector<Long> candidates = getCandidates(n, uid1);
	
				// Guided random selection of a link
				Long uid2 = this.selectLink(n, candidates, this.linkSelectionMethod, uid1);
				
				if(uid2 != -1) {
					level += 1;				
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
				else {
					removing.add(uid1);
				}
				count++;
			}
			else {
				removing.add(uid1);
			}
		}
		System.out.println();
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

	/**
	 * @return the diffusionNetwork
	 */
	public DiffusionNetwork getDiffusionNetwork() {
		return diffusionNetwork;
	}

	/**
	 * @param diffusionNetwork the diffusionNetwork to set
	 */
	public void setDiffusionNetwork(DiffusionNetwork diffusionNetwork) {
		this.diffusionNetwork = diffusionNetwork;
	}

	/**
	 * @return the userFileName
	 */
	public String getUserFileName() {
		return userFileName;
	}

	/**
	 * @param userFileName the userFileName to set
	 */
	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}

	/**
	 * @return the relationFileName
	 */
	public String getRelationFileName() {
		return relationFileName;
	}

	/**
	 * @param relationFileName the relationFileName to set
	 */
	public void setRelationFileName(String relationFileName) {
		this.relationFileName = relationFileName;
	}
} 
