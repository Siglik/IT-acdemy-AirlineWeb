package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;


/**
 * The persistent class for the airplane_model database table.
 * 
 */
//Entity
//Table(name="airplane_model")
//NamedQuery(name="AirplaneModel.findAll", query="SELECT a FROM AirplaneModel a")
public class AirplaneModel extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//Column(name="air_stewards")
	private int airStewards;

	//Column(name="avg_speed")
	private int avgSpeed;

	//Column(name="flight_range")
	private int flightRange;

	private String name;

	private int navigators;

	private int pilots;

	//Column(name="radio_operators")
	private int radioOperators;

	public AirplaneModel() {
	}

	public int getAirStewards() {
		return this.airStewards;
	}

	public void setAirStewards(int airStewards) {
		this.airStewards = airStewards;
	}

	public int getAvgSpeed() {
		return this.avgSpeed;
	}

	public void setAvgSpeed(int avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public int getFlightRange() {
		return this.flightRange;
	}

	public void setFlightRange(int flightRange) {
		this.flightRange = flightRange;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNavigators() {
		return this.navigators;
	}

	public void setNavigators(int navigators) {
		this.navigators = navigators;
	}

	public int getPilots() {
		return this.pilots;
	}

	public void setPilots(int pilots) {
		this.pilots = pilots;
	}

	public int getRadioOperators() {
		return this.radioOperators;
	}

	public void setRadioOperators(int radioOperators) {
		this.radioOperators = radioOperators;
	}

}