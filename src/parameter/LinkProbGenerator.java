package parameter;

import network.*;
import java.util.*;

import utilities.LinkUnit;

public class LinkProbGenerator{
	//<messageId, poer> 
	public static Map<Long,Long > getMessagePair( String filename ,String qualifierFilename , String languageFilename)throws Exception{
		// Encoding
		String encoding = "UTF-8";
		
		Map<Long, Long> ret = new HashMap< Long,Long>();
		MessageManager m_m = new MessageManager( filename , qualifierFilename, languageFilename, encoding);
		Vector<Message> messages = m_m.getMessages() ;
		for( Message m : messages) {
			ret.put( m.getId(), m.getUserId() );
		}
		messages = null;
		return ret ;
	}
	
	//<messageId, poer>
	public static  Map<Long, Long> getMessagePair( String filename ) throws Exception{
		// Encoding
		String encoding = "UTF-8";
		
	//	BufferedReader reader = new BufferedReader( new FileReader( filename ) );
		Map<Long, Long> ret = new HashMap< Long, Long>();
	/*	while( true ){
			String line = reader.readLine();
			if( line == null) break;
			String[] tmp  = line.trim().split("\t");
			Long messageId = Long.parseInt( tmp[0] );
			Long poerId = Long.parseInt( tmp[1] );
			ret.put( messageId , poerId );
		}		
	*/
		MessageManager m_m = new MessageManager(filename, encoding);
		Vector<Message> messages = m_m.getMessages();
		for( Message m : messages ) {
			ret.put( m.getId() , m.getUserId() );
		}	
		messages = null;
		return ret;
	}
	public static Map<Long,Collection<Long> > getResponseCollection( String filename , String qualifierFilename , String languageFilename )throws Exception{
		// Encoding
		String encoding = "UTF-8";
		
		Map<Long,Collection<Long> > ret = new HashMap<Long, Collection<Long>>();
		ResponseManager r_m = new ResponseManager( filename, qualifierFilename, languageFilename, encoding ) ; 
		Vector<Response> responses = r_m.getResponses();
		for( Response r : responses ) {
			Long mid = r.getMessageId( );
			Long uid = r.getUserId();
			Collection<Long> tmpCollection = ret.get( mid );
			if( tmpCollection == null){
				tmpCollection = new HashSet<Long>();
				ret.put( mid, tmpCollection );
			}
			tmpCollection.add( uid );
		}
		return ret; 	
	

	}
	//<messageId, resposner>
	public static  Map<Long,Collection<Long> > getResponseCollection( String filename ) throws Exception{	
		// Encoding
		String encoding = "UTF-8";
		
		//BufferedReader reader = new BufferedReader( new FileReader( filename ) );
		Map<Long, Collection<Long> > ret = new HashMap< Long, Collection<Long>>();
		/*while( true ){
			String line = reader.readLine();
			if( line == null) break;
			String[] tmp  = line.trim().split("\t");
			Long messageId = Long.parseInt( tmp[1] );
			Long responserId = Long.parseInt( tmp[2] );
			Collection<Long> tmpCollection = ret.get( messageId );
			if( tmpCollection == null){
				tmpCollection = new HashSet<Long>();
				ret.put( messageId , tmpCollection );
			}
			tmpCollection.add( responserId ); 
		}*/
		ResponseManager r_m = new ResponseManager( filename, encoding ) ; 
		Vector<Response> responses = r_m.getResponses();
		for( Response r : responses ) {
			Long mid = r.getMessageId( );
			Long uid = r.getUserId();
			Collection<Long> tmpCollection = ret.get( mid );
			if( tmpCollection == null){
				tmpCollection = new HashSet<Long>();
				ret.put( mid, tmpCollection );
			}
			tmpCollection.add( uid );
		}
		return ret; 	
	}   
	// < Responser , Map< Poer, count>  > 
	public static  Map< Long, Map<Long,Integer> > concateLink(
			Map<Long, Long> messagePair ,
			Map<Long, Collection<Long> > responseCollection 
			) throws Exception{
		Map< Long, Map< Long, Integer> > ret = new HashMap<Long, Map<Long,Integer>>();
		for( Long messageId : messagePair.keySet() ){
			Long poer = messagePair.get( messageId );
			Collection<Long> responsers = responseCollection.get( messageId );
			if( responsers == null  ){continue;} 
			for( Long responser : responsers ){
				Map<Long,Integer> responseCount =  ret.get( responser );
				if( responseCount == null){
					responseCount = new HashMap<Long,Integer>();
					responseCount.put( poer , 0 ); 
					ret.put( responser, responseCount );
				} 
				Integer count = responseCount.get( poer ) ;
				if( count == null) count = 0;
				count += 1 ;
				responseCount.put( poer, count );
			}

		}
		return ret;
	}

	/**
	 * @param	responseCounts	get from "concateLink()" predictor. ket= responser, value=Map<poer,counts>  
	 * */
	public static Collection<LinkUnit> getLoyalty( Map<Long, Map<Long,Integer>> responseCounts ) {
		Collection<LinkUnit> loyaltys = new HashSet<LinkUnit>();
		for( Long responser : responseCounts.keySet() ){
			Map<Long,Integer> responseCount = responseCounts.get(responser );
			// get total counts
			double totalcounts = 0.0; 
			for( Integer count : responseCount.values() ){
				totalcounts += (double) count ;
			} 
			for( Long poer : responseCount.keySet() ){
				double count  = (double) responseCount.get( poer );
				double loyalty = count / totalcounts;
				LinkUnit linkunit = new LinkUnit( poer,  responser , loyalty ); 
				loyaltys.add( linkunit );
			}
		}
		return loyaltys ;
	}
	/**
	 * calculate responseness. and it will normalize for each node on the base of in-edge
	 * @param	messagePair get from "messagePair()"
	 * @param	responseCounts get from "concateLink()"
	 * */
	public static Collection<LinkUnit> getResponseness( Map<Long,Long> messagePair, Map< Long, Map<Long,Integer> > responseCounts ){
		Collection<LinkUnit> ret =  new HashSet<LinkUnit>();
		// get message counts
		Map<Long, Integer> poCounts = new HashMap<Long,Integer>(); 
		for( Long messageId : messagePair.keySet() ){
			Long poer = messagePair.get( messageId );
			Integer poCount = poCounts.get( poer );
			if( poCount == null ){poCount = 0;}
			poCount = poCount + 1;
			poCounts.put( poer , poCount );
		} 
		// calculate responseness
		for( Long responser : responseCounts.keySet() ){
			Map<Long,Integer> responseCount = responseCounts.get(responser );
			// < poer, responseness>
			Map<Long,Double>  rawresponseness = new HashMap<Long, Double>(); 
			double totalresponseness = 0.0;
			for( Long poer : responseCount.keySet() ){
				double count  = (double) responseCount.get( poer );
				double poCount =(double) poCounts.get( poer ); 
				double responseness = count / poCount ;
				totalresponseness += responseness ;
				rawresponseness.put( poer, responseness );
			}
			// normalized it 
			for( Long poer : rawresponseness.keySet() ){
				double normalizedresponseness = rawresponseness.get( poer ) / totalresponseness   ;  
				LinkUnit linkunit = new LinkUnit( poer, responser , normalizedresponseness);
				ret.add( linkunit );
			}	
		}
		return ret ;	
	} 


} 
