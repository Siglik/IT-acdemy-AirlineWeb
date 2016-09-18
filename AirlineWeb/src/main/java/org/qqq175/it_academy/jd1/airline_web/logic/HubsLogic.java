/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
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

	private String getCountryName(Hub hub) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		City city = cityDAO.findEntityById(hub.getCityId());
		return citiesLogic.getCountyName(city);
	}

	private String getCityName(Hub hub) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		City city = cityDAO.findEntityById(hub.getCityId());

		return city.getName();
	}

}
