/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.service.actions.implemented.add;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import org.qqq175.it_academy.jd1.airline_web.logic.view.CitiesLogic;
import org.qqq175.it_academy.jd1.airline_web.model.DataSource;
import org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent;
import org.qqq175.it_academy.jd1.airline_web.service.actions.interfaces.Action;
import org.qqq175.it_academy.jd1.airline_web.util.JSPPathManager;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * @author qqq175
 *
 */
public class NewCityAction implements Action {

	/* (non-Javadoc)
	 * @see org.qqq175.it_academy.jd1.airline_web.service.Action#execute(org.qqq175.it_academy.jd1.airline_web.service.SessionRequestContent)
	 */
	@Override
	public SessionRequestContent execute(SessionRequestContent requestContent) {
		
		String mode = requestContent.getParameter("mode");
		
		requestContent.setAttribute("page", JSPPathManager.getProperty("page.main"));
		requestContent.setAttribute("mainform", JSPPathManager.getProperty("page.admin"));
		requestContent.setAttribute("userpage", JSPPathManager.getProperty("form.admin.city.new"));
		
		switch (mode){
		case "ADD":
			String placeId = requestContent.getParameter("place_id");
			try {
				if (addCity(placeId)){
					requestContent.setAttribute("mainState", "Город добавлен.");
				} else {
					requestContent.setAttribute("mainState", "Такой город уже есть в БД.");
				}
			} catch (SQLException | UnsupportedOperation e) {
				requestContent.setAttribute("mainState", "Ошибка при обращении к БД");
				e.printStackTrace();
			}
			break;
		default:
		}
		
		

		return requestContent;
	}
	
	private boolean addCity(String placeId) throws SQLException, UnsupportedOperation{
		DataSource db = DataSource.getInstance();
		try (Connection connection = db.getConnection()) {
			CitiesLogic citiesLogic = new CitiesLogic(connection, new Locale("ru-RU"));
			return citiesLogic.addCity(placeId);
		}
	}
}
