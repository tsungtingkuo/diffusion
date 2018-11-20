package score ;

import java.util.*;
import network.* ;

public class PathRecallScorer{

	public static double getEdgeRecall( Network scorePath, Network predictPath ){
		double totalScoreEdges = (double ) scorePath.getEdgeCount();
		double correctEdges = 0.0;
		for( Long eid : scorePath.getEdges() ){
			Long from = scorePath.getSource( eid );
			Long to = scorePath.getDest(eid ) ;
			if( predictPath.findEdge( from , to )  != null ){
				correctEdges += 1.0;
			}
		} 
		return correctEdges / totalScoreEdges ;
	}

	public static double getVertexRecall( Network scorePath , Network predictPath , Collection<Long> A0){
		double totalScoreVertices = (double )scorePath.getVertexCount();
		for( Long v : scorePath.getVertices() ){
			if( A0.contains( v ) ){
				totalScoreVertices -= 1.0;
			}
		}

		double correctVertices = 0.0;
		for( Long v : scorePath.getVertices() ){
			if( predictPath.containsVertex( v ) && !A0.contains( v)  ) {
				correctVertices += 1.0;
			}
		}  	
		return correctVertices / totalScoreVertices; 
	
	}

}
