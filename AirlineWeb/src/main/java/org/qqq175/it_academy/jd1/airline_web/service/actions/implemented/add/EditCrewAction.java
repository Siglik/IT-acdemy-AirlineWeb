/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.AirplaneModelDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.EmployeeDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.FlightDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.AirplaneModel;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class EditCrewAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		int flightId = Integer.parseInt(requestContent.getParameter("flightId"));
		DataSource db = DataSource.getInstance();
		try (Connection connection = db.getConnection()) {
			FlightDAO flightDAO = new FlightDAO(connection);
			Flight flight = flightDAO.findEntityById(flightId);

			RouteDAO routeDAO = new RouteDAO(connection);
			Route route = routeDAO.findEntityById(flight.getRouteId());
			
			AirplaneModelDAO airplaneModelDAO = new AirplaneModelDAO(connection);
			AirplaneModel airplaneModel = airplaneModelDAO.findEntityById(flight.getAirplaneModelId());
			

			// get flight crew
			List<Employee> crew = flightDAO.getFlightCrew(flight);
			
			//get crew lists by speciality
			List<Employee> crewPilots = crew.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.PILOT)).collect(Collectors.toList());

			List<Employee> crewNavigators = crew.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.NAVIGATOR))
			        .collect(Collectors.toList());

			List<Employee> crewRadioOperators = crew.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.RADIO_OPERATOR))
			        .collect(Collectors.toList());

			List<Employee> crewAirStewards = crew.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.AIR_STEWARD))
			        .collect(Collectors.toList());

			// get all employees from current hub
			EmployeeDAO employeeDAO = new EmployeeDAO(connection);
			List<Employee> emloyees = employeeDAO.findAll().stream().filter(emp -> emp.getHubId() == route.getHubId())
			        .collect(Collectors.toList());
			
			//get emloyees lists by speciality
			List<Employee> pilots = emloyees.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.PILOT)).collect(Collectors.toList());

			List<Employee> navigators = emloyees.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.NAVIGATOR))
			        .collect(Collectors.toList());

			List<Employee> radioOperators = emloyees.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.RADIO_OPERATOR))
			        .collect(Collectors.toList());

			List<Employee> airStewards = emloyees.stream()
			        .filter(empl -> empl.getSpecialty().equals(Employee.Speciality.AIR_STEWARD))
			        .collect(Collectors.toList());

			requestContent.setAttribute("flightId", flight.getId());
			requestContent.setAttribute("airplane", airplaneModel);
			
			requestContent.setAttribute("crewPilots", crewPilots);
			requestContent.setAttribute("crewNavigators", crewNavigators);
			requestContent.setAttribute("crewRadioOperators", crewRadioOperators);
			requestContent.setAttribute("crewAirStewards", crewAirStewards);
			
			requestContent.setAttribute("pilots", pilots);
			requestContent.setAttribute("navigators", navigators);
			requestContent.setAttribute("radioOperators", radioOperators);
			requestContent.setAttribute("airStewards", airStewards);
			
		} catch (SQLException | EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.dispatcher"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.dispatcher.crew.edit"));

		return requestContent;

	}

}