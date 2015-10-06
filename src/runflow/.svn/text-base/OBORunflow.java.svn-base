package runflow;

import model.*;
import network.*;
import score.*;
import java.util.*;
import utilities.*;

public class OBORunflow implements Runflow {

	String oneStageOutput= null;
	String pathOutput= null;
	/**
	 * @param	pathOutput diffusion path output
	 * */
	public void setPathOutput( String pathOutput){
		this.pathOutput =  pathOutput;
	}

	/**
	 * @param	oneStageOutput	one stage output
	 * */
	public void setOneStageOutput( String oneStageOutput ){
		this.oneStageOutput = oneStageOutput ;
	}

	/**
	 * @param	m	input model
	 * @param	n	network
	 * @param	oldA0	old initial nodes set
	 * @param	A0	initial nodes set
	 * @param	scorer	scorer
	 * @param	scores	output scores
	 * */
	public void run( Model m, Network n  , Collection<Long> oldA0 , Collection<Long> A0 , Scorer scorer , Map<Long, Double> scores ) throws Exception {
		Network o_n =new Network();
		//Map<Long,Long> total_link2time= new HashMap<Long,Long>();
		// cal score for each user
		double score =0.0;
		Map<Long,Long> link2time = new HashMap<Long,Long>();
		// old A0 will interfere propagate 
		Collection<Long> thisRoundA0 = new HashSet<Long>( oldA0 ); 
		double nownode = 0.0;
		double totalnodes = (double)A0.size();
		for( Long uid : A0 ){
			o_n = new Network();	
			link2time.clear();
			thisRoundA0.add( uid );
			m.setA0( thisRoundA0  );
			m.run( n , o_n , link2time );
			
			if( oneStageOutput != null){
				try{
					IOUtility.writeOneStage( oneStageOutput, o_n , uid );  
				}catch( Exception e ){
					System.out.println("Cannot print one stage.");
					System.out.println( e.getMessage() );
				}
			} 
			if( pathOutput != null ){
				try{
					if( o_n.getVertexCount() > 0 )
						IOUtility.writeTreeLinks( pathOutput , o_n,link2time  ,uid );
				}catch( Exception e ){
					System.out.println( "Cannot print path.");
					System.out.println( e.getMessage() );
				}
			}
			score = 0.0;
			
			scorer.setLink2time( link2time );		
			score = scorer.getScore(o_n, uid );
			
			System.out.printf("OBORf: uid=%d score=%f [%.4f]\r",
					uid,
					score,
					(nownode / totalnodes ) );
			nownode += 1.0;
			scores.put( uid ,  score ); 
			o_n = null;
			thisRoundA0.remove( uid );
		}
	}
}
