/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class DeleteRouteAction implements Action {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		int hubId = Integer.parseInt(requestContent.getParameter("routeId"));

		try (Connection connection = db.getConnection()) {
			RouteDAO routeDAO = new RouteDAO(connection);
			Route route = routeDAO.findEntityById(hubId);
			
			if (routeDAO.delete(route)) {
				requestContent.setAttribute("mainState", "Маршрут удален.");
			} else {
				requestContent.setAttribute("mainState", "Ошибка при удалении маршрута.");
			}
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			requestContent.setAttribute("mainState", "Ошибка при обращении к БД.");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
			requestContent.setAttribute("mainState", "Маршрут не найден.");
		}

		Action action = ActionEnum.SHOW_ROUTES.getAction();

		return action.execute(requestContent);
	}

}
