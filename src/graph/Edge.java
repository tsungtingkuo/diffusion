package graph;

import edu.uci.ics.jung.graph.util.*;
//
public class Edge{
	Pair<Long> nodes;
	Long time ;
	public Edge( Pair<Long> nodes ){
		this( nodes, null );
	} 
	public Edge( Pair<Long> nodes, Long time ){
		this.nodes = nodes;
		this.time = time ;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Edge( Long v1, Long v2 , Long time ){
		this( new Pair( v1, v2 ), time );
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Edge( Long v1, Long v2 ){
		this( new Pair(v1, v2));
	}
	public boolean equals( Object obj){
		Edge e2 = (Edge) obj ;
		if(  e2.getFirst() == this.getFirst() && 
			e2.getSecond() == this.getSecond() && 
			e2.getTime() == this.getTime() ){
			return true;	
		}
		return false;
	}
	public int hashCode(){
		return nodes.hashCode() ;
	}
	public Long getFirst(){
		return nodes.getFirst();
	}
	public Long getSecond(){
		return nodes.getSecond();
	}
	public Long getTime(){
		return time;
	}
} 
