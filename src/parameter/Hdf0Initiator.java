package parameter;

import java.util.Collection;
import network.*;


public class Hdf0Initiator{
/**
 *	<p>Assign HdHeat f0 for HdModel.</p><p> f0 = # of user / # of initial user for initial set.
 *	f0 = 0 for other user</p>
 *	@param n input network
 *	@param A0 initial set
 * */
	public static Network initParam( Network n ,Collection<Long> A0 ){
		for( Long uid : n.getVertices() ){
			User u = n.getUser( uid );
			u.setHdHeat( 0.0 );
		}
		double h0 = (double) n.getUserCount() / (double) A0.size() ; 
		for( Long uid : A0 ){
			User u = n.getUser(uid );
			u.setHdHeat( h0 );
		}
		return n;
	}
} 
