/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.AirplaneModel;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * @author qqq175
 *
 */
public class AirplaneModelDAO extends BasicDAO<AirplaneModel> {
	private static String TABLE_NAME = "airplane_model";
	private static int COLUMN_COUNT = 8;
	
	public AirplaneModelDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	public boolean create(AirplaneModel entity) throws UnsupportedOperation {
		throw new UnsupportedOperation();
	}

	@Override
	public boolean update(AirplaneModel entity) throws UnsupportedOperation {
		throw new UnsupportedOperation();
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, AirplaneModel entity) throws SQLException, UnsupportedOperation {
		throw new UnsupportedOperation();
	}

	@Override
	protected AirplaneModel fillEntity(ResultSet resultSet) throws SQLException {
		AirplaneModel airplaneModel = new AirplaneModel();
		airplaneModel.setId(resultSet.getInt(1));
		airplaneModel.setName(resultSet.getString(2));
		airplaneModel.setFlightRange(resultSet.getInt(3));
		airplaneModel.setAvgSpeed(resultSet.getInt(4));
		airplaneModel.setPilots(resultSet.getInt(5));
		airplaneModel.setNavigators(resultSet.getInt(6));
		airplaneModel.setRadioOperators(resultSet.getInt(7));
		airplaneModel.setAirStewards(resultSet.getInt(8));
		return airplaneModel;
	}

}
