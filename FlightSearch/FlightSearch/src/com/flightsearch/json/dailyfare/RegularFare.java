
package com.flightsearch.json.dailyfare;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RegularFare {

    @SerializedName("fareKey")
    @Expose
    private String fareKey;
    @SerializedName("fareClass")
    @Expose
    private String fareClass;
    @SerializedName("fares")
    @Expose
    private List<Fare> fares = new ArrayList<Fare>();

    /**
     * 
     * @return
     *     The fareKey
     */
    public String getFareKey() {
        return fareKey;
    }

    /**
     * 
     * @param fareKey
     *     The fareKey
     */
    public void setFareKey(String fareKey) {
        this.fareKey = fareKey;
    }

    /**
     * 
     * @return
     *     The fareClass
     */
    public String getFareClass() {
        return fareClass;
    }

    /**
     * 
     * @param fareClass
     *     The fareClass
     */
    public void setFareClass(String fareClass) {
        this.fareClass = fareClass;
    }

    /**
     * 
     * @return
     *     The fares
     */
    public List<Fare> getFares() {
        return fares;
    }

    /**
     * 
     * @param fares
     *     The fares
     */
    public void setFares(List<Fare> fares) {
        this.fares = fares;
    }

}
