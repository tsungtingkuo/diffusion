package selector;
import network.*;
import java.util.*;

public class RandomKSelector{
	public static List<Long> getK(int k, Network n   ){
		return getK( k ,n, new Random() );
	}
	public static List<Long> getK(int k, Network n ,Random r ){
		if( n.getVertexCount() < k ){
			System.out.println("# nodes is less than k "); 
			return new ArrayList<Long>(n.getVertices() );
		}	
		List<Long> ret = new ArrayList<Long>();
		ArrayList<Long> totalList = new ArrayList<Long>( n.getVertices() );
		int totalSize = totalList.size();
		while( ret.size()  < k ){
			long choosedUid = totalList.get( r.nextInt( totalSize) );
			if( ret.contains( choosedUid ) ){
				continue;
			}else{
				ret.add( choosedUid );
			}
		}
		return ret ;
	}
}
