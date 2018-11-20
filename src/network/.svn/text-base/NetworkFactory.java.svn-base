package network;

import java.io.*;

public class NetworkFactory {

	public static Network loadUser(boolean isIII, Network n, String userFileName,
			String countryFileName, String cityFileName,
			String stateFileName, String relationshipFileName,
			String userRegistrationTimeFileName) throws Exception {
		if(isIII) {
			n = loadUser(true, n, userFileName);
		}
		else {
			n = loadUser(false, n, userFileName, countryFileName, cityFileName, stateFileName, relationshipFileName);
			n = loadUserRegistrationTime(n, userRegistrationTimeFileName);
		}
		return n;
	}
	
	public static Network loadUserRegistrationTime(Network n, String userRegistrationTimeFileName) throws Exception {
		FileInputStream fis = new FileInputStream(userRegistrationTimeFileName);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		LineNumberReader lnr = new LineNumberReader(isr);
		String sv = null;
		while ((sv=lnr.readLine()) != null) {
			String[] t = sv.split(",");
			int id = Integer.parseInt(t[0]);
			int registrationTime = Integer.parseInt(t[1]);
			User u = n.getUser(id);
			u.setRegistrationTime(registrationTime);
		}				
		lnr.close();
		isr.close();
		fis.close();
		return n;
	}
	
	public static Network loadUser(boolean isIII, Network n, String userFileName,
			String countryFileName, String cityFileName,
			String stateFileName, String relationshipFileName) throws Exception {
		if(isIII) {
			n = loadUser(true, n, userFileName);
		}
		else {
			UserFactory.initialize(countryFileName, cityFileName, stateFileName, relationshipFileName);
			FileInputStream fis = new FileInputStream(userFileName);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			LineNumberReader lnr = new LineNumberReader(isr);
			String sv = null;
			while ((sv=lnr.readLine()) != null) {
				String[] t = sv.split("\t");
				long id = Long.parseLong(t[0]);
				String displayName = t[1];
				String nickName = t[2];
				
				int locationCountry = -1;
				if(t[3].length() > 0) {
					locationCountry = UserFactory.indexOfLocationCountry(t[3]);
				}
				
				int locationCity = -1;
				if(t[4].length() > 0) {
					locationCity = UserFactory.indexOfLocationCity(t[4]);
				}
				
				int locationState = -1;
				if(t[5].length() > 0) {
					locationState = UserFactory.indexOfLocationState(t[5]);
				}
				
				int dateOfBirth = -1;
				if(t[6].length()>0 && t[6]!="0000-00-00") {
					dateOfBirth = Integer.parseInt(t[6].replaceAll("-", ""));
				}
				
				int relationship = -1;
				if(t.length==8 && t[7].length()>0) {
					relationship = UserFactory.indexOfRelationship(t[7]);
				}
				
				User u = UserFactory.getUser(id, displayName, nickName,
						locationCountry, locationCity, locationState,
						dateOfBirth, relationship);
				n.addVertex(id);
				n.addUser(u);
			}				
			lnr.close();
			isr.close();
			fis.close();
			//System.out.println("User = " + n.getUserCount());
		}
		return n;
	}
	
	public static Network loadUser(boolean isIII, Network n, String userFileName) throws Exception {
		FileReader frv = new FileReader(userFileName);
		LineNumberReader lnrv = new LineNumberReader(frv);
		String sv = null;
		if(isIII) {
			sv = lnrv.readLine();
		}
		while ((sv=lnrv.readLine()) != null) {
			String[] t = sv.split("\t");
			long id = Long.parseLong(t[0]);
			User u = UserFactory.getUser(id);
			n.addVertex(id);
			n.addUser(u);
		}				
		lnrv.close();
		frv.close();
		//System.out.println("User = " + n.getUserCount());
		return n;
	}

	public static Network loadRelation(boolean isIII, Network n, String relationFileName) throws Exception {
		FileReader fre = new FileReader(relationFileName);
		LineNumberReader lnre = new LineNumberReader(fre);
		String se = null;
		if(isIII) {
			se = lnre.readLine();
		}
		while ((se=lnre.readLine()) != null) {
			Relation r = RelationFactory.getRelation();
			String[] t = se.split("\t");
			long sourceId = Long.parseLong(t[0]);
			if(n.getUser(sourceId) == null) {
				n.addUser(new User(sourceId));
				//System.out.println("MISS: " + sourceId);
			}
			long destinationId = Long.parseLong(t[1]);
			if(n.getUser(destinationId) == null) {
				n.addUser(new User(destinationId));
				//System.out.println("MISS: " + destinationId);
			}
			boolean result = n.addEdge(r.getId(), sourceId, destinationId);
			if(!result) {
				//System.out.println("REPEAT: " + sourceId + " -> " + destinationId);
			}
			n.addRelation(r);
		}				
		lnre.close();
		fre.close();
		//System.out.println("Relation = " + n.getRelationCount());
		return n;
	}

	public static Network getNetwork(boolean isIII, String userFileName, String relationFileName) throws Exception {
		Network n = new Network();
		n = loadUser(isIII, n, userFileName);		
		n = loadRelation(isIII, n, relationFileName);
		return n;
	}
	
	public static Network getNetwork(boolean isIII, String userFileName, String relationFileName,
			String countryFileName, String cityFileName,
			String stateFileName, String relationshipFileName) throws Exception {
		Network n = new Network();
		n = loadUser(isIII, n, userFileName, countryFileName, cityFileName, stateFileName, relationshipFileName);
		n = loadRelation(isIII, n, relationFileName);
		return n;
	}
	
	public static Network getNetwork(boolean isIII, String userFileName, String relationFileName,
			String countryFileName, String cityFileName,
			String stateFileName, String relationshipFileName,
			String userRegistrationTimeFileName) throws Exception {
		Network n = new Network();
		n = loadUser(isIII, n, userFileName, countryFileName, cityFileName, stateFileName, relationshipFileName, userRegistrationTimeFileName);
		n = loadRelation(isIII, n, relationFileName);
		return n;
	}
	
	public static int saveRelation(Network n, String relationFileName) throws Exception {
		PrintWriter pw = new PrintWriter(relationFileName);
		int count = 0;
		for(long edge : n.getEdges()) {
			long sourceId = n.getSource(edge);
			long destinationId = n.getDest(edge);
			pw.println(sourceId + "\t" + destinationId);
			count++;
		}
		pw.flush();
		pw.close();
		return count;
	}
}
