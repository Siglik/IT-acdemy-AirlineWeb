/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented.HubDAO;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Hub;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class EmployeesLogic extends EntityToViewLogic<Employee> {
	private Connection connection;
	private Locale locale;
	private HubsLogic hubsLogic;
	
	public EmployeesLogic(Connection connection, Locale locale) {
		this.connection = connection;
		this.locale = locale;
		hubsLogic = new HubsLogic(this.connection, this.locale);
	}
	
	@Override
	public Map<String, String> toMap(Employee employee) throws SQLException, EntityNotFoundException {
		Map<String, String> hubMap = new HashMap<>();


		hubMap.put("id", Integer.toString(employee.getId()));
		hubMap.put("firstName",employee.getFirstName());
		hubMap.put("lastName", employee.getLastName());
		hubMap.put("speciality", employee.getSpecialty().toString().toLowerCase().replace("_", " "));
		hubMap.put("hub", getHubCityName(employee));

		return hubMap;
	}
	/**
	 * 
	 * @param employee
	 * @return
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	private String getHubCityName(Employee employee) throws SQLException, EntityNotFoundException {
		HubDAO hubDAO = new HubDAO(connection);
		Hub hub = hubDAO.findEntityById(employee.getHubId());
		return hubsLogic.getCityName(hub);
	}

}
