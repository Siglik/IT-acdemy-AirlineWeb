/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class DeleteCityAction implements Action {

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
		int cityId = Integer.parseInt(requestContent.getParameter("cityId"));

		try (Connection connection = db.getConnection()) {
			CityDAO cityDAO = new CityDAO(connection);
			City city = cityDAO.findEntityById(cityId);
			if (cityDAO.delete(city)) {
				requestContent.setAttribute("mainState", "Город удален.");
			} else {
				requestContent.setAttribute("mainState", "Ошибка при удалении города.");
			}
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			requestContent.setAttribute("mainState", "Ошибка при обращении к БД.");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
			requestContent.setAttribute("mainState", "Город не найден.");
		}

		Action action = ActionEnum.SHOW_CITIES.getAction();

		return action.execute(requestContent);
	}

}
