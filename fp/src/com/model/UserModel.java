package com.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

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
	@XmlElement(name="notifications")
	private ArrayList<NotificationModel> notifications;
	@XmlElement(name="myCalendars")
	private ArrayList<CalendarModel> myCalendars;
	
	// Skal vi ha med dette?
	@XmlElement(name="calendars")
	private ArrayList<CalendarModel> subscribedCalendars;
	
	/* JAXB */
	public UserModel(){
		
	}
	
	public UserModel(String username, String password, String email,
			String name, String surname, String phoneNumber, int isAdmin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.isAdmin = isAdmin==1;
		this.notifications = new ArrayList<NotificationModel>();
		this.myCalendars = new ArrayList<CalendarModel>();
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

	public ArrayList<NotificationModel> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<NotificationModel> notifications) {
		this.notifications = notifications;
	}
	
	public void addNotification(NotificationModel notification) {
		this.notifications.add(notification);
	}
	
	public void removeNotification(NotificationModel notification) {
		this.notifications.remove(notification);
	}

	public ArrayList<CalendarModel> getCalendars() {
		return myCalendars;
	}

	public void setCalendars(ArrayList<CalendarModel> calendars) {
		this.myCalendars = calendars;
	}
	
	public void addCalendar(CalendarModel calendar) {
		if (!myCalendars.contains(calendar))
			this.myCalendars.add(calendar);
	}
	
	public void removeCalendar(CalendarModel calendar) {
		this.myCalendars.remove(calendar);
	}

	public ArrayList<CalendarModel> getSubscribedCalendars() {
		return subscribedCalendars;
	}

	public void setSubscribedCalendars(ArrayList<CalendarModel> subscribedCalendars) {
		this.subscribedCalendars = subscribedCalendars;
	}
	
	public void addSubscribedCalendars(CalendarModel calendar) {
		if (!subscribedCalendars.contains(calendar))
			this.subscribedCalendars.add(calendar);
	}
	
	public void removeSubscribedCalendars(CalendarModel calendar) {
		this.subscribedCalendars.remove(calendar);
	}
		

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", password=" + password
				+ ", email=" + email + ", name=" + name + ", surname="
				+ surname + ", phoneNumber=" + phoneNumber + ", isAdmin="
				+ isAdmin + ", notifications=" + notifications + "]";
	}

}
