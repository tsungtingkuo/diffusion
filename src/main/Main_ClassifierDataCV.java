package main;
import java.util.*;

import network.DiffusionNetwork;
import network.DiffusionNetworkFactory;
import utility.*;
import classifier.*;

public class Main_ClassifierDataCV {
	
	public static void main(String[] args) throws Exception {
		
		// Task
		int predictionTask = 1;
		//int predictionTask = Integer.parseInt(args[0]);		
		
		// Classifier
		//String classifier = "nb";
		//String classifier = "liblinear";
		//String classifier = "libsvm";
		String classifier = args[0];
		
		// Instance
		//int instance = ClassifierCV.INSTANCE_LINK;
		int instance = ClassifierCV.INSTANCE_LINKTOPIC;
		//int instance = Integer.parseInt(args[1]);
		
		// Feature
		//int feature = Integer.parseInt(args[2]);
		int feature = Integer.parseInt(args[1]);

		// Parameter
		String w = "1";
		//String c = "1";
		String c = args[2];

		// Fold
		int fold = Integer.parseInt(args[3]);
		
		// Dataset
    	String dataset = "plurk";
    	
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Initialize
		ClassifierCV my = new ClassifierCV(dataset, classifier, instance, feature, w, c, fold);
		my.initializeData();

		// Train
		my.prepareDataTrain(predictionTask);
		my.resetTotal();
		
		// Test
		int totalPositive = 0;
		int totalNegative = 0;
		String testListFileName = "list/cv" + fold + "_test.txt";
    	int[] testConcepts = Utility.loadIntegerArray(testListFileName);
    	System.out.print("Generating testing data ");
		for(int testConcept : testConcepts) {
			String positive = "data/" + dataset + "_iii/cv" + fold + "/test/dn_" + testConcept + "_test.txt";
			String negative = "data/" + dataset + "_iii/negative/cv" + fold + "/test/unweighted/dn_" + testConcept + "_test.txt";
   			DiffusionNetwork testdn = DiffusionNetworkFactory.combineDiffusionNetwork(positive, negative);
   			my.prepareDataTest(testConcept, testdn);
 			totalPositive += my.getTotalPositive();
 			totalNegative += my.getTotalNegative();
 	    	System.out.print(".");
		}
    	System.out.println(" done.");
		System.out.println("Testing data: possitive = " + totalPositive + ", total negative = " + totalNegative);
		
		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed minutes = " + elapsedTime/60000);
	}
}
