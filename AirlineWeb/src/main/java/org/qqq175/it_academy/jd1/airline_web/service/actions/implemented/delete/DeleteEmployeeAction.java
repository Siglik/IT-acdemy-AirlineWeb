/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.EmployeeDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class DeleteEmployeeAction implements Action {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		int employeeId = Integer.parseInt(requestContent.getParameter("employeeID"));

		try (Connection connection = db.getConnection()) {
			EmployeeDAO employeeDAO = new EmployeeDAO(connection);
			Employee employee = employeeDAO.findEntityById(employeeId);
			
			if (employeeDAO.delete(employee)) {
				requestContent.setAttribute("mainState", "Сотрудник удален.");
			} else {
				requestContent.setAttribute("mainState", "Ошибка при удалении сотрудника.");
			}
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			requestContent.setAttribute("mainState", "Ошибка при обращении к БД.");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
			requestContent.setAttribute("mainState", "Сотрудник не найден.");
		}

		Action action = ActionEnum.SHOW_EMPLOYEES.getAction();

		return action.execute(requestContent);
	}

}
