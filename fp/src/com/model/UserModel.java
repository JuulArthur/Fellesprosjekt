package com.model;

import java.sql.SQLException;

import javax.xml.bind.annotation.*;

import com.server.db.Factory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserModel {
	@XmlElement(name="username")
	private String username;
	@XmlElement(name="password")
	private String password;
	@XmlElement(name="email")
	private String email;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="surname")
	private String surname;
	@XmlElement(name="phonenumber")
	private String phoneNumber;
	@XmlElement(name="isAdmin")
	private boolean isAdmin;
	
	private Factory f;
	
	/* JAXB */
	public UserModel(){}
	
	public UserModel(String username, String password, String email,
			String name, String surname, String phoneNumber, int isAdmin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.isAdmin = isAdmin==1;
		
		this.f = new Factory();
	}
	public UserModel() {
		// her perok!
	}
	
	/* Methods */
	
	public void createUser() {
		//f.createEmployee(name, birthyear);
	}
	
	public void removeUser(UserModel u) {
		//f.removeUser(u);
	}
	
	/*
	 * Getters and setters
	 * NOT for Factory f
	 */
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", password=" + password
				+ ", email=" + email + ", name=" + name + ", surname="
				+ surname + ", phoneNumber=" + phoneNumber + ", isAdmin="
				+ isAdmin + ", f=" + f + ", getUsername()=" + getUsername()
				+ ", getPassword()=" + getPassword() + ", getEmail()="
				+ getEmail() + ", getName()=" + getName() + ", getSurname()="
				+ getSurname() + ", getPhoneNumber()=" + getPhoneNumber()
				+ ", isAdmin()=" + isAdmin() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
