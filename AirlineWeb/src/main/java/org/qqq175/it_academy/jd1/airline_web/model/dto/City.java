package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;


/**
 * The persistent class for the city database table.
 * 
 */
//Entity
//Table(name="city")
//NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private double latitude;

	private double longitude;

	private String name;

	//Column(name="place_id")
	private String placeId;

	//uni-directional many-to-one association to Country
	private int countryId;

	public City() {
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceId() {
		return this.placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

}