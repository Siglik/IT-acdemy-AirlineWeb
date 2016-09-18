package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the flight database table.
 * 
 */
//Entity
//Table(name="flight")
//NamedQuery(name="Flight.findAll", query="SELECT f FROM Flight f")
public class Flight extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//Temporal(TemporalType.TIMESTAMP)
	//Column(name="dept_time")
	private Date deptTime;

	//Temporal(TemporalType.TIMESTAMP)
	//Column(name="dept_time_back")
	private Date deptTimeBack;

	//uni-directional many-to-one association to Route
	private int routeId;

	//uni-directional many-to-one association to AirplaneModel
	//JoinColumn(name="airplane_model_id")
	private int airplaneModelId;

	public Flight() {
	}

	public Date getDeptTime() {
		return this.deptTime;
	}

	public void setDeptTime(Date deptTime) {
		this.deptTime = deptTime;
	}

	public Date getDeptTimeBack() {
		return this.deptTimeBack;
	}

	public void setDeptTimeBack(Date deptTimeBack) {
		this.deptTimeBack = deptTimeBack;
	}

	/**
	 * @return the routeId
	 */
	public int getRouteId() {
		return routeId;
	}

	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	/**
	 * @return the airplaneModelId
	 */
	public int getAirplaneModelId() {
		return airplaneModelId;
	}

	/**
	 * @param airplaneModelId the airplaneModelId to set
	 */
	public void setAirplaneModelId(int airplaneModelId) {
		this.airplaneModelId = airplaneModelId;
	}

}