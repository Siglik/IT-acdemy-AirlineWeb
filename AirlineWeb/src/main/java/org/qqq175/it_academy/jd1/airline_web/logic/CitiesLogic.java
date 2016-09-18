package org.qqq175.it_academy.jd1.airline_web.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CountryDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Country;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * 
 * @author qqq175
 *
 */
public class CitiesLogic extends EntityToViewLogic<City>{
	private Connection connection;
	private Locale locale;
	
	public CitiesLogic(Connection connection, Locale locale) {
		this.connection = connection;
		this.locale = locale;
	}
	
	@Override
	public Map<String, String> toMap(City city) throws SQLException, EntityNotFoundException {
		Map<String, String> routeMap = new HashMap<>();


		routeMap.put("id", Integer.toString(city.getId()));
		routeMap.put("name", city.getName());
		routeMap.put("country", getCountyName(city));
		routeMap.put("latitude", Double.toString(city.getLatitude()));
		routeMap.put("longitude", Double.toString(city.getLongitude()));

		return routeMap;
	}

	String getCountyName(City city) throws SQLException, EntityNotFoundException {
		CountryDAO countryDAO = new CountryDAO(connection);
		Country country = countryDAO.findEntityById(city.getCountryId());

		
		return country.getLongName() + ", " + country.getShortName();
	}
}
