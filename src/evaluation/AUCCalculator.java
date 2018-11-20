package evaluation;

import java.io.*;
import java.util.*;
import utility.*;

public class AUCCalculator {

	private static final Random r = new Random();

	public static void main(String[] args) throws Exception {
		//double[] scores = {0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505};
		//int[] answers = {1, 1, 0, 1, 1, 1, 0, 0, 1, 0};
		
		//double[] scores = {0.9, 0.8, 0.7, 0.6, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505};
		//int[] answers = {1, 1, 0, 1, 1, 1, 0, 0, 1, 0};

		//double[] scores = {0.8, 0.6, 0.7, 0.9, 0.55, 0.54, 0.53, 0.52, 0.51, 0.505};
		//int[] answers = {1, 1, 0, 1, 1, 1, 0, 0, 1, 0};

		double[] scores = {1, 0, 0, 0, 0, 0, 0, 0, 1};
		int[] answers = {1, 0, 0, 0, 0, 0, 0, 0, 0};

		System.out.println(AUCCalculator.calculate(scores, answers));
	}

	public static double[] calculate(double[] scores, int[] answers, int repeat) throws Exception {
		double[] aucs = new double[repeat];
		for(int i=0; i<repeat; i++) {
			aucs[i] = calculate(scores, answers);
		}
		return aucs;
	}

	public static double calculate(double[] scores, int[] answers) throws Exception {
		
		// Deal with tie
		scores = Utility.scoreArrayToRankArrayRandomAscending(scores);
		scores = Utility.getNormalizedScoreArray(scores);

		// Save to file
		long time = Calendar.getInstance().getTimeInMillis();
		String unique = time + "." + r.nextLong();
		String dir = "temp";
		String aucName = dir + "/auc" + unique + ".txt";
		String printName = dir + "/print" + unique + ".txt";
		File f = new File(dir);
		f.mkdir();
		PrintWriter pw = new PrintWriter(aucName);
		for(int i=0; i<scores.length; i++) {
			pw.println(scores[i] + " " + answers[i]);
		}
		pw.flush();
		pw.close();
		String[] args = new String[2];
		args[0] = aucName;
		args[1] = "list";
		
		// Redirect System.out
		PrintStream so = System.out;
		FileOutputStream fos = new FileOutputStream(printName);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		
		// Calculate
		auc.AUCCalculator.main(args);
		
		// Reset System.out
		System.setOut(so);
		ps.flush();
		ps.close();
		fos.flush();
		fos.close();

		// Get AUC
		FileReader fr = new FileReader(printName);
		LineNumberReader lnr = new LineNumberReader(fr);
		String s = null;
		String ls = null;
		while ((s=lnr.readLine()) != null) {
			ls = s;
		}
		lnr.close();
		fr.close();
		String[] t = ls.split(" ");
		double auc = Double.parseDouble(t[t.length-1]);
		
		if(auc < 0.5) {
			auc = 1.0 - auc;
		}
		
		// Remove files
		File f1 = new File(aucName);
		f1.delete();
		File f2 = new File(aucName + ".pr");
		f2.delete();
		File f3 = new File(aucName + ".roc");
		f3.delete();
		File f4 = new File(aucName + ".spr");
		f4.delete();
		File f5 = new File(printName);
		f5.delete();

		return auc;
	}
}
