/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.EmployeeDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.view.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.logic.view.HubsLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * @author qqq175
 *
 */
public class NewEmployeeAction implements Action {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		String mode = requestContent.getParameter("mode");
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> hubsMaps = new ArrayList<>();
		
		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.dispatcher"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.dispatcher.employee.new"));

		switch (mode) {
		case "ADD":
			int hubId = Integer.parseInt(requestContent.getParameter("hubId"));
			String firstName = requestContent.getParameter("firstName");
			String lastName = requestContent.getParameter("lastName");
			Employee.Speciality spec = Employee.Speciality.valueOf(requestContent.getParameter("speciality").toUpperCase());
			
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setHubId(hubId);
			employee.setSpecialty(spec);
			
			/*<script type="text/javascript">alert("hack! heyo")</script>*/
			try (Connection connection = db.getConnection()) {
				EmployeeDAO employeeDAO = new EmployeeDAO(connection);
				
				if (employeeDAO.create(employee)) {
					requestContent.setAttribute("mainState", "Сотрудник добавлен.");
				}
			} catch (SQLException | UnsupportedOperation e) {
				requestContent.setAttribute("mainState", "Ошибка при обращении к БД");
				e.printStackTrace();
			}
			Action action = ActionEnum.SHOW_EMPLOYEES.getAction();

			return action.execute(requestContent);

		default:
			try (Connection connection = db.getConnection()) {
				HubsLogic hubsLogic = new HubsLogic(connection, new Locale("ru"));
				hubsMaps = hubsLogic.getHubsMaps();
			} catch (SQLException e) {
				Logger.getInstance().log(
				        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			}
		}

		requestContent.setAttribute("hubs", hubsMaps);

		return requestContent;
	}

}
