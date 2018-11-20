package score ;

import java.util.*;
import utility.Utility;

public class SpearmanScorer{
	/**
	 * @param	test	prediction result
	 * @param	answer	ground truth
	 * */
	public static double getR( Map<Long,Double> test , Map<Long,Double> answer){
		double[] x= new double[ answer.size() ];
		double[] y= new double[ answer.size() ];
		int i = 0 ;
		for( Long answerKey : answer.keySet( )){
			Double answerValue = answer.get( answerKey);
			x[ i ] = answerValue ;
			Double testValue = test.get(answerKey );
			if( testValue == null){
		//		System.out.println("not found test: "+ answerKey );
				y[i] = 0.0;
			}else{
				y[i] = testValue ;
			}
		//	System.out.println( x[i] +"\t"+ y[i] );
			i++; 
		}
		double[] rank_x = Utility.scoreArrayToRankArrayDescending( x ) ;
		double[] rank_y = Utility.scoreArrayToRankArrayDescending( y );

//		for( i = 0 ; i < rank_x.length ; i++){
//			System.out.println( "x[" + i + "]=" + rank_x[i] + "\ty[" +i+ "]="+rank_y[i]);
//		}

		return Utility.computeCorrelationCoefficient( rank_x,  rank_y);
		//SpearmanCorrelation spearmancorr = new SpearmanCorrelation( new PairedData( x, y)  );
		//return spearmancorr.getR();
	} 
}
