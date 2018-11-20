package model;

import network.*;
import java.util.*;

public class HdModel extends Model{
	private static int p= 30;
	public Collection<Long> A0;
	double t ;
	double alpha ;
	int finalActivateNodes = 0;
	public int getFinalActivateNodes(){
		return finalActivateNodes;
	}
	public HdModel(){
		t = 1.0;
		alpha = 0.5;
		finalActivateNodes = 0;
	}
	
	public void setTime( double t ){
		this.t = t ;
	}
	public void setP( int p ){
		HdModel.p =  p;
	}
	public void setAlpha( double alpha ){
		this.alpha = alpha ;
	}
	public void setA0( Collection<Long> A0 ){ this.A0 = A0; }
	/**
	 * @param	n	input network. You must init f(0) and thresholds for each node outsid 
 	 * @param	o_n	output path
 	 * @param	link2time	map path link to generated time
 	*/
	public void resetHeat(Network n  ){
		//You must init f(0), thresholds for each node outside 
		//init
		for( Long uid : n.getVertices() ){
			User u = n.getUser( uid );
			u.setState( User.STATE_INACTIVATED );
			u.setHdHeat( 0.0 );
		}
		
	}
	public void setInitHeatEqualiy( Network n, Network o_n){
		for( Long uid : A0 ){
			User u = n.getUser( uid );
			u.setState( User.STATE_ACTIVATED );
			u.setHdHeat( (double) n.getVertexCount() /(double) A0.size() );
			o_n.addVertex( uid );
		}
	}
	public void run( Network n, Network o_n , Map<Long,Long> link2time ){
		
		/**
		 * WANNING: You must set heat for each node first.
		 * */
		// do not assume id is contnuous.
		Map<Long,Double> nextHeats = new HashMap<Long,Double>();

		Set<Long> thisRoundUid = new HashSet<Long>( A0 );
		Set<Long> nextRoundUid  = new HashSet<Long>();
		Set<Long> lastRoundNewUid = new HashSet<Long>();
		
		for( int i = 0 ; i < p ;i++){
			//init 
			//for( int j = 0 ; j < nextHeat.length; j++){ nextHeat[j] = 0.0;}
			for( Long uid1 : n.getVertices() ){
				nextHeats.put( uid1 , 0.0 );
			}
			nextRoundUid =new HashSet<Long>();

			// run through each node
			for( Long uid1 : thisRoundUid ){
				User u1 = n.getUser( uid1 );
				double u1Heat = u1.getHdHeat();
				double u1Outdegree  = (double) n.outDegree( uid1 ); 
				Double nextHeat = nextHeats.get( uid1 );
				// testing
				//double testingTotalHeat = 0.0;
				if( u1Outdegree  > 0.0 ){      
					if( !lastRoundNewUid.contains( uid1 ) ){
						nextRoundUid.addAll( n.getSuccessors( uid1 ) );
					}
					
					//nextHeat[ uid1 ] += ( 1.0 - alpha * t / p ) * u1Heat;
					nextHeat += ( 1.0 - alpha * t / p ) * u1Heat;
					nextHeats.put( uid1 , nextHeat );
					for( Long uid2 : n.getSuccessors( uid1 ) ){
						//nextHeat[uid2 ] += ( ( alpha * t  /  p)  * ( 1.0/ u1Outdegree) )  * u1Heat ;
						Double nextHeatUid2 = nextHeats.get( uid2 );
						User u2 = n.getUser( uid2 ) ;
						nextHeatUid2 += ( ( alpha * t  /  p)  * ( 1.0/ u1Outdegree) )  * u1Heat ;
						nextHeats.put( uid2 , nextHeatUid2 );
						if( nextHeatUid2 >= u2.getHdThreshold() && u2.getState() == User.STATE_INACTIVATED ){
							u2.setState( User.STATE_ACTIVATED);
							Long rid = n.findEdge( uid1 , uid2 );
							if( !o_n.containsVertex( uid1 )){
								o_n.addVertex( uid1 );
							}
							if( !o_n.containsVertex( uid2 )) {
								o_n.addVertex( uid2 );
							}
							o_n.addEdge( rid , uid1 , uid2 );
							link2time.put( rid , (long)i );
						}

						//testingTotalHeat += (( alpha * t /   p) * (1.0 /u1Outdegree) ) * u1Heat ; 	
					}
				}else{
					nextHeat += u1Heat ;
					//nextHeat[ uid1 ] += u1Heat ;
				}
				 
			} 	
			// set back
			// testing 
			//double totalHeat = 0.0;
			for( Long uid : n.getVertices() ){
				User u = n.getUser( uid );
				//u.setHdHeat( nextHeat[ uid ] ) ;
				u.setHdHeat( nextHeats.get( uid ) );
				//totalHeat += nextHeat[ uid ]; 
				//totalHeat += nextHeats.get( uid );
			}
	//		System.out.println("Total Heat = " + totalHeat );
			
			thisRoundUid.addAll( nextRoundUid );
			lastRoundNewUid = nextRoundUid ;
		}
		
	// set finalActivateNode
		finalActivateNodes = 0;
		for( Long uid : n.getVertices() ){
			User u = n.getUser( uid );
			if( u.getHdHeat() >= u.getHdThreshold() ){
				//u.setActivated( true );
				//u.setState( User.STATE_ACTIVATED);
				finalActivateNodes+=1;
			}
		}   

	}  
}
