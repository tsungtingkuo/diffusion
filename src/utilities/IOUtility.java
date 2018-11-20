package utilities;

import java.io.*;
import java.util.*;
import network.*;
import edu.uci.ics.jung.graph.util.Pair; 
import graph.*;

public class IOUtility{
	
	public static boolean checkFilesExists( Collection<String> filenames ){
		for( String filename : filenames ){
			if( !IOUtility.isFileExist( filename )){
				System.out.println( "File: "+ filename + "\t not found.");
				return false;
			} 
		}
		return true;
	}
	public static boolean isFileExist( String fileName ){
		File f = new File( fileName);
		return f.exists();
	} 
	/**
	 * @param	fileName	output file name
	 * @param	scores	scores of node
	 * */
	public static  void writeScores( String fileName , Map<Long,Double> scores )throws Exception{
	        PrintStream printStream = new PrintStream(
				new FileOutputStream(
					new File( fileName)));
		for( Long uid : scores.keySet() ){
			printStream.println( uid +"\t" + scores.get( uid ) );		
		}
		printStream.close();
	}
	/**
	 * @param	fileName	output file name
	 * @param	uids	set of user ids
	 * */
	public static void writeUserSet( String fileName , Collection<Long> uids ) throws Exception{
	        PrintStream printStream = new PrintStream(
				new FileOutputStream(
					new File( fileName)));
		for( Long uid : uids ){
			printStream.println( uid );		
		}
		printStream.close();
	}
	/**
	 * @param	fileName	input file name
	 * @param	scores	output scores
	 * */
	public static void readScores( String fileName, Map<Long, Double> scores) throws Exception{
		BufferedReader br = new BufferedReader( new FileReader( fileName )) ;	
		String tmpString = br.readLine();
		while( tmpString !=null){
			String[] tmp = tmpString.split("\t");
			Long uid = Long.parseLong( tmp[0] );
			Double score= Double.parseDouble( tmp[1] );
			scores.put(  uid, score );
			tmpString = br.readLine();
		}
		br.close();
	}
	/**
	 * @param	fileName	output file name
	 * @param	n	graph
	 * */
	public static void writeGraph( String fileName ,  Network n ) throws Exception {
		 PrintStream printStream = new PrintStream(
				new FileOutputStream(
					new File( fileName)));
		for( Long eid : n.getEdges() ){
			Pair<Long> pair = n.getEndpoints( eid ) ;
			Long u1 = pair.getFirst();
			Long u2 = pair.getSecond();
			printStream.println( u1 +"\t" + u2 );
		}
		 
		printStream.close();
	
	}
	
	public static long levelNode = -9;
	public static long headNode = -1; 

	public static void writePath( String filename, Network tree ,Map<Long,Long> link2time ) throws Exception{
		File f = new File( filename );
		if( f.exists() ){ f.delete();}
		for( Long uid : tree.getVertices() ){
			if( tree.inDegree( uid ) == 0 ){
				writeTreeLinks( filename ,tree, link2time, uid ) ;
			}
		}	
	}

	public static void writeA0Forest( String fileName , Network rawForest , Map<Long,Long> link2time, Collection<Long> A0 ) throws Exception{
		File f = new File( fileName );
		if( f.exists() ){
			f.delete();
		}
		for( Long root : A0 ){
			writeTreeLinks( fileName, rawForest, link2time, root );
		}
	}

	public static void writeTreeLinks( String fileName, Network tree, Map<Long,Long> link2time ,  Long root ) throws Exception{
		PrintStream printStream = new PrintStream(
			new FileOutputStream(
				new File( fileName) , true ));
		Queue<Long> queue = new LinkedList<Long>();
		queue.offer( root ) ;

		while( queue.peek() != null){
			Long uid = queue.poll();
			for( Long u2 : tree.getSuccessors( uid ) ){
				Long eid = tree.findEdge( uid , u2 );
				Long time = null;
				time = link2time.get( eid );
				if( time == null ) time = (long)-1;
				printStream.println( uid +"\t"+ u2 +"\t"+ time);
				queue.offer( u2 );
			}
		}
		printStream.println("");
		printStream.close();
	}
	
	public static void writeTreeDfs( String fileName , Network tree , Long root )throws Exception{
		PrintStream printStream = new PrintStream(
				new FileOutputStream(
					new File( fileName)));
		Stack<Long> stack = new Stack<Long>();
		
		//add head node 
		stack.push( root );
	

		// run dfs from head node
		int level =-1; // start from head node , which is level -1
		while(!stack.empty()){
			Long uid = stack.pop(); 
			if( uid == levelNode ){ level -= 1; continue;}
					
			if( uid != headNode ){
				String line ="";
				for( int i = 0 ; i < level ; i++ ){line += "--"; }
				line += String.valueOf( uid );
				if( level == 0 ){ line = "\n" +line ;}
				printStream.println( line );
			}
			// add all successor
			if( tree.getSuccessors(uid )==null ||tree.getSuccessors(uid).size()==0) continue; 
			stack.push( levelNode );
			level+=1 ;
			for( Long next : tree.getSuccessors( uid )){stack.push( next ) ;}
		}
		printStream.close();
	

	}

	/**
	 * write the tree structrue in dfs way.It doesn't handle well if two in link to the same node.
	 * @param	fileName	output file name
	 * @param	tree	tree
	 * */
	public static void writeForestDfs( String fileName , Network tree ) throws Exception {
		// add head link to all roots
		Set<Long> roots = new HashSet<Long>();
		for( Long uid : tree.getVertices() ){
			if( tree.inDegree( uid ) == 0 ){
				roots.add( uid ) ;
			}
		}
		long rid =-1;
		tree.addVertex( headNode );
		for( Long root : roots ){
			tree.addEdge( rid , headNode , root );
			rid--;
		}	
		
		//run 
		writeTreeDfs( fileName , tree , headNode );
		
		//remove head node and adge 
		tree.removeVertex( headNode );
		for( rid += 1  ; rid < 0 ; rid++ ){tree.removeEdge( rid ); } 
		
	
	}
	/**
	 * Write the path one stage nodes for debuging.
	 * @param	fileName	output file name
	 * @param	n	input path
	 * */
	public static void writeOneStage( String fileName  , Network n , Long headNode) throws Exception{
		PrintStream printStream = new PrintStream(
					new FileOutputStream(
					new File( fileName), true));
		printStream.println( String.valueOf(headNode) +":");
		for( Long onestage : n.getSuccessors( headNode )){
			printStream.println("--"+ String.valueOf(onestage));
		}
		printStream.println("");

		printStream.close();
	} 
	/**
	 * Write the link value to file.
	 * @param	fileName	output file name
	 * @param	linkUnits	input link units
	 * */
	public static void writeLinkValue( String fileName, Collection<LinkUnit> linkunits )throws Exception{
		PrintStream printStream = new PrintStream(
					new FileOutputStream(
					new File( fileName) ));
		for( LinkUnit linkunit : linkunits ){
			Long from = linkunit.getFrom();
			Long to = linkunit.getTo();
			double value = linkunit.getValue() ;
			printStream.println( from +"\t" + to+ "\t" + value ) ;
		}
		printStream.close();
	} 
	/**
	 * Read link values from file.
	 * @param	fileName	input file name
	 * */
	public static Collection<LinkUnit> readLinkValue( String fileName ) throws Exception{
		BufferedReader br = new BufferedReader( new FileReader( fileName )) ;	
		String tmpString = br.readLine();
		Collection<LinkUnit> ret= new HashSet<LinkUnit>();
		while( tmpString !=null){
			String[] tmp = tmpString.split("\t");
			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			double value = Double.parseDouble( tmp[2] );
			ret.add( new LinkUnit( from , to , value ));
		}
		br.close();
		return ret ;
	}

	private static Long _getHead( Collection<Edge> graph ){
                Map<Long, Integer> inDegreeCal = new HashMap<Long, Integer>();
                for( Edge edge : graph ){
                        Long from = edge.getFirst();
                        Long to = edge.getSecond();
                        if( !inDegreeCal.containsKey( from) ){
                                inDegreeCal.put(from, 0 );
                        }
                        if( !inDegreeCal.containsKey( to ) ){
                                inDegreeCal.put( to, 0 );
                        }
                        Integer tmpDegree = inDegreeCal.get( to  );
                        tmpDegree += 1 ;
                        inDegreeCal.put( to , tmpDegree );
                }
                System.out.println( "In degree : " + inDegreeCal );
                for( Long key : inDegreeCal.keySet() ){
                        if( inDegreeCal.get( key ) == 0){
                                return key ;
                        }
                }
                return null;
        }
 

	public static Map<Long,Collection<Edge>> readGraphs( String filename ) throws Exception{
                Map<Long,Collection<Edge>> ret = new HashMap<Long,Collection<Edge>>();
                BufferedReader br = new BufferedReader( new FileReader( filename ));
                String tmpString = null;
                Collection< Edge> oneGraphCollection = new HashSet< Edge >();
                Long headNode=null;
                while( ( tmpString = br.readLine()) != null ){
                        //a blank -> new a graph
                        if( tmpString.trim().length() == 0){
                                if( oneGraphCollection.size() != 0 ){
                                        headNode = _getHead( oneGraphCollection );
                                        if( headNode == null ){
                                                System.out.println("error: not found head node");
                                        }
                                        if( headNode > 0 ){
                                                ret.put( headNode , oneGraphCollection );
                                                headNode=null;
                                        }
                                        oneGraphCollection = new HashSet< Edge>();
                                }
                                continue;
                        }
                        // add link to oneGraph
                        String[] tmp = tmpString.trim().split("\t");
                        if( tmp.length <2 ) continue;
                        Long from = Long.parseLong( tmp[0] );
                        Long to = Long.parseLong( tmp[1] );
                        Long time = null;
                       if( tmp.length >= 3) time = Long.parseLong( tmp[2] );

                        oneGraphCollection.add( new Edge( from , to, time ));
                }
                if( oneGraphCollection.size() != 0 ){
                        headNode = _getHead( oneGraphCollection );
                        ret.put( headNode , oneGraphCollection );
                }
                return ret ;
        }


	public static Network readOneForest( String filename ) throws Exception{
		BufferedReader br = new BufferedReader( new FileReader( filename ));
                String tmpString = null;
                Network retG = new Network();
		long eid=0;	
                while( ( tmpString = br.readLine()) != null ){
			String[] tmp = tmpString.trim().split("\t");
			if( tmp.length < 2 ) continue;
			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			retG.addEdge( eid , from , to );
			eid++;
		}
                return retG;
	} 
	/*
	public static Vector< Long> readTimes( String filename ) throws Exception{
		BufferedReader br = new BufferedReader( new FileReader( filename ));
                String tmpString = null;
		Vector<Long> ret = new Vector<Long>(); 
       		while( ( tmpString = br.readLine()) != null ){
			String[] tmp = tmpString.trim().split("\t");
			if( tmp.length < 3 ) continue;
			Long time = Long.parseLong( tmp[2] );
			ret.append( time );
		}
		Collection.sort( ret );
		return ret;
	} */ 
	public static void writeTimes( String filename, Map<Long,Long> user2Time  ) throws Exception {
		PrintStream printStream = new PrintStream(
					new FileOutputStream(
					new File( filename) ));
		for( Long v : user2Time.keySet() ){
			printStream.println( v +"\t"+ user2Time.get(v) );
		}
		printStream.close();

	}
	public static List<UserDate> readUserDate( String filename ) throws Exception{
                BufferedReader br = new BufferedReader( new FileReader( filename ));
		String tmpString=null ;
		List<UserDate> ret = new Vector<UserDate>();

		while( ( tmpString = br.readLine()) != null ){
			String[] tmp = tmpString.trim().split("\t");
			if( tmp.length < 2 ) continue;
			Long user = Long.parseLong( tmp[0] );
			Long time = Long.parseLong( tmp[1] );
			ret.add( new UserDate( user, time ) );
		}
		return ret ;
	}
}

