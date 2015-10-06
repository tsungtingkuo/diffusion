package main;
import java.util.*;
import utility.*;
import network.*;

public class Main_SortDataCV {

	public static void main(String[] args) throws Exception {
				
		// Parameter
		String type = args[0];
		int fold = Integer.parseInt(args[1]);

		// Dataset
		String dataset = "plurk";

		// Encoding
		String encoding = "UTF-8";
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// File names
    	String listFileName = "list/cv" + fold + "_" + type + ".txt";
    	
		// Diffusion Network
    	DiffusionNetwork dntrain = null;
    	if(type.equalsIgnoreCase("test")) {
	    	System.out.print("Loading networks ... ");
	    	dntrain = DiffusionNetworkFactory.getDiffusionNetwork("dna/dn_cv" + fold + "_train.txt");
	    	System.out.println("done.");
    	}
    	
    	// Sort data (and then filter!)
    	int[] concepts = Utility.loadIntegerArray(listFileName);
    	CascadesManager cm = new CascadesManager(encoding);
    	for(int concept : concepts) {
    		System.out.print("Sorting fold " + fold + ", concept " + concept + " ... ");
    		cm.loadCascades(concept, "data/" + dataset + "_iii/cv" + fold + "/" + type + "/response_" + concept + "_" + type + ".txt");
    		cm.sortCascades(concept);
        	if(type.equalsIgnoreCase("test")) {
        		cm.filterCascades(concept, dntrain);
        	}
    		cm.saveCascades(concept, "data/" + dataset + "_iii/cv" + fold + "/" + type + "/sorted_" + concept + "_" + type + ".txt");
    		System.out.println("done.");
    	}

		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed seconds = " + elapsedTime/1000);
	}
}
