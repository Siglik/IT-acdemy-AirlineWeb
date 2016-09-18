package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
//Entity
//Table(name="employee")
//NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee  extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Speciality{
		PILOT, NAVIGATOR, RADIO_OPERATOR, AIR_STEWARD
	}

	//Column(name="first_name")
	private String firstName;

	//Column(name="last_name")
	private String lastName;

	private Speciality specialty;

//	//uni-directional many-to-many association to Flight
//	//ManyToMany
//	//JoinTable(
//	//	name="crew"
//	//	, joinColumns={
//			//JoinColumn(name="employee_id")
//			}
//	//	, inverseJoinColumns={
//			//JoinColumn(name="flight_id")
//			}
//	//	)
	private List<Flight> flights;

	//bi-directional many-to-one association to Hub
	//ManyToOne(fetch=FetchType.LAZY)
	private int hubId;

	public Employee() {
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Speciality getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(Speciality specialty) {
		this.specialty = specialty;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	/**
	 * @return the hubId
	 */
	public int getHubId() {
		return hubId;
	}

	/**
	 * @param hubId the hubId to set
	 */
	public void setHubId(int hubId) {
		this.hubId = hubId;
	}

}