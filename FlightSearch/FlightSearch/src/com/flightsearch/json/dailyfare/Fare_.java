
package com.flightsearch.json.dailyfare;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Fare_ {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("hasDiscount")
    @Expose
    private Boolean hasDiscount;
    @SerializedName("publishedFare")
    @Expose
    private Double publishedFare;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The hasDiscount
     */
    public Boolean getHasDiscount() {
        return hasDiscount;
    }

    /**
     * 
     * @param hasDiscount
     *     The hasDiscount
     */
    public void setHasDiscount(Boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    /**
     * 
     * @return
     *     The publishedFare
     */
    public Double getPublishedFare() {
        return publishedFare;
    }

    /**
     * 
     * @param publishedFare
     *     The publishedFare
     */
    public void setPublishedFare(Double publishedFare) {
        this.publishedFare = publishedFare;
    }

}
