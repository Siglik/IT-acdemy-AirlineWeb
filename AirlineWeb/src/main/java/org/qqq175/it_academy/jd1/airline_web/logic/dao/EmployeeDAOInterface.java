package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.model.dto.Employee;
import org.qqq175.it_academy.jd1.airline_web.model.dto.Flight;

public interface EmployeeDAOInterface extends BasicDAOInterface<Employee> {
	List<Flight> findEmployeeFlights(Employee employee) throws SQLException;
	
	boolean appointToFlight(Flight flight, Employee employee) throws SQLException;
	boolean removeFromFlight(Flight flight, Employee employee) throws SQLException;
}
