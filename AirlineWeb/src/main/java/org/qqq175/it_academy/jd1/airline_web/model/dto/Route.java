package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;

/**
 * The persistent class for the route database table.
 * 
 */
// Entity
// Table(name="route")
// NamedQuery(name="Route.findAll", query="SELECT r FROM Route r")
public class Route extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// uni-directional many-to-one association to Hub
	private int hubId;

	// uni-directional many-to-one association to City)
	private int cityId;

	public Route() {
	}

	/**
	 * @return the hubId
	 */
	public int getHubId() {
		return hubId;
	}

	/**
	 * @param hubId
	 *            the hubId to set
	 */
	public void setHubId(int hubId) {
		this.hubId = hubId;
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