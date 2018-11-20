package utilities;

import java.io.*;
import java.util.*;
import network.*;

public class TimeUtility{
	public static StartTimeAndEndTime getMessageStartAndEndTime( String filename ){
		BufferedReader br =null;
		try{ 
			br =new BufferedReader( new FileReader( filename )) ;}
		catch( Exception e ){
			e.printStackTrace();
		}
		
		String tmpString =null;
		try{
			tmpString =br.readLine();
		}catch( Exception e ) {
			e.printStackTrace();
		}
		
		boolean isFirst = true;
		Long startTime =(long) 0, endTime=(long) 0 ;
		while( tmpString !=null){
			String[] tmp = tmpString.split("\t");
			Long time =null;
			try{ 
				time = Long.parseLong( tmp[5] );
			}catch(Exception e ){
				continue;
			}
			if( isFirst ){
				startTime = time ;
				endTime = time ;
				isFirst =false;;
			} 
			if( time < startTime ){ startTime = time ;}
			if( endTime < time ){endTime = time ;}
			try{
				tmpString= br.readLine();
			}catch( Exception e ){
				e.printStackTrace();
			}
		}
		try{
			br.close();
		}catch( Exception e ){
			e.printStackTrace();
		}
		return new StartTimeAndEndTime( startTime *1000 , endTime *1000 ) ;
		
	}

	/**
	 * return should be in millis-second
	 *
	 * */
	public static StartTimeAndEndTime getResponseStartAndEndTime( String filename ){
	
		BufferedReader br =null;
		try{ 
			br =new BufferedReader( new FileReader( filename )) ;}
		catch( Exception e ){
			e.printStackTrace();
		}
		String tmpString =null;
		try{ tmpString =  br.readLine();}catch(Exception e ){ e.printStackTrace() ; }
		
		boolean isFirst = true;
		Long startTime=(long)0  , endTime=(long) 0 ;
		while( tmpString !=null){
			String[] tmp = tmpString.split("\t");
			Long time =null;
			try{ 
				time = Long.parseLong( tmp[6] );
			}catch(Exception e ){
				continue;
			}
			if( isFirst ){
				startTime = time ;
				endTime = time ;
				isFirst =false;;
			} 
			if( time < startTime ){ startTime = time ;}
			if( endTime < time ){endTime = time ;}
			
			try{
				tmpString= br.readLine();
			}catch( Exception  e ){
				e.printStackTrace();
			}
		}
		try{
			br.close();
		}catch( Exception e ){
			e.printStackTrace();
		}
		return new StartTimeAndEndTime( startTime *1000 , endTime*1000 ) ;
	}

	public static StartTimeAndEndTime getMessageStartAndEndTime( boolean isIII, Calendar floorTime , MessageManager m_m){
		Collection<Message> messages = m_m.getMessages( );
		Long startTime = (long)0, endTime= (long)0  ;
		boolean isFirst= true;
		int outside_messages = 0;
		for( Message m : messages ){
			Long time=null ;
			if( isIII ){
				time = (long) m.getTime().getTimeInMillis();  
			}else{
				time = (long)m.getTimestamp() * 1000;
			}
			if(  m.getTime().before( floorTime ) ||
				m.getTime().getTimeInMillis() > System.currentTimeMillis()
					){
				System.out.println("Error:  message time stamp.");
				System.out.println( m.getId() + "\t" + m.getTime() );
				outside_messages += 1;
				continue;
			}
			
			//System.out.println( time );
			if( isFirst ){
				startTime = time;
				endTime = time;
				isFirst= false;
				continue;
			}
			if( time < startTime ){
				startTime = time ;
			}
			if( time > endTime ){
				endTime = time ;
			}
		}
		System.out.println("Outside message : "+ outside_messages );
		return new StartTimeAndEndTime( startTime , endTime );
	}


	public static StartTimeAndEndTime getResponseStartAndEndTime( boolean isIII, Calendar floorTime , ResponseManager r_m  ){
			
		Collection<Response> responses = r_m.getResponses( );
		Long startTime = (long)0 , endTime =(long)0  ;
		boolean isFirst= true;
		int outside_responses = 0;
		for( Response r : responses  ){
			
			Long time = null;
			if( isIII ){
				time = (long) r.getTime().getTimeInMillis(); 
			}else{
				time = (long) r.getTimestamp() * 1000;
			}
			if(   r.getTime().before( floorTime ) || 
				r.getTime().getTimeInMillis() > System.currentTimeMillis()  ){
				System.out.println("Error: eror response time stamp.");
				System.out.println( r.getId() + "\t" + r.getTime() );
				outside_responses += 1;
				continue;
			}
			

			if( isFirst ){
				startTime = time;
				endTime = time;
				isFirst= false;
				continue;
			}
			if( time < startTime ){
				startTime = time ;
			}
			if( time > endTime ){
				endTime = time ;
			}

		}
		System.out.println( "OutSide Response" + outside_responses );
		return new StartTimeAndEndTime( startTime , endTime );
	}

	public static StartTimeAndEndTime getMessageResponseStartAndEndTime(boolean isIII,  MessageManager m_m , ResponseManager r_m  , Calendar floorTime){
		StartTimeAndEndTime messageTimes = TimeUtility.getMessageStartAndEndTime( isIII, floorTime ,m_m);
		StartTimeAndEndTime responseTimes = TimeUtility.getResponseStartAndEndTime( isIII, floorTime, r_m  );
		Long startTime=(long)0, endTime=(long)0 ;
		if( messageTimes.startTime < responseTimes.startTime ){
			startTime = messageTimes.startTime ;
		}else{
			startTime = responseTimes.startTime ;
		}

		if( responseTimes.endTime > messageTimes.endTime ){
			endTime = responseTimes.endTime ;
		}else{
			endTime = messageTimes.endTime;
		}
		return new StartTimeAndEndTime( startTime, endTime );
	
	}

	public static StartTimeAndEndTime getMessageResponseStartAndEndTime( String messageFilename , String responseFilename , String qualifierFilename , String langFilename ){
		//MessageManager m_m = null;
		//ResponseManager r_m = null;
		/*
		try{
			m_m = new MessageManager( messageFilename , qualifierFilename , langFilename );
			r_m = new ResponseManager( responseFilename , qualifierFilename, langFilename );
		}catch( Exception e ){
			e.printStackTrace();
		}

		StartTimeAndEndTime messageTimes = TimeUtility.getMessageStartAndEndTime( m_m );
		StartTimeAndEndTime responseTimes = TimeUtility.getResponseStartAndEndTime( r_m );
		*/
		StartTimeAndEndTime messageTimes = TimeUtility.getMessageStartAndEndTime( messageFilename );
		StartTimeAndEndTime responseTimes = TimeUtility.getResponseStartAndEndTime( responseFilename  );
		Long startTime=(long)0, endTime=(long)0 ;
		if( messageTimes.startTime < responseTimes.startTime ){
			startTime = messageTimes.startTime ;
		}else{
			startTime = responseTimes.startTime ;
		}

		if( responseTimes.endTime > messageTimes.endTime ){
			endTime = responseTimes.endTime ;
		}else{
			endTime = messageTimes.endTime;
		}
		return new StartTimeAndEndTime( startTime, endTime );
	}
	
	/**
	 * for iii.plurk
	 */
	public static StartTimeAndEndTime getMessageResponseStartAndEndTime( String messageFilename , String responseFilename , Calendar floorTime ){
		MessageManager m_m=null;
		ResponseManager r_m =null;
		try{
			// Encoding
			String encoding = "UTF-8";
			
			m_m = new MessageManager( messageFilename , encoding);
			r_m = new ResponseManager( responseFilename , encoding);
		}catch( Exception e ){
			e.printStackTrace();
			return null;
		}
		boolean isIII= true;
		StartTimeAndEndTime messageTimes = TimeUtility.getMessageStartAndEndTime( isIII, floorTime , m_m );
		StartTimeAndEndTime responseTimes = TimeUtility.getResponseStartAndEndTime( isIII,  floorTime , r_m );

		Long startTime=(long)0, endTime=(long)0 ;
		if( messageTimes.startTime < responseTimes.startTime ){
			startTime = messageTimes.startTime ;
		}else{
			startTime = responseTimes.startTime ;
		}

		if( responseTimes.endTime > messageTimes.endTime ){
			endTime = responseTimes.endTime ;
		}else{
			endTime = messageTimes.endTime;
		}
		return new StartTimeAndEndTime( startTime, endTime );
	}


}
