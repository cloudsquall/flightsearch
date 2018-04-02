
package com.meiyo.json.monthlyfare;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Price__ {

    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("valueMainUnit")
    @Expose
    private String valueMainUnit;
    @SerializedName("valueFractionalUnit")
    @Expose
    private String valueFractionalUnit;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("currencySymbol")
    @Expose
    private String currencySymbol;

    /**
     * 
     * @return
     *     The value
     */
    public Double getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The valueMainUnit
     */
    public String getValueMainUnit() {
        return valueMainUnit;
    }

    /**
     * 
     * @param valueMainUnit
     *     The valueMainUnit
     */
    public void setValueMainUnit(String valueMainUnit) {
        this.valueMainUnit = valueMainUnit;
    }

    /**
     * 
     * @return
     *     The valueFractionalUnit
     */
    public String getValueFractionalUnit() {
        return valueFractionalUnit;
    }

    /**
     * 
     * @param valueFractionalUnit
     *     The valueFractionalUnit
     */
    public void setValueFractionalUnit(String valueFractionalUnit) {
        this.valueFractionalUnit = valueFractionalUnit;
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
     *     The currencySymbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * 
     * @param currencySymbol
     *     The currencySymbol
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

}
