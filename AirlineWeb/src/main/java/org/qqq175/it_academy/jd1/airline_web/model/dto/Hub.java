package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;


/**
 * The persistent class for the hub database table.
 * 
 */
//Entity
//Table(name="hub")
//NamedQuery(name="Hub.findAll", query="SELECT h FROM Hub h")
public class Hub extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//uni-directional many-to-one association to City
	private int cityId;

	public Hub() {
	}

	/**
	 * @return the cityId
	 */
	public int getCityId() {
		return cityId;
	}

	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
}