/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.CountryDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Country;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class CountryDAO extends BasicDAO<Country> implements CountryDAOInterface{
	private static String TABLE_NAME = "country";
	private static int COLUMN_COUNT = 3;
	
	public CountryDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

		@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, Country entity) throws SQLException {
		prepStatment.setString(1, entity.getLongName());
		prepStatment.setString(2, entity.getShortName());
	}

	@Override
	protected Country fillEntity(ResultSet resultSet) throws SQLException {
		Country country = new Country();
		country.setId(resultSet.getInt(1));
		country.setLongName(resultSet.getString(2));
		country.setShortName(resultSet.getString(3));
		return country;
	}

	@Override
	public int getCountryIdByShortName(String shortName) throws SQLException, EntityNotFoundException {
		try (PreparedStatement prepStatment = this.getConnection().prepareStatement(this.getSqlQuery().getQuery("sql.country.idByShortName"))) {
			prepStatment.setString(1, shortName);
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				if (resultSet.next()){
					return resultSet.getInt(1);
				}
			}
		}
		return -1;
	}

}
