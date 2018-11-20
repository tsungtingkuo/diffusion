package main;
import java.util.*;

public class Main_ClassifierCV {

	static String aucResult = "";
	
	public static void main(String[] args) throws Exception {
		
		int folders = 4;
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Main
		aucResult = "";
		
		double auc = 0.0;
		double aucSD = 0.0;
		double aucCI = 0.0;
		
		double aucAll = 0.0;
		
		String[] aucString = new String[folders];
		String[] aucWiki = new String[folders];
		
		String feature = "|" + args[2] + "|";
		for(int i=0; i<args[1].length(); i++) {
			feature += args[1].charAt(i);
			feature += "|";
		}		

		for(int fold=0; fold<folders; fold++) {
			
			// Main
			String[] a = {args[0], args[1], args[2], Integer.toString(fold+1)};
			Main_ClassifierDataCV.main(a);
			Main_ClassifierTrainCV.main(a);
			Main_PredictorCV.main(a);
			
			// AUC
			auc = Main_PredictorCV.getAucAll();
			aucSD = Main_PredictorCV.getAucSDAll();
			aucCI = Main_PredictorCV.getAucCIAll();
			aucAll += auc;
			Formatter aucF = new Formatter();
			aucString[fold] = aucF.format("%.2f%%", auc*100).toString();
			Formatter aucSDF = new Formatter();
			String aucSDString = aucSDF.format("%.2f%%", aucSD*100).toString();
			Formatter aucCIF = new Formatter();
			String aucCIString = aucCIF.format("%.2f%%", aucCI*100).toString();
			aucWiki[fold] = feature + (fold+1) + "|" + aucString[fold] + "|" + aucSDString + "|" + aucCIString + "|";
			System.out.println("AUC: " + aucWiki[fold]);
		}
		
		// AUC
		System.out.println();
		System.out.println("AUC:");
		for(int fold=0; fold<folders; fold++) {
			System.out.println(aucWiki[fold]);
		}

		// AUC
		System.out.println();
		System.out.println("AUC:");
		aucAll = (double)aucAll / (double)folders;
		Formatter aucFormatter = new Formatter();
		aucFormatter.format("%.2f%%", aucAll*100);
		aucResult += feature;
		for(int fold=0; fold<folders; fold++) {
			aucResult += (aucString[fold] + "|");
		}
		aucResult += (aucFormatter + "|");
		System.out.println(aucResult);

		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed minutes = " + elapsedTime/60000);
	}

	/**
	 * @return the aucResult
	 */
	public static String getAucResult() {
		return aucResult;
	}
}
