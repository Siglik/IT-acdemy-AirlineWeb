/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.FlightDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class DeleteFlightAction implements Action {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		int flightId = Integer.parseInt(requestContent.getParameter("flightId"));

		try (Connection connection = db.getConnection()) {
			FlightDAO flightDAO = new FlightDAO(connection);
			Flight flight = flightDAO.findEntityById(flightId);
			
			if (flightDAO.delete(flight)) {
				requestContent.setAttribute("mainState", "Рейс удален.");
			} else {
				requestContent.setAttribute("mainState", "Ошибка при удалении рейса.");
			}
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			requestContent.setAttribute("mainState", "Ошибка при обращении к БД.");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
			requestContent.setAttribute("mainState", "Рейс не найден.");
		}

		Action action = ActionEnum.SHOW_FLIGHTS.getAction();

		return action.execute(requestContent);
	}

}
