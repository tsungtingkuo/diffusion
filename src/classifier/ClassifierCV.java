package classifier;

import java.io.*;
import java.util.*;
import network.*;
import liblinear.*;
import utility.*;

public class ClassifierCV {

	final static public int INSTANCE_UNKNOWN = -1;
	final static public int INSTANCE_LINK = 0;
	final static public int INSTANCE_LINKTOPIC = 1;

	String dataset = "";
	String classifier = "";
	String w = "1";
	String c = "1";
	int instance = INSTANCE_UNKNOWN;
	int feature = 0;
	int fold = 0;

	int positive = 0;
	int negative = 0;
	int totalPositive = 0;
	int totalNegative = 0;
	
	DiffusionNetwork dn = null;

	DiffusionNetwork dnTrain = null;
	DiffusionNetwork dnPattern = null;

	DiffusionNetwork dnTest = null;
	DiffusionNetwork dnTestSimilarityLDA = null;
	
	DiffusionNetwork dnNegative = null;

	double[] testSimilarityLDA = null;

	double[] testHiddenLDA = null;

	TreeMap<Long, double[]> nodeHiddenLDA = null;
	TreeMap<Long, double[]> nodeHiddenLDA2 = null;

	public ClassifierCV(String dataset, String classifier, int instance, int feature, String w, String c, int fold) {
		super();
		this.dataset = dataset;
		this.classifier = classifier;
		this.instance = instance;
		this.feature = feature;
		this.w = w;
		this.c = c;
		this.fold = fold;
	}

	public void getData(PrintWriter pw, long source, long dest) throws Exception {

		Vector<Double> features = new Vector<Double>();

		// TG (7)
		if((this.feature/1000000)%10 == 1) {
			for(int i=0; i<this.testHiddenLDA.length; i++) {
				features.add(this.testHiddenLDA[i]);
			}
		}

		// TS (1)
		if((this.feature/100000)%10 == 1) {
			features.add(this.dnTestSimilarityLDA.getOutWeightPlusOne(source, dest));	// Useful
		}

		// UG (14)
		if((this.feature/10000)%10 == 1) {
			double[] sourceNodes2 = this.nodeHiddenLDA2.get(source);
			if(sourceNodes2 != null) {
				for(int i=0; i<sourceNodes2.length; i++) {
					features.add(sourceNodes2[i]);
				}
			}
			double[] destNodes2 = this.nodeHiddenLDA2.get(dest);
			if(destNodes2 != null) {
				for(int i=0; i<destNodes2.length; i++) {
					features.add(destNodes2[i]);
				}
			}
		}

		// UPLC (14)
		if((this.feature/1000)%10 == 1) {
		
			double[] sourceNodes = this.nodeHiddenLDA.get(source);
			if(sourceNodes != null) {
				for(int i=0; i<sourceNodes.length; i++) {
					features.add(sourceNodes[i]);
				}
			}
			double[] destNodes = this.nodeHiddenLDA.get(dest);
			if(destNodes != null) {
				for(int i=0; i<destNodes.length; i++) {
					features.add(destNodes[i]);
				}
			}
		}

		// ID (2)
		if((this.feature/100)%10 == 1) {
			features.add(this.dn.inDegreePlusOne(source));
			features.add(this.dn.inDegreePlusOne(dest));
		}
			
		// OD (2)
		if((this.feature/10)%10 == 1) {
			features.add(this.dn.outDegreePlusOne(source));
			features.add(this.dn.outDegreePlusOne(dest));
		}

		// NDT (1)
		if((this.feature/1)%10 == 1) {
			features.add(this.dnPattern.getOutWeightPlusOne(source, dest));	// Useful
		}

		// Label
		String label = "0";
		if(this.dnTest != null) {
			Long edge = this.dnTest.findEdge(source, dest);
			if(edge != null) {
				label = "1";
			}
		}
		
		if(label.equalsIgnoreCase("0")) {
			this.negative++;
			this.totalNegative++;
		}
		else {
			this.positive++;
			this.totalPositive++;
		}
		
		// Print
		this.getData(pw, label, features);
	}

	public void initializeData() throws Exception {	
    	this.dnTrain = DiffusionNetworkFactory.getDiffusionNetworkWithContent("dna/dn_cv" + this.fold + "_train_content.txt");
    	this.dn = dnTrain;    	
    	this.dnPattern = DiffusionNetworkFactory.getDiffusionNetwork("patterndn/cv" + this.fold + "_train_1_1_dn.txt");

    	Vector<Long> nNodeLDA = Utility.loadLongVector("topic/n_node_cv" + this.fold + ".txt");
    	double[][] nodeLDA = Utility.loadDouble2DArray("topic/lda_node_cv" + this.fold + ".txt");
    	this.nodeHiddenLDA = new TreeMap<Long, double[]>();
	    for(int i=0; i<nNodeLDA.size(); i++) {
	    	this.nodeHiddenLDA.put(nNodeLDA.get(i), nodeLDA[i]);
	    }

	    Vector<Long> nNodeLDA2 = Utility.loadLongVector("topic/n_mr_node_cv" + this.fold + ".txt");
	    double[][] nodeLDA2 = Utility.loadDouble2DArray("topic/lda_mr_node_cv" + this.fold + ".txt");
	    this.nodeHiddenLDA2 = new TreeMap<Long, double[]>();
	    for(int i=0; i<nNodeLDA2.size(); i++) {
	    	this.nodeHiddenLDA2.put(nNodeLDA2.get(i), nodeLDA2[i]);
	    }
	}
	
	public void getDataTrain(int predictionTask) throws Exception {	
		PrintWriter pw = new PrintWriter("classifier/" + dataset + "_cv" + fold + "_" + instance + "_" + feature + ".libsvm");
		if(this.instance == INSTANCE_LINKTOPIC) {
	    	int[] trainList = Utility.loadIntegerArray("list/cv" + this.fold + "_train.txt");
	    	int[] allList = Utility.loadIntegerArray("list/all.txt");
	    	double[][] hiddenLDA = Utility.loadDouble2DArray("topic/lda.txt");
	    	double[][] similarityLDA = Utility.loadDouble2DArray("similarity/lda.txt");	    		    	
	    	System.out.print("Generating training data ");
			for(int c=0; c<trainList.length; c++) {
				int concept = trainList[c];
				int index = Utility.firstIndexOfIntegerArray(allList, concept);
				this.dnTest = DiffusionNetworkFactory.getDiffusionNetwork("data/plurk_iii/cv" + fold + "/train/dn_" + concept + "_train.txt");
				this.dnTestSimilarityLDA = DiffusionNetworkFactory.getDiffusionNetworkWithContent("dna/lda/cv" + fold + "/dn_train_content_" + concept + ".txt");
				this.dnNegative = DiffusionNetworkFactory.getDiffusionNetwork("data/plurk_iii/negative/train_unweighted/dn_" + concept + "_test.txt");
				this.testSimilarityLDA = similarityLDA[index];
				this.testHiddenLDA = hiddenLDA[index];
				
				this.positive = 0;
				this.negative = 0;
				this.getDataTrain(pw, predictionTask);
		    	System.out.print(".");
			}
	    	System.out.println(" done.");
			System.out.println("Training data: possitive = " + this.totalPositive + ", total negative = " + this.totalNegative);
		}
		else {
			this.getDataTrain(pw, predictionTask);			
		}
		pw.flush();
		pw.close();
	}
	
	public void getDataTrain(PrintWriter pw, int predictionTask) throws Exception {
		for(long e : this.dnTest.getEdges()) {
			long source = this.dnTest.getSource(e);
			long dest = this.dnTest.getDest(e);
			this.getData(pw, source, dest);
		}
		for(long e : this.dnNegative.getEdges()) {
			long source = this.dnNegative.getSource(e);
			long dest = this.dnNegative.getDest(e);
			this.getData(pw, source, dest);
		}
	}

	public void getDataTest(int concept, long[] aggresives) throws Exception {
		PrintWriter sd = new PrintWriter("classifier/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_source_dest.libsvm");
    	PrintWriter pw = new PrintWriter("classifier/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + ".libsvm");
		double[][] similarityLDA = Utility.loadDouble2DArray("similarity/lda.txt");
		double[][] hiddenLDA = Utility.loadDouble2DArray("topic/lda.txt");
    	int[] allList = Utility.loadIntegerArray("list/all.txt");
		if(this.instance == INSTANCE_LINKTOPIC) {
			int index = Utility.firstIndexOfIntegerArray(allList, concept);
			this.dnTestSimilarityLDA = DiffusionNetworkFactory.getDiffusionNetworkWithContent("dna/lda/cv" + this.fold + "/dn_train_content_" + concept + ".txt");
			this.testSimilarityLDA = similarityLDA[index];
			this.testHiddenLDA = hiddenLDA[index];
		}
		for(long source : aggresives) {
			for(long e : this.dn.getOutEdges(source)) {
				long dest = this.dn.getDest(e);
				sd.println(source + "," + dest);
				this.getData(pw, source, dest);
			}
		}
		sd.flush();
		sd.close();
		pw.flush();
		pw.close();
	}

	public void getDataTest(int concept, DiffusionNetwork testdn) throws Exception {
		PrintWriter sd = new PrintWriter("classifier/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_source_dest.libsvm");
    	PrintWriter pw = new PrintWriter("classifier/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + ".libsvm");
		double[][] similarityLDA = Utility.loadDouble2DArray("similarity/lda.txt");
		double[][] hiddenLDA = Utility.loadDouble2DArray("topic/lda.txt");
    	int[] allList = Utility.loadIntegerArray("list/all.txt");
    	if(this.instance == INSTANCE_LINKTOPIC) {
			int index = Utility.firstIndexOfIntegerArray(allList, concept);
			this.dnTestSimilarityLDA = DiffusionNetworkFactory.getDiffusionNetworkWithContent("dna/lda/cv" + this.fold + "/dn_train_content_" + concept + ".txt");
			this.testSimilarityLDA = similarityLDA[index];
			this.testHiddenLDA = hiddenLDA[index];
    	}
		for(long e : testdn.getEdges()) {
			long source = testdn.getSource(e);
			long dest = testdn.getDest(e);
			sd.println(source + "," + dest);
			this.getData(pw, source, dest);
		}
		sd.flush();
		sd.close();
		pw.flush();
		pw.close();
	}
	
	public void getData(PrintWriter pw, String label, Vector<Double> features) throws Exception {
		pw.print(label);
		for(int i=0; i<features.size(); i++) {
			double feature = features.get(i);
			pw.print(" " + (i+1) + ":" + feature);
		}
		pw.println();
	}

	public void scaleDataTrain() throws Exception {
		this.scaleData("");
	}

	public void scaleDataTest(int concept) throws Exception {
		this.scaleData(Integer.toString(concept) + "_");
	}

	public void scaleData(String s) throws Exception {
			
		// Scale parameters
		String input = "classifier/" + dataset + "_cv" + this.fold + "_" + s + instance + "_" + feature + ".libsvm";
		String output = "classifier/" + dataset + "_cv" + this.fold + "_" + s + instance + "_" + feature + "_scaled.libsvm";

		String parameters[] = {"-l", "0", "-u", "1", input};

		// Redirect System.out
		PrintStream so = System.out;
		FileOutputStream fos = new FileOutputStream(output);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);

		// Scale
		LibLinearScale.main(parameters);
		
		// Reset System.out
		System.setOut(so);
		ps.flush();
		ps.close();
		fos.flush();
		fos.close();
	}

	public void prepareDataTrain(int predictionTask) throws Exception {
		this.getDataTrain(predictionTask);
		this.scaleDataTrain();
		if(this.classifier.equalsIgnoreCase("nb")) {
			this.convertDataTrain();
		}
	}

	public void prepareDataTest(int concept, DiffusionNetwork testdn) throws Exception {
		this.getDataTest(concept, testdn);
		this.scaleDataTest(concept);
		this.convertDataTest(concept);
	}	

	public void prepareDataTest(int concept, long[] aggresives) throws Exception {
		this.getDataTest(concept, aggresives);
		this.scaleDataTest(concept);
		this.convertDataTest(concept);
	}	
	
	public void convertDataTrain() throws Exception {
		this.convertData("");
	}

	public void convertDataTest(int concept) throws Exception {
		this.convertData(Integer.toString(concept) + "_");
	}

	public void convertData(String s) throws Exception {
	}
	
	public void trainSVM(String s, String t, String c, String h, String d, String n, String e) throws Exception {
	}

	public void trainLinear(String s, String c, String b, String e, String w) throws Exception {
		String input = "classifier/" + dataset + "_cv" + fold + "_" + instance + "_" + feature + "_scaled.libsvm";
		String model = "classifier/" + this.classifier + "/" + dataset + "_cv" + fold + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled.model";
		String parameters[] = {"-s", s, "-c", c, "-B", b, "-e", e, "-w1", w, input, model};
		Train.main(parameters);
	}

	public void trainWeka() throws Exception {
	}
	
	public void test(int concept) throws Exception {
		String input = "classifier/" + dataset + "_cv" + fold + "_" + concept + "_" + instance + "_" + feature + "_scaled.libsvm";
		String model = "classifier/" + this.classifier + "/" + dataset + "_cv" + fold + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled.model";
		String output = "classifier/" + this.classifier + "/" + dataset + "_cv" + fold + "_" + concept + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled.txt";
		String decisionValueOutput = "classifier/" + this.classifier + "/" + dataset + "_cv" + fold + "_" + concept + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled_dv.txt";
		String srcDest = "classifier/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_source_dest.libsvm";
		String result = "classifier/" + this.classifier + "/" + dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled_dn.txt";
		String[] parameters = {input, model, output};
		if(this.classifier.equalsIgnoreCase("libsvm")) {
		}
		else if(this.classifier.equalsIgnoreCase("liblinear")){
			Predict.setDecisionValueFileName(decisionValueOutput);
			Predict.main(parameters);
		}
		else {
		}
		Vector<String> dv = Utility.loadVector(decisionValueOutput);
		Vector<String> sd = Utility.loadVector(srcDest);
		PrintWriter pw = new PrintWriter(result);
		for(int i=0; i<sd.size(); i++) {
			String[] t = sd.get(i).split(",");
			pw.print(t[0] + "\t" + t[1]);
			pw.println("\t" + dv.get(i));
		}
		pw.flush();
		pw.close();
	}

	/**
	 * @return the totalPositive
	 */
	public int getTotalPositive() {
		return totalPositive;
	}

	/**
	 * @return the totalNegative
	 */
	public int getTotalNegative() {
		return totalNegative;
	}
	
	public void resetTotal() {
		this.totalPositive = 0;
		this.totalNegative = 0;
	}
}
