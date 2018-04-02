
package com.flightsearch.json.monthlyfare;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MonthlyFare {

    @SerializedName("outbound")
    @Expose
    private Outbound outbound;

    @SerializedName("inbound")
    @Expose
    private Inbound inbound;

    
    /**
     * 
     * @return
     *     The outbound
     */
    public Outbound getOutbound() {
        return outbound;
    }

    /**
     * 
     * @param outbound
     *     The outbound
     */
    public void setOutbound(Outbound outbound) {
        this.outbound = outbound;
    }

    
    /**
     * 
     * @return
     *     The inbound
     */
    public Inbound getInbound() {
        return inbound;
    }

    /**
     * 
     * @param inbound
     *     The inbound
     */
    public void setInbound(Inbound inbound) {
        this.inbound = inbound;
    }
}
