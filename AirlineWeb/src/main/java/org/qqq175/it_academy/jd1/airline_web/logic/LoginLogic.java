/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.UserDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.User;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;
import org.qqq175.it_academy.jd1.airline_web.util.Settings;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class LoginLogic {
	
	/**
	 * calc password sha-256 hash
	 * @param input
	 * @return
	 */
	public String calcSHA(String input){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes("UTF-8"));
			byte[] digest = md.digest();
			
			return String.format("%064x", new java.math.BigInteger(1, digest));
			
		} catch (UnsupportedEncodingException e) {
			Logger.getInstance().log(e);
		}  catch (NoSuchAlgorithmException e1) {
			Logger.getInstance().log(e1);
		}
		
		return null;
	}
	
	/**
	 * @param password
	 * @return
	 */
	public String saltPassword(String password){
		return password + Settings.getInstance().getSalt();
	}
	
	/**
	 * @param password
	 * @return
	 */
	public String pepperPassword(String password, String login){
		return password + login.toUpperCase();
	}
	
	
	/**
	 * If user and pass is right return true, else return false
	 * @param login
	 * @param passwordHash
	 * @param connection
	 * @return
	 * @throws SQLException 
	 */
	public boolean loginUser(String login, String password, Connection connection, SessionRequestContent requestContent) throws SQLException{
		String saltedPassword = pepperPassword(saltPassword(password), login);

		UserDAO userDAO = new UserDAO(connection);
		User user = null;
		try{
			user = userDAO.findUser(login, saltedPassword);
		} catch (EntityNotFoundException e) {
			Logger.getInstance().log(e);
		}
		
		if(user != null){
			return applyUserToSession(user, requestContent);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param login
	 * @param passwordHash
	 * @param connection
	 * @param requestContent
	 * @return
	 */
	private boolean applyUserToSession(User user, SessionRequestContent requestContent){
		requestContent.setSessionAttribute("airlineUser", user);
		return true;
	}
}
