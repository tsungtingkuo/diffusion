package network;

import java.io.*;
import java.util.*;

public class Message implements Serializable {

	private static final long serialVersionUID = -8931759209379033023L;

	long id = -1;
	long userId = -1;
	int qualifier = -1;
	int lang = -1;
	String content = null;
	int timestamp = -1;
	GregorianCalendar time = null;
	int response = -1;
	int like = -1;
	int conceptId = -1;
	
	public Message(long id, long userId, String content, GregorianCalendar time,
			int response, int like, int conceptId) {
		super();
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.time = time;
		this.response = response;
		this.like = like;
		this.conceptId = conceptId;
	}
	
	public Message(long id, long userId, int qualifier, int lang, String content,
			int timestamp) {
		super();
		this.id = id;
		this.userId = userId;
		this.qualifier = qualifier;
		this.lang = lang;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	
	/**
	 * @return the qualifier
	 */
	public int getQualifier() {
		return qualifier;
	}
	
	/**
	 * @return the lang
	 */
	public int getLang() {
		return lang;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}
	
	/**
	 * @return the response
	 */
	public int getResponse() {
		return response;
	}

	/**
	 * @return the like
	 */
	public int getLike() {
		return like;
	}

	/**
	 * @return the conceptId
	 */
	public int getConceptId() {
		return conceptId;
	}

	
	/**
	 * @return the time
	 */
	public GregorianCalendar getTime() {
		return time;
	}

	/**
	 * @return String format of all Message information
	 * @see java.lang.String#toString()
	 */
	public String toString() {
		return id + "," + userId + "," + qualifier + "," + lang + "," + content + "," + timestamp;
	}
}
