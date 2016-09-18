package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.User;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
/**
 * 
 * @author qqq175

 */
public interface UserDAOInterface extends BasicDAOInterface<User> {
	/**
	 * Find user by password and login
	 * @param login
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	User findUser(String login, String password) throws SQLException, EntityNotFoundException;
}
