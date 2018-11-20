package output;

import utilities.*;
import java.io.*;
import java.util.*;
import score.*;
import network.*;

public class PathOrganizer{

	PathTable table ;
	static int VALID_FOLDER= 0;
	static int TEST_FOLDER=1;
	Network valid_ubi_path ;
	Network test_ubi_path;

	Collection<Long> valid_ubi_A0; 
	Collection<Long> test_ubi_A0 ;

	/**
	 * */
	public PathOrganizer( 
			Network valid_ubi_path,
			Network test_ubi_path  ){
		table = new PathTable();
		this.valid_ubi_path = valid_ubi_path;
		this.test_ubi_path = test_ubi_path ;
		this.valid_ubi_A0 = findA0( valid_ubi_path ); 
		this.test_ubi_A0 = findA0( test_ubi_path) ;
		System.out.println( "---------------------------------" );
		double valid_ubi_vertex_count = PathCounter.getVertexCount( valid_ubi_path, valid_ubi_A0 );
		double valid_ubi_edge_count = PathCounter.getEdgeCount( valid_ubi_path );
		double test_ubi_vertex_count = PathCounter.getVertexCount( test_ubi_path, test_ubi_A0 );
		double test_ubi_edge_count = PathCounter.getEdgeCount( test_ubi_path );
		System.out.println("valid: #v=" + valid_ubi_vertex_count + " #e="+ valid_ubi_edge_count );
		System.out.println("test: #v=" + test_ubi_vertex_count + " #e="+ test_ubi_edge_count );
		System.out.println( "---------------------------------" );
	}
	private Collection<Long> findA0( Network forest ){
		Collection<Long> ret = new HashSet<Long>();
		for( Long v : forest.getVertices() ){
			if( forest.inDegree( v ) == 0){
				ret.add( v) ;
			}
		}
		return ret ;
	}
	public void readHeadDir( String headFolderPath) throws Exception{
		// valid
		readOneDir( headFolderPath +"/valid/", PathOrganizer.VALID_FOLDER );
		readOneDir( headFolderPath +"/test/" ,PathOrganizer.TEST_FOLDER );
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
		String[] runflows = new String[3] ;
		runflows[0] = "loo";
		runflows[1] = "obo";
		runflows[2] = "total";
		String[] models = new String[15];
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
		models[12] =".rw.";
		models[13] = ".greedy.";
		models[14] = ".courteous.";
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
		//Map<Long ,Double> scores=  new HashMap<Long,Double>();
		//IOUtility.readScores( folderpath+"/"+ filename , scores);
 		Network predictPath = IOUtility.readOneForest( folderpath + "/"+ filename  );

		if( this.table == null){
			System.out.println( "TABLE IS NULL");
		}
		PathRow r = new PathRow( runflow , model , parametor );
		if( foldertype ==PathOrganizer.VALID_FOLDER ){
			/** we have used one hop  scorer **/
			/* test for svn*/
			double ubi_edge_precision = OneHopPrecisionScorer.getEdgePrecision( valid_ubi_path , predictPath ); 
			double ubi_edge_recall = OneHopRecallScorer.getEdgeRecall( valid_ubi_path , predictPath );
			double ubi_edge_f1 = OneHopF1Scorer.getEdgeF1( valid_ubi_path, predictPath );
			
/*			double ubi_edge_precision = PathPrecisionScorer.getEdgePrecision( valid_ubi_path , predictPath ); 
			double ubi_edge_recall = PathRecallScorer.getEdgeRecall( valid_ubi_path , predictPath );
			double ubi_edge_f1 = PathF1Scorer.getEdgeF1( valid_ubi_path, predictPath );
*/
//			double ubi_edge_precision = TurnIfFailPrecisionScorer.getEdgePrecision( valid_ubi_path , predictPath ); 
//			double ubi_edge_recall = TurnIfFailRecallScorer.getEdgeRecall( valid_ubi_path , predictPath );
//			double ubi_edge_f1 = TurnIfFailF1Scorer.getEdgeF1( valid_ubi_path, predictPath );	

			double ubi_edge_count = PathCounter.getEdgeCount( predictPath );
			
			double ubi_vertex_precision = OneHopPrecisionScorer.getVertexPrecision( valid_ubi_path , predictPath , valid_ubi_A0 ); 
			double ubi_vertex_recall = OneHopRecallScorer.getVertexRecall( valid_ubi_path , predictPath , valid_ubi_A0 );
			double ubi_vertex_f1 = OneHopF1Scorer.getVertexF1( valid_ubi_path, predictPath , valid_ubi_A0);
			
/*			double ubi_vertex_precision = PathPrecisionScorer.getVertexPrecision( valid_ubi_path , predictPath , valid_ubi_A0 ); 
			double ubi_vertex_recall = PathRecallScorer.getVertexRecall( valid_ubi_path , predictPath , valid_ubi_A0 );
			double ubi_vertex_f1 = PathF1Scorer.getVertexF1( valid_ubi_path, predictPath , valid_ubi_A0);
*/
//			double ubi_vertex_precision = TurnIfFailPrecisionScorer.getVertexPrecision( valid_ubi_path , predictPath, valid_ubi_A0 ); 
//			double ubi_vertex_recall = TurnIfFailRecallScorer.getVertexRecall( valid_ubi_path , predictPath , valid_ubi_A0);
//			double ubi_vertex_f1 = TurnIfFailF1Scorer.getVertexF1( valid_ubi_path, predictPath , valid_ubi_A0 );

			double ubi_vertex_count = PathCounter.getVertexCount( predictPath , valid_ubi_A0 );

			r.setValidUbiEdgePrecision( ubi_edge_precision );
			r.setValidUbiEdgeRecall( ubi_edge_recall );
			r.setValidUbiEdgeF1( ubi_edge_f1 );
			r.setValidUbiEdgeCount( ubi_edge_count );
			r.setValidUbiVertexPrecision( ubi_vertex_precision );
			r.setValidUbiVertexRecall( ubi_vertex_recall );
			r.setValidUbiVertexF1( ubi_vertex_f1 );
			r.setValidUbiVertexCount( ubi_vertex_count );
		}
		else if( foldertype ==PathOrganizer.TEST_FOLDER ){

			double ubi_edge_precision = OneHopPrecisionScorer.getEdgePrecision( test_ubi_path , predictPath ); 
			double ubi_edge_recall = OneHopRecallScorer.getEdgeRecall( test_ubi_path , predictPath );
			double ubi_edge_f1 = OneHopF1Scorer.getEdgeF1( test_ubi_path, predictPath );
			
/*			double ubi_edge_precision = PathPrecisionScorer.getEdgePrecision( test_ubi_path , predictPath ); 
			double ubi_edge_recall = PathRecallScorer.getEdgeRecall( test_ubi_path , predictPath );
			double ubi_edge_f1 = PathF1Scorer.getEdgeF1( test_ubi_path, predictPath );
*/
/*			double ubi_edge_precision = TurnIfFailPrecisionScorer.getEdgePrecision( test_ubi_path , predictPath ); 
			double ubi_edge_recall = TurnIfFailRecallScorer.getEdgeRecall( test_ubi_path , predictPath );
			double ubi_edge_f1 = TurnIfFailF1Scorer.getEdgeF1( test_ubi_path, predictPath );	
*/
			double ubi_edge_count = PathCounter.getEdgeCount( predictPath );

			double ubi_vertex_precision = OneHopPrecisionScorer.getVertexPrecision( test_ubi_path , predictPath, test_ubi_A0 ); 
			double ubi_vertex_recall = OneHopRecallScorer.getVertexRecall( test_ubi_path , predictPath , test_ubi_A0);
			double ubi_vertex_f1 = OneHopF1Scorer.getVertexF1( test_ubi_path, predictPath , test_ubi_A0 );
			
/*			double ubi_vertex_precision = PathPrecisionScorer.getVertexPrecision( test_ubi_path , predictPath, test_ubi_A0 ); 
			double ubi_vertex_recall = PathRecallScorer.getVertexRecall( test_ubi_path , predictPath , test_ubi_A0);
			double ubi_vertex_f1 = PathF1Scorer.getVertexF1( test_ubi_path, predictPath , test_ubi_A0 );
*/
/*			double ubi_vertex_precision = TurnIfFailPrecisionScorer.getVertexPrecision( test_ubi_path , predictPath, test_ubi_A0 ); 
			double ubi_vertex_recall = TurnIfFailRecallScorer.getVertexRecall( test_ubi_path , predictPath , test_ubi_A0);
			double ubi_vertex_f1 = TurnIfFailF1Scorer.getVertexF1( test_ubi_path, predictPath , test_ubi_A0 );
*/
			double ubi_vertex_count = PathCounter.getVertexCount( predictPath , test_ubi_A0 );
			
			r.setTestUbiEdgePrecision( ubi_edge_precision );
			r.setTestUbiEdgeRecall( ubi_edge_recall );
			r.setTestUbiEdgeF1( ubi_edge_f1 );
			r.setTestUbiEdgeCount( ubi_edge_count );
			r.setTestUbiVertexPrecision( ubi_vertex_precision );
			r.setTestUbiVertexRecall( ubi_vertex_recall );
			r.setTestUbiVertexF1( ubi_vertex_f1 );
			r.setTestUbiVertexCount( ubi_vertex_count );

		}else{
			System.out.println( "ERROR: no folder type.");
		}
		this.table.upsert( r );
	}    
} 
