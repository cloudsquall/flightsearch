package com.flightsearch.printers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.Properties;

import com.flightsearch.json.dailyfare.Flight;
import com.flightsearch.pojo.FlightMatch;
import com.flightsearch.printers.MatchPrinter.DateMatchComparator;
import com.flightsearch.printers.MatchPrinter.PriceMatchComparator;

/**
 * Default abstract implementations for printing (outputting) matches.
 */
public abstract class MatchPrinter {
	public static DecimalFormat df = new DecimalFormat("#.##");
	static {
		df.setRoundingMode(RoundingMode.CEILING);
	}
	private static SimpleDateFormat targetDateFormat = new SimpleDateFormat("EEE dd-MMM HH:mm");

	final static String CSV_SEPARATOR = ",";

	private Map<String, List<FlightMatch>> shortlist = new HashMap<String, List<FlightMatch>>();

	final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private static Properties airports = null;

	public static Properties getAirports() {
		if (airports == null) {
			airports = getProperties("airports");
		}

		return airports;
	}

	public static Properties getProperties(String fileName) {
		String file = fileName + ".properties";
		Properties props = new Properties();
		InputStream is = null;

		// First try loading from the current directory
		try {
			File f = new File(file);
			is = new FileInputStream( f );
		}
		catch ( Exception e ) { is = null; }

		try {
			if ( is == null ) {
				// Try loading from classpath
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			}

			// Try loading properties from the file (if found)
			props.load( is );
		}
		catch ( Exception e ) {System.out.println("Couldn't load " + file + " : " +e.getMessage());e.printStackTrace();}

		return props;
	}


	public static void log(String outbound, String suffix, Object s) throws IOException {
		String fileName = "flights-output-" + outbound + "-" + suffix + ".csv";
		new File(fileName).createNewFile();
		Files.write(Paths.get(fileName), (s.toString() + LINE_SEPARATOR).getBytes(), StandardOpenOption.APPEND);
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

	public void print(List<FlightMatch> matches) throws IOException {
		Collections.sort(matches, new PriceMatchComparator());
		for (FlightMatch match : matches) {
			if (!fitsPrinterCriteria(match)) {
				continue;
			}

			String destination = match.getOutboundFlight().getDestination();
			List<FlightMatch> shortDest = shortlist.get(destination);
			if (shortDest == null) {
				shortDest = new ArrayList<FlightMatch>();
			}
			if (shortDest.size() >= 10) {
				continue;
			}

			shortDest.add(match);
			shortlist.put(destination, shortDest);
		}
		log(matches.get(0).getOutboundFlight().getOrigin(), getFileSuffix(), "Price,Destination,OutTime,EmailText,PlainText");
		for (String destination : shortlist.keySet()) {
			List<FlightMatch> groupedMatches = shortlist.get(destination);
			Collections.sort(groupedMatches, new DateMatchComparator());
			for (FlightMatch match : groupedMatches) {
				Flight outbound = match.getOutboundFlight();
				Flight rtn = match.getReturnFlight();
				String total = df.format(match.getTotal());
				//log("user", "Matching flight: " + total + " " + match.getOutboundFlight().toPrintString() + " " + match.getReturnFlight().toPrintString());

				log(outbound.getOrigin(), getFileSuffix(), total + CSV_SEPARATOR + outbound.toPrintString(CSV_SEPARATOR) + CSV_SEPARATOR + "\"" + toEmailString(match, outbound.getDestination(), total) + "\"" + CSV_SEPARATOR + "\"" + toPlainTextString(match, outbound.getDestination(), total) + "\"");
			}
		}
	}


	public abstract String getFileSuffix();

	private static String toEmailString(FlightMatch match, String destination, String total) {
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
			return "<tr><td>" + airportName + "</td><td>" + targetDateFormat.format(match.getOutboundTime()) + "</td><td>" + targetDateFormat.format(match.getReturnTime()) + "</td><td>" + total + "</td></tr>";
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
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
			return airportName + " on " + targetDateFormat.format(match.getOutboundTime()) + " returning " + targetDateFormat.format(match.getReturnTime()) + " for \uFFFD" + total + "</td></tr>";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public abstract boolean fitsPrinterCriteria(FlightMatch match);
}
