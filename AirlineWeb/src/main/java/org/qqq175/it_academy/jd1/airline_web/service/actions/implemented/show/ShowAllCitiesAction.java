package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.show;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.view.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;

public class ShowAllCitiesAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		List<Map<String, String>> citiesMaps = new ArrayList<>();
		DataSource db = DataSource.getInstance();

		try (Connection connection = db.getConnection()) {
			CitiesLogic cityLogic = new CitiesLogic(connection, new Locale("ru-RU"));
			citiesMaps = cityLogic.getCitiesMaps();
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
		}

		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.admin.cities"));
		requestContent.setAttribute("cities", citiesMaps);
		requestContent.setAttribute("currentPage", "cities");

		return requestContent;
	}

}
