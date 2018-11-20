package pattern;

import java.io.*;
import java.util.*;
import network.*;

public class IntraTopicTrain {

	CascadesManager cm = null;
	int support = 1;
	TreeMap<Integer, Vector<TreeMap<String, Integer>>> patterns = new TreeMap<Integer, Vector<TreeMap<String, Integer>>>();
	
	public IntraTopicTrain(TreeMap<Integer, Vector<TreeMap<String, Integer>>> patterns) {
		super();
		this.patterns = patterns;
	}
	
	public IntraTopicTrain(CascadesManager cm, int support) {
		super();
		this.cm = cm;
		this.support = support;
	}

	public int identifyTwoHopPatterns() {
    	System.out.print("Identifying two-hop patterns ");

    	int count = 0;
    	for(int concept : this.cm.getConcepts()) {
			
    		System.out.print(".");
			
			// Identify candidate patterns
    		Vector<TreeMap<String, Integer>> conceptResult = this.patterns.get(concept);
    		TreeMap<String, Integer> oneHopResult = conceptResult.get(0);
    		Vector<String> candidates = new Vector<String>();
    		Vector<Integer> frequencies = new Vector<Integer>();
			for(String s : oneHopResult.keySet()) {
				String[] t = s.split("\t");
				long lastUserId = Long.parseLong(t[t.length-1]);
				for(String ss : oneHopResult.keySet()) {
					String[] tt = ss.split("\t");
					long sourceUserId = Long.parseLong(tt[0]);
					long destUserId = Long.parseLong(tt[1]);
					if(lastUserId == sourceUserId) {
						int frequency = oneHopResult.get(s);
						if(frequency > oneHopResult.get(ss)) {
							frequency = oneHopResult.get(ss);
						}
						candidates.add(s + "\t" + destUserId);
						frequencies.add(frequency);
					}
				}
    		}

			// Compute count table
			TreeMap<String, Integer> table = computeCountTable(candidates, frequencies);
	    		
			// Identify frequent patterns
			TreeMap<String, Integer> result = identifyFrequentPatterns(table);
			count += result.size();
			
			// Store patterns
			conceptResult.add(result);		
			this.patterns.put(concept, conceptResult);
		}
		
    	System.out.println(" done, count = " + count);
		return count;
	}

	public int identifyOneHopPatterns() {
    	System.out.print("Identifying one-hop patterns ");

    	int count = 0;
    	for(int concept : this.cm.getConcepts()) {
			
    		System.out.print(".");
    		
			// Identify candidate patterns
			Vector<Cascade> cascades = this.cm.getCascades(concept);
    		Vector<String> candidates = new Vector<String>();
    		Vector<Integer> frequencies = new Vector<Integer>();
			for(Cascade c : cascades) {
				candidates.add(c.getUserIdString());
				frequencies.add(1);
			}
			
			// Compute count table
			TreeMap<String, Integer> table = computeCountTable(candidates, frequencies);
	    		
			// Identify frequent patterns
			TreeMap<String, Integer> result = identifyFrequentPatterns(table);
			count += result.size();
			
			// Store patterns
			Vector<TreeMap<String, Integer>> conceptResult = new Vector<TreeMap<String, Integer>>();
			conceptResult.add(result);		
			this.patterns.put(concept, conceptResult);
		}
		
    	System.out.println(" done, count = " + count);
    	return count;
	}
	
	public int savePatterns(String prefix, String postfix) throws Exception {
		
		// Find max hop patterns
		int maxHop = 0;
		for(int concept : this.patterns.keySet()) {
			int hop = this.patterns.get(concept).size();
			if(maxHop < hop) {
				maxHop = hop;
			}
		}
		
		// Save files
		for(int i=0; i<maxHop; i++) {
			PrintWriter pw = new PrintWriter(prefix + (i+1) + postfix);
			for(int concept : this.patterns.keySet()) {
				Vector<TreeMap<String, Integer>> conceptResult = this.patterns.get(concept);
				TreeMap<String, Integer> result = conceptResult.get(i);
				if(result != null) {
					for(String s : result.keySet()) {
						pw.println(concept + "," + s + "," + result.get(s));
					}
				}
			}
			pw.flush();
			pw.close();
		}
		
		return maxHop;
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
