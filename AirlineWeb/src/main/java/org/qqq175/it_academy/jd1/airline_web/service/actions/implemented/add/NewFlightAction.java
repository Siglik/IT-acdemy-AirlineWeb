/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.AirplaneModelDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.FlightDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.view.RoutesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.AirplaneModel;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * @author qqq175
 *
 */
public class NewFlightAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action#
	 * execute(org.qqq175.it_academy.jd1.airline_web.service.
	 * SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		String mode = requestContent.getParameter("mode");
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> routesMaps = new ArrayList<>();
		List<AirplaneModel> airplanes = new ArrayList<>();

		switch (mode) {
		case "ADD":
			try (Connection connection = db.getConnection()) {
				int routeId = Integer.parseInt(requestContent.getParameter("routeId"));
				int airplaneId = Integer.parseInt(requestContent.getParameter("airplaneId"));
				String dateString = requestContent.getParameter("deptDate").replace("+", " ");

				AirplaneModelDAO airplaneModelDAO = new AirplaneModelDAO(connection);
				AirplaneModel airplaneModel = airplaneModelDAO.findEntityById(airplaneId);

				RouteDAO routeDAO = new RouteDAO(connection);
				Route route = routeDAO.findEntityById(routeId);

				if (!this.isPlaneOK(airplaneModel, route)) {
					requestContent.setAttribute("mainState", "ОШИБКА. Маршрут длинее дальности полета выбранной модели самолета.");
				} else {
					Date deptDate = this.getDateFromString(dateString);
					Date deptBackDate = calcDeptBackDate(deptDate, airplaneModel, route);
					
					Flight flight = new Flight();
					flight.setAirplaneModelId(airplaneId);
					flight.setRouteId(routeId);
					flight.setDeptTime(deptDate);
					flight.setDeptTimeBack(deptBackDate);
					
					FlightDAO flightDAO = new FlightDAO(connection);
					if(flightDAO.create(flight)){
						requestContent.setAttribute("mainState", "Рейс добавлен.");
					} else {
						requestContent.setAttribute("mainState", "Ошибка при добвлении рейса.");
					}
				}

			} catch (SQLException | EntityNotFoundException | UnsupportedOperation e) {
				requestContent.setAttribute("mainState", "Ошибка при обращении к БД");
			} catch (DateTimeParseException e) {
				requestContent.setAttribute("mainState", "Неверный формат даты!");
			}

			// reroute to list page
			Action action = ActionEnum.SHOW_FLIGHTS.getAction();

			return action.execute(requestContent);

		default:
			try (Connection connection = db.getConnection()) {
				RoutesLogic routesLogic = new RoutesLogic(connection, new Locale("ru"));
				routesMaps = routesLogic.getRoutesMaps();

				AirplaneModelDAO airplaneModelDAO = new AirplaneModelDAO(connection);
				airplanes = airplaneModelDAO.findAll();

			} catch (SQLException e) {
				Logger.getInstance().log(
				        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			}
		}

		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.admin.flight.new"));
		requestContent.setAttribute("routes", routesMaps);
		requestContent.setAttribute("airplanes", airplanes);

		return requestContent;
	}

	/**
	 * 
	 * @param airplaneModel
	 * @param route
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	private boolean isPlaneOK(AirplaneModel airplaneModel, Route route) throws SQLException, EntityNotFoundException {
		DataSource db = DataSource.getInstance();
		try (Connection connection = db.getConnection()) {
			RoutesLogic routesLogic = new RoutesLogic(connection, new Locale("ru-RU"));

			return routesLogic.getRouteDistance(route) <= airplaneModel.getFlightRange();
		}
	}

	/**
	 * 
	 * @param dateString
	 * @return
	 */
	Date getDateFromString(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return Date.from(LocalDateTime.from(formatter.parse(dateString)).atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 
	 * @param deptDate
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	Date calcDeptBackDate(Date deptDate, AirplaneModel airplaneModel, Route route) throws SQLException, EntityNotFoundException {
		DataSource db = DataSource.getInstance();
		try (Connection connection = db.getConnection()) {
			RoutesLogic routesLogic = new RoutesLogic(connection, new Locale("ru-RU"));
			
		double distance = routesLogic.getRouteDistance(route);
		double speed = airplaneModel.getAvgSpeed();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(deptDate);
		//add flight time
		cal.add(Calendar.MINUTE, (int)(distance/speed*60));
		//add rest time
		if (distance/speed > 2) {
			cal.add(Calendar.HOUR, 6);
		} else {
			cal.add(Calendar.HOUR, 10);
		}

		return cal.getTime();
		}
	}
}