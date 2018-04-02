import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.meiyo.json.dailyfare.DailyFares;
import com.meiyo.json.dailyfare.Flight;
import com.meiyo.json.dailyfare.Trip;
import com.meiyo.json.monthlyfare.Fare;
import com.meiyo.json.monthlyfare.MonthlyFare;
import com.meiyo.json.schedule.Airport;
import com.meiyo.json.schedule.Schedule;
import com.meiyo.pojo.FlightMatch;
import com.meiyo.printers.LongWeekendPrinter;
import com.meiyo.printers.MatchPrinter;
import com.meiyo.printers.WeekendPrinter;

/**
 * A Ryanair weekend flight searcher. It will find:
 * - return flights to any destination in the next N months
 * - under X return price
 * - with or without UK & Ireland destinations
 * - including long weekends (or not)
 */
public class FlightSearch {
	final static List<MatchPrinter> printers;
	static{
		System.setProperty("com.sun.net.ssl.checkRevocation", "false");
		printers = new ArrayList<MatchPrinter>();
		printers.add(new WeekendPrinter());
		printers.add(new LongWeekendPrinter());
	}

	final static String SEPARATOR = System.getProperty("line.separator");
	final static DateTimeFormatter SOURCE_DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	final static long START_TIME = Calendar.getInstance().getTimeInMillis();

	/* Connection timeout limit. */
	final static int TIMEOUT_LIMIT = 10000;
	/* Max request attempts before soft fail. */
	final static int RETRY_MAX = 5;

	/* Create your own unique user agent; may decrease chances of rejected searches. */
	final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586";
	/* Origin airport. */
	final static String FROM_AIRPORT = "DUB";
	/* Maximum trip return price to include in search results. */
	final static int BUDGET_AMT = 75;
	/* Number of months (from current) to include. */
	final static int NUM_MONTHS = 7;
	/* Start search from month current+START_OFFSET_MONTHS. */
	final static int START_OFFSET_MONTHS = 0;
	/* Include long weekends in search. */
	final static boolean INCLUDE_LONGWEEKENDS = false;
	/* Include UK & Ireland results in search. */
	final static boolean INCLUDE_UKI = false;
	
	static int retryCount = 0;

	public static void main(String[] args) throws Exception {
		List<FlightMatch> matches = new ArrayList<FlightMatch>();

		try {
			log("Starting ..");

			//String from = "DUB";
			Schedule schedule = getSchedule();
			for (Airport airport : schedule.getAirports()) {
				String from = airport.getIataCode();
				if (from.equals(FROM_AIRPORT)) {
					for (String route : airport.getRoutes()) {

						if (!route.startsWith("airport")) {
							continue;
						}
						
						String code = route.split(":")[1];

						if (!INCLUDE_UKI && UKI_CODES.contains(code)) {
							continue;
						}
						
						log("Checking route: " + code);
						Set<LocalDate> candidateOutboundDates = getCandidateOutboundDates(from, code, NUM_MONTHS, BUDGET_AMT);
						for (LocalDate day : candidateOutboundDates) {
							// found a candidate! let's check it out further
							matches.addAll(getMatches(from, code, day, BUDGET_AMT));							

						}
					}
				}
			}

		} catch (Exception e) {
			log("Fatal error: " + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			for (MatchPrinter printer : printers) {
				printer.print(matches);
			}
		}
	}

	private static List<FlightMatch> getMatches(String from, String to, LocalDate outDate, int budget) throws IOException, InterruptedException {
		List<FlightMatch> matches = new ArrayList<FlightMatch>();
		if (retryCount >= RETRY_MAX) {
			retryCount = 0;
			return matches;
		}

		try { 
			log("Checking daily matches: " + from + "->" + to + " " + outDate.toString());
			
			LocalDate returnDate = calcNextDayOfWeek(outDate, DateTimeConstants.SUNDAY);
			DailyFares dailyFares = getDailyFares(from, to, outDate, returnDate);

			List<Flight> outboundFlights = new ArrayList<Flight>();
			List<Flight> returnFlights = new ArrayList<Flight>();

			for (Trip trip : dailyFares.getTrips()) {
				if (trip.getOrigin().equals(from)) {
					// this is an outbound flight
					outboundFlights.addAll(filterOutboundCandidateFlights(trip, budget));
				}

				if (trip.getOrigin().equals(to)) {
					// this is the return flight
					returnFlights.addAll(filterReturnCandidateFlights(trip, budget));
				}
			}

			// we now have a list of potential outbound and return flights.. we now need to match them for valid combinations!
			for (Flight outbound : outboundFlights) {
				com.meiyo.json.dailyfare.Fare outboundLowest = outbound.getLowestFare();
				if (outboundLowest == null) {
					continue;
				}
				for (Flight returnFlight : returnFlights) {
					com.meiyo.json.dailyfare.Fare returnLowest = returnFlight.getLowestFare();
					if (returnLowest == null) {
						continue;
					}
					double total = outboundLowest.getAmount() + returnLowest.getAmount();
					if (total <= budget) {
						// we finally have a flight match..
						matches.add(new FlightMatch(outbound, returnFlight));
					}	
				}
			}
		} catch(Exception e) {
			log("Retrying after Exception:" + e.getMessage());
			e.printStackTrace();
			retryCount++;
			sleep(5);
			return getMatches(from, to, outDate, budget);
		}

		return matches;
	}


	private static LocalDate calcNextDayOfWeek(LocalDate currentDay, int targetDay) {
		if (currentDay.getDayOfWeek() >= targetDay) {
			currentDay = currentDay.plusWeeks(1);
		}
		return currentDay.withDayOfWeek(targetDay);
	}


	private static List<Flight> filterOutboundCandidateFlights(Trip trip, int budget) {
		List<Flight> candidates = new ArrayList<Flight>();
		for (com.meiyo.json.dailyfare.Date scheduleDate : trip.getDates()) {
			LocalDate flightDate = LocalDateTime.parse(scheduleDate.getDateOut(), SOURCE_DATETIME_FORMATTER).toLocalDate();
			if (!isOutboundDay(flightDate)) {
				continue;
			}

			for (Flight flight : scheduleDate.getFlights()) {
				// lets filter out the right times for this flight - thursday evenings or saturday mornings or any other time
				// TODO need to consider timezones here (normalize to UTC?)
				if (flight.getTime().isEmpty()) {
					continue;
				}

				int dayOfWeek = flightDate.getDayOfWeek();
				int flightHour = LocalDateTime.parse(flight.getTime().get(0), SOURCE_DATETIME_FORMATTER).getHourOfDay();
				if (dayOfWeek == DateTimeConstants.THURSDAY && flightHour < 17) {
					continue;
				}
				if (dayOfWeek == DateTimeConstants.SATURDAY && flightHour >= 12) {
					continue;
				}

				// lastly, we need to make sure this fare is below our threshold
				// TODO are any of these fares disabled or a bad class?
				com.meiyo.json.dailyfare.Fare lowestFare = flight.getLowestFare();
				if (lowestFare != null && lowestFare.getAmount() < budget) {
					// we have a low price flight at a candidate time
					flight.setOrigin(trip.getOrigin());
					flight.setDestination(trip.getDestination());
					candidates.add(flight);
				}
			}

		}

		return candidates;
	}

	private static List<Flight> filterReturnCandidateFlights(Trip trip, int budget) {
		List<Flight> candidates = new ArrayList<Flight>();
		for (com.meiyo.json.dailyfare.Date scheduleDate : trip.getDates()) {
			LocalDate flightDate = LocalDateTime.parse(scheduleDate.getDateOut(), SOURCE_DATETIME_FORMATTER).toLocalDate();
			if (!isReturnDay(flightDate)) {
				continue;
			}

			for (Flight flight : scheduleDate.getFlights()) {
				// TODO need to consider timezones here (normalize to UTC?)
				if (flight.getTime().isEmpty()) {
					continue;
				}

				// lastly, we need to make sure this fare is below our threshold
				// TODO are any of these fares disabled or a bad class?
				com.meiyo.json.dailyfare.Fare lowestFare = flight.getLowestFare();
				if (lowestFare != null && lowestFare.getAmount() < budget) {
					// we have a low price flight at a candidate time
					flight.setOrigin(trip.getOrigin());
					flight.setDestination(trip.getDestination());
					candidates.add(flight);
				}
			}

		}

		return candidates;
	}


	/**
	 * Gets all airports that the carrier flies to/from.
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static Schedule getSchedule() throws IOException, InterruptedException {
		Gson gson = new Gson();
		String url = "https://api.ryanair.com/aggregate/3/common?embedded=airports,countries,cities,regions,nearbyAirports,defaultAirport&market=en-ie";
		Connection.Response result = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.method(Connection.Method.GET)
				.timeout(TIMEOUT_LIMIT)
				.followRedirects(true)
				.ignoreContentType(true)
				//.cookies(cookiesFromFile)
				.execute();
		sleep();

		return gson.fromJson(result.body(), Schedule.class);

	}

	private static DailyFares getDailyFares(String from, String to, LocalDate dateOut, LocalDate dateReturn) throws IOException, InterruptedException {
		Gson gson = new Gson();
		// high frequency requests to this url may lead to 404's (dos protection)
		sleep(25);
		String url = "https://desktopapps.ryanair.com/en-ie/availability?ADT=1&CHD=0&DateIn=" + dateReturn + "&DateOut=" + dateOut + "&Destination=" + to + "&FlexDaysIn=6&FlexDaysOut=4&ToUs=AGREED&INF=0&Origin=" + from + "&RoundTrip=true&TEEN=0";
		Connection.Response result = Jsoup.connect(url)
				.userAgent(USER_AGENT)
				.method(Connection.Method.GET)
				.timeout(TIMEOUT_LIMIT)
				.followRedirects(true)
				.ignoreContentType(true)
				//.cookies(cookiesFromFile)
				.execute();
		sleep(25);

		return gson.fromJson(result.body(), DailyFares.class);

	}

	/**
	 * Retrieve a list of dates for the next numMonths that may have a return flight matching the budget.
	 *  
	 * @param from
	 * @param to
	 * @param numMonths
	 * @param budget
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static Set<LocalDate> getCandidateOutboundDates(String from, String to, int numMonths, int budget) throws IOException, InterruptedException {
		Gson gson = new Gson();
		Map<LocalDate, Fare> outboundFares = new HashMap<LocalDate, Fare>();
		Set<LocalDate> candidateDates = new HashSet<LocalDate>();

		for (int i = 0; i < numMonths; i++) {
			LocalDate thisMonth = new LocalDate();
			thisMonth = thisMonth.plusMonths(i + START_OFFSET_MONTHS);
			thisMonth = thisMonth.withDayOfMonth(1);
			String url = "https://api.ryanair.com/farefinder/3/oneWayFares/" + from + "/" + to + "/cheapestPerDay?market=en-ie&outboundMonthOfDate=" + thisMonth;
			Connection.Response outboundResult = Jsoup.connect(url)
					.userAgent(USER_AGENT)
					.method(Connection.Method.GET)
					.timeout(TIMEOUT_LIMIT)
					.followRedirects(true)
					.ignoreContentType(true)
					//.cookies(cookiesFromFile)
					.execute();
			sleep();

			// for this month, check the outbound fare for each day
			MonthlyFare thisMonthFare = gson.fromJson(outboundResult.body(), MonthlyFare.class);
			for (Fare fare : thisMonthFare.getOutbound().getFares()) {
				// thursday - saturday for long/short weekend outbound
				LocalDate date = new LocalDate(fare.getDay());
				if (!isOutboundDay(date)) { 
					continue;
				}
				
				// we assert that a single outbound flight must be less than 80% of total budget..
				if (fare.getPrice() != null && fare.getPrice().getValue() <= (budget * 0.8)) {
					outboundFares.put(date, fare);
				}
			}

			if (outboundFares.size() > 0) {
				// okay, we have at least one outbound candidate - let's check for a matching return
				Connection.Response inboundResult = Jsoup.connect("https://api.ryanair.com/farefinder/3/oneWayFares/" + from + "/" + to + "/cheapestPerDay?market=en-ie&outboundMonthOfDate=" + thisMonth + "&inboundMonthOfDate=" + thisMonth)
						.userAgent(USER_AGENT)
						.method(Connection.Method.GET)
						.timeout(TIMEOUT_LIMIT)
						.followRedirects(true)
						.ignoreContentType(true)
						//.cookies(cookiesFromFile)
						.execute();

				sleep();

				MonthlyFare inboundMonth = gson.fromJson(inboundResult.body(), MonthlyFare.class);
				for (Fare returnFare : inboundMonth.getOutbound().getFares()) {
					LocalDate returnDate = new LocalDate(returnFare.getDay());
					if (!isReturnDay(returnDate)) {
						continue;
					}

					if (returnFare.getPrice() == null || returnFare.getPrice().getValue() > budget) {
						continue;
					}
					// check if we had a matching outbound flight on thursday, friday or saturday
					// but only include the earliest (if thursday and friday match, only thursday is returned)
					for (LocalDate outboundDate = calcPreviousDayOfWeek(returnDate, DateTimeConstants.THURSDAY); outboundDate.getDayOfWeek() <= DateTimeConstants.SATURDAY; outboundDate = outboundDate.plusDays(1)) {
						Fare outboundFare = outboundFares.get(outboundDate);
						if (outboundFare == null) {
							continue;
						}
						double totalCost = (outboundFare.getPrice().getValue() + returnFare.getPrice().getValue());

						if (totalCost <= budget) { 
							candidateDates.add(outboundDate);
							log("Candidate daily fare: " + totalCost + " " + outboundDate.getDayOfWeek() + ":" + outboundFare.getDay() + " " + returnDate.getDayOfWeek() + ":"+ returnFare.getDay());
							break;
						}	
					}
				}
			}
		}
		return candidateDates;
	}

	// currentDay = Monday, get previous friday => will minus a week and set as friday
	// currentDay = Sunday, get previous friday => will just set as friday
	private static LocalDate calcPreviousDayOfWeek(LocalDate currentDay, int targetDay) {
		if (currentDay.getDayOfWeek() <= targetDay) {
			currentDay = currentDay.minusWeeks(1);
		}
		return currentDay.withDayOfWeek(targetDay);
	}

	private static boolean isOutboundDay(LocalDate date) {
		if (!INCLUDE_LONGWEEKENDS) {
			return date.getDayOfWeek() == DateTimeConstants.FRIDAY || date.getDayOfWeek() == DateTimeConstants.SATURDAY;
		}
		
		return (date.getDayOfWeek() == DateTimeConstants.THURSDAY || date.getDayOfWeek() == DateTimeConstants.FRIDAY || date.getDayOfWeek() == DateTimeConstants.SATURDAY);
	}
	
	private static boolean isReturnDay(LocalDate date) {
		if (!INCLUDE_LONGWEEKENDS) {
			return date.getDayOfWeek() == DateTimeConstants.SUNDAY;
		}
		
		return (date.getDayOfWeek() == DateTimeConstants.SUNDAY || date.getDayOfWeek() == DateTimeConstants.MONDAY);
	}
	
	private static void log(Object s) throws IOException {
		log(s, false);
	}

	private static void log(Object s, boolean toFile) throws IOException {
		String fileName = "flights-output.txt";
		new File(fileName).createNewFile();
		if (toFile) {
			Files.write(Paths.get(fileName), (s.toString() + SEPARATOR).getBytes(), StandardOpenOption.APPEND);

			System.out.println(s.toString());
		} else {
			System.out.println(new Date() + " " + s.toString());
		}
	}

	private static void sleep() throws InterruptedException {
		sleep(14);
	}

	private static void sleep(int multiplier) throws InterruptedException {
		sleepRandom(37 * multiplier, 86 * multiplier);
	}

	/**
	 * Sleep a random amount of time, giving the appearance of a normal user request.
	 * @param minMilli
	 * @param maxMilli
	 * @throws InterruptedException
	 */
	private static void sleepRandom(int minMilli, int maxMilli) throws InterruptedException {
		Thread.sleep((long)(minMilli+(Math.random() * maxMilli)));
	}

	private final static ArrayList<String> UKI_CODES = new ArrayList<String>() {{
    	add("DUB");
    	add("BFS");
    	add("BHX");
    	add("BOH");
    	add("BRS");
    	add("CWL");
    	add("LDY");
    	add("EMA");
    	add("EDI");
    	add("GLA");
    	add("PIK");
    	add("LBA");
    	add("LPL");
    	add("LGW");
    	add("LTN");
    	add("STN");
    	add("MAN");
    	add("NCL");
    	add("NQY");
    	add("ORK");
    	add("KIR");
    	add("NOC");
    	add("SNN");
	}};
}
