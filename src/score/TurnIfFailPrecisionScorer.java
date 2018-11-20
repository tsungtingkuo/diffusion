package score;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import network.Network;

public class TurnIfFailPrecisionScorer {
	
	
	public static double getEdgePrecision( Network scorePath, Network predictPath ){
		//find a0 set 
		Collection<Long> a0 = new HashSet<Long>();
		for( Long v : predictPath.getVertices()){
			if( predictPath.inDegree(v) == 0 ){ a0.add( v );}
		}
		//traverse predict path
		// dfs stack of node 
		Stack<Long> dfsStack = new  Stack<Long>();
		for( Long v: a0 ){
			dfsStack.push(v );
		}
		double totalEdges= 0.0;
		double trueEdges = 0.0;
		while( !dfsStack.empty() ){
			Long v = dfsStack.pop();
			for( Long v2 : predictPath.getSuccessors(v )){
				if( scorePath.findEdge(v , v2) != null ){
					trueEdges += 1.0;
					dfsStack.add(v2);
				}
				totalEdges += 1.0;
			}
		}			
		return trueEdges / totalEdges;
	}

	public static double getVertexPrecision( Network scorePath , Network predictPath , Collection<Long> A0 ){
		//find a0 set 
		Collection<Long> a0 = new HashSet<Long>();
		for( Long v : predictPath.getVertices()){
			if( predictPath.inDegree(v) == 0 ){ a0.add( v );}
		}
		//traverse predict path
		// dfs stack of node 
		Stack<Long> dfsStack = new  Stack<Long>();
		for( Long v: a0 ){
			dfsStack.push(v );
		}
		
		Set<Long> totalTraversedVertices = new HashSet<Long>(); 
		Set<Long> trueTraversedVertices = new HashSet<Long>();
		while( dfsStack.empty() ){
			Long v = dfsStack.pop();
			for( Long v2 : predictPath.getSuccessors(v) ){
				if( scorePath.findEdge(v, v2) != null ){
					dfsStack.add( v2);
					trueTraversedVertices.add( v2 );
				}
				totalTraversedVertices.add( v2 );
			}
		}
		return (double) trueTraversedVertices.size() / (double) totalTraversedVertices.size();
	}
}
