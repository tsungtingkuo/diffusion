package score ;

import java.util.*;
import network.* ;

public class PathCounter{
	public static double getVertexCount( Network forest , Collection<Long> a0 ){
		int count = 0 ;
		for( Long v : forest.getVertices() ){
			if( !a0.contains( v )){
				count++;
			}
		}
		return (double )count ;
	} 		
	public static double getEdgeCount( Network forest ){
		return (double )forest.getEdgeCount() ;
	}
}
