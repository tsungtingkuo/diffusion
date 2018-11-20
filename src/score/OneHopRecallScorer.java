package score;

import java.util.Collection;
import java.util.HashSet;

import network.Network;
import utilities.GraphUtility;
import edu.uci.ics.jung.graph.util.Pair;

public class OneHopRecallScorer {
	public static double getEdgeRecall( Network scorePath, Network predictPath ){
		Collection<Long> a0 = GraphUtility.getA0(scorePath);
		Collection<Pair<Long>> oneHopScoreEdges  = new HashSet<Pair<Long>>();
		Collection<Pair<Long>> oneHopPredictEdges =new HashSet<Pair<Long>>();
		for( Long v_in_a0 : a0 ){
			for( Long v2 :scorePath.getSuccessors(v_in_a0)){
				oneHopScoreEdges.add(new Pair<Long>(v_in_a0, v2 ) );
			}
		}
		for( Long v_in_a0 : a0 ){
			Collection<Long> successors = predictPath.getSuccessors(v_in_a0);
			if( successors == null) continue;
			for( Long v2 : successors ){
				oneHopPredictEdges.add( new Pair<Long>( v_in_a0 , v2 )); 
			}
		}
		
		double totalScoreEdges = (double) oneHopScoreEdges.size();
		double correctEdges = 0.0;
		if( totalScoreEdges == 0.0 ) return 0.0;
		// count correct Edges 
		for( Pair<Long>  e1 : oneHopPredictEdges ){
			if( oneHopScoreEdges.contains( e1 ) ){
				correctEdges  += 1.0;
			}
		}
		
		
		return correctEdges / totalScoreEdges ;
	}

	public static double getVertexRecall( Network scorePath , Network predictPath , Collection<Long> A0 ){
		Collection<Long> a0 = GraphUtility.getA0(scorePath);
		Collection<Long> oneHopScoreVertices = new HashSet<Long>();
		Collection<Long> oneHopPredictVertices =new HashSet<Long>();
		
		for( Long v_in_a0 : a0 ){
			for( Long v2 :scorePath.getSuccessors(v_in_a0)){
				oneHopScoreVertices.add( v2 ) ;
			}
		}
		for( Long v_in_a0 : a0 ){
			Collection<Long> successors = predictPath.getSuccessors(v_in_a0 );
			if( successors == null ) continue;
			for( Long v2 : successors ){
				oneHopPredictVertices.add( v2); 
			}
		}
		double totalScoreVertices = (double) oneHopScoreVertices.size();
		double correctVertices = 0.0;
		if( totalScoreVertices == 0.0 ) return 0.0;
		for( Long v_in_predict : oneHopPredictVertices){
			if( oneHopScoreVertices.contains( v_in_predict) ){
				correctVertices+= 1.0 ;
			}
		}	
		return correctVertices / totalScoreVertices; 
	
	}

}
