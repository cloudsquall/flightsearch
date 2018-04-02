
package com.meiyo.json.schedule;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Schedule {

    @SerializedName("defaultAirport")
    @Expose
    private DefaultAirport defaultAirport;
    @SerializedName("regions")
    @Expose
    private List<Region> regions = new ArrayList<Region>();
    @SerializedName("cities")
    @Expose
    private List<City> cities = new ArrayList<City>();
    @SerializedName("countries")
    @Expose
    private List<Country> countries = new ArrayList<Country>();
    @SerializedName("nearbyAirports")
    @Expose
    private List<NearbyAirport> nearbyAirports = new ArrayList<NearbyAirport>();
    @SerializedName("airports")
    @Expose
    private List<Airport> airports = new ArrayList<Airport>();

    /**
     * 
     * @return
     *     The defaultAirport
     */
    public DefaultAirport getDefaultAirport() {
        return defaultAirport;
    }

    /**
     * 
     * @param defaultAirport
     *     The defaultAirport
     */
    public void setDefaultAirport(DefaultAirport defaultAirport) {
        this.defaultAirport = defaultAirport;
    }

    /**
     * 
     * @return
     *     The regions
     */
    public List<Region> getRegions() {
        return regions;
    }

    /**
     * 
     * @param regions
     *     The regions
     */
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    /**
     * 
     * @return
     *     The cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * 
     * @param cities
     *     The cities
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    /**
     * 
     * @return
     *     The countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * 
     * @param countries
     *     The countries
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * 
     * @return
     *     The nearbyAirports
     */
    public List<NearbyAirport> getNearbyAirports() {
        return nearbyAirports;
    }

    /**
     * 
     * @param nearbyAirports
     *     The nearbyAirports
     */
    public void setNearbyAirports(List<NearbyAirport> nearbyAirports) {
        this.nearbyAirports = nearbyAirports;
    }

    /**
     * 
     * @return
     *     The airports
     */
    public List<Airport> getAirports() {
        return airports;
    }

    /**
     * 
     * @param airports
     *     The airports
     */
    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }

}
