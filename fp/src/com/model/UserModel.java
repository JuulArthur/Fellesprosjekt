package com.model;

import java.util.ArrayList;
import java.beans.PropertyChangeSupport;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserModel {
	
	public final static String USERNAME_PROPERTY = "Username";
	public final static String PASSWORD_PROPERTY = "Password";
	public final static String EMAIL_PROPERTY = "Email";
	public final static String NAME_PROPERTY = "Name";
	public final static String SURNAME_PROPERTY = "Surname";
	public final static String PHONENUMBER_PROPERTY = "PhoneNumber";
	public final static String ISADMIN_PROPERTY = "Isadmin";
	public final static String NOTIFICATIONS_PROPERTY = "Notifications";
	public final static String MYCALENDARS_PROPERTY = "MyCalendars";
	public final static String SUBSCRIBEDCALENDARS_PROPERTY = "SubscribedCalendars";
	
	@XmlTransient
	PropertyChangeSupport changeSupport;
	
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
	@XmlElement(name="subscribedCalendars")
	private ArrayList<CalendarModel> subscribedCalendars;
	
	@XmlElement(name="summonedTo")
	private CalendarModel summonedTo;
	
	/* JAXB */
	public UserModel(){
		changeSupport = new PropertyChangeSupport(this);
	}
	
	public UserModel(String username){
		changeSupport = new PropertyChangeSupport(this);
		this.username = username;
	}
	
	public UserModel(String username, String password, String email,
			String name, String surname, String phoneNumber, int isAdmin) {
		
		changeSupport = new PropertyChangeSupport(this);
		
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.isAdmin = isAdmin==1;
		this.notifications = new ArrayList<NotificationModel>();
		this.myCalendars = new ArrayList<CalendarModel>();
		this.subscribedCalendars = new ArrayList<CalendarModel>();
		this.summonedTo = new CalendarModel();
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
		String oldValue = this.username;
		this.username = username;
		changeSupport.firePropertyChange(USERNAME_PROPERTY, oldValue, username);
		
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String oldValue = this.password;
		this.password = password;
		changeSupport.firePropertyChange(PASSWORD_PROPERTY, oldValue, password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		String oldValue = this.email;
		this.email = email;
		changeSupport.firePropertyChange(EMAIL_PROPERTY, oldValue, email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		changeSupport.firePropertyChange(NAME_PROPERTY, oldValue, name);
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		String oldValue = this.surname;
		this.surname = surname;
		changeSupport.firePropertyChange(SURNAME_PROPERTY, oldValue, surname);
		
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		String oldValue = this.phoneNumber;
		this.phoneNumber = phoneNumber;
		changeSupport.firePropertyChange(PHONENUMBER_PROPERTY, oldValue, phoneNumber);
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		boolean oldValue = this.isAdmin;
		this.isAdmin = isAdmin;
		changeSupport.firePropertyChange(ISADMIN_PROPERTY, oldValue, isAdmin);

	}

	public ArrayList<NotificationModel> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<NotificationModel> notifications) {
		ArrayList<NotificationModel> oldValue = this.notifications;
		this.notifications = notifications;
		changeSupport.firePropertyChange(NOTIFICATIONS_PROPERTY, oldValue, notifications);
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
		ArrayList<CalendarModel> oldValue = this.myCalendars;
		this.myCalendars = calendars;
		changeSupport.firePropertyChange(MYCALENDARS_PROPERTY, oldValue, calendars);
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
		ArrayList<CalendarModel> oldValue = this.subscribedCalendars;
		this.subscribedCalendars = subscribedCalendars;
		changeSupport.firePropertyChange(SUBSCRIBEDCALENDARS_PROPERTY, oldValue, subscribedCalendars);
	}
	
	public void addSubscribedCalendars(CalendarModel calendar) {
		if (!subscribedCalendars.contains(calendar))
			this.subscribedCalendars.add(calendar);
	}
	
	public void removeSubscribedCalendars(CalendarModel calendar) {
		this.subscribedCalendars.remove(calendar);
	}
	
		

	public CalendarModel getSummonedTo() {
		return summonedTo;
	}

	public void setSummonedTo(CalendarModel summonedTo) {
		CalendarModel oldValue = this.summonedTo;
		this.summonedTo = summonedTo;
		changeSupport.firePropertyChange(MYCALENDARS_PROPERTY, oldValue, summonedTo);
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", password=" + password
				+ ", email=" + email + ", name=" + name + ", surname="
				+ surname + ", phoneNumber=" + phoneNumber + ", isAdmin="
				+ isAdmin + ", notifications=" + notifications + "]";
	}
	public ArrayList<CalendarModel> getMyCalendar(){
		return this.myCalendars;
	}

}
