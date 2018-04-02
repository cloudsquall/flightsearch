
package com.flightsearch.json.schedule;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Airport {

    @SerializedName("iataCode")
    @Expose
    private String iataCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seoName")
    @Expose
    private String seoName;
    @SerializedName("coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("base")
    @Expose
    private Boolean base;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("regionCode")
    @Expose
    private String regionCode;
    @SerializedName("cityCode")
    @Expose
    private String cityCode;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("routes")
    @Expose
    private List<String> routes = new ArrayList<String>();
    @SerializedName("seasonalRoutes")
    @Expose
    private List<Object> seasonalRoutes = new ArrayList<Object>();
    @SerializedName("categories")
    @Expose
    private List<String> categories = new ArrayList<String>();
    @SerializedName("priority")
    @Expose
    private Integer priority;

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

    /**
     * 
     * @return
     *     The coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * @return
     *     The base
     */
    public Boolean getBase() {
        return base;
    }

    /**
     * 
     * @param base
     *     The base
     */
    public void setBase(Boolean base) {
        this.base = base;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 
     * @return
     *     The regionCode
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * 
     * @param regionCode
     *     The regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * 
     * @return
     *     The cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 
     * @param cityCode
     *     The cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 
     * @return
     *     The currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 
     * @param currencyCode
     *     The currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 
     * @return
     *     The routes
     */
    public List<String> getRoutes() {
        return routes;
    }

    /**
     * 
     * @param routes
     *     The routes
     */
    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    /**
     * 
     * @return
     *     The seasonalRoutes
     */
    public List<Object> getSeasonalRoutes() {
        return seasonalRoutes;
    }

    /**
     * 
     * @param seasonalRoutes
     *     The seasonalRoutes
     */
    public void setSeasonalRoutes(List<Object> seasonalRoutes) {
        this.seasonalRoutes = seasonalRoutes;
    }

    /**
     * 
     * @return
     *     The categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 
     * @param priority
     *     The priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
