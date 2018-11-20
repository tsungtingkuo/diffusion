package network;

import java.util.*;
import utility.Utility;

public class ResponseFactory {

	static boolean initialized = false;

	static Vector<String> qualifier = null;
	static Vector<String> lang = null;

	public static String getQualifier(int index) {
		return qualifier.get(index);
	}

	public static String getLang(int index) {
		return lang.get(index);
	}
	
	public static int indexOfQualifier(String name) {
		return qualifier.indexOf(name);
	}
	
	public static int indexOfLang(String name) {
		return lang.indexOf(name);
	}
	
	// Must be initialized first
	public static void initialize(String qualifierFileName, String langFileName) throws Exception {
		qualifier = Utility.loadVector(qualifierFileName);
		lang = Utility.loadVector(langFileName);
		initialized = true;
	}

	// Use this instead of create Response directly
	public static Response getResponse(long id, long messageId, long userId, String content, GregorianCalendar time, int conceptId) {
		return new Response(id, messageId, userId, content, time, conceptId);
	}
	
	// Use this instead of create Response directly
	public static Response getResponse(long id, long messageId, long userId, int qualifier, int lang, String content, int timestamp) {
		if(isInitialized()) {
			return new Response(id, messageId, userId, qualifier, lang, content, timestamp);
		}
		else {
			System.out.println("Not initialized, returning NULL. Please call initialize() first.");
			return null;
		}
	}
	
	/**
	 * @return the initialized
	 */
	public static boolean isInitialized() {
		return initialized;
	}
}
