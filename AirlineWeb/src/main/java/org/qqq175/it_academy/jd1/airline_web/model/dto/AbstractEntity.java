/**
 * 
 */
package org.qqq175.it_academy.jd1.airline_web.model.dto;

/**
 * @author qqq175
 *
 */
public abstract class AbstractEntity {
	//Id
	private int id;
	
	public int getId() {
		return this.id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
