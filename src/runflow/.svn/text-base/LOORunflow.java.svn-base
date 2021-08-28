package runflow;

import model.*;
import network.*;
import score.*;
import java.util.*;

public class LOORunflow implements Runflow {
	public static long headNodeId = -1;

	public void addHeadNode(  Network n , Collection<Long> a0 ,Map<Long,Long> link2time ){
		if( n.containsVertex( headNodeId) ){
			System.out.println("HEAD NODE IS CONTAINED");
		}
		n.addVertex( headNodeId);
		long headRid = -1;
		long minLinkTime = -1 ;
		// get min Link time
		for( Long timestamp : link2time.values() ){
			if( minLinkTime == -1 || timestamp < minLinkTime ){
				minLinkTime = timestamp ;
			}
		}

		for( Long uid : a0 ){
			n.addEdge( headRid, headNodeId, uid ); 
			link2time.put( headRid , minLinkTime );
			headRid--;
		}
	}
	public void removeHeadNode( Network n ){
		if( !n.containsVertex( headNodeId ) ){
			System.out.println("NO HEAD NODE");
			return ;
		}
		Set<Long> edges = new HashSet<Long>();
		edges.addAll( n.getOutEdges( headNodeId ));
		edges.addAll( n.getInEdges( headNodeId ) );
		for( Long rid : edges ){
			n.removeEdge( rid );
		}
		n.removeVertex(  headNodeId );
	}


	/**
	 * @param	m	input model
	 * @param	n	network
	 * @param	A0	initial nodes set
	 * @param	scorer	scorer
	 * @param	scores	output scores
	 * */
	public void run( Model m, Network n , Collection<Long> oldA0 , Collection<Long> A0 , Scorer scorer , Map<Long, Double> scores ) throws Exception{
		Network o_n =new Network();
		Map<Long,Long> total_link2time= new HashMap<Long,Long>();
		double totalScore= 0.0;
		// get score for all nodes
		Collection<Long> totalA0 = new HashSet<Long>( A0 );
		totalA0.addAll( oldA0 );
		m.setA0( totalA0 ) ;
		m.run( n , o_n , total_link2time );
		// cal all score 
		totalScore = 0.0;
		this.addHeadNode( o_n ,totalA0 ,total_link2time );
		scorer.setLink2time( total_link2time );
		totalScore = scorer.getScore( o_n , headNodeId );
		this.removeHeadNode( o_n );

		total_link2time.clear();
		total_link2time = null;
		// cal score for each user
		Set<Long> tmpAll = new HashSet<Long>( totalA0 ); 
		double totalMinusScore =0.0;
		Map<Long,Long> link2time = new HashMap<Long,Long>();
		double nownode = 0.0;
		double totalnodes = (double)A0.size();
		for( Long uid : A0 ){
			tmpAll.remove( uid);
			o_n = new Network();
		
			link2time.clear();
			m.setA0( tmpAll );
			m.run( n , o_n , link2time );

			totalMinusScore = 0.0;
			// cal total minus score 
			this.addHeadNode( o_n, tmpAll, link2time );
			scorer.setLink2time( link2time );
			totalMinusScore = scorer.getScore( o_n, headNodeId );
			this.removeHeadNode( o_n );
			scores.put( uid , totalScore - totalMinusScore ); 
			tmpAll.add(uid );
			o_n = null;

			// print out score 
			//System.out.print("LOORf: uid="+uid+" totalscore="+ totalScore + " totalMinusScore="+ totalMinusScore+"\t"+( nownode / totalnodes) +"%\r" );
			System.out.printf("LOORf: uid=%d totalscore=%.4f totalminusscore=%.4f [%.3f]\r", 
					uid ,
					totalScore,
					totalMinusScore,
					( nownode / totalnodes ) );
			nownode+= 1.0;
		}
	}
} 
