package score;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import network.Network;

public class TurnIfFailRecallScorer {
	public static double getEdgeRecall( Network scorePath, Network predictPath ){
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

		double trueEdges = 0.0;
		while( !dfsStack.empty() ){
			Long v = dfsStack.pop();
			for( Long v2 : predictPath.getSuccessors(v )){
				if( scorePath.findEdge(v , v2) != null ){
					trueEdges += 1.0;
					dfsStack.add(v2);
				}

			}
		}			
		return trueEdges / (double) scorePath.getEdgeCount();
	}

	public static double getVertexRecall( Network scorePath , Network predictPath , Collection<Long> A0 ){
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
		
		Set<Long> trueTraversedVertices = new HashSet<Long>();
		while( dfsStack.empty() ){
			Long v = dfsStack.pop();
			for( Long v2 : predictPath.getSuccessors(v) ){
				if( scorePath.findEdge(v, v2) != null ){
					dfsStack.add( v2);
					trueTraversedVertices.add( v2 );
				}

			}
		}
		return (double) trueTraversedVertices.size() / (double) scorePath.getEdgeCount();
	}
}
