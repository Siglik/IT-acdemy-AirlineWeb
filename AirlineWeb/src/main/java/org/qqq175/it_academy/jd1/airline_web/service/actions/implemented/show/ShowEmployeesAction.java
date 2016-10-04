/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.show;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.EmployeeDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.view.EmployeesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class ShowEmployeesAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> employeesMaps = new ArrayList<>();

		try (Connection connection = db.getConnection()) {
			EmployeeDAO employeeDAO = new EmployeeDAO(connection);
			List<Employee> employees = employeeDAO.findAll();

			//filter list if nessesarry
			try {
				Employee.Speciality filter = Employee.Speciality
				        .valueOf(requestContent.getParameter("speciality").toUpperCase());
				employees = employees.stream().filter(empl -> empl.getSpecialty().equals(filter)).collect(Collectors.toList());
			} catch (IllegalArgumentException e) {
				// nothing to do, just show all
			}
			
			EmployeesLogic employeesLogic = new EmployeesLogic(connection, new Locale("ru"));
			employeesMaps = employeesLogic.listToMaps(employees);

		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}


		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.dispatcher"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.dispatcher.employees"));
		requestContent.setAttribute("employees", employeesMaps);
		requestContent.setAttribute("currentPage", "employee");

		return requestContent;
	}

}
