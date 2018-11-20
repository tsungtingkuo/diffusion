package predictor;

import java.util.Random;

import network.DiffusionNetwork;

public class ICOneStepPredictor extends GuidedKNeighborsPredictor {

	public double score(long sourceId, long destinationId) {
		if ( this.dnt.weightedOutDegree( sourceId) == 0) return 0.0;
		// calculate the weight of the link
		double prob = this.dnt.getOutWeight(sourceId, destinationId) / this.dnt.weightedOutDegree(sourceId);

		// throw a dice and return 1 or 0
		Random r = new Random();
		double randRatio = r.nextFloat();
		//System.out.println("prob: "+ prob + " rand: "+ randRatio);
		if(  randRatio <=  prob ){
			return 1.0;
		}
		return 0.0;
	}
	public ICOneStepPredictor(DiffusionNetwork dn, DiffusionNetwork dnt)
			throws Exception {
		super(dn, dnt);
		// TODO Auto-generated constructor stub
	}

}
