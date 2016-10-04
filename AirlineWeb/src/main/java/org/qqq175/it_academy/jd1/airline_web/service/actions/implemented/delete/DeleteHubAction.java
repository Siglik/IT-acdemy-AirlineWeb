/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class DeleteHubAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.
	 * it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		int hubId = Integer.parseInt(requestContent.getParameter("hubId"));

		try (Connection connection = db.getConnection()) {
			HubDAO hubDAO = new HubDAO(connection);
			Hub hub = hubDAO.findEntityById(hubId);

			if (hubDAO.delete(hub)) {
				requestContent.setAttribute("mainState", "Хаб удален.");
			} else {
				requestContent.setAttribute("mainState", "Ошибка при удалении хаба.");
			}
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			requestContent.setAttribute("mainState", "Ошибка при обращении к БД.");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
			requestContent.setAttribute("mainState", "Хаб не найден.");
		}

		Action action = ActionEnum.SHOW_HUBS.getAction();

		return action.execute(requestContent);
	}

}
