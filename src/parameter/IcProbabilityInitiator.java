package parameter;

import network.*;
import java.io.*;

public class IcProbabilityInitiator{
/**
 *	Assign Ic Probability ofr network n.
 *	The Lt threshold value = v 
 *	@param n input network
 *	@param p lt threshold
 * */
	public static Network initParam( Network n , double p ){
		for( Long rid : n.getEdges() ){
			Relation r = n.getRelation( rid ) ;
			r.setIcProbability( p );
		}
		return n ;
	}
	public static Network initParamFromFile( Network n , String fileName )throws Exception{
		n = IcProbabilityInitiator.initParam( n , 0.0 );
		
		BufferedReader br = new BufferedReader( new FileReader( fileName )) ;	
	 	String tmpLine = null;
		while( true ){
			tmpLine = br.readLine();
			if( tmpLine == null ) break;
			String[] tmp = tmpLine.split("\t");
			Long from = Long.parseLong( tmp[0] );
			Long to = Long.parseLong( tmp[1] );
			double value = Double.parseDouble( tmp[2] );
			Long rid = n.findEdge( from, to );
			if( rid == null ) continue;
			Relation r = n.getRelation( rid ) ;
			r.setIcProbability( value );
		}
		br.close();
		
		
		/*Collection<LinkUnit> linkProbs = IOUtility.readLinkValue( fileName );
		for( LinkUnit linkunit : linkProbs ){
			Long from = linkunit.getFrom();
			Long to = linkunit.getTo();
			double value = linkunit.getValue();
			Long rid = n.findEdge( from , to );
			Relation r = n.getRelation( rid );
			r.setIcProbability( value );
		} 
		*/
		return n;
	}    
}
