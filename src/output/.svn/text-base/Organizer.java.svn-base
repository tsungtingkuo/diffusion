package output;

import utilities.*;
import java.io.*;
import java.util.*;
import score.*;

public class Organizer{

	Table table ;
	static int VALID_FOLDER= 0;
	static int TEST_FOLDER=1;
	Map<Long,Double> valid_ubi_scores ; 
	Map<Long,Double> test_ubi_scores;
	int k;
	/**
	 * @param	ubi_scores	you must set the answers ubi_scores first
	 * */
	public Organizer(Map<Long,Double> valid_ubi_scores,  Map<Long,Double> test_ubi_scores , int k ){
		table = new Table();
		this.valid_ubi_scores = valid_ubi_scores;
		this.test_ubi_scores = test_ubi_scores ;
		this.k = k;
	}
	public void readHeadDir( String headFolderPath) throws Exception{
		// valid
		readOneDir( headFolderPath +"/valid/", Organizer.VALID_FOLDER );
		readOneDir( headFolderPath +"/test/" , Organizer.TEST_FOLDER );
	}
	/**
	 *
	 * */
	public void readOneDir( String folderpath , int foldertype ) throws Exception{
		File dir= new File( folderpath );
		FilenameFilter filter = new FilenameFilter(){
			public boolean accept( File dir , String name ){
				if( name.indexOf( "rst." ) != -1 ){
					return false;
				}
				if(name.indexOf("rst") != -1)
					return true;
				return false;
			}
		};
		String[] results = dir.list( filter );
		for( String filename : results ){
			readOneFile( folderpath , filename, foldertype  );
		}
	} 
	public void print( String outputFilename )throws Exception{
		this.table.print( outputFilename );
	}

	public void readOneFile( String folderpath , String filename , int foldertype)throws Exception{
		String[] runflows = new String[2] ;
		runflows[0] = "loo";
		runflows[1] = "obo";
		String[] models = new String[14];
		models[0] = ".lt.";
		models[1] = ".ic.";
		models[2] = ".sis.";
		models[3] = ".sir.";
		models[4] = ".hd.";
		models[5] = ".degree.";
		models[6] = ".pgprior.";
		models[7] = ".pagerank.";
		models[8] = ".closeness.";
		models[9] = ".betweenness.";
		models[10] = ".indegree.";
		models[11] =".outdegree.";
		models[12] = ".greedy.";
		models[13] = ".courteous.";
		String[] treetypes = new String[2] ;
		treetypes[0] = ".lbi";
		treetypes[1] = ".ubi";
		
		String runflow= "N/A";
		String model="N/A";
		String treetype="N/A" ;
		for( String runflowType : runflows ){
			if( filename.indexOf( runflowType )!= -1 ){
				runflow= runflowType;
				break;
			}
		}
		for( String modelType: models ){
			if( filename.indexOf( modelType ) != -1 ){
				model = modelType;
				break;
			}
		}
		for( String treeType: treetypes ){
			if( filename.indexOf( treeType) != -1 ){
				treetype  =treeType ;
				break;
			}
		}
		String parametor ="";
		// it's baseline
		if( treetype.compareTo( "N/A" ) == 0 ){
			parametor ="N/A";	
		}else{
			System.out.println("model:\t"+model+"\ttreetype:\t"+ treetype);
			parametor = filename.substring( filename.indexOf( model ) + model.length() , filename.indexOf( treetype) );
			System.out.println( "parameter:\t"+ parametor + "\tfilename:\t" + filename);
		}
		model= model.replace( ".", "");
		treetype = treetype.replace( ".","");
		Map<Long ,Double> scores=  new HashMap<Long,Double>();
		IOUtility.readScores( folderpath+"/"+ filename , scores);
 				if( this.table == null){
			System.out.println( "TABLE IS NULL");
		}
		Row r = new Row( runflow , model , parametor );
		if( foldertype == Organizer.VALID_FOLDER ){
			double ubi_precision = PrecisionScorer.getR(scores, valid_ubi_scores, k);
			double ubi_recall = RecallScorer.getR(scores, valid_ubi_scores, k);
			double ubi_accuracy = AccuracyScorer.getR(scores, valid_ubi_scores, k);
			double ubi_f1 = F1Scorer.getR(scores, valid_ubi_scores, k);
			r.setValidUbiPrecision(ubi_precision);
			r.setValidUbiRecall(ubi_recall);
			r.setValidUbiAccuracy(ubi_accuracy);
			r.setValidUbiF1(ubi_f1);
//			double ubi_kendall= KendalltauScorer.getR( scores , valid_ubi_scores);
//			double ubi_spearman = SpearmanScorer.getR( scores,  valid_ubi_scores ); 
//			r.setValidUbiKendall( ubi_kendall );
//			r.setValidUbiSpearman( ubi_spearman );
		}
		else if( foldertype == Organizer.TEST_FOLDER ){
			double ubi_precision = PrecisionScorer.getR(scores, test_ubi_scores, k);
			double ubi_recall = RecallScorer.getR(scores, test_ubi_scores, k);
			double ubi_accuracy = AccuracyScorer.getR(scores, test_ubi_scores, k);
			double ubi_f1 = F1Scorer.getR(scores, test_ubi_scores, k);
			r.setTestUbiPrecision(ubi_precision);
			r.setTestUbiRecall(ubi_recall);
			r.setTestUbiAccuracy(ubi_accuracy);
			r.setTestUbiF1(ubi_f1);
//			double ubi_kendall= KendalltauScorer.getR( scores , test_ubi_scores);
//			double ubi_spearman = SpearmanScorer.getR( scores,  test_ubi_scores ); 
//			r.setTestUbiKendall( ubi_kendall );
//			r.setTestUbiSpearman( ubi_spearman );
		}else{
			System.out.println( "ERROR: no folder type.");
		}
		this.table.upsert( r );
	}    
} 
