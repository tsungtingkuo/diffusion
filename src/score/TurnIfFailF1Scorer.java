package score;

import network.*;
import java.util.*;

public class TurnIfFailF1Scorer {


		public static double getEdgeF1( Network scorePath, Network predictPath ){
			double precision = TurnIfFailPrecisionScorer.getEdgePrecision( scorePath, predictPath );
			double recall = TurnIfFailRecallScorer.getEdgeRecall( scorePath, predictPath );
			if( precision+ recall > 0 ){
				return 2.0 * precision * recall /( precision+ recall );
			}else{
				return 0.0;
			}
		
		}

		public static double getVertexF1( Network scorePath , Network predictPath, Collection<Long> A0 ){
			double precision = TurnIfFailPrecisionScorer.getVertexPrecision( scorePath, predictPath , A0);
			double recall = TurnIfFailRecallScorer.getVertexRecall( scorePath, predictPath ,A0 );
			if( precision+ recall > 0 ){
				return 2.0 * precision * recall /( precision+ recall );
			}else{
				return 0.0;
			}
		}
}
