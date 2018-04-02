package com.flightsearch.printers;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.flightsearch.json.dailyfare.Flight;
import com.flightsearch.pojo.FlightMatch;

/**
 * Print matches in table format for Reddit.
 */
public class RedditPrinter extends MatchPrinter {
	final static String SEPARATOR = System.getProperty("line.separator");
	final static String CSV_SEPARATOR = ",";

	private static SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
	private static SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MMM hh:mmaaa");  

	public static DecimalFormat df = new DecimalFormat("#.##");
	static {
		df.setRoundingMode(RoundingMode.CEILING);
	}

	@Override
	public void print(List<FlightMatch> matches) throws IOException {
		Collections.sort(matches, new MatchComparator());
		log(matches.get(0).getOutboundFlight().getOrigin(), "Price,Destination,OutTime"); 
		for (FlightMatch match : matches) {
			Flight outbound = match.getOutboundFlight();
			Flight rtn = match.getReturnFlight();
			String total = df.format(match.getTotal()); 
			//log("user", "Matching flight: " + total + " " + match.getOutboundFlight().toPrintString() + " " + match.getReturnFlight().toPrintString());

			log(outbound.getOrigin(), total + CSV_SEPARATOR + outbound.toPrintString(CSV_SEPARATOR) + CSV_SEPARATOR + rtn.toPrintString(CSV_SEPARATOR) + CSV_SEPARATOR + "\"" + toRedditString(outbound, rtn, total)+ "\"");
		}	
	}

	private static String toRedditString(Flight outbound, Flight returnFlight, String total) {
		//Liverpool, UK|15 Dec 08:45 PM|17 Dec 08:25 PM|63.99
		String airportName = outbound.getDestination();
		try {
			String property = getAirports().getProperty(outbound.getDestination());
			if (property != null) {
				String outboundAirport[] = property.split(",");
				airportName = outboundAirport[0];
				if (!airportName.equals(outboundAirport[1])) {
					airportName += ", " + outboundAirport[1];
				} else {
					airportName += ", " + outboundAirport[2];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return airportName + "|" + targetDateFormat.format(sourceDateFormat.parse(outbound.getOutTime())) + "|" + targetDateFormat.format(sourceDateFormat.parse(returnFlight.getOutTime())) + "|" + total;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

	public static void log(String outbound, Object s) throws IOException {
		String fileName = "flights-output-reddit-" + outbound + ".csv";
		new File(fileName).createNewFile();
		Files.write(Paths.get(fileName), (s.toString() + SEPARATOR).getBytes(), StandardOpenOption.APPEND);
	}

	public class MatchComparator implements Comparator<FlightMatch> {

		@Override
		public int compare(FlightMatch o1, FlightMatch o2) {

			if (!o1.getOutboundFlight().getDestination().equals(o2.getOutboundFlight().getDestination())) {
				return o1.getOutboundFlight().getDestination().compareTo(o2.getOutboundFlight().getDestination());
			}

			return o1.getTotal().compareTo(o2.getTotal());
		}

	}

	@Override
	public boolean fitsPrinterCriteria(FlightMatch match) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getFileSuffix() {
		// TODO Auto-generated method stub
		return null;
	}
}
