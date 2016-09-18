package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

public interface FlightDAOInterface extends BasicDAOInterface<Flight> {
	List<Flight> findUnderstaffedCrew() throws SQLException, EntityNotFoundException;
	List<Employee> getFlightCrew(Flight flight) throws SQLException;
	boolean isStaffed(Flight flight) throws SQLException, EntityNotFoundException;
}
