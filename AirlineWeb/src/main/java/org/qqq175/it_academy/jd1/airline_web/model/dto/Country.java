package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;


/**
 * The persistent class for the country database table.
 * 
 */
//Entity
//Table(name="country")
//NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//Column(name="long_name")
	private String longName;

	//Column(name="short_name")
	private String shortName;

	public Country() {
	}

	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}