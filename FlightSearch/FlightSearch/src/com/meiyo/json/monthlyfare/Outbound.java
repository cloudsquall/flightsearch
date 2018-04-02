
package com.meiyo.json.monthlyfare;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Outbound {

    @SerializedName("fares")
    @Expose
    private List<Fare> fares = new ArrayList<Fare>();
    @SerializedName("minFare")
    @Expose
    private MinFare minFare;
    @SerializedName("maxFare")
    @Expose
    private MaxFare maxFare;

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

    /**
     * 
     * @return
     *     The minFare
     */
    public MinFare getMinFare() {
        return minFare;
    }

    /**
     * 
     * @param minFare
     *     The minFare
     */
    public void setMinFare(MinFare minFare) {
        this.minFare = minFare;
    }

    /**
     * 
     * @return
     *     The maxFare
     */
    public MaxFare getMaxFare() {
        return maxFare;
    }

    /**
     * 
     * @param maxFare
     *     The maxFare
     */
    public void setMaxFare(MaxFare maxFare) {
        this.maxFare = maxFare;
    }

}
