package org.qqq175.it_academy.jd1.airline_web.service.implemented_actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.service.Action;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

public class ShowAllCitiesAction implements Action {

	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> citiesMaps = new ArrayList<>();

		try (Connection connection = db.getConnection()) {
			CityDAO cityDAO = new CityDAO(connection);
			List<City> cities = cityDAO.findAll();
			CitiesLogic citiesLogic = new CitiesLogic(connection, new Locale("ru"));
			citiesMaps = citiesLogic.listToMaps(cities);
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			System.out.println("SQL ERROR");
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}

		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.admin.cities"));
		requestContent.setAttribute("cities", citiesMaps);
		requestContent.setAttribute("currentPage", "cities");

		return requestContent;
	}

}
