import java.util.*;
import main.*;

public class PrepareData {

	public static void main(String[] args) throws Exception {
		
		// The input of this program is cv train/test lists
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		for(int f=0; f<4; f++) {
		
			String fold = Integer.toString(f+1);
			
			String[] train = {"train", fold}; 
			Main_DNCombineAllCV.main(train);
	
			String[] test = {"test", fold}; 
			Main_DNCombineAllCV.main(test);
			
			String[] sim = {"lda", fold}; 
			Main_DNCombineTrainWithContentAllSimilarityCV.main(sim);
			
			String[] pattern = {fold}; 
			Main_PatternCV.main(pattern);
			Main_PatternToDN.main(pattern);
		}
		
		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed seconds = " + elapsedTime/1000);
	}
}
