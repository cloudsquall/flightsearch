package com.meiyo.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.meiyo.json.dailyfare.Flight;

public class FlightMatch {

	private static SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	Flight outboundFlight;
	Flight returnFlight;
	
	private Double total;
	private Date outboundTime;
	private Date returnTime;
	
	public FlightMatch(Flight outboundFlight, Flight returnFlight) throws ParseException {
		this.outboundFlight = outboundFlight;
		this.returnFlight = returnFlight;
		this.total = outboundFlight.getLowestFare().getAmount() + returnFlight.getLowestFare().getAmount();
		this.outboundTime = sourceDateFormat.parse(outboundFlight.getOutTime());
		this.returnTime = sourceDateFormat.parse(returnFlight.getOutTime());
	}
	public Flight getOutboundFlight() {
		return outboundFlight;
	}
	public void setOutboundFlight(Flight outboundFlight) {
		this.outboundFlight = outboundFlight;
	}
	public Flight getReturnFlight() {
		return returnFlight;
	}
	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
	}
	public Double getTotal() {
		return total;
	}
	
	public Date getOutboundTime() {
		return this.outboundTime;
	}
	
	public Date getReturnTime() {
		return this.returnTime;
	}
}
