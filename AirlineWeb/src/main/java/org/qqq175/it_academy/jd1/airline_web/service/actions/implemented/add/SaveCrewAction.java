/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.EmployeeDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.FlightDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class SaveCrewAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		int flightId = Integer.parseInt(requestContent.getParameter("flightId"));
		DataSource db = DataSource.getInstance();
		List<Integer> employeesIDs = getEmployeesIDs(requestContent);

		employeesIDs.forEach(id -> System.out.print(" " + id));

		if ((new HashSet<Integer>(employeesIDs)).size() < employeesIDs.size()) {
			requestContent.setAttribute("mainState", "Ошибка. Сотрудник назначен на рейс более одного раза.");
		} else {
			try (Connection connection = db.getConnection()) {
				connection.setAutoCommit(false);
				
				FlightDAO flightDAO = new FlightDAO(connection);
				Flight flight = flightDAO.findEntityById(flightId);
				flightDAO.dismissFlightCrew(flight);
				
				EmployeeDAO employeeDAO = new EmployeeDAO(connection);
				
				for(Integer empId : employeesIDs){
					employeeDAO.appointToFlight(flight, employeeDAO.findEntityById(empId));
				}
				connection.commit();
				requestContent.setAttribute("mainState", "Изменения в экипаже сохранены.");
			} catch (SQLException | EntityNotFoundException e) {
				e.printStackTrace();
				requestContent.setAttribute("mainState", "Ошибка при созранении в БД.");
			}
		}
		
		Action action = ActionEnum.SHOW_FLIGHTS.getAction();

		return action.execute(requestContent);
	}

	List<Integer> getEmployeesIDs(SessionRequestContent requestContent) {
		List<String> empStringIDs = new ArrayList<>();

		String[] tmp = requestContent.getParameters("pilotIDs");
		if (tmp != null) {
			empStringIDs.addAll(Arrays.asList(tmp));
			tmp = null;
		}
		tmp = requestContent.getParameters("navigatorIDs");
		if (tmp != null) {
			empStringIDs.addAll(Arrays.asList(tmp));
			tmp = null;
		}
		tmp = requestContent.getParameters("radioOperatorIDs");
		if (tmp != null) {
			empStringIDs.addAll(Arrays.asList(tmp));
			tmp = null;
		}
		tmp = requestContent.getParameters("airStewardIDs");
		if (tmp != null) {
			empStringIDs.addAll(Arrays.asList(tmp));
			tmp = null;
		}

		return empStringIDs.stream().map(emp -> Integer.valueOf(emp)).collect(Collectors.toList());
	}
}
