
package parameter;

import network.*;

public class InDegLinkParamInitiator{
/**
 * assigned weight on each link for IcProbability or LtWeight. 
 * <p> weight of e(u,v) = 1.0  / indegree( v )
 * @param	n	input network
 * */
	public static Network initParam( Network n ){
		for( Long vid : n.getVertices() ){
			double p = 1.0 / (double) n.inDegree( vid ); 
			for( Long rid : n.getInEdges( vid ) ){
				Relation r = n.getRelation( rid );
				r.setIcProbability( p );
				r.setLtWeight( p );
			}
		}
		return n;
	}
}
