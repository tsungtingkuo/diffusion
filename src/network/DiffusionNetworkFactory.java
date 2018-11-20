package network;

import java.io.*;
import java.util.TreeMap;

import similarity.*;

public class DiffusionNetworkFactory extends NetworkFactory {
	
	public static DiffusionNetwork getDiffusionNetwork(MessageManager mm, ResponseManager rm) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		for(Response r : rm.getResponses()) {
			Message m = mm.getMessage(r.getMessageId());
			if(m != null) {
				long sourceId = m.getUserId();
				long destinationId = r.getUserId();
//				if(n.getUser(sourceId)==null || n.getUser(destinationId)==null) {
//					break;
//				}
				Long relationId = dn.findEdge(sourceId, destinationId);
				if(relationId != null) {
					Relation relation = dn.getRelation(relationId);
					double weight = relation.getWeight();
					relation.setWeight(weight + 1.0);
				}
				else {
					Relation relation = RelationFactory.getRelation();
					if(dn.getUser(sourceId) == null) {
						dn.addUser(new User(sourceId));
					}
					if(dn.getUser(destinationId) == null) {
						dn.addUser(new User(destinationId));
					}
					dn.addEdge(relation.getId(), sourceId, destinationId);
					dn.addRelation(relation);
				}
			}
		}
		return dn;
	}
	
	public static int saveRelation(DiffusionNetwork dn, String relationFileName) throws Exception {
		PrintWriter pw = new PrintWriter(relationFileName);
		int count = 0;
		for(long edge : dn.getEdges()) {
			long sourceId = dn.getSource(edge);
			long destinationId = dn.getDest(edge);
			Relation r = dn.getRelation(edge);
			pw.println(sourceId + "\t" + destinationId + "\t" + r.getWeight());
			count++;
		}
		pw.flush();
		pw.close();
		return count;
	}
	
	public static int saveRelationWithContent(DiffusionNetwork dn, String relationFileName) throws Exception {
		PrintWriter pw = new PrintWriter(relationFileName);
		int count = 0;
		for(long edge : dn.getEdges()) {
			long sourceId = dn.getSource(edge);
			long destinationId = dn.getDest(edge);
			Relation r = dn.getRelation(edge);
			pw.println(sourceId + "\t" + destinationId + "\t" + r.getWeight() + "\t" + r.getLegnth() + "\t" + r.getHref() + "\t" + r.getImg());
			count++;
		}
		pw.flush();
		pw.close();
		return count;
	}
	
	public static int saveRelationUnweighted(DiffusionNetwork dn, String relationFileName) throws Exception {
		PrintWriter pw = new PrintWriter(relationFileName);
		int count = 0;
		for(long edge : dn.getEdges()) {
			long sourceId = dn.getSource(edge);
			long destinationId = dn.getDest(edge);
			pw.println(sourceId + "\t" + destinationId + "\t" + 1.0);
			count++;
		}
		pw.flush();
		pw.close();
		return count;
	}
	
	public static DiffusionNetwork loadRelation(DiffusionNetwork dn, String relationFileName) throws Exception {
		FileReader fre = new FileReader(relationFileName);
		LineNumberReader lnre = new LineNumberReader(fre);
		String se = null;
		while ((se=lnre.readLine()) != null) {
			String[] t = se.split("\t");
			long sourceId = Long.parseLong(t[0]);
			if(dn.getUser(sourceId) == null) {
				dn.addUser(new User(sourceId));
			}
			long destinationId = Long.parseLong(t[1]);
			if(dn.getUser(destinationId) == null) {
				dn.addUser(new User(destinationId));
			}
			Long edge = dn.findEdge(sourceId, destinationId);
			if(edge == null) {
				Relation r = RelationFactory.getRelation();
				r.setWeight(Double.parseDouble(t[2]));
				dn.addEdge(r.getId(), sourceId, destinationId);
				dn.addRelation(r);
			}
			else {
				Relation r = dn.getRelation(edge);
				r.setWeight(r.getWeight() + Double.parseDouble(t[2]));
			}
		}				
		lnre.close();
		fre.close();
		return dn;
	}

	public static DiffusionNetwork loadRelationWithContent(DiffusionNetwork dn, String relationFileName) throws Exception {
		return loadRelationWithContent(dn, relationFileName, 1.0);
	}
	
	public static DiffusionNetwork loadRelationWithContent(DiffusionNetwork dn, String relationFileName, double topicSimilarity) throws Exception {
		
		// Increase the effect of topic similarity
		//topicSimilarity *= topicSimilarity;
		
		FileReader fre = new FileReader(relationFileName);
		LineNumberReader lnre = new LineNumberReader(fre);
		String se = null;
		while ((se=lnre.readLine()) != null) {
			String[] t = se.split("\t");
			long sourceId = Long.parseLong(t[0]);
			if(dn.getUser(sourceId) == null) {
				dn.addUser(new User(sourceId));
			}
			long destinationId = Long.parseLong(t[1]);
			if(dn.getUser(destinationId) == null) {
				dn.addUser(new User(destinationId));
			}
			Long edge = dn.findEdge(sourceId, destinationId);
			if(edge == null) {
				Relation r = RelationFactory.getRelation();
				r.setWeight(Double.parseDouble(t[2])*topicSimilarity);
//				double w = Double.parseDouble(t[2]);
//				if(w > 0.0) {
//					r.setWeight(topicSimilarity);
//				}
				r.setLegnth(Double.parseDouble(t[3])*topicSimilarity);
				r.setHref(Double.parseDouble(t[4])*topicSimilarity);
				r.setImg(Double.parseDouble(t[5])*topicSimilarity);
				dn.addEdge(r.getId(), sourceId, destinationId);
				dn.addRelation(r);
			}
			else {
				Relation r = dn.getRelation(edge);
				r.setWeight(r.getWeight() + Double.parseDouble(t[2])*topicSimilarity);
//				double w = Double.parseDouble(t[2]);
//				if(w > 0.0) {
//					r.setWeight(r.getWeight() + topicSimilarity);
//				}
				r.setLegnth(r.getLegnth() + Double.parseDouble(t[3])*topicSimilarity);
				r.setHref(r.getHref() + Double.parseDouble(t[4])*topicSimilarity);
				r.setImg(r.getImg() + Double.parseDouble(t[5])*topicSimilarity);
			}
		}				
		lnre.close();
		fre.close();
		return dn;
	}
	
	public static int saveDiffusionNetworkUnweighted(DiffusionNetwork dn, String fileName) throws Exception {
		return saveRelationUnweighted(dn, fileName);
	}

	public static int saveDiffusionNetwork(DiffusionNetwork dn, String fileName) throws Exception {
		return saveRelation(dn, fileName);
	}

	public static int saveDiffusionNetworkWithContent(DiffusionNetwork dn, String fileName) throws Exception {
		return saveRelationWithContent(dn, fileName);
	}

	public static DiffusionNetwork getDiffusionNetworkWithContent(String fileName) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		dn = loadRelationWithContent(dn, fileName);
		return dn;
	}
	
	public static DiffusionNetwork getDiffusionNetwork(String fileName) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		dn = loadRelation(dn, fileName);
		return dn;
	}
	
	public static DiffusionNetwork combineDiffusionNetwork(String fileName1, String fileName2) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		dn = loadRelation(dn, fileName1);
		dn = loadRelation(dn, fileName2);
		return dn;
	}
	
	public static DiffusionNetwork combineDiffusionNetworkAll(String prefix, String postfix, int startInclusive, int stopInclusive) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		for(int i=startInclusive; i<=stopInclusive; i++) {
			System.out.print("Combining concept " + i + " ... ");
			dn = loadRelation(dn, prefix + i + postfix);
			System.out.println("done.");
		}
		return dn;
	}

	public static DiffusionNetwork combineDiffusionNetworkAll(String prefix, String postfix, int[] list) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		for(int i : list) {
			System.out.print("Combining concept " + i + " ... ");
			dn = loadRelation(dn, prefix + i + postfix);
			System.out.println("done.");
		}
		return dn;
	}

	public static DiffusionNetwork combineDiffusionNetworkAllWithContentCategory(String prefix, String postfix, int[] list, TreeMap<Integer, Integer> topicToCategory, int category) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		for(int i : list) {
			if(topicToCategory.get(i) == category) {
				System.out.print("Combining category " + category + ", concept " + i + " ... ");
				dn = loadRelationWithContent(dn, prefix + i + postfix);
				System.out.println("done.");
			}
		}
		return dn;
	}

	public static DiffusionNetwork combineDiffusionNetworkAllWithContent(String prefix, String postfix, int[] list) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();
		for(int i : list) {
			System.out.print("Combining concept " + i + " ... ");
			dn = loadRelationWithContent(dn, prefix + i + postfix);
			System.out.println("done.");
		}
		return dn;
	}

	public static DiffusionNetwork combineDNAllWithContentUsingSimilarityCategory(String prefix, String postfix, int[] list, int topic, TopicSimilarity ts, TreeMap<Integer, Integer> topicToCategory, int category) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();		
		for(int i : list) {
			if(topicToCategory.get(i) == category) {
				double similarity = ts.getNormalizedSimilarity(topic, i);
				System.out.print("Combining category " + category + ", concept " + i + ", similarity = " + similarity + " ... ");
				dn = loadRelationWithContent(dn, prefix + i + postfix, similarity);
				System.out.println("done.");
			}
		}
		return dn;
	}

	public static DiffusionNetwork combineDNAllWithContentUsingSimilarity(String prefix, String postfix, int[] list, int topic, TopicSimilarity ts) throws Exception {
		DiffusionNetwork dn = new DiffusionNetwork();		
		for(int i : list) {
			//double similarity = ts.getSimilarity(topic, i);
			double similarity = ts.getNormalizedSimilarity(topic, i);
			System.out.print("Combining concept " + i + ", similarity = " + similarity + " ... ");
			dn = loadRelationWithContent(dn, prefix + i + postfix, similarity);
			System.out.println("done.");
		}
		return dn;
	}
}
