package network;

import java.util.*;

public class Cascade implements Comparable<Cascade> {

	long sourceUserId = -1;
	long destUserId = -1;
	GregorianCalendar time = null;
	String content = null;
	
	public Cascade(long sourceUserId, long destUserId, GregorianCalendar time,
			String content) {
		super();
		this.sourceUserId = sourceUserId;
		this.destUserId = destUserId;
		this.time = time;
		this.content = content;
	}

	public String getUserIdString() {
		return this.sourceUserId + "\t" + this.destUserId;
	}
	
	@Override
	public int compareTo(Cascade c) {
		return this.getTime().compareTo(c.getTime());
	}

	/**
	 * @return the sourceUserId
	 */
	public long getSourceUserId() {
		return sourceUserId;
	}

	/**
	 * @return the destUserId
	 */
	public long getDestUserId() {
		return destUserId;
	}

	/**
	 * @return the time
	 */
	public GregorianCalendar getTime() {
		return time;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
}
