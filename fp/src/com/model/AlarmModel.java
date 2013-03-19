package com.model;

import java.beans.PropertyChangeSupport;
import java.sql.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.model.support.SqlDateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlarmModel {
	
	public final static String DATE_PROPERTY = "Date";
	public final static String TEXT_PROPERTY = "Text";
	public final static String APPOINTMENT_PROPERTY = "Appointment";
	public final static String CREATOR_PROPERTY = "Creator";
	
	@XmlTransient
	private PropertyChangeSupport changeSupport;
	
	@XmlJavaTypeAdapter(SqlDateAdapter.class)//@XmlElement(name = "date")
	private Date date;
	@XmlElement(name="text")
	private String text;
	@XmlElement(name="appointment")
	private AppointmentModel appointment;
	@XmlElement(name="creator")
	private UserModel creator;
	@XmlElement(name="time")
	private int time;
	
	public AlarmModel(){} /* for jabx */
	
	public AlarmModel(Date date, String text,
			 int time, AppointmentModel appointment, UserModel creator) {
		this.date = date;
		this.text = text;
		this.appointment = appointment;
		this.creator = creator;
		this.time = time;
		changeSupport = new PropertyChangeSupport(this);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		Date oldValue = this.date;
		this.date = date;
		changeSupport.firePropertyChange(DATE_PROPERTY, oldValue, date);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldValue = this.text;
		this.text = text;
		changeSupport.firePropertyChange(TEXT_PROPERTY, oldValue, text);
	}

	public AppointmentModel getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentModel appointment) {
		AppointmentModel oldValue = this.appointment;
		this.appointment = appointment;
		changeSupport.firePropertyChange(APPOINTMENT_PROPERTY, oldValue, appointment);
	}

	public UserModel getCreator() {
		return creator;
	}

	public void setCreator(UserModel creator) {
		UserModel oldValue = this.creator;
		this.creator = creator;
		changeSupport.firePropertyChange(CREATOR_PROPERTY, oldValue, creator);
	}
	
	public int getTime(){
		return this.time;
	}
	
	public void setTime(int time){
		this.time = time;
	}

	@Override
	public String toString() {
		return "AlarmModel [date=" + date + ", text=" + text + ", appointment="
				+ appointment + ", creator=" + creator + ", getDate()="
				+ getDate() + ", getText()=" + getText()
				+ ", getAppointment()=" + getAppointment() + ", getCreator()="
				+ getCreator() + "]";
	}

}
