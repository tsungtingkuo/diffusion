package network;

import java.io.*;
//import java.text.*;
import java.util.*;

public class MessageManager {

	Vector<Message> messages = new Vector<Message>();
	
	public MessageManager(String messageFileName, String encoding) throws Exception {
		FileInputStream fis = new FileInputStream(messageFileName);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = lnr.readLine();
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
//			while(t.length < 7) {
//				//System.out.println(sv);
//				sv += lnr.readLine();
//				t = sv.split("\t");
//				//System.out.println(sv);
//			}
			if(t[0].contains("_")) {
				t[0] = t[0].split("_")[1];
			}
			long id = Long.parseLong(t[0]);
//			long id = -1;
//			try {
//				id = Long.parseLong(t[0]);
//			}
//			catch(NumberFormatException nfe) {
//				System.out.println(sv);
//			}
			long userId = Long.parseLong(t[1]);			
			String content = t[2];
			GregorianCalendar time = Time.parseTime(t[3]);
//			GregorianCalendar time = null;
//			try {
//				time = Time.parseTime(t[3]);
//			}
//			catch(ParseException pe) {
//				System.out.println(sv);
//			}
			int response = Integer.parseInt(t[4]);
			int like = Integer.parseInt(t[5]);
			int conceptId = Integer.parseInt(t[6]);
			Message m = MessageFactory.getMessage(id, userId, content, time, response, like, conceptId);
			this.messages.add(m);
		}				
		lnr.close();
		isr.close();
		fis.close();
	}
	
	public MessageManager(String messageFileName,
			String qualifierFileName, String langFileName, String encoding) throws Exception {
		MessageFactory.initialize(qualifierFileName, langFileName);
		FileInputStream fis = new FileInputStream(messageFileName);
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = null;
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
			long id = Long.parseLong(t[0]);
			long userId = Long.parseLong(t[1]);

			int qualifier = -1;
			if(t[2].length() > 0) {
				qualifier = MessageFactory.indexOfQualifier(t[2]);
			}
			
			int lang = -1;
			if(t[3].length() > 0) {
				lang = MessageFactory.indexOfLang(t[3]);
			}
			
			String content = t[4];
			
			int timestamp = Integer.parseInt(t[5]);
			
			Message m = MessageFactory.getMessage(id, userId, qualifier, lang, content, timestamp);
			this.messages.add(m);
		}				
		lnr.close();
		isr.close();
		fis.close();
	}

	public Message getMessage(long id) {
		for(Message m : this.messages) {
			if(m.getId() == id) {
				return m;
			}
		}
		return null;		
	}
	
	/**
	 * @return the messages
	 */
	public Vector<Message> getMessages() {
		return messages;
	}
	
	public int getMessagesCount() {
		return messages.size();
	}
	
	public Vector<Message> getMessagesByUser(long userId) {
		Vector<Message> v = new Vector<Message>();
		for(Message m : this.messages) {
			if(m.getUserId() == userId) {
				v.add(m);
			}
		}
		return v;
	}
	
	public Vector<Message> getMessagesByContentKeyword(String keyword) {
		Vector<Message> v = new Vector<Message>();
		for(Message m : this.messages) {
			if(m.getContent().contains(keyword)) {
				v.add(m);
			}
		}
		return v;
	}
	
	public static int cleanMessage(boolean isIII, Network n, String inputFileName, String outputFileName, String encoding) throws Exception {
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
//				while(t.length < 7) {
//					//System.out.println(s);
//					s += lnr.readLine();
//					t = s.split("\t");
//					//System.out.println(s);
//				}
//			}
			long userId = Long.parseLong(t[1]);
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
}
