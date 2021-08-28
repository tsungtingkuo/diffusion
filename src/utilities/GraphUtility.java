package utilities;

import java.util.Collection;

import network.Network;
import java.util.HashSet;
public class GraphUtility {
	public static Collection<Long> getA0( Network n){
		Collection<Long> ret = new HashSet<Long>();
		for( Long v : n.getVertices()){
			if( n.inDegree(v )== 0 ){
				ret.add( v);
			}
		}
		return ret ;
	}
}
