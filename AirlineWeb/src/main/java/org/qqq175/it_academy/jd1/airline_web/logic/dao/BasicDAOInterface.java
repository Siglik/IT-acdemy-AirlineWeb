package org.qqq175.it_academy.jd1.airline_web.logic.dao;

import java.sql.SQLException;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.model.dto.AbstractEntity;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

public interface BasicDAOInterface<T extends AbstractEntity> {
	boolean create(T entity) throws SQLException, UnsupportedOperation;

	List<T> findAll() throws SQLException;

	T findEntityById(int id) throws SQLException, EntityNotFoundException;

	boolean update(T entity) throws SQLException, UnsupportedOperation;

	boolean delete(int id) throws SQLException;

	boolean delete(T entity) throws SQLException;
}
