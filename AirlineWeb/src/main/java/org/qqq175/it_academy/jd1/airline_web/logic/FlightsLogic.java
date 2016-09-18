/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.AirplaneModelDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.AirplaneModel;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class FlightsLogic extends EntityToViewLogic<Flight>{
	private Connection connection;
	private Locale locale;
	private RoutesLogic routesLogic;

	/**
	 * Constructor
	 * 
	 * @param connection
	 * @param locale
	 */
	public FlightsLogic(Connection connection, Locale locale) {
		this.connection = connection;
		this.locale = locale;
		routesLogic = new RoutesLogic(connection, locale);
	}


	/**
	 * 
	 * @param flight
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public Map<String, String> toMap(Flight flight) throws SQLException, EntityNotFoundException {
		Map<String, String> flightMap = new HashMap<>();
		Calendar cal = new GregorianCalendar();

		flightMap.put("id", Integer.toString(flight.getId()));
		flightMap.put("hub", getHubName(flight));
		flightMap.put("destination", getDestName(flight));

		Date flightTime = getFlightTime(flight);
		flightMap.put("flightTime", getFlightTimeString(flight));

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);

		flightMap.put("deptTime", dateFormat.format(flight.getDeptTime()));

		cal.setTime(flight.getDeptTime());
		cal.add(Calendar.SECOND, (int) (flightTime.getTime() / 1000));
		Date arrivalDate = cal.getTime();
		flightMap.put("arrTime", dateFormat.format(arrivalDate));

		flightMap.put("deptTimeBack", dateFormat.format(flight.getDeptTimeBack()));

		cal.setTime(flight.getDeptTimeBack());
		cal.add(Calendar.SECOND, (int) (flightTime.getTime() / 1000));
		Date arrivalBackDate = cal.getTime();
		flightMap.put("arrTimeBack", dateFormat.format(arrivalBackDate));
		System.out.println(flightMap);
		return flightMap;
	}

	/**
	 * 
	 * @param flight
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public String getHubName(Flight flight) throws SQLException, EntityNotFoundException {
		Route route = getRoute(flight);

		return routesLogic.getHubName(route);
	}

	/**
	 * 
	 * @param flight
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public String getDestName(Flight flight) throws SQLException, EntityNotFoundException {
		Route route = getRoute(flight);

		return routesLogic.getDestName(route);
	}

	/**
	 * 
	 * @param flight
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public Date getFlightTime(Flight flight) throws SQLException, EntityNotFoundException {
		Route route = getRoute(flight);
		AirplaneModel airplaneModel = getAirplaneModel(flight);
		double distance = routesLogic.getRouteDistance(route);

		double timeHours = distance / airplaneModel.getAvgSpeed();

		Date time = new Date((long) (timeHours * 3600 * 1000));

		return time;
	}

	/**
	 * 
	 * @param flight
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public String getFlightTimeString(Flight flight) throws SQLException, EntityNotFoundException {
		Date time = getFlightTime(flight);
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String timeString = formatter.format(time);

		return timeString;
	}

	/**
	 * 
	 * @param flight
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	private Route getRoute(Flight flight) throws SQLException, EntityNotFoundException {
		RouteDAO routeDAO = new RouteDAO(connection);
		Route route = routeDAO.findEntityById(flight.getRouteId());

		return route;
	}

	/**
	 * 
	 * @param flight
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	private AirplaneModel getAirplaneModel(Flight flight) throws SQLException, EntityNotFoundException {
		AirplaneModelDAO airplaneModelDAO = new AirplaneModelDAO(connection);
		AirplaneModel airplaneModel = airplaneModelDAO.findEntityById(flight.getAirplaneModelId());

		return airplaneModel;
	}
}
