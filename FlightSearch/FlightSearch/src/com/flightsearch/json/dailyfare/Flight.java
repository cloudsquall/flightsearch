
package com.flightsearch.json.dailyfare;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Flight {

    @SerializedName("flightNumber")
    @Expose
    private String flightNumber;
    @SerializedName("time")
    @Expose
    private List<String> time = new ArrayList<String>();
    @SerializedName("timeUTC")
    @Expose
    private List<String> timeUTC = new ArrayList<String>();
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("faresLeft")
    @Expose
    private Integer faresLeft;
    @SerializedName("flightKey")
    @Expose
    private String flightKey;
    @SerializedName("infantsLeft")
    @Expose
    private Integer infantsLeft;
    @SerializedName("regularFare")
    @Expose
    private RegularFare regularFare;
    @SerializedName("businessFare")
    @Expose
    private BusinessFare businessFare;

    private String origin;
    private String destination;
    
    public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
     * 
     * @return
     *     The flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * 
     * @param flightNumber
     *     The flightNumber
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * 
     * @return
     *     The time
     */
    public List<String> getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(List<String> time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The timeUTC
     */
    public List<String> getTimeUTC() {
        return timeUTC;
    }

    /**
     * 
     * @param timeUTC
     *     The timeUTC
     */
    public void setTimeUTC(List<String> timeUTC) {
        this.timeUTC = timeUTC;
    }

    /**
     * 
     * @return
     *     The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The faresLeft
     */
    public Integer getFaresLeft() {
        return faresLeft;
    }

    /**
     * 
     * @param faresLeft
     *     The faresLeft
     */
    public void setFaresLeft(Integer faresLeft) {
        this.faresLeft = faresLeft;
    }

    /**
     * 
     * @return
     *     The flightKey
     */
    public String getFlightKey() {
        return flightKey;
    }

    /**
     * 
     * @param flightKey
     *     The flightKey
     */
    public void setFlightKey(String flightKey) {
        this.flightKey = flightKey;
    }

    /**
     * 
     * @return
     *     The infantsLeft
     */
    public Integer getInfantsLeft() {
        return infantsLeft;
    }

    /**
     * 
     * @param infantsLeft
     *     The infantsLeft
     */
    public void setInfantsLeft(Integer infantsLeft) {
        this.infantsLeft = infantsLeft;
    }

    /**
     * 
     * @return
     *     The regularFare
     */
    public RegularFare getRegularFare() {
        return regularFare;
    }

    /**
     * 
     * @param regularFare
     *     The regularFare
     */
    public void setRegularFare(RegularFare regularFare) {
        this.regularFare = regularFare;
    }

    /**
     * 
     * @return
     *     The businessFare
     */
    public BusinessFare getBusinessFare() {
        return businessFare;
    }

    /**
     * 
     * @param businessFare
     *     The businessFare
     */
    public void setBusinessFare(BusinessFare businessFare) {
        this.businessFare = businessFare;
    }

    
	public com.flightsearch.json.dailyfare.Fare getLowestFare() {
		com.flightsearch.json.dailyfare.Fare lowestFare = null;
		if (this.getRegularFare() != null) {
			for (com.flightsearch.json.dailyfare.Fare thisFare : this.getRegularFare().getFares()) {
				// finding the lowest fare
				if (lowestFare == null || thisFare.getAmount() < lowestFare.getAmount())
					lowestFare = thisFare;
			}
		}

		return lowestFare;
	}
	
	public String getOutTime() {
		
		if (this.getTime().size() > 1)
			return this.getTime().get(0);
			
		return null;
	}
	
	public String toPrintString(String separator) {
		return this.getOrigin() + "->" + this.getDestination() + separator + this.getOutTime();
	}
}
