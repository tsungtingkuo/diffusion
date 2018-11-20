package score ;

import java.util.*;
import network.* ;

public class PathF1Scorer{

	public static double getEdgeF1( Network scorePath, Network predictPath ){
		double precision = PathPrecisionScorer.getEdgePrecision( scorePath, predictPath );
		double recall = PathRecallScorer.getEdgeRecall( scorePath, predictPath );
		if( precision+ recall > 0 ){
			return 2.0 * precision * recall /( precision+ recall );
		}else{
			return 0.0;
		}
	
	}

	public static double getVertexF1( Network scorePath , Network predictPath, Collection<Long> A0 ){
		double precision = PathPrecisionScorer.getVertexPrecision( scorePath, predictPath , A0);
		double recall = PathRecallScorer.getVertexRecall( scorePath, predictPath ,A0 );
		if( precision+ recall > 0 ){
			return 2.0 * precision * recall /( precision+ recall );
		}else{
			return 0.0;
		}
	}

}
