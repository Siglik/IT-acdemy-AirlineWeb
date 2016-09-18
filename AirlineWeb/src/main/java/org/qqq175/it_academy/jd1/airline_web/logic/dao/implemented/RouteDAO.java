/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.Route;

/**
 * @author qqq175
 *
 */
public class RouteDAO extends BasicDAO<Route> {
	private static String TABLE_NAME = "route";
	private static int COLUMN_COUNT = 3;
	
	public RouteDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, Route entity) throws SQLException {
		prepStatment.setInt(1, entity.getHubId());
		prepStatment.setInt(2, entity.getCityId());
	}

	@Override
	protected Route fillEntity(ResultSet resultSet) throws SQLException {
		Route route = new Route();
		route.setId(resultSet.getInt(1));
		route.setHubId(resultSet.getInt(2));
		route.setCityId(resultSet.getInt(3));
		return route;
	}

}
