package runflow;

import model.*;
import network.*;
import java.util.*;

public class TotalRunflow {

	Map<Long,Long> link2time; 
	public Map<Long, Long> getLink2time(){
		return link2time;
	}
	/**
	 * @param	m	input model
	 * @param	n	network
	 * @param	oldA0	old initial nodes set
	 * @param	A0	initial nodes set
	 * */
	public Network run( Model m, Network n  , Collection<Long> oldA0 , Collection<Long> A0 ) throws Exception {
				
		Collection<Long> thisRoundA0 = new HashSet<Long>( oldA0 ); 
		thisRoundA0.addAll( A0);

		link2time = new HashMap<Long,Long>();
		
		Network o_n =new Network();
		
		m.setA0( thisRoundA0  );
		m.run( n , o_n , link2time );
	
		return o_n;	
	
	}		

} 


