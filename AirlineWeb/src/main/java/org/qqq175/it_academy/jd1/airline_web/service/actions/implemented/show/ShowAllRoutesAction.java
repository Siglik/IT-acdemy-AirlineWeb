/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.show;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.view.RoutesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;

/**
 * @author qqq175
 *
 */
public class ShowAllRoutesAction implements Action {

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
		List<Map<String, String>> routesMaps = new ArrayList<>();

		try (Connection connection = db.getConnection()) {
			RoutesLogic routesLogic = new RoutesLogic(connection, new Locale("ru"));
			routesMaps = routesLogic.getRoutesMaps();
		} catch (SQLException e) {
			Logger.getInstance().log(
			        "Undefined sql error: " + e.getSQLState() + "\n" + e.getErrorCode() + "\n" + e.getMessage(), e);
			System.out.println("SQL ERROR");
		} 

		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("page.admin.routes"));
		requestContent.setAttribute("routes", routesMaps);
		requestContent.setAttribute("currentPage", "routes");

		return requestContent;
	}

}
