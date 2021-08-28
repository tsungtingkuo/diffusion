package score ;

import java.util.*;
import network.* ;

public class PathPrecisionScorer{

	public static double getEdgePrecision( Network scorePath, Network predictPath ){
		double totalScoreEdges = (double ) predictPath.getEdgeCount();
		double correctEdges = 0.0;
		if( totalScoreEdges == 0.0 ) return 0.0;
		for( Long eid : predictPath.getEdges() ){
			Long from = predictPath.getSource( eid );
			Long to = predictPath.getDest( eid) ;
			if( scorePath.findEdge( from , to )  != null ){
				correctEdges += 1.0;
			}
		} 
		return correctEdges / totalScoreEdges ;
	}

	public static double getVertexPrecision( Network scorePath , Network predictPath , Collection<Long> A0 ){
		double totalScoreVertices = predictPath.getVertexCount();
		for(Long v : predictPath.getVertices() ){
			if( A0.contains( v ) ) {
				totalScoreVertices -= 1.0;
			}
		}
		double correctVertices = 0.0;
		if( totalScoreVertices == 0.0 ) return 0.0;
		for( Long v : predictPath.getVertices() ){
			if( scorePath.containsVertex( v ) && !A0.contains( v )  ) {
				correctVertices += 1.0;
			}
		}  	
		return correctVertices / totalScoreVertices; 
	
	}

}
