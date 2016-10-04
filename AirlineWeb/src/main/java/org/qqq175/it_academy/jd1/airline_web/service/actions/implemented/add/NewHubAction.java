package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.view.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

public class NewHubAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		String mode = requestContent.getParameter("mode");
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> citiesMaps = new ArrayList<>();
		
		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.admin.hub.new"));

		switch (mode) {
		case "ADD":
			int cityId = Integer.parseInt(requestContent.getParameter("cityId"));
			try {
				if (addHub(cityId)) {
					requestContent.setAttribute("mainState", "Хаб добавлен.");
				} else {
					requestContent.setAttribute("mainState", "Такой хаб уже есть в БД.");
				}
			} catch (SQLException | UnsupportedOperation e) {
				requestContent.setAttribute("mainState", "Ошибка при обращении к БД");
				e.printStackTrace();
			}
			Action action = ActionEnum.SHOW_HUBS.getAction();

			return action.execute(requestContent);

		default:
			try (Connection connection = db.getConnection()) {
				CitiesLogic citiesLogic = new CitiesLogic(connection, new Locale("ru"));
				citiesMaps = citiesLogic.getCitiesMaps();
			} catch (SQLException e) {
				Logger.getInstance().log(
				        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			}
		}

		requestContent.setAttribute("cities", citiesMaps);

		return requestContent;
	}

	/**
	 * 
	 * @param cityId
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedOperation
	 */
	public boolean addHub(int cityId) throws SQLException, UnsupportedOperation {

		DataSource db = DataSource.getInstance();
		try (Connection connection = db.getConnection()) {
			Hub hub = new Hub();
			hub.setCityId(cityId);

			HubDAO hubDAO = new HubDAO(connection);
			return hubDAO.create(hub);
		}
	}
}
