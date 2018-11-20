package network;

import java.io.*;
import java.util.GregorianCalendar;

public class Response implements Serializable {

	private static final long serialVersionUID = 6088328568199685034L;

	long id = -1;
	long messageId = -1;
	long userId = -1;
	int qualifier = -1;
	int lang = -1;
	String content = null;
	int timestamp = -1;
	GregorianCalendar time = null;
	int conceptId = -1;

	public Response(long id, long messageId, long userId, String content,
			GregorianCalendar time, int conceptId) {
		super();
		this.id = id;
		this.messageId = messageId;
		this.userId = userId;
		this.content = content;
		this.time = time;
		this.conceptId = conceptId;
	}
	
	public Response(long id, long messageId, long userId, int qualifier, int lang, String content,
			int timestamp) {
		super();
		this.id = id;
		this.messageId = messageId;
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
	 * @return the messageId
	 */
	public long getMessageId() {
		return messageId;
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
	 * @return the time
	 */
	public GregorianCalendar getTime() {
		return time;
	}

	/**
	 * @return the conceptId
	 */
	public int getConceptId() {
		return conceptId;
	}

	/**
	 * @return String format of all Response information
	 * @see java.lang.String#toString()
	 */
	public String toString() {
		return id + "," + messageId + "," + userId + "," + qualifier + "," + lang + "," + content + "," + timestamp;
	}
}
