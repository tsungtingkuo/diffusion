package network;

import java.io.*;
import java.util.*;
//import java.text.*;

public class ResponseManager {

	Vector<Response> responses = new Vector<Response>();

	public ResponseManager(String responseFileName, String encoding) throws Exception {
		FileInputStream fis = new FileInputStream(responseFileName);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = lnr.readLine();
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
//			while(t.length < 6) {
//				//System.out.println(sv);
//				sv += lnr.readLine();
//				t = sv.split("\t");
//				//System.out.println(sv);
//			}
			if(t[0].contains("_")) {
				t[0] = t[0].split("_")[2];
			}
			long id = Long.parseLong(t[0]);
//			long id = -1;
//			try {
//				id = Long.parseLong(t[0]);
//			}
//			catch(NumberFormatException nfe) {
//				System.out.println(sv);
//			}
			if(t[1].contains("_")) {
				t[1] = t[1].split("_")[1];
			}
			long messageId = Long.parseLong(t[1]);
			long userId = Long.parseLong(t[2]);
			String content = t[3];
			GregorianCalendar time = Time.parseTime(t[4]);
//			GregorianCalendar time = null;
//			try {
//				time = Time.parseTime(t[4]);
//			}
//			catch(ParseException pe) {
//				System.out.println(sv);
//			}
			int conceptId = Integer.parseInt(t[5]);
			Response r = ResponseFactory.getResponse(id, messageId, userId, content, time, conceptId);
			this.responses.add(r);
		}				
		lnr.close();
		isr.close();
		fis.close();
	}

	public ResponseManager(String responseFileName,
			String qualifierFileName, String langFileName, String encoding) throws Exception {
		ResponseFactory.initialize(qualifierFileName, langFileName);
		FileInputStream fis = new FileInputStream(responseFileName);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = null;
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
			long id = Long.parseLong(t[0]);
			long messageId = Long.parseLong(t[1]);
			long userId = Long.parseLong(t[2]);

			int qualifier = -1;
			if(t[3].length() > 0) {
				qualifier = ResponseFactory.indexOfQualifier(t[3]);
			}
			
			int lang = -1;
			if(t[4].length() > 0) {
				lang = ResponseFactory.indexOfLang(t[4]);
			}
			
			String content = t[5];
			
			int timestamp = Integer.parseInt(t[6]);
			
			Response r = ResponseFactory.getResponse(id, messageId, userId, qualifier, lang, content, timestamp);
			this.responses.add(r);
		}				
		lnr.close();
		isr.close();
		fis.close();
	}

	public Response getResponse(long id) {
		for(Response r : this.responses) {
			if(r.getId() == id) {
				return r;
			}
		}
		return null;		
	}
	
	/**
	 * @return the messages
	 */
	public Vector<Response> getResponses() {
		return responses;
	}
	
	public int getResponsesCount() {
		return responses.size();
	}
	
	public Vector<Response> getResponsesByMessage(long messageId) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(r.getMessageId() == messageId) {
				v.add(r);
			}
		}
		return v;
	}
	
	public Vector<Response> getResponsesByUser(long userId) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(r.getUserId() == userId) {
				v.add(r);
			}
		}
		return v;
	}
	
	public Vector<Response> getResponsesByUserAndMessage(long userId, long messageId) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(r.getUserId()==userId && r.getMessageId()==messageId) {
				v.add(r);
			}
		}
		return v;
	}

	public Vector<Response> getResponsesByUserOrMessage(long userId, long messageId) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(r.getUserId()==userId || r.getMessageId()==messageId) {
				v.add(r);
			}
		}
		return v;
	}

	public Vector<Response> getResponsesByContentKeyword(String keyword) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(r.getContent().contains(keyword)) {
				v.add(r);
			}
		}
		return v;
	}
	
	public static int cleanResponse(boolean isIII, Network n, String inputFileName, String outputFileName, String encoding) throws Exception {
		FileOutputStream fos = new FileOutputStream(outputFileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
		BufferedWriter bw = new BufferedWriter(osw);
		FileInputStream fis = new FileInputStream(inputFileName);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String s = null;
		if(isIII) {
			s = lnr.readLine();
			bw.write(s + "\n");
		}
		int count = 0;
		while ((s=lnr.readLine()) != null) {
			String[] t = s.split("\t");
//			if(isIII) {
//				while(t.length < 6) {
//					//System.out.println(s);
//					s += lnr.readLine();
//					t = s.split("\t");
//					//System.out.println(s);
//				}
//			}
			long userId = Long.parseLong(t[2]);
			User u = n.getUser(userId);
			if(u != null) {
				bw.write(s + "\n");
				count++;
				if(count%10000 == 0) {
					//System.out.println(count);
				}
			}
		}				
		bw.flush();
		
		lnr.close();
		isr.close();
		fis.close();
		bw.close();
		osw.close();
		fos.close();
		return count;
	}
	
	public Vector<Response> getResponsesByTime(GregorianCalendar time, boolean isBefore, boolean isInclusive) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(isBefore==true && isInclusive==true && r.getTime().compareTo(time)<=0) {
				v.add(r);
			}
			else if(isBefore==true && isInclusive==false && r.getTime().compareTo(time)<0) {				
				v.add(r);
			}
			else if(isBefore==false && isInclusive==true && r.getTime().compareTo(time)>=0) {				
				v.add(r);
			}
			else if(isBefore==false && isInclusive==false && r.getTime().compareTo(time)>0) {				
				v.add(r);
			}
		}
		return v;
	}

	public Vector<Response> getResponsesBetweenTime(GregorianCalendar startTime, GregorianCalendar stopTime, boolean startInclusive, boolean stopInclusive) {
		Vector<Response> v = new Vector<Response>();
		for(Response r : this.responses) {
			if(startInclusive==true && r.getTime().compareTo(startTime)>=0 && stopInclusive==true && r.getTime().compareTo(stopTime)<=0) {
				v.add(r);
			}
			else if(startInclusive==true && r.getTime().compareTo(startTime)>=0 && stopInclusive==false && r.getTime().compareTo(stopTime)<0) {				
				v.add(r);
			}
			else if(startInclusive==false && r.getTime().compareTo(startTime)>0 && stopInclusive==true && r.getTime().compareTo(stopTime)<=0) {
				v.add(r);
			}
			else if(startInclusive==false && r.getTime().compareTo(startTime)>0 && stopInclusive==false && r.getTime().compareTo(stopTime)<0) {				
				v.add(r);
			}
		}
		return v;
	}
}
