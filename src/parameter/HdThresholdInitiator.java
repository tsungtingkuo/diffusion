package parameter;
import network.*;

public class HdThresholdInitiator{

	/**
 	* Initialize HdThreshold for network.
	* @param n input network
	* @param theta threshold
 	*/
	public static Network initParam( Network n ,double theta){
		for( Long uid : n.getVertices() ){
			User u = n.getUser( uid );
			u.setHdThreshold( theta );
		}
		return n;
	}
}
