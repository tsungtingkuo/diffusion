package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SparseMatrix{
	Map<Integer, Map<Integer,Double>> _data;
	public int m ;
	public int n;
	class Row{
		int i; int j; double v ; 
		public Row( int i, int j , double v ){ this.i = i ; this.j= j;this.v= v;}
		public int get_i(){return i;}
		public int get_j(){return j;}
		public double get_v(){return v ;}
	}
	
	public SparseMatrix( int m , int n){
		this._data = new HashMap<Integer, Map<Integer,Double> >();
		this.m = m ;
		this.n = n ;
	}
	public void insert( int i, int j , double v ){
		if( !this._data.containsKey(i)){
			this._data.put( i, new HashMap<Integer,Double>());		
		}
		this._data.get( i).put( j ,  v);
	}
	public double get( int i , int j ){
		if( ! this._data.containsKey(i)){
			return 0.0;
		}
		if( ! this._data.get(i).containsKey(j) ){
			return 0.0;
		}
		return this._data.get(i).get(j);
	}
	public List<Row> find(){
		List<Row> ret =new ArrayList<Row>();
		for( int i : _data.keySet()){
			for( int j: _data.get(i).keySet()){	
				double v = _data.get(i).get(j);
				ret.add( new Row( i,j,v) );
			}
		}
		return ret ;
	}
}