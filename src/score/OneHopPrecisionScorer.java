package score;

import java.util.Collection;
import java.util.HashSet;
import edu.uci.ics.jung.graph.util.Pair;
import utilities.GraphUtility;
import network.Network;

public class OneHopPrecisionScorer {

	public static double getEdgePrecision( Network scorePath, Network predictPath ){
		Collection<Long> a0 = GraphUtility.getA0(scorePath);
		Collection<Pair<Long>> oneHopScoreEdges  = new HashSet<Pair<Long>>();
		Collection<Pair<Long>> oneHopPredictEdges =new HashSet<Pair<Long>>();
		for( Long v_in_a0 : a0 ){
			for( Long v2 :scorePath.getSuccessors(v_in_a0)){
				oneHopScoreEdges.add(new Pair<Long>(v_in_a0, v2 ) );
			}
		}
		for( Long v_in_a0 : a0 ){
			Collection<Long> successors = predictPath.getSuccessors(v_in_a0 );
			if( successors == null) continue;
			for( Long v2 : successors ){
				oneHopPredictEdges.add( new Pair<Long>( v_in_a0 , v2 )); 
			}
		}
		
		double totalPredictEdges = (double) oneHopPredictEdges.size();
		double correctEdges = 0.0;
		if( totalPredictEdges == 0.0 ) return 0.0;
		// count correct Edges 
		for( Pair<Long>  e1 : oneHopPredictEdges ){
			if( oneHopScoreEdges.contains( e1 ) ){
				correctEdges  += 1.0;
			}
		}
		
		//testing 
		//System.out.println( "correct # edges = "+ correctEdges + "\t total # edges =" +totalPredictEdges);
	
		
		
		return correctEdges / totalPredictEdges ;
	}

	public static double getVertexPrecision( Network scorePath , Network predictPath , Collection<Long> A0 ){
		Collection<Long> a0 = GraphUtility.getA0(scorePath);
		Collection<Long> oneHopScoreVertices = new HashSet<Long>();
		Collection<Long> oneHopPredictVertices =new HashSet<Long>();
		
		for( Long v_in_a0 : a0 ){
			for( Long v2 :scorePath.getSuccessors(v_in_a0)){
				oneHopScoreVertices.add( v2 ) ;
			}
		}
		for( Long v_in_a0 : a0 ){
			Collection<Long> successors = predictPath.getSuccessors(v_in_a0) ;
			if( successors == null ) continue ;
 			for( Long v2 : predictPath.getSuccessors(v_in_a0) ){
				oneHopPredictVertices.add( v2); 
			}
		}
		double totalPredictVertices = (double) oneHopPredictVertices.size();
		double correctVertices = 0.0;
		if( totalPredictVertices == 0.0 ) return 0.0;
		for( Long v_in_predict : oneHopPredictVertices){
			if( oneHopScoreVertices.contains( v_in_predict) ){
				correctVertices+= 1.0 ;
			}
		}	

		//System.out.println( "correct # vertices = "+ correctVertices + "\t total # vertices =" + totalPredictVertices);
		
		// testing 2
		/*
		int correctVerticesWrongEdges = 0;
		for( Long v_in_a0 : a0 ){
			Collection<Long> successors = predictPath.getSuccessors(v_in_a0) ;
			if( successors == null ) continue ;
			for( Long v_in_predict : successors ){
				if( scorePath.findEdge( v_in_a0, v_in_predict ) == null && oneHopScoreVertices.contains( v_in_predict )){
					correctVerticesWrongEdges += 1;
				}
			}			
		}
		System.out.println( "# Correct Verices but Wrong Edges = " + correctVerticesWrongEdges);
		*/
		
		return correctVertices / totalPredictVertices; 
	
	}

}
