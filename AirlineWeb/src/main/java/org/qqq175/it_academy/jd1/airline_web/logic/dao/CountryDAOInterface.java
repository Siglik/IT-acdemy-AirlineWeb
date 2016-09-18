package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.Country;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

public interface CountryDAOInterface extends BasicDAOInterface<Country> {
	
	/**
	 * Find country by short name and return ID or -1 if not found
	 * @param shortName
	 * @return country ID or -1 if not found
	 * @throws SQLException
	 * @throws EntityNotFoundException
	 */
	int getCountryIdByShortName(String shortName) throws SQLException, EntityNotFoundException;
}
