package selector;

import network.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Unit{
	public Long uid;
	public int outdegree; 
	public Unit( Long  uid , int outdegree ){
		this.uid = uid; 
		this.outdegree = outdegree ;
	}
}
public class TopKSelector{
	/**
	 *	Get top-k degree nodes.
	 *	@param n input network
	 *	@param k k
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Long> getTopK( Network n , int k ){
		ArrayList<Unit> list = new ArrayList<Unit>();
		// sort by out degree	
		for( Long uid : n.getVertices() ){
			Unit u = new Unit( uid , n.outDegree(uid ));
			list.add( u );
		}	
		Collections.sort( list , 
			new Comparator(){
				public int compare( Object o1, Object o2){
					Unit u1 = (Unit)  o1 ;
					Unit u2 = (Unit) o2 ;
					return u2.outdegree - u1.outdegree ;   
				};
				public boolean equals( Object obj ){
					return false;	
				};
			}); 
		// add-in top k out-degree nodes 
		List<Long> ret = new ArrayList<Long>();
		for( int i = 0 ; i < k && i < list.size()  ; i++){
			ret.add( list.get( i ).uid ) ; 
		}
		return ret ;
	} 
}
