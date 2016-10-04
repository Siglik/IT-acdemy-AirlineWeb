package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.UserDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.User;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

public class UserDAO extends BasicDAO<User> implements UserDAOInterface {
	private static String TABLE_NAME = "user";
	private static int COLUMN_COUNT = 6;

	public UserDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	public User findUser(String login, String password) throws SQLException, EntityNotFoundException {
		try (PreparedStatement prepStatment = this.getConnection()
		        .prepareStatement(getSqlQuery().getQuery("sql.user.findBy.login.password"))) {
			prepStatment.setString(1, login);
			prepStatment.setString(2, password);
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				return toDTO(resultSet);
			}
		}
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, User entity) throws SQLException, UnsupportedOperation {
		throw new UnsupportedOperation();

	}
	
	/**
	 * Create user DTO from current result set entry
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected User fillEntity(ResultSet resultSet) throws SQLException{
		User user = new User();
		user.setId(resultSet.getInt(1));
		user.setLogin(resultSet.getString(2));
		user.setPasswordSha(resultSet.getString(3));
		user.setFirstName(resultSet.getString(4));
		user.setLastName(resultSet.getString(5));
		user.setType(User.UserType.valueOf(resultSet.getString(6).toUpperCase()));
		return user;
	}
}
