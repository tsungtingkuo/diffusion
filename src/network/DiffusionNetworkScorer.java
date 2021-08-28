package network;

//import java.io.*;
//import java.util.*;
import evaluation.*;
import utility.*;

public class DiffusionNetworkScorer {
	
//	int tp = 0;
//	int grounds = 0;
//	int predictions = 0;
//	double precision = 0.0;
//	double recall = 0.0;
//	double f1 = 0.0;
	double auc = 0.0;
	double aucSD = 0.0;
	double aucCI = 0.0;
//	double spearman = 0.0;
//	double nonZeroSpearman = 0.0;
	int t = 0;
	int f = 0;
	double accuracy = 0.0;
	
	public DiffusionNetworkScorer(DiffusionNetwork ground, DiffusionNetwork prediction, int concept, int repeat, double confidenceLevel) throws Exception {

		double[] scores = new double[prediction.getEdgeCount()];
//		double[] diffusions = new double[prediction.getEdgeCount()];
		int[] answers = new int[prediction.getEdgeCount()];
//		Vector<Double> nonZeroScores = new Vector<Double>(); 
//		Vector<Double> nonZeroDiffusions = new Vector<Double>(); 
		int i = 0;
		for(long e : prediction.getEdges()) {
			long source = prediction.getSource(e);
			long dest = prediction.getDest(e);
			if(ground.findEdge(source, dest) != null) {
				answers[i] = 1;
//				diffusions[i] = ground.getWeight(source, dest);
//				nonZeroScores.add(prediction.getRelation(e).getScore());
//				nonZeroDiffusions.add(diffusions[i]);
			}
			else {
				answers[i] = 0;
//				diffusions[i] = 0.0;
			}
			scores[i] = prediction.getRelation(e).getScore();
			i++;
		}

//		int[] binaries = new int[scores.length];
//		for(int j=0; j<scores.length; j++) {
//			if(scores[j] >= 0.5) {
//				binaries[j] = 1;
//			}
//			else {
//				binaries[j] = 0;
//			}
//		}
		int[] binaries = Utility.scoreArrayToBinaryArrayAscending(scores);
		this.t = Utility.computeTrue(binaries, answers);
		this.f = Utility.computeFalse(binaries, answers);
		this.accuracy = (double)this.t/(double)(this.t+this.f);

//		PrintWriter tppw = new PrintWriter("confusion/tp_" + concept + ".txt");
//		PrintWriter fnpw = new PrintWriter("confusion/fn_" + concept + ".txt");
//		for(long e : ground.getEdges()) {
//			long source = ground.getSource(e);
//			long dest = ground.getDest(e);
//			Long p = prediction.findEdge(source, dest);
//			if(p != null) {
//				if(prediction.getRelation(p).isPredicted()) {
//					tppw.println(source + "\t" + dest);
//					this.tp++;
//				}
//			}
//			else {
//				fnpw.println(source + "\t" + dest);
//			}
//		}
//		tppw.flush();
//		tppw.close();
//		fnpw.flush();
//		fnpw.close();

//		PrintWriter fppw = new PrintWriter("confusion/fp_" + concept + ".txt");
//		for(long e : prediction.getEdges()) {
//			if(prediction.getRelation(e).isPredicted()) {
//				long source = prediction.getSource(e);
//				long dest = prediction.getDest(e);
//				if(ground.findEdge(source, dest) == null) {
//					fppw.println(source + "\t" + dest);
//				}
//				this.predictions++;
//			}
//		}
//		fppw.flush();
//		fppw.close();

//		this.grounds = ground.getEdgeCount();
//		this.precision = (double)tp / (double)this.predictions;
//		this.recall = (double)tp / (double)this.grounds;
//		this.f1 = 2 * this.precision * this.recall / (this.precision + this.recall);
		
		double[] aucs = AUCCalculator.calculate(scores, answers, repeat);
		this.auc = Utility.computeMean(aucs);
		this.aucSD = Utility.computeStandardDeviation(aucs);
		this.aucCI = Utility.computeConfidenceInterval(this.aucSD, confidenceLevel, repeat);
		
//		double[] rankScores = Utility.scoreArrayToRankArrayAverageAscending(scores);
//		double[] rankDiffusions = Utility.scoreArrayToRankArrayAverageAscending(diffusions);
//		this.spearman = Math.abs(Utility.computeCorrelationCoefficient(rankScores, rankDiffusions));
		
//		double[] rankNonZeroScores = Utility.scoreArrayToRankArrayAverageAscending(Utility.doubleVectorToDoubleArray(nonZeroScores));
//		double[] rankNonZeroDiffusions = Utility.scoreArrayToRankArrayAverageAscending(Utility.doubleVectorToDoubleArray(nonZeroDiffusions));
//		this.nonZeroSpearman = Math.abs(Utility.computeCorrelationCoefficient(rankNonZeroScores, rankNonZeroDiffusions));		
	}

	/**
	 * @return the accuracy
	 */
	public double getAccuracy() {
		return accuracy;
	}

	/**
	 * @return the t
	 */
	public int getT() {
		return t;
	}

	/**
	 * @return the f
	 */
	public int getF() {
		return f;
	}

//	/**
//	 * @return the precision
//	 */
//	public double getPrecision() {
//		return precision;
//	}
//
//	/**
//	 * @return the recall
//	 */
//	public double getRecall() {
//		return recall;
//	}
//
//	/**
//	 * @return the f1
//	 */
//	public double getF1() {
//		return f1;
//	}
//
//	/**
//	 * @return the tp
//	 */
//	public int getTp() {
//		return tp;
//	}
//
//	/**
//	 * @return the grounds
//	 */
//	public int getGrounds() {
//		return grounds;
//	}
//
//	/**
//	 * @return the predictions
//	 */
//	public int getPredictions() {
//		return predictions;
//	}

	/**
	 * @return the auc
	 */
	public double getAuc() {
		return auc;
	}

	/**
	 * @return the aucSD
	 */
	public double getAucSD() {
		return aucSD;
	}

	/**
	 * @return the aucCI
	 */
	public double getAucCI() {
		return aucCI;
	}

//	/**
//	 * @return the spearman
//	 */
//	public double getSpearman() {
//		return spearman;
//	}

//	/**
//	 * @return the nonZeroSpearman
//	 */
//	public double getNonZeroSpearman() {
//		return nonZeroSpearman;
//	}
}
