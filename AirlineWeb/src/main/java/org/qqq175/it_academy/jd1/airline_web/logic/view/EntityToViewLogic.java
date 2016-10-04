/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.logic.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.qqq175.it_academy.jd1.airline_web.model.dto.AbstractEntity;
import org.qqq175.it_academy.jd1.airline_web.util.exception.EntityNotFoundException;

/**
 * @author qqq175
 *
 */
public abstract class EntityToViewLogic<T extends AbstractEntity> {
	
	public List<Map<String, String>> listToMaps(List<T> entityList)
	        throws SQLException, EntityNotFoundException {
		List<Map<String, String>> entityMaps = new ArrayList<>(entityList.size());

		for (T entity : entityList) {
			entityMaps.add(toMap(entity));
		}
		return entityMaps;
	}
	
	public abstract Map<String,String> toMap(T entity) throws SQLException, EntityNotFoundException;
}
