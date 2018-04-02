package com.flightsearch.printers;

import com.flightsearch.pojo.FlightMatch;

/**
 * Filter and print matches for short weekends.
 */
public class WeekendPrinter extends MatchPrinter {
	final static String FILE_SUFFIX = "wknd";

	@Override
	public boolean fitsPrinterCriteria(FlightMatch match) {
		return printingCriteria(match);
	}
	
	public static boolean printingCriteria(FlightMatch match) {
		if (match.getReturnTime().getDay() != 0 || match.getReturnTime().getHours() < 13) { // sunday
			return false;
		}
		
		if (match.getOutboundTime().getDay() == 5 && match.getOutboundTime().getHours() < 15) { // friday
			return false;
		}
		
		if (match.getReturnTime().getDay() >= match.getOutboundTime().getDay()) {
			return (match.getReturnTime().getDay() - match.getOutboundTime().getDay()) <= 2;
		} else {
			return (match.getReturnTime().getDay()+7 - match.getOutboundTime().getDay()) <= 2;
		}
	}

	@Override
	public String getFileSuffix() {
		return FILE_SUFFIX;
	}
}
