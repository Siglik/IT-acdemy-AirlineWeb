/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class HubsLogic extends EntityToViewLogic<Hub>{
	private Connection connection;
	private Locale locale;
	private CitiesLogic citiesLogic;
	
	public HubsLogic(Connection connection, Locale locale) {
		this.connection = connection;
		this.locale = locale;
		citiesLogic = new CitiesLogic(this.connection, this.locale);
	}
	
	@Override
	public Map<String, String> toMap(Hub hub) throws SQLException, EntityNotFoundException {
		Map<String, String> hubMap = new HashMap<>();


		hubMap.put("id", Integer.toString(hub.getId()));
		hubMap.put("name", getCityName(hub));
		hubMap.put("country", getCountryName(hub));

		return hubMap;
	}

	public String getCountryName(Hub hub) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		City city = cityDAO.findEntityById(hub.getCityId());
		return citiesLogic.getCountyName(city);
	}

	public String getCityName(Hub hub) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		City city = cityDAO.findEntityById(hub.getCityId());

		return city.getName();
	}
	
	/**
	 * @return
	 */
	public List<Map<String, String>> getHubsMaps(){

		try {
			HubDAO hubDAO = new HubDAO(connection);
			List<Hub> hubs = hubDAO.findAll();
			return this.listToMaps(hubs);
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}
		
		return new ArrayList<>();
	}
}
