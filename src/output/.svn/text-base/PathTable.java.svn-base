package output;
import java.io.*;
import java.util.*;
class PathRow{
	static double nullvalue = -100.0;
	String runflow ; 
	String model;
	String parametor;
	double valid_ubi_kendall;
	double valid_ubi_spearman;
	double test_ubi_kendall;
	double test_ubi_spearman;
	
	double valid_ubi_vertex_precision;
	double valid_ubi_vertex_recall;
	double valid_ubi_vertex_f1 ;

	double valid_ubi_vertex_count;

	double valid_ubi_edge_precision;
	double valid_ubi_edge_recall;
	double valid_ubi_edge_f1 ;

	double valid_ubi_edge_count ;

	double test_ubi_vertex_precision;
	double test_ubi_vertex_recall;
	double test_ubi_vertex_f1 ;

	double test_ubi_vertex_count ;

	double test_ubi_edge_precision;
	double test_ubi_edge_recall;
	double test_ubi_edge_f1 ;

	double test_ubi_edge_count ;

	public void setValidUbiEdgePrecision( double value){ this.valid_ubi_edge_precision = value; }
	public void setValidUbiEdgeRecall( double value){ this.valid_ubi_edge_recall = value; }
	public void setValidUbiEdgeF1( double value){ this.valid_ubi_edge_f1 = value; }
	public void setValidUbiEdgeCount( double value ){ this.valid_ubi_edge_count = value ; }

	public void setValidUbiVertexPrecision( double value){ this.valid_ubi_vertex_precision = value; }
	public void setValidUbiVertexRecall( double value){ this.valid_ubi_vertex_recall = value; }
	public void setValidUbiVertexF1( double value){ this.valid_ubi_vertex_f1 = value; }
	public void setValidUbiVertexCount( double value ){ this.valid_ubi_vertex_count = value ; }

	public void setTestUbiEdgePrecision( double value){ this.test_ubi_edge_precision = value; }
	public void setTestUbiEdgeRecall( double value){ this.test_ubi_edge_recall = value; }
	public void setTestUbiEdgeF1( double value){ this.test_ubi_edge_f1 = value; }
	public void setTestUbiEdgeCount( double value ){ this.test_ubi_edge_count = value ;}

	public void setTestUbiVertexPrecision( double value){ this.test_ubi_vertex_precision = value; }
	public void setTestUbiVertexRecall( double value){ this.test_ubi_vertex_recall = value; }
	public void setTestUbiVertexF1( double value){ this.test_ubi_vertex_f1 = value; }
	public void setTestUbiVertexCount( double value ){ this.test_ubi_vertex_count = value; }

	public PathRow(  String runflow , String model, String parametor ){
		if( runflow == null){ runflow = "null";}
		this.runflow = runflow ; 
		if( model == null) { model = "null"; } 
		this.model = model;
		this.parametor = parametor ;

		valid_ubi_vertex_precision= nullvalue;
		valid_ubi_vertex_recall= nullvalue;
		valid_ubi_vertex_f1 = nullvalue;

		valid_ubi_vertex_count = nullvalue;
		
		valid_ubi_edge_precision= nullvalue;
		valid_ubi_edge_recall= nullvalue;
		valid_ubi_edge_f1= nullvalue ;

		valid_ubi_edge_count = nullvalue;

		test_ubi_vertex_precision= nullvalue;
		test_ubi_vertex_recall= nullvalue;
		test_ubi_vertex_f1 = nullvalue;

		test_ubi_vertex_count = nullvalue;
		test_ubi_edge_count = nullvalue;

		test_ubi_edge_precision= nullvalue;
		test_ubi_edge_recall= nullvalue;
		test_ubi_edge_f1 = nullvalue;
	}
	public String getString( ){
		return 	"| |"+
			runflow + "|" +
			model +"|"+
			parametor+ "|"+
			valid_ubi_edge_precision +"|"+
			valid_ubi_edge_recall +"|" +
			valid_ubi_edge_f1 +"|"+
			valid_ubi_edge_count +"|"+
			valid_ubi_vertex_precision+"|"+
			valid_ubi_vertex_recall+"|"+
			valid_ubi_vertex_f1+"|"+
			valid_ubi_vertex_count +"|"+
			test_ubi_edge_precision+"|"+
			test_ubi_edge_recall+"|"+
			test_ubi_edge_f1+"|"+
			test_ubi_edge_count+ "|"+
			test_ubi_vertex_precision+"|"+
			test_ubi_vertex_recall+"|"+
			test_ubi_vertex_f1+"|"+
			test_ubi_vertex_count+"|" ;
			/*valid_ubi_vertex_precision + "|" +
			valid_ubi_vertex_recall +"|" +
			valid_ubi_vertex_f1  + "|" +
			valid_ubi_edge_precision + "|" +
			valid_ubi_edge_recall + "|" +
			valid_ubi_edge_f1 + "|" +
			test_ubi_vertex_precision +"|"+
			test_ubi_vertex_recall +"|"+
			test_ubi_vertex_f1 +"|"+
			test_ubi_edge_precision +"|"+
			test_ubi_edge_recall +"|"+
			test_ubi_edge_f1  +"|" ;*/
	}

	public int compare( PathRow r2 ){
		int compareScore= -1;
		String[] compareArray1 = new String[3] ;
		String[] compareArray2 = new String[3] ;
		compareArray1[0] = this.model ; compareArray2[0] = r2.model;
		compareArray1[1] = this.runflow ; compareArray2[1] = r2.runflow;
		compareArray1[2] = this.parametor ; compareArray2[2] = r2.parametor ;	

		for( int i = 0 ; i < compareArray1.length ;i++){
		//	System.out.println( compareArray1[i] +"\t"+ compareArray2[i] );
			compareScore = compareArray1[i].compareTo( compareArray2[i] );
			if( compareScore == 0 ){
				continue;	
			}else{
				return compareScore;		
			}
		}
		return 0;
	} 
} 


public class PathTable{
	Vector<PathRow> rows;
	String[] rowTitle; 
	public PathTable(){
		rowTitle = new String[19] ;
		rowTitle[0] = "runflow";
		rowTitle[1] = "model";
		rowTitle[2] = "parameter";
		rowTitle[3] = "valid-ubi-edge-precision";
		rowTitle[4] = "valid-ubi-edge-recall";
		rowTitle[5] = "valid-ubi-edge-f1";
		rowTitle[6] = "valid-ubi-edge-count";
		rowTitle[7] = "valid-ubi-vertex-precision";
		rowTitle[8] = "valid-ubi-vertex-recall";
		rowTitle[9] = "valid-ubi-vertex-f1";
		rowTitle[10] = "valid-ubi-vertex-count";
		rowTitle[11] = "test-ubi-edge-precision";
		rowTitle[12] = "test-ubi-edge-recall";
		rowTitle[13] = "test-ubi-edge-f1";
		rowTitle[14] = "test-ubi-edge-count";
		rowTitle[15] = "test-ubi-vertex-precision";
		rowTitle[16] = "test-ubi-vertex-recall";
		rowTitle[17] = "test-ubi-vertex-f1";
		rowTitle[18] = "test-ubi-vertex-count";
		rows=new Vector<PathRow>();
	}
	public void upsert( PathRow new_r ){
		for( PathRow r : rows ){
			if( r.compare( new_r ) == 0){
				// update
				if( r.valid_ubi_edge_precision == PathRow.nullvalue && new_r.valid_ubi_edge_precision != PathRow.nullvalue  ){


					r.valid_ubi_edge_precision = new_r.valid_ubi_edge_precision;
					r.valid_ubi_edge_recall = new_r.valid_ubi_edge_recall;
					r.valid_ubi_edge_f1 = new_r.valid_ubi_edge_f1;
					r.valid_ubi_edge_count = new_r.valid_ubi_edge_count ;
					r.valid_ubi_vertex_precision = new_r.valid_ubi_vertex_precision;
					r.valid_ubi_vertex_recall = new_r.valid_ubi_vertex_recall;
					r.valid_ubi_vertex_f1 = new_r.valid_ubi_vertex_f1;
					r.valid_ubi_vertex_count = new_r.valid_ubi_vertex_count ;
				}
				if( r.test_ubi_edge_precision == PathRow.nullvalue && new_r.test_ubi_edge_precision != PathRow.nullvalue ){
					r.test_ubi_edge_precision = new_r.test_ubi_edge_precision;
					r.test_ubi_edge_recall = new_r.test_ubi_edge_recall;
					r.test_ubi_edge_f1 = new_r.test_ubi_edge_f1;
					r.test_ubi_edge_count = new_r.test_ubi_edge_count;
					r.test_ubi_vertex_precision = new_r.test_ubi_vertex_precision;
					r.test_ubi_vertex_recall = new_r.test_ubi_vertex_recall;
					r.test_ubi_vertex_f1 = new_r.test_ubi_vertex_f1;
					r.test_ubi_vertex_count = new_r.test_ubi_vertex_count;
				
				}
				return ;
			} 
		} 
		//insert 
		rows.add( new_r  ) ;
	}/* 
	public void insert( String data_name , String runflow , String model , double ubi_kendall , double ubi_spearman ){
		System.out.println( data_name+ "\t"+runflow + "\t"+ model +"\t"+ubi_kendall +"\t"+ubi_spearman );
		Row r = new Row( data_name, runflow , model, ubi_kendall ,ubi_spearman );
		rows.add( r );
	} 
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(){
		Comparator comparator = new Comparator(){
			public int compare( Object o1  , Object o2 ){
				PathRow r1 =  (PathRow) o1 ;
				PathRow r2 = (PathRow) o2 ;
				return r1.compare( r2 );
			}
			public boolean equals( Object obj ){
				return false;
			}
		};
		Collections.sort( rows , comparator );
	} 
	public void print( String filename ) throws Exception{
		this.sort();
		PrintStream printStream = new PrintStream(
				new FileOutputStream(
					new File( filename)));
		String printline="";
		printline = "sid"; 
		for( String rowname : rowTitle ){
			printline = printline +"|" + rowname;
		}
		printline = "|" + printline + "|" ;
		printline = printline.trim();
		printStream.println( printline);

		for( PathRow r : rows ){
			printStream.println( r.getString() );
		}
		printStream.close();
	}


	
}
