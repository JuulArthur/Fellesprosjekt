package com.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class NotificationModel {
	
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
		this.text = text;
	}

	public AppointmentModel getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentModel appointment) {
		this.appointment = appointment;
	}

	public UserModel getCreator() {
		return creator;
	}

	public void setCreator(UserModel creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "NotificationModel [text=" + text + ", appointment="
				+ appointment + ", creator=" + creator + ", getText()="
				+ getText() + ", getAppointment()=" + getAppointment()
				+ ", getCreator()=" + getCreator() + "]";
	}
	
	

}
