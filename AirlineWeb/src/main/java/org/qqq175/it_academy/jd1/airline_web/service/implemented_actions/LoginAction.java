/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.implemented_actions;

import java.sql.Connection;
import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.logic.LoginLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.service.Action;
import org.qqq175.it_academy.jd1.airline_web.service.ActionEnum;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.Logger;

/**
 * @author qqq175
 *
 */
public class LoginAction implements Action {
	LoginLogic loginLogic;

	/**
	 * 
	 */
	public LoginAction() {
		loginLogic = new LoginLogic();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.
	 * it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		String login = requestContent.getParameter("login");
		String password = requestContent.getParameter("password");
		Boolean success = false;
		String errorMessage = "";

		DataSource db = DataSource.getInstance();
		
		try (Connection connection = db.getConnection()) {
			
			if (loginLogic.loginUser(login, password, connection, requestContent)) {
					success = true;
			} else {
				Logger.getInstance().log("Unsuccessful login attemp for userId " + login);
				errorMessage = "Wrong login or password.";
			}
		} catch (SQLException e) {
			Logger.getInstance().log("Undefined sql error for userId " + login, e);
			errorMessage = "Undefined sql error";
		}
		
		
		if (success) {
			ActionEnum.SHOW_FLIGHTS.getAction().execute(requestContent);
		} else {
			requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
			requestContent.setAttribute("mainform", JSPPathManager.getProperty("form.login"));
			requestContent.setAttribute("loginError", errorMessage);
		}

		return requestContent;
	}
}
