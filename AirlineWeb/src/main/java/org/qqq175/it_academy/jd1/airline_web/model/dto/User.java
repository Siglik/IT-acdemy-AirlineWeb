package org.qqq175.it_academy.jd1.airline_web.model.dto;

import java.io.Serializable;


/**
 * The persistent class for the user database table.
 * 
 */
//Entity
//Table(name="user")
//NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum UserType{
		ADMIN, DISPATCHER, ROOT
	}
	
	//Column(name="first_name")
	private String firstName;

	//Column(name="last_name")
	private String lastName;

	private String login;

	//Column(name="password_sha")
	private String passwordSha;

	private UserType type;

	public User() {
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

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordSha() {
		return this.passwordSha;
	}

	public void setPasswordSha(String passwordSha) {
		this.passwordSha = passwordSha;
	}

	public UserType getType() {
		return this.type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
}