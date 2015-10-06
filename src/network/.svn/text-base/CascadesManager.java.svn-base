package network;

import java.io.*;
import java.util.*;
import utility.*;

public class CascadesManager {

	String encoding = "UTF-8";
	TreeMap<Integer, Vector<Cascade>> cc = new TreeMap<Integer, Vector<Cascade>>();
		
	public CascadesManager(String encoding) {
		super();
		this.encoding = encoding;
	}

	public int saveCascades(int concept, String fileName) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos, this.encoding);
		BufferedWriter bw = new BufferedWriter(osw);
		for(Cascade c : this.cc.get(concept)) {
			bw.write(c.getSourceUserId() + "\t" + c.getDestUserId() + "\t" + Time.formatTime(c.getTime()) + "\t" + c.getContent() + "\n");
		}
		bw.flush();
		bw.close();
		osw.close();
		fos.close();		
		return this.cc.get(concept).size();
	}
	
	public int loadCascades(int concept, String fileName) throws Exception {
		FileInputStream fis = new FileInputStream(fileName);
		InputStreamReader isr = new InputStreamReader(fis, this.encoding);
		LineNumberReader lnr = new LineNumberReader(isr);
		String s = null;
		int i=0;
		while ((s=lnr.readLine()) != null) {
			String[] t = s.split("\t");
			long sourceUserId = Long.parseLong(t[0]); 
			long destUserId = Long.parseLong(t[1]); 
			GregorianCalendar time = Time.parseTime(t[2]);
			String content = t[3];
			Cascade c = new Cascade(sourceUserId, destUserId, time, content);
			this.putCascade(concept, c);
			i++;
		}
		lnr.close();
		isr.close();
		fis.close();
		return i;
	}
	
	public boolean putCascade(int concept, Cascade c) {
		if(this.cc.containsKey(concept)) {
			this.cc.get(concept).add(c);
			return true;
		}
		else {
			Vector<Cascade> v = new Vector<Cascade>();
			v.add(c);
			this.cc.put(concept, v);
			return false;
		}
	}
	
	public Vector<Cascade> getCascades(int concept) {
		return this.cc.get(concept);
	}
	
	public int size() {
		return this.cc.size();
	}
	
	public int[] getConcepts() {
		return Utility.integerVectorToIntegerArray(new Vector<Integer>(this.cc.keySet()));
	}
	
	public void sortCascades(int concept) {
		Collections.sort(this.cc.get(concept));
	}
	
	public void sortCascades() {
		for(int concept : this.cc.keySet()) {
			Collections.sort(this.cc.get(concept));
		}
	}

	public void filterCascades(int concept, DiffusionNetwork dntrain) {
		Vector<Cascade> v = new Vector<Cascade>();
		for(Cascade c : this.cc.get(concept)) {
			if(dntrain.findEdge(c.getSourceUserId(), c.getDestUserId()) != null) {
				v.add(c);
			}
		}
		this.cc.put(concept, v);
	}
	
	public void filterCascades(int concept, long start, long stop) {
		Vector<Cascade> v = new Vector<Cascade>();
		for(Cascade c : this.cc.get(concept)) {
			long time = c.getTime().getTimeInMillis();
			if(time>=start && time<stop) {
				v.add(c);
			}
		}
		this.cc.put(concept, v);
	}
}
