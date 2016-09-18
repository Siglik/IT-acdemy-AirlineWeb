package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;

import org.qqq175.it_academy.jd1.airline_web.model.dto.City;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

public interface CityDAOInterface extends BasicDAOInterface<City> {
	int getCityIdByPlaceId(String placeId) throws SQLException, EntityNotFoundException;
}
