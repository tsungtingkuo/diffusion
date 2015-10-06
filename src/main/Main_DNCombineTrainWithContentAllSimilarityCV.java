package main;
import java.util.*;
import utility.*;
import network.*;
import similarity.*;

public class Main_DNCombineTrainWithContentAllSimilarityCV {

	public static void main(String[] args) throws Exception {
		
		// Name
		//String sim = "allone";
		//String sim = "random";
		//String sim = "jaccard";
		//String sim = "lda";
		//String sim = "lda_message_sum";
		//String sim = "lda_message_average";
		//String sim = "lda_mr";
		String sim = args[0];
				
		// Parameter
		int fold = Integer.parseInt(args[1]);

		// File names
		String topicListFileName = "list/all.txt";
		String trainListFileName = "list/cv" + fold + "_train.txt";
		String testListFileName = "list/cv" + fold + "_test.txt";
		String similarityFileName = "similarity/" + sim + ".txt";
		
		// Dataset
		String dataset = "plurk";
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Combine diffusion networks
		String trainPrefix = "data/plurk_iii/cv" + fold + "/train/dn_";
		String trainPostfix = "_train.txt";
		int[] topicList = Utility.loadIntegerArray(topicListFileName);
		int[] trainList = Utility.loadIntegerArray(trainListFileName);
		TopicSimilarity ts = new TopicSimilarity(dataset, "", topicListFileName, trainListFileName, testListFileName, similarityFileName);
		for(int i : topicList) {
			System.out.println("Combining topic " + i + " ... ");
			String trainFileName = "dna/" + sim + "/cv" + fold + "/dn_train_content_" + i + ".txt";
			DiffusionNetwork trainDN = DiffusionNetworkFactory.combineDNAllWithContentUsingSimilarity(trainPrefix, trainPostfix, trainList, i, ts);
			DiffusionNetworkFactory.saveDiffusionNetworkWithContent(trainDN, trainFileName);
			System.out.println("done.");
		}
		
		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed seconds = " + elapsedTime/1000);
	}
}
