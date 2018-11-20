package network;

import java.io.*;
import java.util.*;
import utility.Utility;

public class UserRegistrationTimeManager {

	public static void computeAndSaveUserRegistrationTime(Network n, String messageFileName, String responseFileName, String registrationTimeFileName) throws Exception {
		n = computeUserRegistrationTimeUsingMessage(n, messageFileName);
		n = computeUserRegistrationTimeUsingResponse(n, responseFileName);
		saveUserRegistrationTime(n, registrationTimeFileName);
	}

	public static void saveUserRegistrationTime(Network n, String fileName) throws Exception {
		TreeMap<Long, Integer> tm = new TreeMap<Long, Integer>();
		for(long id : n.getVertices()) {
			User u = n.getUser(id);
			tm.put(id, u.getRegistrationTime());
		}
		Utility.saveLongToIntegerTreeMap(tm, fileName);
	}
	
	public static Network computeUserRegistrationTimeUsingMessage(Network n, String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = null;
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
			long userId = Long.parseLong(t[1]);
			int timestamp = Integer.parseInt(t[5]);
			User u = n.getUser(userId);
			if(u != null) {
				int registrationTime = u.getRegistrationTime();
				if(registrationTime==-1 || registrationTime>timestamp) {
					u.setRegistrationTime(timestamp);
				}
			}
			else {
				System.out.println("NULL, id = " + userId);
			}
		}				
		lnr.close();
		isr.close();
		fis.close();
		return n;
	}

	public static Network computeUserRegistrationTimeUsingResponse(Network n, String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = null;
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split("\t");
			long userId = Long.parseLong(t[2]);
			int timestamp = Integer.parseInt(t[6]);
			User u = n.getUser(userId);
			if(u != null) {
				int registrationTime = u.getRegistrationTime();
				if(registrationTime==-1 || registrationTime>timestamp) {
					u.setRegistrationTime(timestamp);
				}
			}
			else {
				System.out.println("NULL, id = " + userId);
			}
		}				
		lnr.close();
		isr.close();
		fis.close();
		return n;
	}
}
