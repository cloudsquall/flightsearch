
package com.flightsearch.json.schedule;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class NearbyAirport {

    @SerializedName("iataCode")
    @Expose
    private String iataCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seoName")
    @Expose
    private String seoName;

    /**
     * 
     * @return
     *     The iataCode
     */
    public String getIataCode() {
        return iataCode;
    }

    /**
     * 
     * @param iataCode
     *     The iataCode
     */
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The seoName
     */
    public String getSeoName() {
        return seoName;
    }

    /**
     * 
     * @param seoName
     *     The seoName
     */
    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

}
