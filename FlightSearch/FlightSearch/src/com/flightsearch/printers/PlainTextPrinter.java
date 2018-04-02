package com.flightsearch.printers;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flightsearch.json.dailyfare.Flight;
import com.flightsearch.pojo.FlightMatch;

/**
 * Print matches in plain text format.
 */
public class PlainTextPrinter extends MatchPrinter {

	final static String SEPARATOR = System.getProperty("line.separator");
	final static String CSV_SEPARATOR = ",";

	private static SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd-MMM hh:mmaaa");  

	public static DecimalFormat df = new DecimalFormat("#.##");
	static {
		df.setRoundingMode(RoundingMode.CEILING);
	}

	private Map<String, List<FlightMatch>> shortlist = new HashMap<String, List<FlightMatch>>();

	@Override
	public void print(List<FlightMatch> matches) throws IOException {
		Collections.sort(matches, new PriceMatchComparator());
		for (FlightMatch match : matches) {
			String destination = match.getOutboundFlight().getDestination();
			List<FlightMatch> shortDest = shortlist.get(destination);
			if (shortDest == null) {
				shortDest = new ArrayList<FlightMatch>();
			}
			if (shortDest.size() >= 3) {
				continue;
			}

			shortDest.add(match);
			shortlist.put(destination, shortDest);
		}
		log(matches.get(0).getOutboundFlight().getOrigin(), "Price,Destination,OutTime"); 
		for (String destination : shortlist.keySet()) {
			List<FlightMatch> groupedMatches = shortlist.get(destination);
			Collections.sort(groupedMatches, new DateMatchComparator());
			for (FlightMatch match : groupedMatches) {
				Flight outbound = match.getOutboundFlight();
				Flight rtn = match.getReturnFlight();
				String total = df.format(match.getTotal()); 

				log(outbound.getOrigin(), total + CSV_SEPARATOR + outbound.toPrintString(CSV_SEPARATOR) + CSV_SEPARATOR + rtn.toPrintString(CSV_SEPARATOR) + CSV_SEPARATOR + "\"" + toPlainTextString(match, outbound.getDestination(), total)+ "\"");
			}	
		}
	}

	private static String toPlainTextString(FlightMatch match, String destination, String total) {
		//Liverpool, UK|15 Dec 08:45 PM|17 Dec 08:25 PM|63.99
		String airportName = destination;
		try {
			String property = getAirports().getProperty(destination);
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
			return airportName + " on " + targetDateFormat.format(match.getOutboundTime()) + " returning " + targetDateFormat.format(match.getReturnTime()) + " for €" + total + "</td></tr>";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static void log(String outbound, Object s) throws IOException {
		String fileName = "flights-output-plaintext-" + outbound + ".csv";
		new File(fileName).createNewFile();
		Files.write(Paths.get(fileName), (s.toString() + SEPARATOR).getBytes(), StandardOpenOption.APPEND);
	}

	public class PriceMatchComparator implements Comparator<FlightMatch> {

		@Override
		public int compare(FlightMatch o1, FlightMatch o2) {

			if (!o1.getOutboundFlight().getDestination().equals(o2.getOutboundFlight().getDestination())) {
				return o1.getOutboundFlight().getDestination().compareTo(o2.getOutboundFlight().getDestination());
			}

			return o1.getTotal().compareTo(o2.getTotal());
		}

	}
	public class DateMatchComparator implements Comparator<FlightMatch> {

		@Override
		public int compare(FlightMatch o1, FlightMatch o2) {

			if (!o1.getOutboundFlight().getDestination().equals(o2.getOutboundFlight().getDestination())) {
				return o1.getOutboundFlight().getDestination().compareTo(o2.getOutboundFlight().getDestination());
			}

			return o1.getOutboundTime().compareTo(o2.getReturnTime());
		}

	}
	@Override
	public boolean fitsPrinterCriteria(FlightMatch match) {
		return false;
	}

	@Override
	public String getFileSuffix() {
		return null;
	}
}
