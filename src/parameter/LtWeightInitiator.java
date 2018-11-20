package parameter;

import network.*;
import java.io.*;

public class LtWeightInitiator{
/**
 *	Assign Ic Probability ofr network n.
 *	@param n input network
 *	@param w init weight 
 * */
	public static Network initParam( Network n , double w ){
		for( Long rid : n.getEdges() ){
			Relation r = n.getRelation( rid ) ;
			r.setLtWeight( w );
		}
		return n ;
	}
	public static Network initParamFromFile( Network n , String fileName )throws Exception{
		n = LtWeightInitiator.initParam( n , 0.0 );

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
			if( rid == null) continue;
			Relation r = n.getRelation( rid ) ;
			r.setLtWeight( value );
		}
		br.close();
		
		/*Collection<LinkUnit> linkWeights = IOUtility.readLinkValue( fileName );
		for( LinkUnit linkunit : linkWeights ){
			Integer from = linkunit.getFrom();
			Integer to = linkunit.getTo();
			double value = linkunit.getValue();
			Integer rid = n.findEdge( from , to );
			Relation r = n.getRelation( rid );
			r.setLtWeight( value );
		} 
		*/
		return n;
	}    
}
