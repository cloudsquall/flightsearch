
package com.flightsearch.json.dailyfare;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DailyFares {

    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currPrecision")
    @Expose
    private Integer currPrecision;
    @SerializedName("trips")
    @Expose
    private List<Trip> trips = new ArrayList<Trip>();
    @SerializedName("serverTimeUTC")
    @Expose
    private String serverTimeUTC;

    /**
     * 
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The currPrecision
     */
    public Integer getCurrPrecision() {
        return currPrecision;
    }

    /**
     * 
     * @param currPrecision
     *     The currPrecision
     */
    public void setCurrPrecision(Integer currPrecision) {
        this.currPrecision = currPrecision;
    }

    /**
     * 
     * @return
     *     The trips
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * 
     * @param trips
     *     The trips
     */
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    /**
     * 
     * @return
     *     The serverTimeUTC
     */
    public String getServerTimeUTC() {
        return serverTimeUTC;
    }

    /**
     * 
     * @param serverTimeUTC
     *     The serverTimeUTC
     */
    public void setServerTimeUTC(String serverTimeUTC) {
        this.serverTimeUTC = serverTimeUTC;
    }

}
