/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;

/**
 * @author qqq175
 *
 */
public class HubDAO extends BasicDAO<Hub>{
	private static String TABLE_NAME = "hub";
	private static int COLUMN_COUNT = 2;
	
	public HubDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, Hub entity) throws SQLException {
			prepStatment.setInt(1, entity.getCityId());
	}

	@Override
	protected Hub fillEntity(ResultSet resultSet) throws SQLException {
		Hub hub = new Hub();
		hub.setId(resultSet.getInt(1));
		hub.setCityId(resultSet.getInt(2));
		return hub;
	}

}
