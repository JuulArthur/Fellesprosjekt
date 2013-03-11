package com.model;

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
	private int phoneNumber;
	@XmlElement(name="isAdmin")
	private boolean isAdmin;
	
	private Factory f;
	
	public UserModel(String username, String password, String email,
			String name, String surname, int phoneNumber, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.isAdmin = isAdmin;
		
		this.f = new Factory();
	}
	
	/* Methods */
	public void createProject() {
		//f.createProject();
	}
	
	public void removeProject(ProjectModel p) {
		//f.removeProject(p);
	}
	
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

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
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
