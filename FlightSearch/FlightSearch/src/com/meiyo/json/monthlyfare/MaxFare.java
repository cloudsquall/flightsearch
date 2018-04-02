
package com.meiyo.json.monthlyfare;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MaxFare {

    @SerializedName("price")
    @Expose
    private Price__ price;
    @SerializedName("soldOut")
    @Expose
    private Boolean soldOut;
    @SerializedName("unavailable")
    @Expose
    private Boolean unavailable;
    @SerializedName("day")
    @Expose
    private String day;

    /**
     * 
     * @return
     *     The price
     */
    public Price__ getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Price__ price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The soldOut
     */
    public Boolean getSoldOut() {
        return soldOut;
    }

    /**
     * 
     * @param soldOut
     *     The soldOut
     */
    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    /**
     * 
     * @return
     *     The unavailable
     */
    public Boolean getUnavailable() {
        return unavailable;
    }

    /**
     * 
     * @param unavailable
     *     The unavailable
     */
    public void setUnavailable(Boolean unavailable) {
        this.unavailable = unavailable;
    }

    /**
     * 
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

}
