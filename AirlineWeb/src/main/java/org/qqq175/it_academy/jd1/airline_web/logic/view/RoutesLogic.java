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

import org.qqq175.it_academy.jd1.airline_web.logic.Calculations;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.CityDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class RoutesLogic extends EntityToViewLogic<Route>{
	private Connection connection;
	private Locale locale;
	
	public RoutesLogic(Connection connection, Locale locale) {
		this.connection = connection;
		this.locale = locale;
	}


	/**
	 * 
	 * @param flight
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public Map<String, String> toMap(Route route) throws SQLException, EntityNotFoundException {
		Map<String, String> routeMap = new HashMap<>();


		routeMap.put("id", Integer.toString(route.getId()));
		routeMap.put("hub", getHubName(route));
		routeMap.put("destination", getDestName(route));

		routeMap.put("distance", Integer.toString((int)getRouteDistance(route)));

		return routeMap;
	}

	
	
	/**
	 * 
	 * @param route
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public double getRouteDistance(Route route) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		HubDAO hubDAO = new HubDAO(connection);
		Hub deptHub = hubDAO.findEntityById(route.getHubId());
		City deptCity = cityDAO.findEntityById(deptHub.getCityId());
		City destCity = cityDAO.findEntityById(route.getCityId());

		return Calculations.calcDistance(deptCity, destCity);
	}
	
	/**
	 * 
	 * @param route
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public String getDestName(Route route) throws SQLException, EntityNotFoundException {
		CityDAO cityDAO = new CityDAO(connection);
		City destCity = cityDAO.findEntityById(route.getCityId());

		return destCity.getName();
	}
	
	/**
	 * 
	 * @param route
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	public String getHubName(Route route) throws SQLException, EntityNotFoundException {
		HubDAO hubDAO = new HubDAO(connection);
		Hub hub = hubDAO.findEntityById(route.getHubId());
		CityDAO cityDAO = new CityDAO(connection);
		City deptCity = cityDAO.findEntityById(hub.getCityId());

		return deptCity.getName();
	}
	
	/**
	 * @return
	 */
	public List<Map<String, String>> getRoutesMaps(){

		try {
			RouteDAO routeDAO = new RouteDAO(connection);
			List<Route> routes = routeDAO.findAll();
			return this.listToMaps(routes);
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log("Sql reference error (not found) ", e);
		}
		
		return new ArrayList<>();
	}
}
