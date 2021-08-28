package output;
import java.io.*;
import java.util.*;
class Row{
	static double nullvalue = -100.0;
	String runflow ; 
	String model;
	String parametor;
	double valid_ubi_kendall;
	double valid_ubi_spearman;
	double test_ubi_kendall;
	double test_ubi_spearman;
	double valid_ubi_precision;
	double test_ubi_precision;
	double valid_ubi_recall;
	double test_ubi_recall;
	double valid_ubi_f1;
	double test_ubi_f1;
	double valid_ubi_accuracy;
	double test_ubi_accuracy;
	public void setValidUbiKendall( double valid_ubi_kendall ){ this.valid_ubi_kendall = valid_ubi_kendall ; }
	public void setValidUbiSpearman( double valid_ubi_spearman ){this.valid_ubi_spearman = valid_ubi_spearman;}
	public void setTestUbiKendall( double test_ubi_kendall ){ this.test_ubi_kendall = test_ubi_kendall;}
	public void setTestUbiSpearman( double test_ubi_spearman){ this.test_ubi_spearman = test_ubi_spearman;}
	public void setValidUbiPrecision(double value){
		this.valid_ubi_precision = value;
	}

	public void setTestUbiPrecision(double value){
		this.test_ubi_precision = value;
	}

	public void setValidUbiAccuracy(double value){
		this.valid_ubi_accuracy = value;
	}

	public void setTestUbiAccuracy(double value){
		this.test_ubi_accuracy = value;
	}

	public void setValidUbiRecall(double value){
		this.valid_ubi_recall = value;
	}

	public void setTestUbiRecall(double value){
		this.test_ubi_recall = value;
	}

	public void setValidUbiF1(double value){
		this.valid_ubi_f1 = value;
	}

	public void setTestUbiF1(double value){
		this.test_ubi_f1 = value;
	}

	public Row(  String runflow , String model, String parametor ){
		if( runflow == null){ runflow = "null";}
		this.runflow = runflow ; 
		if( model == null) { model = "null"; } 
		this.model = model;
		this.parametor = parametor ;
		valid_ubi_kendall = nullvalue;
		valid_ubi_spearman = nullvalue;
		valid_ubi_precision = nullvalue;
		valid_ubi_recall = nullvalue;
		valid_ubi_accuracy = nullvalue;
		valid_ubi_f1 = nullvalue;
		test_ubi_kendall = nullvalue;
		test_ubi_spearman = nullvalue;
		test_ubi_precision = nullvalue;
		test_ubi_recall = nullvalue;
		test_ubi_accuracy = nullvalue;
		test_ubi_f1 = nullvalue;
	}
	public String getString( ){
/*		String str_valid_ubi_kendall , str_valid_ubi_spearman , str_test_ubi_kendall, str_test_ubi_spearman ;
		if( valid_ubi_kendall == nullvalue ){
			str_valid_ubi_kendall = "-";
		else
			str_valid_ubi_kendall= String.valueOf( valid_ubi_kendall ) ;

		if( valid_ubi_spearman == nullvalue )
			str_valid_ubi_spearman = "-";
		else
			str_valid_ubi_spearman = String.valueOf( valid_ubi_spearman ) ;

		if( test_ubi_kendall == nullvalue )
			str_test_ubi_kendall = "-";
		else
			str_test_ubi_kendall= String.valueOf( test_ubi_kendall ) ;

		if( test_ubi_spearman == nullvalue )
			str_test_ubi_spearman = "-";
		else
			str_test_ubi_spearman= String.valueOf( test_ubi_spearman ) ;
*/
		String str_valid_ubi_precision, str_test_ubi_precision;
		String str_valid_ubi_f1, str_test_ubi_f1;
		String str_valid_ubi_accuracy, str_test_ubi_accuracy;
		String str_valid_ubi_recall, str_test_ubi_recall;
		if( valid_ubi_precision == nullvalue )
			str_valid_ubi_precision = "-";
		else
			str_valid_ubi_precision = String.valueOf(valid_ubi_precision) ;

		if(test_ubi_precision == nullvalue)
			str_test_ubi_precision = "-";
		else
			str_test_ubi_precision = String.valueOf(test_ubi_precision);

		if( valid_ubi_f1 == nullvalue )
			str_valid_ubi_f1 = "-";
		else
			str_valid_ubi_f1 = String.valueOf(valid_ubi_f1) ;

		if(test_ubi_f1 == nullvalue)
			str_test_ubi_f1 = "-";
		else
			str_test_ubi_f1 = String.valueOf(test_ubi_f1);

		if( valid_ubi_accuracy == nullvalue )
			str_valid_ubi_accuracy = "-";
		else
			str_valid_ubi_accuracy = String.valueOf(valid_ubi_accuracy) ;

		if(test_ubi_accuracy == nullvalue)
			str_test_ubi_accuracy = "-";
		else
			str_test_ubi_accuracy = String.valueOf(test_ubi_accuracy);

		if( valid_ubi_recall == nullvalue )
			str_valid_ubi_recall = "-";
		else
			str_valid_ubi_recall = String.valueOf(valid_ubi_recall) ;

		if(test_ubi_recall == nullvalue)
			str_test_ubi_recall = "-";
		else
			str_test_ubi_recall = String.valueOf(test_ubi_recall);

		return 
			"| |"+
			runflow + "|" +
			model +"|"+
			parametor+ "|"+
			str_valid_ubi_precision + "|" +
			str_valid_ubi_recall + "|" + 
			str_valid_ubi_accuracy + "|" +
			str_valid_ubi_f1 + "|" + 
			str_test_ubi_precision + "|" + 
			str_test_ubi_recall + "|" + 
			str_test_ubi_accuracy + "|" +
			str_test_ubi_f1 + "|";
/*			str_valid_ubi_kendall +"\t"+
			str_valid_ubi_spearman+"\t"+
			str_test_ubi_kendall+"\t"+
			str_test_ubi_spearman;
*/
	}
	public int compare( Row r2 ){
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


public class Table{
	Vector<Row> rows;
	String[] rowTitle; 
	public Table(){
		rowTitle = new String[11] ;
		rowTitle[0] = "runflow";
		rowTitle[1] = "model";
		rowTitle[2] = "parameter";
		rowTitle[3] = "valid-ubi-precision";
		rowTitle[4] = "valid-ubi-recall";
		rowTitle[5] = "valid-ubi-accuracy";
		rowTitle[6] = "valid-ubi-f1";
		rowTitle[7] = "test-ubi-precision";
		rowTitle[8] = "test-ubi-recall";
		rowTitle[9] = "test-ubi-accuracy";
		rowTitle[10] = "test-ubi-f1";
//		rowTitle[3] = "valid-ubi-kendall";
//		rowTitle[4] = "valid-ubi-spearman";
//		rowTitle[5] = "test-ubi-kendall";
//		rowTitle[6] = "test-ubi-spearman";
		rows=new Vector<Row>();
	}
	public void upsert( Row new_r ){
		for( Row r : rows ){
			if( r.compare( new_r ) == 0){
				// update
				if( r.valid_ubi_precision == Row.nullvalue && new_r.valid_ubi_precision != Row.nullvalue  ){
					r.valid_ubi_precision = new_r.valid_ubi_precision;
					r.valid_ubi_recall = new_r.valid_ubi_recall;
					r.valid_ubi_accuracy = new_r.valid_ubi_accuracy;
					r.valid_ubi_f1 = new_r.valid_ubi_f1;
//					r.valid_ubi_kendall = new_r.valid_ubi_kendall ;
//					r.valid_ubi_spearman = new_r.valid_ubi_spearman ;
				}
				if( r.test_ubi_precision == Row.nullvalue && new_r.test_ubi_precision != Row.nullvalue ){
					r.test_ubi_precision = new_r.test_ubi_precision;
					r.test_ubi_recall = new_r.test_ubi_recall;
					r.test_ubi_accuracy = new_r.test_ubi_accuracy;
					r.test_ubi_f1 = new_r.test_ubi_f1;
//					r.test_ubi_kendall = new_r.test_ubi_kendall  ; 
//					r.test_ubi_spearman = new_r.test_ubi_spearman;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sort(){
		Comparator comparator = new Comparator(){
			public int compare( Object o1  , Object o2 ){
				Row r1 =  (Row) o1 ;
				Row r2 = (Row) o2 ;
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

		for( Row r : rows ){
			printStream.println( r.getString() );
		}
		printStream.close();
	}


	
}
