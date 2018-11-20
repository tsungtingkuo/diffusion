

package parameter;

import network.*;
public class LtThresholdInitiator{
/**
 *	Assign LtThreshold ofr network n.
 *	The Lt threshold value = v 
 *	@param n input network
 *	@param v lt threshold
 * */
	public static Network initParam( Network n , double v ){
		for( Long uid : n.getVertices() ){
			User u = n.getUser( uid ) ;
			u.setLtThreshold( v ) ;
		}
		return n ;
	}


}
