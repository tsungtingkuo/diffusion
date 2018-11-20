import java.util.*;

import main.Main_ClassifierCV;

public class LearningBased {

	public static void main(String[] args) throws Exception {

		// Parameter
		int cLevel = 1;
		double cInit = 0.0001;
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Main
		String[] aucResults = new String[cLevel];
		double order = 1.0;
		for(int i=0; i<cLevel; i++) {
			double c = cInit * order;
			order *= 10.0;			
			String[] a = {"liblinear", args[0], Double.toString(c)};
			Main_ClassifierCV.main(a);
			aucResults[i] = Main_ClassifierCV.getAucResult();
		}
		
		// AUC
		System.out.println();
		System.out.println("AUC:");
		for(int i=0; i<cLevel; i++) {
			System.out.println(aucResults[i]);
		}

		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed minutes = " + elapsedTime/60000);
	}
}
