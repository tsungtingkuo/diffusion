package parameter;

import network.*;
public class SIRInitiator{
/**
 *	Assign SIR parameter of network n.
 *	The Lt threshold value = v 
 *	@param n input network
 *	@param birthRate birthrate
 *	@param deathRate recoveredRate
 * */
	public static Network initParam( Network n , double birthRate , double deathRate ){
		for( Long rid : n.getEdges() ){
			Relation r = n.getRelation( rid ) ;
			r.setSirBirthRate( birthRate );
		}
		for( Long uid : n.getVertices() ){
			User user = n.getUser( uid );
			user.setSirDeathRate( deathRate );
		}
		return n ;
	}
}
