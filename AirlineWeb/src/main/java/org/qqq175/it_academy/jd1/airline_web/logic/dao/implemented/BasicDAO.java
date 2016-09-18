/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.dao.implemented;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.qqq175.it_academy.jd1.airline_web.logic.dao.BasicDAOInterface;
import org.qqq175.it_academy.jd1.airline_web.model.dto.AbstractEntity;
import org.qqq175.it_academy.jd1.airline_web.util.SQLQueryManager;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;
import org.qqq175.it_academy.jd1.airline_web.util.exception.UnsupportedOperation;

/**
 * @author qqq175
 *
 */
public abstract class BasicDAO<T extends AbstractEntity> implements BasicDAOInterface<T> {
	private String tableName;
	private int colCount;
	protected Connection connection;
	protected SQLQueryManager sqlQuery;

	/**
	 * @param tableName
	 */
	public BasicDAO(String tableName, int colCount, Connection connection) {
		this.tableName = tableName;
		this.colCount = colCount;
		this.connection = connection;
		this.sqlQuery = SQLQueryManager.getInstance();
	}

	@Override
	public boolean create(T entity) throws SQLException, UnsupportedOperation {
		String query = sqlQuery.getQuery("sql." + tableName + ".insert");
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepareWithEntity(prepStatment, entity);
			int affectedRows = prepStatment.executeUpdate();
			if (affectedRows > 0)
				return true;
		}
		return false;
	}

	@Override
	public List<T> findAll() throws SQLException {
		String query = sqlQuery.getQuery("sql.entity.findAll");
		query = insertTableName(query);
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				return toDTOList(resultSet);
			}
		}
	}

	@Override
	public T findEntityById(int id) throws SQLException, EntityNotFoundException {
		String query = sqlQuery.getQuery("sql.entity.findBy.id");
		query = insertTableName(query);
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepareWithId(prepStatment, id);
			System.out.println(prepStatment);
			System.out.println(query);
			try (ResultSet resultSet = prepStatment.executeQuery()) {
				return toDTO(resultSet);
			}
		}
	}

	@Override
	public boolean update(T entity) throws SQLException, UnsupportedOperation {
		String query = sqlQuery.getQuery("sql." + tableName + ".update");
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepareWithEntity(prepStatment, entity);
			prepStatment.setInt(colCount, entity.getId());
			int affectedRows = prepStatment.executeUpdate();
			if (affectedRows > 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		String query = sqlQuery.getQuery("sql.entity.deleteBy.id");
		query = insertTableName(query);
		try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
			prepareWithId(prepStatment, id);

			return prepStatment.executeUpdate() == 1;
		}
	}

	@Override
	public boolean delete(T entity) throws SQLException {
		return delete(entity.getId());
	}

	private void prepareWithId(PreparedStatement prepStatment, int id) throws SQLException {
		prepStatment.setInt(1, id);
	}

	private String insertTableName(String query) {
		return query.replaceFirst("\\{tableName\\}", tableName);
	}

	protected abstract void prepareWithEntity(PreparedStatement prepStatment, T entity)
	        throws SQLException, UnsupportedOperation;

	protected T toDTO(ResultSet resultSet) throws EntityNotFoundException, SQLException {
		if (resultSet.next()) {
			return fillEntity(resultSet);
		} else {
			throw new EntityNotFoundException();
		}
	}

	protected List<T> toDTOList(ResultSet resultSet) throws SQLException {
		List<T> resultList = new ArrayList<>();
		while (resultSet.next()) {
			resultList.add(fillEntity(resultSet));
		}
		return resultList;
	}

	protected abstract T fillEntity(ResultSet resultSet) throws SQLException;

	/**
	 * @return the tableName
	 */
	protected String getTableName() {
		return tableName;
	}

	/**
	 * @return the connection
	 */
	protected Connection getConnection() {
		return connection;
	}

	/**
	 * @return the sqlQuery
	 */
	protected SQLQueryManager getSqlQuery() {
		return sqlQuery;
	}
}
