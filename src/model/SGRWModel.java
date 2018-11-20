package model;

import utilities.*;
import network.*;
import java.util.*;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import utility.*;

public class SGRWModel extends GRWModel {
	
	public static final int SIMPLIFIED = -1;

	TreeMap<Long, Integer> simplifiedMap = null;
	int simplifiedActivatedNumber = 0;
	
	public SGRWModel(int linkSelectionMethod, String simplifiedFileName) throws Exception {
		super(SIMPLIFIED, linkSelectionMethod);
		this.simplifiedMap = Utility.loadLongToIntegerTreeMap(simplifiedFileName);
		for(long id : this.simplifiedMap.keySet()) {
			this.simplifiedActivatedNumber += this.simplifiedMap.get(id);
		}
	}

	/**
	 * @param	n	input network
	 * @param	o_n	output network
	 * @param	link2time	map link to time
	 * */
	public void run( Network n , Network o_n , Map<Long,Long> link2time   ){
		
		if(this.nodeSelectionMethod==PAGERANK
				||this.linkSelectionMethod==PAGERANK
				||this.nodeSelectionMethod==INVERSE_PAGERANK
				||this.linkSelectionMethod==INVERSE_PAGERANK
				) {
			this.pr = new PageRank<Long, Long>(n, 0.15);
			this.pr.evaluate();
		}

		int level =1;
		Vector<Long> activated = new Vector<Long>();
		Vector<Long> queue = new Vector<Long>();
		for( Long uid : n.getVertices() ){ 
			n.getUser(uid).setState( User.STATE_INACTIVATED );
		}	
		
		//init
		Collection<Long> notFoundUsers = new HashSet<Long>();
		for( Long uid : A0 ){
			if( n.getUser(uid ) == null){
				System.out.println("NO USER FOUND");
				notFoundUsers.add( uid) ;
			}
			else {
				activated.add(uid);
				queue.add(uid);
				n.getUser(uid).setState( User.STATE_ACTIVATED );
				o_n.addVertex( uid ) ;
			}
		}
		A0.removeAll( notFoundUsers );
		notFoundUsers.clear(); 
		try{
			IOUtility.writeUserSet( "NotFoundUserSet", notFoundUsers );
		}catch( Exception e ){
			System.out.println("ERROR");
		}

		// start 
		System.out.println("Original = " + queue.size());
		
		int finalActivated = queue.size() + this.simplifiedActivatedNumber;
		if(finalActivated > n.getVertexCount()) {
			finalActivated = n.getVertexCount();
		}
		
		HashSet<Long> removing = new HashSet<Long>();
		int count = 0;
		int countRepeat = 0;
		while(activated.size() < finalActivated){
			if(count%100 == 0) {
				System.out.print(count + ".");
				if(count == 0) {
					countRepeat++;
					if(countRepeat > 100) {
						break;
					}
				}
			}
			
			if(count == finalActivated) {
				Vector<Long> tempQueue = new Vector<Long>();
				for(long uid : queue) {
					if(!removing.contains(uid)) {
						tempQueue.add(uid);
					}
				}
				System.out.println("Final = " + finalActivated + ", activated = " + activated.size() + ", queue = " + queue.size() + ", removed = " + tempQueue.size());
				queue = new Vector<Long>(tempQueue);
				removing.clear();
				count = 0;
				countRepeat = 0;
			}
			
			// Simplified guided random selection of a node
			for(Long uid1 : this.simplifiedMap.keySet()) {
				int currentCount = 0;
				Vector<Long> candidates = getCandidates(n, uid1);
				int simplifiedTargetNumber = this.simplifiedMap.get(uid1);
				if(candidates.size() > simplifiedTargetNumber) {
					while(currentCount < simplifiedTargetNumber) {
			
						// Guided random selection of a link
						Long uid2 = this.selectLink(n, candidates, this.linkSelectionMethod, uid1);
						
						if(uid2 != -1) {
							level += 1;				
							User u2 = n.getUser( uid2 );
							u2.setState( User.STATE_ACTIVATED );
							Relation r = n.getRelation( n.findEdge( uid1, uid2 ) );
							o_n.addVertex( uid2) ;
							o_n.addEdge( r.getId() , uid1, uid2 );
							link2time.put( r.getId() ,(long) level );
							activated.add(uid2);
							currentCount++;
						}
						else {
							removing.add(uid1);
						}
						count++;
					}
				}
				else if(candidates.size() == simplifiedTargetNumber) {
					for(long uid2 : candidates) {
						level += 1;				
						User u2 = n.getUser( uid2 );
						u2.setState( User.STATE_ACTIVATED );
						Relation r = n.getRelation( n.findEdge( uid1, uid2 ) );
						o_n.addVertex( uid2) ;
						o_n.addEdge( r.getId() , uid1, uid2 );
						link2time.put( r.getId() ,(long) level );
						activated.add(uid2);
					}
				}
				else {
					removing.add(uid1);
				}
			}
		}
		System.out.println();
		System.out.println("Count = " + count); 
	}	
} 
