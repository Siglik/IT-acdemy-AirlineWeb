/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.FlightDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.AirplaneModel;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public class FlightDAO extends BasicDAO<Flight> implements FlightDAOInterface {
	private static String TABLE_NAME = "flight";
	private static int COLUMN_COUNT = 5;

	public FlightDAO(Connection connection) {
		super(TABLE_NAME, COLUMN_COUNT, connection);
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, Flight entity) throws SQLException {
		prepStatment.setInt(1, entity.getRouteId());
		java.text.SimpleDateFormat sdFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		prepStatment.setInt(2, entity.getAirplaneModelId());
		prepStatment.setString(3, sdFormat.format(entity.getDeptTime().getTime()));
		prepStatment.setString(4, sdFormat.format(entity.getDeptTimeBack().getTime()));
	}

	@Override
	protected Flight fillEntity(ResultSet resultSet) throws SQLException {
		Flight flight = new Flight();
		flight.setId(resultSet.getInt(1));
		flight.setRouteId(resultSet.getInt(2));
		flight.setAirplaneModelId(resultSet.getInt(3));
		flight.setDeptTime(resultSet.getTimestamp(4));
		flight.setDeptTimeBack(resultSet.getTimestamp(5));
		return flight;
	}

	@Override
	public List<Flight> findUnderstaffedCrew() throws SQLException, EntityNotFoundException {
		List<Flight> flights = this.findAll();
		Iterator<Flight> iter = flights.iterator();
		while (iter.hasNext()) {
			Flight flight = iter.next();
			if (!isStaffed(flight)) {
				iter.remove();
			}
		}

		return flights;
	}

	public boolean isStaffed(Flight flight) throws SQLException, EntityNotFoundException {
		String query = sqlQuery.getQuery("sql.employee.findFlights");
		AirplaneModelDAO airplaneModelDAO = new AirplaneModelDAO(connection);
		AirplaneModel airplaneModel = airplaneModelDAO.findEntityById(flight.getAirplaneModelId());

		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				int pilots = 0;
				int navigators = 0;
				int radioOperators = 0;
				int airStewards = 0;
				while (resultSet.next()) {
					Employee.Speciality speciality = Employee.Speciality.valueOf(resultSet.getString(2).toUpperCase());
					int count = resultSet.getInt(2);
					switch (speciality) {
					case PILOT:
						pilots = count;
						break;
					case NAVIGATOR:
						navigators = count;
						break;
					case RADIO_OPERATOR:
						radioOperators = count;
						break;
					case AIR_STEWARD:
						airStewards = count;
						break;
					}
				}

				if (pilots >= airplaneModel.getPilots() && navigators >= airplaneModel.getNavigators()
				        && radioOperators >= airplaneModel.getRadioOperators()
				        && airStewards >= airplaneModel.getAirStewards()) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public List<Employee> getFlightCrew(Flight flight) throws SQLException {
		String query = sqlQuery.getQuery("sql.flight.findCrew");
		EmployeeDAO employeeDAO = new EmployeeDAO(connection);
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepStatment.setInt(1, flight.getId());
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				return employeeDAO.toDTOList(resultSet);
			}
		}
	}
	
	public int dismissFlightCrew(Flight flight) throws SQLException{
		String query = sqlQuery.getQuery("sql.flight.dismisscrew");

		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepStatment.setInt(1, flight.getId());

			return prepStatment.executeUpdate();
		}
	}
}
