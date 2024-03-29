package main;
import java.io.*;
import java.util.*;
import utility.*;

public class Main_PatternToDN {
	
	public static void main(String[] args) throws Exception {
		
		// Timer start
		long startTime = (new Date()).getTime();
		System.out.println("Timer start ... ");

		// Convert
		File dir = new File("pattern");
		String[] files = dir.list();
		for(String f : files) {
			String[] t = f.split("\\.");
			PrintWriter pw = new PrintWriter("patterndn/" + t[0] + "_dn." + t[1]);
			Vector<String> v = Utility.loadVector("pattern/" + f);
			for(String s : v) {
				String[] st = s.split(",");
				pw.println(st[0] + "\t" + st[1]);
			}
			pw.flush();
			pw.close();
		}    	

		// Timer stop
		long stopTime = (new Date()).getTime();
		long elapsedTime = stopTime - startTime;
		System.out.println("Timer stop, elapsed seconds = " + elapsedTime/1000);
	}
}
