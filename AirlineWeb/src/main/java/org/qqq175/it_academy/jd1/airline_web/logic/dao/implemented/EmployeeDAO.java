/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.EmployeeDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;

/**
 * @author qqq175
 *
 */
public class EmployeeDAO extends BasicDAO<Employee> implements EmployeeDAOInterface {
	private static String TABLE_NAME = "employee";
	private static int COLUMN_COUNT = 3;

	public EmployeeDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.qqq175.it_academy.jd1.airline_web.logic.dao.EmployeeDAOInterface#
	 * findEmployeeFlights(org.qqq175.it_academy.jd1.airline_web.logic.dto.
	 * Employee)
	 */
	@Override
	public List<Flight> findEmployeeFlights(Employee employee) throws SQLException {
		String query = sqlQuery.getQuery("sql.flight.getCrewSpecCount");
		FlightDAO flightDAO = new FlightDAO(connection);
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepStatment.setInt(1, employee.getId());
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				return flightDAO.toDTOList(resultSet);
			}
		}
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, Employee entity) throws SQLException {
		prepStatment.setString(1, entity.getFirstName());
		prepStatment.setString(2, entity.getLastName());
		prepStatment.setString(3, entity.getSpecialty().name().toLowerCase());
		prepStatment.setInt(4, entity.getHubId());
	}

	@Override
	protected Employee fillEntity(ResultSet resultSet) throws SQLException {
		Employee employee = new Employee();
		employee.setId(resultSet.getInt(1));
		employee.setFirstName(resultSet.getString(2));
		employee.setLastName(resultSet.getString(3));
		employee.setSpecialty(Employee.Speciality.valueOf(resultSet.getString(4).toUpperCase()));
		employee.setHubId(resultSet.getInt(5));
		return employee;
	}

	@Override
	public boolean appointToFlight(Flight flight, Employee employee) throws SQLException {
		String query = sqlQuery.getQuery("sql.employee.appointToFlight");
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepStatment.setInt(1, flight.getId());
			prepStatment.setInt(2, employee.getId());
			int affectedRows = prepStatment.executeUpdate();
			if (affectedRows > 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean removeFromFlight(Flight flight, Employee employee) throws SQLException {
		String query = sqlQuery.getQuery("sql.employee.removeFromFlight");
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepStatment.setInt(1, flight.getId());
			prepStatment.setInt(2, employee.getId());

			return prepStatment.executeUpdate() > 0;
		}
	}

}
