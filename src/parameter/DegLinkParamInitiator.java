

package parameter;

import network.*;


public class DegLinkParamInitiator{
/**
 * assigned weight on each link for IcProbability or LtWeight. 
 * <p> weight of e(u,v) = 1 / outdegree( u )
 * @param	n	input network
 * */
	public static Network initParam( Network n ){
		for( Long uid : n.getVertices() ){
			double v =  1.0 / (double) n.outDegree( uid ) ;	
			for( Long rid : n.getOutEdges( uid ) ){
				Relation r = n.getRelation( rid );
				r.setIcProbability( v) ;
				r.setLtWeight( v );
			}	
		}
		return n;
	}
}
