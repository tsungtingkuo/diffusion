package transformer;

import network.*;
import edu.uci.ics.jung.algorithms.scoring.*;

public class LinkPageRankEdgeWeights implements org.apache.commons.collections15.Transformer<Long, Double> {

	DiffusionNetwork dn = null;
	DiffusionNetwork ln = null;
	PageRank<Long, Long> prNode = null;

	public LinkPageRankEdgeWeights(DiffusionNetwork dn,
			DiffusionNetwork ln, PageRank<Long, Long> prNode) {
		super();
		this.dn = dn;
		this.ln = ln;
		this.prNode = prNode;
	}

	@Override
	public Double transform(Long e) {
		return this.prNode.getVertexScore(this.dn.getDest(this.ln.getSource(e)));
	}
}
