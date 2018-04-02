
package com.meiyo.json.dailyfare;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Date {

    @SerializedName("dateOut")
    @Expose
    private String dateOut;
    @SerializedName("flights")
    @Expose
    private List<Flight> flights = new ArrayList<Flight>();

    /**
     * 
     * @return
     *     The dateOut
     */
    public String getDateOut() {
        return dateOut;
    }

    /**
     * 
     * @param dateOut
     *     The dateOut
     */
    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }

    /**
     * 
     * @return
     *     The flights
     */
    public List<Flight> getFlights() {
        return flights;
    }

    /**
     * 
     * @param flights
     *     The flights
     */
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
