package similarity;

import java.util.*;

import utility.*;

public class TopicSimilarity {

	final public static int UNKNOWN = -1;
	final public static int ALLONE = 0;
	final public static int RANDOM = 1;
	final public static int JACCARD = 2;
	final public static int LDA = 3;
	final public static int CATEGORY = 4;
	
	int type = UNKNOWN;
	String sim = null;
	String dataset = null;
	String valid = null;
	int[] topicList = null;
	int[] trainList = null;
	int[] testList = null;
	double[][] similarities = null;
		
	public TopicSimilarity(String dataset, String valid, String topicListFileName, String trainListFileName, String testListFileName, String similarityFileName) throws Exception {
		this(UNKNOWN, dataset, valid, topicListFileName, trainListFileName, testListFileName, "");
		this.similarities = Utility.loadDouble2DArray(similarityFileName);
	}
	
	public TopicSimilarity(int type, String dataset, String valid, String topicListFileName, String trainListFileName, String testListFileName, String sim) throws Exception {
		super();
		this.sim = sim;
		this.type = type;
		this.dataset = dataset;
		this.valid = valid;
		this.topicList = Utility.loadIntegerArray(topicListFileName);
		this.trainList = Utility.loadIntegerArray(trainListFileName);
		this.testList = Utility.loadIntegerArray(testListFileName);
	}

	public void computeSimilarities() throws Exception {
		this.similarities = new double[this.topicList.length][this.topicList.length];
		switch(this.type) {
		case ALLONE:
			this.computeAllOneSimilarities();
			break;
		case RANDOM:
			this.computeRandomSimilarities();
			break;
		case JACCARD:
			this.computeJaccardSimilarities();
			break;
		case LDA:
			this.computeLDASimilarities();
			break;
		case CATEGORY:
			this.computeCategorySimilarities();
			break;
		}
	}
	
	public void computeCategorySimilarities() throws Exception {
    	TreeMap<Integer, Integer> topicToCategory = Utility.loadIntegerToIntegerTreeMap("list/all_category.txt");
		for(int i=0; i<this.topicList.length; i++) {
			for(int j=0; j<this.topicList.length; j++) {
				if(topicToCategory.get(topicList[i]) == topicToCategory.get(topicList[j])) {
					this.similarities[i][j] = 1.0;
				}
				else {
					this.similarities[i][j] = 0.0;
				}
			}			
		}
	}

	public void computeLDASimilarities() throws Exception {
		double[][] lda = Utility.loadDouble2DArray("topic/" + this.sim + ".txt");
		for(int i=0; i<this.topicList.length; i++) {
			for(int j=0; j<this.topicList.length; j++) {
				this.similarities[i][j] = Utility.computeCosine(lda[i], lda[j]);
				//for(int k=0; k<lda[0].length; k++) {
				//	this.similarities[i][j] += lda[i][k]*lda[j][k];
				//}
			}			
		}
	}
	
	public void computeAllOneSimilarities() throws Exception {
		for(int i=0; i<this.topicList.length; i++) {
			for(int j=0; j<this.topicList.length; j++) {
				this.similarities[i][j] = 1.0;
			}			
		}
	}
	
	public void computeRandomSimilarities() throws Exception {
		Random r = new Random();
		for(int i=0; i<this.topicList.length; i++) {
			for(int j=0; j<this.topicList.length; j++) {
				this.similarities[i][j] = r.nextDouble();
			}			
		}
	}
	
	public void computeJaccardSimilarities() throws Exception {
		for(int i=0; i<this.topicList.length; i++) {
			int ti = this.topicList[i];
			long[] ai = null;
			if(Utility.firstIndexOfIntegerArray(trainList, ti) != -1) {
				ai = Utility.loadLongArray("data/" + this.dataset + "_iii/" + this.valid + "train/aggresive_" + ti + "_train.txt");
			}
			else {
				ai = Utility.loadLongArray("data/" + this.dataset + "_iii/" + this.valid + "test/aggresive_" + ti + "_test.txt");
			}
			for(int j=0; j<this.topicList.length; j++) {
				int tj = this.topicList[j];
				long[] aj = null;
				if(Utility.firstIndexOfIntegerArray(trainList, tj) != -1) {
					aj = Utility.loadLongArray("data/" + this.dataset + "_iii/" + this.valid + "train/aggresive_" + tj + "_train.txt");
				}
				else {
					aj = Utility.loadLongArray("data/" + this.dataset + "_iii/" + this.valid + "test/aggresive_" + tj + "_test.txt");
				}
				this.similarities[i][j] = Utility.computeJaccard(ai, aj);
			}			
		}
	}
	
	public void saveSimilarities(String fileName) throws Exception {
		Utility.saveDouble2DArray(fileName, this.similarities);
	}
	
	public void computeAndSaveSimilarities(String fileName) throws Exception {
		this.computeSimilarities();
		this.saveSimilarities(fileName);
	}
	
	public double getSimilarity(int topic1, int topic2) {
		int index1 = Utility.firstIndexOfIntegerArray(this.topicList, topic1);
		int index2 = Utility.firstIndexOfIntegerArray(this.topicList, topic2);
		return this.similarities[index1][index2];
	}
	
	public double getNormalizedSimilarity(int topic1, int topic2) {
		int index1 = Utility.firstIndexOfIntegerArray(this.topicList, topic1);
		int index2 = Utility.firstIndexOfIntegerArray(this.topicList, topic2);
		double[] normalized = Utility.getNormalizedSumScoreArray(this.similarities[index1]);
		return normalized[index2];
	}

	/**
	 * @return the similarities
	 */
	public double[][] getSimilarities() {
		return similarities;
	}
}
