package com.model;

import javax.xml.bind.annotation.*;

/**
 * Test modell
 * @author perok
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserModel {
	@XmlElement(name="username")
	private String username;
	@XmlElement(name="password")
	private String password;
	
	/*
	 * Getters and setters
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
}
