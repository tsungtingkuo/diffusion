package parameter;

import network.*;
public class SISInitiator{
/**
 *	Assign SIS parameter of network n.
 *	The Lt threshold value = v 
 *	@param n input network
 *	@param birthRate birthrate
 *	@param deathRate deathRate
 * */
	public static Network initParam( Network n , double birthRate , double deathRate ){
		for( Long rid : n.getEdges() ){
			Relation r = n.getRelation( rid ) ;
			r.setSisBirthRate( birthRate );
		}
		for( Long uid : n.getVertices() ){
			User user = n.getUser( uid );
			user.setSisDeathRate( deathRate );
		}
		return n ;
	}
}
