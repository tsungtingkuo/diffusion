package pattern;

import java.io.*;
import java.util.*;
import network.*;

public class InterTopicTrain {

	CascadesManager cm = null;
	int support = 1;
	String substring = "";
	
	Vector<TreeMap<String, Integer>> patterns = new Vector<TreeMap<String, Integer>>();
	
	public InterTopicTrain(Vector<TreeMap<String, Integer>> patterns) {
		super();
		this.patterns = patterns;
	}
	
	public InterTopicTrain(CascadesManager cm, int support, String substring) {
		super();
		this.cm = cm;
		this.support = support;
		this.substring = substring;
	}

//	public int identifyTwoHopPatterns() {
//    	System.out.print("Identifying two-hop patterns ");
//
//    	int count = 0;
//
//    	// Identify candidate patterns
//		Vector<String> candidates = new Vector<String>();
//		Vector<Integer> frequencies = new Vector<Integer>();
//    	for(int i=0; i<this.cm.getConcepts().length; i++) {	
//    		System.out.print(".");
//    		TreeMap<String, Integer> oneHopResult = this.patterns.get(0);
//			TreeMap<String, Integer> topicCandidates = new TreeMap<String, Integer>();
//			for(String s : oneHopResult.keySet()) {
//				String[] t = s.split("\t");
//				long lastUserId = Long.parseLong(t[t.length-1]);
//				for(String ss : oneHopResult.keySet()) {
//					String[] tt = ss.split("\t");
//					long sourceUserId = Long.parseLong(tt[0]);
//					long destUserId = Long.parseLong(tt[1]);
//					if(lastUserId == sourceUserId) {
//						int frequency = oneHopResult.get(s);
//						if(frequency > oneHopResult.get(ss)) {
//							frequency = oneHopResult.get(ss);
//						}
//						topicCandidates.put(s + "\t" + destUserId, frequency);
//					}
//				}
//    		}
//			for(String s: topicCandidates.keySet()) {
//				candidates.add(s);
//				frequencies.add(topicCandidates.get(s));
//			}
//		}
//		
//		// Compute count table
//		TreeMap<String, Integer> table = computeCountTable(candidates, frequencies);
//    		
//		// Identify frequent patterns
//		TreeMap<String, Integer> result = identifyFrequentPatterns(table);
//		count += result.size();
//		
//		// Store patterns
//		this.patterns.add(result);		
//
//    	System.out.println(" done, count = " + count);
//		return count;
//	}

	public int identifyOneHopPatterns() {
    	System.out.print("Identifying one-hop patterns ");

    	int count = 0;

		// Identify candidate patterns
		Vector<String> candidates = new Vector<String>();
		Vector<Integer> frequencies = new Vector<Integer>();
    	for(int concept : this.cm.getConcepts()) {
    		System.out.print(".");
			Vector<Cascade> cascades = this.cm.getCascades(concept);
			TreeMap<String, Integer> topicCandidates = new TreeMap<String, Integer>();
			if(this.substring.equalsIgnoreCase("")) {
				for(Cascade c : cascades) {
					topicCandidates.put(c.getUserIdString(), 1);
				}
			}
			else {
				for(Cascade c : cascades) {
					if(c.getContent().contains(this.substring)) {
						topicCandidates.put(c.getUserIdString(), 1);
					}
				}
			}
			for(String s: topicCandidates.keySet()) {
				candidates.add(s);
				frequencies.add(topicCandidates.get(s));
			}
    	}
    	
		// Compute count table
		TreeMap<String, Integer> table = computeCountTable(candidates, frequencies);
	    		
		// Identify frequent patterns
		TreeMap<String, Integer> result = identifyFrequentPatterns(table);
		count += result.size();
			
		// Store patterns
		this.patterns.add(result);
		
    	System.out.println(" done, count = " + count);
    	return count;
	}
	
	public int savePatterns(String prefix, String postfix) throws Exception {
		for(int i=0; i<this.patterns.size(); i++) {
			PrintWriter pw = new PrintWriter(prefix + (i+1) + postfix);
			TreeMap<String, Integer> result = this.patterns.get(i);
			if(result != null) {
				for(String s : result.keySet()) {
					pw.println(s + "," + result.get(s));
				}
			}
			pw.flush();
			pw.close();
		}		
		return patterns.size();
	}
	
	public TreeMap<String, Integer> computeCountTable(Vector<String> candidates, Vector<Integer> frequencies) {
		TreeMap<String, Integer> table = new TreeMap<String, Integer>();
		for(int i=0; i<candidates.size(); i++) {
			String s= candidates.get(i);
			Integer f = frequencies.get(i);
			if(table.containsKey(s)) {
				table.put(s, table.get(s) + f);
			}
			else {
				table.put(s, f);
			}
		}
		return table;
	}
	
	public TreeMap<String, Integer> identifyFrequentPatterns(TreeMap<String, Integer> table) {
		TreeMap<String, Integer> result = new TreeMap<String, Integer>();  	
		for(String s : table.keySet()) {
			int count = table.get(s);
			if(count >= this.support) {
				result.put(s, count);
			}
		}
		return result;
	}
}
