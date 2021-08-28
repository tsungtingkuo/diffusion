package predictor;

//import java.util.*;
//import utility.*;
import classifier.*;
import network.*;

public class ClassifierGuidedKNeighborsPredictorCV extends GuidedKNeighborsPredictor {
	
	String dataset = "";
	String classifier = "";
	String w = "1";
	String c = "1";
	int instance = ClassifierCV.INSTANCE_UNKNOWN;
	int feature = 0;
	int fold = 0;
	boolean doTest = true;
	ClassifierCV my = null;
	
	public void initializeSVM() throws Exception {
		this.my = new ClassifierCV(this.dataset, this.classifier, this.instance, this.feature, this.w, this.c, this.fold);
	}
	
	public DiffusionNetwork predict(DiffusionNetwork result, int concept, DiffusionNetwork testdn) throws Exception {
		if(this.doTest) {
			this.my.test(concept);
		}
		this.dnt = DiffusionNetworkFactory.getDiffusionNetwork("classifier/" + this.classifier + "/" + this.dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled_dn.txt");
		return predictGuided(result, concept, testdn);
	}

	public DiffusionNetwork predict(DiffusionNetwork result, int concept, long[] aggresives) throws Exception {
		this.my.test(concept);
		this.dnt = DiffusionNetworkFactory.getDiffusionNetwork("classifier/" + this.classifier + "/" + this.dataset + "_cv" + this.fold + "_" + concept + "_" + instance + "_" + feature + "_" + w + "_" + c + "_scaled_dn.txt");
		return predictGuided(result, concept, aggresives);
	}

	public ClassifierGuidedKNeighborsPredictorCV(DiffusionNetwork dn, DiffusionNetwork dnt) throws Exception {
		super(dn, dnt);
	}

	/**
	 * @return the dataset
	 */
	public String getDataset() {
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	/**
	 * @return the classifier
	 */
	public String getClassifier() {
		return classifier;
	}

	/**
	 * @param classifier the classifier to set
	 */
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	/**
	 * @return the instance
	 */
	public int getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(int instance) {
		this.instance = instance;
	}

	/**
	 * @return the feature
	 */
	public int getFeature() {
		return feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(int feature) {
		this.feature = feature;
	}

	/**
	 * @return the w
	 */
	public String getW() {
		return w;
	}

	/**
	 * @param w the w to set
	 */
	public void setW(String w) {
		this.w = w;
	}

	/**
	 * @return the c
	 */
	public String getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(String c) {
		this.c = c;
	}

	/**
	 * @return the fold
	 */
	public int getFold() {
		return fold;
	}

	/**
	 * @param fold the fold to set
	 */
	public void setFold(int fold) {
		this.fold = fold;
	}

	/**
	 * @return the doTest
	 */
	public boolean isDoTest() {
		return doTest;
	}

	/**
	 * @param doTest the doTest to set
	 */
	public void setDoTest(boolean doTest) {
		this.doTest = doTest;
	}
}
