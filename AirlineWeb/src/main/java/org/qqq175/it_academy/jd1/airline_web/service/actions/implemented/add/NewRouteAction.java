/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.RouteDAO;
import org.qqq175.it_academy.jd1.airline_web.logic.view.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.logic.view.HubsLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;


/**
 * @author qqq175
 *
 */
public class NewRouteAction implements Action {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.
	 * it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		String mode = requestContent.getParameter("mode");
		DataSource db = DataSource.getInstance();
		List<Map<String, String>> citiesMaps = new ArrayList<>();
		List<Map<String, String>> hubsMaps = new ArrayList<>();

		switch (mode) {
		case "ADD":

			try (Connection connection = db.getConnection()) {
				int hubId = Integer.parseInt(requestContent.getParameter("hubId"));
				int cityId = Integer.parseInt(requestContent.getParameter("cityId"));
				
				if (isSameCity(hubId, cityId, connection)) {
					 requestContent.setAttribute("mainState", "ОШИБКА: пункт назначения и пункт отправления должны быть различными.");
				} else {
					//create route and add to DB
					Route route = new Route();
					route.setHubId(hubId);
					route.setCityId(cityId);
					RouteDAO routeDAO = new RouteDAO(connection);
					
					if (routeDAO.create(route)) {
						requestContent.setAttribute("mainState", "Маршрут добавлен.");
					} else {
						requestContent.setAttribute("mainState", "Ошибка при добвлении маршрута.");
					}
				}
			}catch (SQLException | EntityNotFoundException | UnsupportedOperation e) {
				requestContent.setAttribute("mainState", "Ошибка при обращении к БД");
			}
			
			//reroute to list page
			Action action = ActionEnum.SHOW_ROUTES.getAction();
			
			return action.execute(requestContent);
			
		default:
			try (Connection connection = db.getConnection()) {
				CitiesLogic citiesLogic = new CitiesLogic(connection, new Locale("ru"));
				citiesMaps = citiesLogic.getCitiesMaps();

				HubsLogic hubsLogic = new HubsLogic(connection, new Locale("ru"));
				hubsMaps = hubsLogic.getHubsMaps();
			} catch (SQLException e) {
				Logger.getInstance().log(
				        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			}
		}

		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.admin.route.new"));
		requestContent.setAttribute("hubs", hubsMaps);
		requestContent.setAttribute("cities", citiesMaps);

		return requestContent;
	}

	/**
	 * check if hub with hubID is located in city with city ID
	 * 
	 * @param hubId
	 * @param cityId
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	private boolean isSameCity(int hubId, int cityId, Connection connection)
	        throws SQLException, EntityNotFoundException {
		HubDAO hubDAO = new HubDAO(connection);
		Hub hub = hubDAO.findEntityById(hubId);

		return hub.getCityId() == cityId;
	}

}
