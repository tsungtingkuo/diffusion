package transformer;

import network.*;

public class LinkPageRankVertexPriors implements org.apache.commons.collections15.Transformer<Long, Double> {

	DiffusionNetwork dn = null;
	DiffusionNetwork dnPattern = null;

	public LinkPageRankVertexPriors(DiffusionNetwork dn,
			DiffusionNetwork dnPattern) {
		super();
		this.dn = dn;
		this.dnPattern = dnPattern;
	}

	@Override
	public Double transform(Long v) {
		return this.dnPattern.getOutWeightPlusOne(this.dn.getSource(v), this.dn.getDest(v));
	}
}
