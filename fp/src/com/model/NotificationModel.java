package com.model;

import java.beans.PropertyChangeSupport;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificationModel {
	
	public final static String TEXT_PROPERTY = "Text";
	public final static String APPOINTMENT_PROPERTY = "Appointment";
	public final static String CREATOR_PROPERTY = "Creator";
	
	@XmlTransient
	private PropertyChangeSupport changeSupport;
	
	@XmlElement(name="text")
	private String text;
	@XmlElement(name="appointment")
	private AppointmentModel appointment;
	@XmlElement(name="creator")
	private UserModel creator;
	
	public NotificationModel() {} /* for jabx */
	
	public NotificationModel(String text,
			AppointmentModel appointment, UserModel creator) {
		this.text = text;
		this.appointment = appointment;
		this.creator = creator;
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

	@Override
	public String toString() {
		return "NotificationModel [text=" + text + ", appointment="
				+ appointment + ", creator=" + creator + ", getText()="
				+ getText() + ", getAppointment()=" + getAppointment()
				+ ", getCreator()=" + getCreator() + "]";
	}
	
	

}
