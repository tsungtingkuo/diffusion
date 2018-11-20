package network;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5522932333649455302L;

	public static final int TOTAL_STATE_NUMBER = 3;
	
	public static final int STATE_INACTIVATED = 0;
	public static final int STATE_ACTIVATED = 1;
	public static final int STATE_RECOVERED = 2;

	// User information
	long id = -1;
	String displayName = null;
	String nickName = null;
	int locationCountry = -1;
	int locationCity = -1;
	int locationState = -1;
	int dateOfBirth = -1;
	int relationship = -1;

	// User state
	int state = STATE_INACTIVATED;
	int previouslyState = STATE_INACTIVATED;
	
	// User registration time
	int registrationTime = -1;
	
	/**
	 * @return the registrationTime
	 */
	public int getRegistrationTime() {
		return registrationTime;
	}

	/**
	 * @param registrationTime the registrationTime to set
	 */
	public void setRegistrationTime(int registrationTime) {
		this.registrationTime = registrationTime;
	}

	// Model parameter
	double ltThreshold = 0;
	double ltValue = 0;
	double hdHeat = 0;
	double hdThreshold = 0;
	double sisDeathRate = 0;
	double sirDeathRate = 0;

	public User(long id, String displayName, String nickName,
			int locationCountry, int locationCity, int locationState,
			int dateOfBirth, int relationship) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.nickName = nickName;
		this.locationCountry = locationCountry;
		this.locationCity = locationCity;
		this.locationState = locationState;
		this.dateOfBirth = dateOfBirth;
		this.relationship = relationship;
	}

	public User(long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @return the locationCountry
	 */
	public int getLocationCountry() {
		return locationCountry;
	}

	/**
	 * @return the locationCity
	 */
	public int getLocationCity() {
		return locationCity;
	}

	/**
	 * @return the locationState
	 */
	public int getLocationState() {
		return locationState;
	}

	/**
	 * @return the dateOfBirth
	 */
	public int getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the relationship
	 */
	public int getRelationship() {
		return relationship;
	}

	/**
	 * @return the ltThreshold
	 */
	public double getLtThreshold() {
		return ltThreshold;
	}

	/**
	 * @return the hdHeat
	 */
	public double getHdHeat() {
		return hdHeat;
	}

	/**
	 * @return the hdThreshold
	 */
	public double getHdThreshold() {
		return hdThreshold;
	}

	/**
	 * @return the sisDeathRate
	 */
	public double getSisDeathRate() {
		return sisDeathRate;
	}

	/**
	 * @return the sirDeathRate
	 */
	public double getSirDeathRate() {
		return sirDeathRate;
	}

	/**
	 * @param ltThreshold the ltThreshold to set
	 */
	public void setLtThreshold(double ltThreshold) {
		this.ltThreshold = ltThreshold;
	}

	/**
	 * @param hdHeat the hdHeat to set
	 */
	public void setHdHeat(double hdHeat) {
		this.hdHeat = hdHeat;
	}

	/**
	 * @param hdThreshold the hdThreshold to set
	 */
	public void setHdThreshold(double hdThreshold) {
		this.hdThreshold = hdThreshold;
	}

	/**
	 * @param sisDeathRate the sisDeathRate to set
	 */
	public void setSisDeathRate(double sisDeathRate) {
		this.sisDeathRate = sisDeathRate;
	}

	/**
	 * @param sirDeathRate the sirDeathRate to set
	 */
	public void setSirDeathRate(double sirDeathRate) {
		this.sirDeathRate = sirDeathRate;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.previouslyState = this.state;
		this.state = state;
	}

	/**
	 * @return the previouslyState
	 */
	public int getPreviouslyState() {
		return previouslyState;
	}

	/**
	 * @return the ltValue
	 */
	public double getLtValue() {
		return ltValue;
	}

	/**
	 * @param ltValue the ltValue to set
	 */
	public void setLtValue(double ltValue) {
		this.ltValue = ltValue;
	}

	/**
	 * @return String format of all User information
	 * @see java.lang.String#toString()
	 */
	public String toString() {
		String s = new String();
		s += this.id;
		s += "," + this.displayName;
		s += "," + this.nickName;
		s += "," + this.locationCountry;
		s += "," + this.locationCity;
		s += "," + this.locationState;
		s += "," + this.dateOfBirth;
		s += "," + this.relationship;
		return s;
	}
}
