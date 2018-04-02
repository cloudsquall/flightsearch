package com.meiyo.printers;

import com.meiyo.pojo.FlightMatch;
/**
 * Filters and prints out results for long weekends.
 */
public class LongWeekendPrinter extends MatchPrinter {
	final static String FILE_SUFFIX = "longwknd";

	@Override
	public boolean fitsPrinterCriteria(FlightMatch match) {
		if (match.getReturnTime().getDay() >= match.getOutboundTime().getDay()) {
			if ((match.getReturnTime().getDay() - match.getOutboundTime().getDay()) <= 2) {
				return false;
			}
		} else {
			if ((match.getReturnTime().getDay()+7 - match.getOutboundTime().getDay()) <= 2) {
				return false;
			}
		}
		
		return !WeekendPrinter.printingCriteria(match);
	}
	
	@Override
	public String getFileSuffix() {
		return FILE_SUFFIX;
	}

}
