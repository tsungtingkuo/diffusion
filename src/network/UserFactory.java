package network;

import java.util.Vector;

import utility.Utility;

public class UserFactory {
	
	static boolean initialized = false;

	static Vector<String> locationCountry = null;
	static Vector<String> locationCity = null;
	static Vector<String> locationState = null;
	static Vector<String> relationship = null;

	public static String getLocationCountry(int index) {
		return locationCountry.get(index);
	}
	
	public static String getLocationCity(int index) {
		return locationCity.get(index);
	}
	
	public static String getLocationState(int index) {
		return locationState.get(index);
	}
	
	public static String getRelationship(int index) {
		return relationship.get(index);
	}
	
	public static int indexOfLocationCountry(String name) {
		return locationCountry.indexOf(name);
	}

	public static int indexOfLocationCity(String name) {
		return locationCity.indexOf(name);
	}

	public static int indexOfLocationState(String name) {
		return locationState.indexOf(name);
	}

	public static int indexOfRelationship(String name) {
		return relationship.indexOf(name);
	}
	
	// Must be initialized first
	public static void initialize(String countryFileName, String cityFileName, String stateFileName, String relationshipFileName) throws Exception {
		locationCountry = Utility.loadVector(countryFileName);
		locationCity = Utility.loadVector(cityFileName);
		locationState = Utility.loadVector(stateFileName);
		relationship = Utility.loadVector(relationshipFileName);
		initialized = true;
	}
	
	// Use this instead of create User directly
	public static User getUser(long id) {
		return new User(id);
	}
	
	// Use this instead of create User directly
	public static User getUser(long id, String displayName, String nickName,
			int locationCountry, int locationCity, int locationState,
			int dateOfBirth, int relationship) {
		if(isInitialized()) {
			return new User(id, displayName, nickName,
				locationCountry, locationCity, locationState,
				dateOfBirth, relationship);
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
