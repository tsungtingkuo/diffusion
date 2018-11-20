package predictor;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import parameter.HdThresholdInitiator;

import score.IFScorer;
import score.Scorer;

import model.HdModel;
import network.DiffusionNetwork;
import network.Network;
import network.User;

@SuppressWarnings("unused")
public class HdPredictor extends GuidedKNeighborsPredictor{
// for degree type
	public final static int INDEGREE = 0;
	public final static int OUTDEGREE = 1; 
	public final static int DEGREE = 2;
	HdModel model;
	Network result_o_n;
	Network base_n;
	/*
	public class Edge{
		long uid1;
		long uid2;
		public Edge( long uid1, long uid2){
			this.uid1 = uid1;  this.uid2 = uid2;
		}
		public int hashCode(){
			return (int) (uid1 * uid2) ;
		}
		public boolean equal( Object obj ){
			Edge e = (Edge) obj;
			if( uid1== e.uid1 && uid2 == e.uid2 ){
				return true;
			} 
			return false;
			
		}
	}*/
	
	public void initHeat( int type, Network n, DiffusionNetwork dn, Network o_n){
		// sum up total degree
		double total_base = 0.0;
		for( Long uid : dn.getVertices() ){
			switch(type){
				case HdPredictor.DEGREE:
					total_base += dn.weightedDegree(uid);
					break;
				case HdPredictor.INDEGREE:
					total_base += dn.weightedInDegree(uid);
					break;
				case HdPredictor.OUTDEGREE:
					total_base += dn.weightedOutDegree(uid);
					break;
			}
		}
		
		double total_nodes = (double) n.getVertexCount();
		
		for( Long uid : dn.getVertices() ){
			User u = n.getUser( uid );
			
			if( u== null){ continue;}
			
			double d = 0.0;
			switch( type ){
				case HdPredictor.DEGREE:
					d= dn.weightedDegree(uid);
					break;
				case HdPredictor.INDEGREE:
					d= dn.weightedInDegree(uid);
					break;
				case HdPredictor.OUTDEGREE:
					d = dn.weightedOutDegree(uid);
					break;
			}
			if( d== 0.0){ continue;}
			
			u.setState( User.STATE_ACTIVATED );
			u.setHdHeat(( d / total_base) * total_nodes ); // total nodes as whole heat 
			o_n.addVertex( uid );
		}
		//System.out.println( "average (i/o)deg :"+ ( total_base / (double)dn.getVertexCount()) );
		
	}
	public Collection<Long> initA0( Network n , DiffusionNetwork dnt ){
		 Collection<Long> n_uids = n.getVertices();
		 Collection<Long> dnt_uids = dnt.getVertices();
		 
		 
		 Collection<Long> a0 = new HashSet<Long>( dnt_uids);
		 a0.retainAll( n_uids);
		 return a0;
	}
	
	public Network runHdModel( int type, double time, Network n, DiffusionNetwork dn ){
		
		this.model = new HdModel();
	//	double time = 0.1;
		double alpha = 1.0; 
		double threshold = 0.6;
		
		model.setTime( time );
		model.setAlpha( alpha ); 
		//init scores
		Map<Long, Double> scores = new HashMap<Long, Double>();
		Scorer scorer =  new IFScorer();
		
		//init a0 
		model.resetHeat(n);
		//initial heat 
		Network o_n = new Network();
		initHeat( type, n, dn, o_n);
		//init threshold 
		HdThresholdInitiator.initParam(n, threshold);
		//init a0
		Collection<Long> a0 = initA0(n, dn);
		model.setA0(a0);
		
		//run hdmodel
		Map<Long,Long> link2time = new HashMap<Long,Long>();
		model.run( n , o_n , link2time );
		
		//System.out.println( "size( o_n): V= " + o_n.getVertexCount() + " E="+ o_n.getEdgeCount());
		return o_n;
		
		// make return 
	/*	Set<Edge> returnSet = new HashSet<Edge>();
		for( long eid : o_n.getEdges()){
			long v1 = o_n.getSource(eid);
			long v2 = o_n.getDest(eid);
			returnSet.add( new Edge(v1, v2));			
		}
		return returnSet;
	*/
	}
	
	public double score(long sourceId, long destinationId) {
	//	System.out.print( sourceId + "->" + destinationId );
		
		
		if(  this.result_o_n.findEdge(sourceId,destinationId) != null ){
		//	System.out.println(" Y");
			return 1.0;
		}
	//	System.out.println(" N");
		return 0.0;
	}
	
	public HdPredictor(int type, double time,   DiffusionNetwork dn , DiffusionNetwork dnt)
			throws Exception {
		super(dn, dnt);
		// TODO Auto-generated constructor stub
		// run hd model
		this.result_o_n = runHdModel(type, time, dn, dnt);
		this.base_n = dn;
	}

	

}
