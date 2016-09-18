/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.implemented_actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.FlightsLogic;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.FlightDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.model.dto.User;
import org.qqq175.it_academy.jd1.airline_web.service.Action;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class ShowAllFlightsAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.
	 * it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		User.UserType userType = ((User) requestContent.getSessionAttribute("airlineUser")).getType();
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> flightsMaps = new ArrayList<>();

		try (Connection connection = db.getConnection()) {
			FlightDAO flightDAO = new FlightDAO(connection);
			List<Flight> flights = flightDAO.findAll();
			FlightsLogic flightLogic = new FlightsLogic(connection, new Locale("ru"));
			flightsMaps = flightLogic.listToMaps(flights);
		} catch (SQLException e) {
			Logger.getInstance().log("Undefined sql error: " + e.getSQLState() +"\n"+ e.getErrorCode() +"\n"+ e.getMessage(), e);
			System.out.println("SQL ERROR");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}

		
		switch (userType) {
		case ADMIN:
			requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
			requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
			requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.admin.flights"));
			requestContent.setAttribute("flights", flightsMaps);
			requestContent.setAttribute("currentPage", "flights");
			break;
		case DISPATCHER:
			requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
			requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.dispatcher"));
			requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.dispatcher.flights"));
			requestContent.setAttribute("flights", flightsMaps);
			requestContent.setAttribute("currentPage", "flights");
			break;
		default:
		}
		return requestContent;
	}

}



//// debug
//Map<String, String> flightMap = new HashMap<>();
//
//flightMap.put("id", Integer.toString(777));
//flightMap.put("hub", "Могилев");
//flightMap.put("destination", "Минск");
//
//Date flightTime = new Date(36771112l);
//DateFormat formatter = DateFormat.getTimeInstance(DateFormat.SHORT, new Locale("ru"));
//formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
//String timeString = formatter.format(flightTime);
//flightMap.put("flightTime", timeString);
//
//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, new Locale("ru"));
//Calendar cal = new GregorianCalendar();
//
//Date date = new Date();
//flightMap.put("deptTime", dateFormat.format(date));
//
//cal.setTime(date);
//cal.add(Calendar.SECOND, (int) (flightTime.getTime() / 1000));
//Date arrivalDate = cal.getTime();
//flightMap.put("arrTime", dateFormat.format(arrivalDate));
//
//flightMap.put("deptTimeBack", dateFormat.format(arrivalDate));
//
//cal.setTime(arrivalDate);
//cal.add(Calendar.SECOND, (int) (flightTime.getTime() / 1000));
//Date arrivalBackDate = cal.getTime();
//flightMap.put("arrTimeBack", dateFormat.format(arrivalBackDate));
//
//flightsMaps.add(flightMap);
// end debug
