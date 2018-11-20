package score;

import java.util.Collection;

import network.Network;

public class OneHopF1Scorer {
	public static double getEdgeF1( Network scorePath, Network predictPath ){
		double precision = OneHopPrecisionScorer.getEdgePrecision( scorePath, predictPath );
		double recall = OneHopRecallScorer.getEdgeRecall( scorePath, predictPath );
		if( precision+ recall > 0 ){
			return 2.0 * precision * recall /( precision+ recall );
		}else{
			return 0.0;
		}
	
	}

	public static double getVertexF1( Network scorePath , Network predictPath, Collection<Long> A0 ){
		double precision = OneHopPrecisionScorer.getVertexPrecision( scorePath, predictPath , A0);
		double recall = OneHopRecallScorer.getVertexRecall( scorePath, predictPath ,A0 );
		if( precision+ recall > 0 ){
			return 2.0 * precision * recall /( precision+ recall );
		}else{
			return 0.0;
		}
	}
}
