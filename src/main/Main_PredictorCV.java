package main;
import java.io.*;
import java.util.*;

import classifier.*;
import predictor.*;
import utility.*;
import network.*;

@SuppressWarnings("unused")
public class Main_PredictorCV {
	
	public static double aucAll = 0.0;
	public static double aucSDAll = 0.0;
	public static double aucCIAll = 0.0;

	public static void main(String[] args) throws Exception {
		
		// Test
		boolean doTest = true;
		
		// Task
		int predictionTask = 1;
		
		// Classifier
		String classifier = args[0];

		// Instance
		int instance = ClassifierCV.INSTANCE_LINKTOPIC;
		
		// Feature
		int feature = Integer.parseInt(args[1]);
		
		// Parameter
		String w = "1";
		String c = args[2];

		// Fold
		int fold = Integer.parseInt(args[3]);
		
		// Name
		String sim = "lda";
		
		// Parameters
		int k = 12;
		int d = 1;
		int support = 5;
		int repeat = 50;
		double confidenceLevel = 0.95;
		String substring = "";
		
		// Result name
		String result = "";
		
		// File names
    	String trainListFileName = "list/cv" + fold + "_train.txt";
    	String testListFileName = "list/cv" + fold + "_test.txt";
    	
    	// Dataset
    	String dataset = "plurk";
    	
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Diffusion Network
    	System.out.print("Loading networks ... ");
    	DiffusionNetwork dntrain = DiffusionNetworkFactory.getDiffusionNetworkWithContent("dna/dn_cv" + fold + "_train_content.txt");
    	DiffusionNetwork dn = dntrain;
    	System.out.println("done.");
    	
		// Predictor and parameters
   	
    	ClassifierGuidedKNeighborsPredictorCV p = new ClassifierGuidedKNeighborsPredictorCV(dn, dntrain);
    	p.setK(k);    	
    	p.setDataset(dataset);
    	p.setClassifier(classifier);
    	p.setW(w);
    	p.setC(c);
    	p.setInstance(instance);
    	p.setFeature(feature);
    	p.setFold(fold);
    	p.setFunction(1);
    	p.setDoTest(doTest);
    	p.initializeSVM();
    	result += "_" + classifier + "_" + instance + "_" + feature + "_" + w + "_guided_" + k + "_neighbor";

    	// Prediction and evaluation
    	String resultFileName = "result/cv" + fold + "_result" + result + ".txt";
    	PrintWriter pw = new PrintWriter(resultFileName);
    	System.out.println();
		System.out.println("^Topic^ACC^T^F^AUC^Stdev^95%CI^");
		pw.println("^Topic^ACC^T^F^AUC^Stdev^95%CI^");
    	int[] testConcepts = Utility.loadIntegerArray(testListFileName);
		double aucSum = 0.0;
		double aucSDSum = 0.0;
		double aucCISum = 0.0;

		for(int testConcept : testConcepts) {

			String positive = "data/" + dataset + "_iii/cv" + fold + "/test/dn_" + testConcept + "_test.txt";
			DiffusionNetwork gdn = DiffusionNetworkFactory.getDiffusionNetwork(positive);
			
   			String negative = "data/" + dataset + "_iii/negative/cv" + fold + "/test/unweighted/dn_" + testConcept + "_test.txt";
   			DiffusionNetwork testdn = DiffusionNetworkFactory.combineDiffusionNetwork(positive, negative);
   			DiffusionNetwork pdn = p.predict(testConcept, testdn);
    		
			DiffusionNetworkScorer dns = new DiffusionNetworkScorer(gdn, pdn, testConcept, repeat, confidenceLevel);
			
			double auc = dns.getAuc();
			double aucSD = dns.getAucSD();
			double aucCI = dns.getAucCI();
						
			aucSum += auc;
			aucSDSum += aucSD;
			aucCISum += aucCI;
			
			System.out.printf("|" + testConcept + "|%.2f%%|%.2f%%|%.2f%%|\n", auc*100, aucSD*100, aucCI*100);
			pw.printf("|" + testConcept + "|%.2f%%|%.2f%%|%.2f%%|\n", auc*100, aucSD*100, aucCI*100);
    	}
		aucAll = (double)aucSum / (double)testConcepts.length;
		aucSDAll = (double)aucSDSum / (double)testConcepts.length;
		aucCIAll = (double)aucCISum / (double)testConcepts.length;
		
		System.out.printf("|%d|**%.2f%%**|%.2f%%|%.2f%%|\n", fold, aucAll*100, aucSDAll*100, aucCIAll*100);    
		pw.printf("|%d|**%.2f%%**|%.2f%%|%.2f%%|\n", fold, aucAll*100, aucSDAll*100, aucCIAll*100);

		pw.flush();
    	pw.close();
		
		// Print parameters
		Utility.printStringArray(args, args.length);
		
		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed minutes = " + elapsedTime/60000);		
	}

	/**
	 * @return the aucAll
	 */
	public static double getAucAll() {
		return aucAll;
	}

	/**
	 * @return the aucSDAll
	 */
	public static double getAucSDAll() {
		return aucSDAll;
	}

	/**
	 * @return the aucCIAll
	 */
	public static double getAucCIAll() {
		return aucCIAll;
	}
}
