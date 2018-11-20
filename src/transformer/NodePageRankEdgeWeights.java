package transformer;

import network.*;

public class NodePageRankEdgeWeights implements org.apache.commons.collections15.Transformer<Long, Double> {

	DiffusionNetwork dn = null;
	DiffusionNetwork dnTrain = null;

	public NodePageRankEdgeWeights(DiffusionNetwork dn,
			DiffusionNetwork dnTrain) {
		super();
		this.dn = dn;
		this.dnTrain = dnTrain;
	}

	public NodePageRankEdgeWeights(DiffusionNetwork dn) {
		super();
		this.dn = dn;
	}

	@Override
	public Double transform(Long e) {
		if(this.dnTrain == null) {
			return this.dn.getOutWeightPlusOne(e);
		}
		else {
			return this.dnTrain.getOutWeightPlusOne(this.dn.getSource(e), this.dn.getDest(e));
		}
	}
}
