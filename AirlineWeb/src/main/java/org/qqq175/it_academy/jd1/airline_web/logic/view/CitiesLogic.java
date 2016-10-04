package org.qqq175.it_academy.jd1.airline_web.logic.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.PlaceLogic;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CountryDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Country;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.GoogleApiException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * 
 * @author qqq175
 *
 */
public class CitiesLogic extends EntityToViewLogic<City> {
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
		routeMap.put("latitude",String.format("%.6f", city.getLatitude()));
		routeMap.put("longitude", String.format("%.6f", city.getLongitude()));

		return routeMap;
	}

	/**
	 * 
	 * @param city
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	String getCountyName(City city) throws SQLException, EntityNotFoundException {
		CountryDAO countryDAO = new CountryDAO(connection);
		Country country = countryDAO.findEntityById(city.getCountryId());

		return country.getLongName() + ", " + country.getShortName();
	}

	public boolean addCity(String placeId) throws SQLException, UnsupportedOperation {
		PlaceLogic placeLogic = new PlaceLogic();
		PlaceLogic.Place place;
		
		//get place from google
		try {
			place = placeLogic.getPlaceByPlaceId(placeId, locale.getLanguage());
		} catch (GoogleApiException e) {
			Logger.getInstance().log(e);
			return false;
		}
		
		//add new country if not exist
		CountryDAO countryDAO = new CountryDAO(connection);
		try {
			int countryId = countryDAO.getCountryIdByShortName(place.getCountry().getShortName());
			if (countryId != -1) {
				place.getCountry().setId(countryId);
			} else {
				countryDAO.create(place.getCountry());
				countryId = countryDAO.getCountryIdByShortName(place.getCountry().getShortName());
				place.getCountry().setId(countryId);
			}
		} finally {

		}

		place.getCity().setCountryId(place.getCountry().getId());
		
		//add new city if not exist
		CityDAO cityDAO = new CityDAO(connection);
		try {
			int cityId = cityDAO.getCityIdByPlaceId(place.getCity().getPlaceId());
			if (cityId != -1) {
				// if exist return false
				return false;
			} else {
				// try to insert to DB and return result
				return cityDAO.create(place.getCity());
			}
		} finally {

		}
	}
	
	/**
	 * @return
	 */
	public List<Map<String, String>> getCitiesMaps(){

		try {
			CityDAO cityDAO = new CityDAO(connection);
			List<City> cities = cityDAO.findAll();
			return this.listToMaps(cities);
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}
		
		return new ArrayList<>();
	}
}
