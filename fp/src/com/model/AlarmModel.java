package com.model;

import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlarmModel {
	
	@XmlElement(name="date")
	private Date date;
	@XmlElement(name="text")
	private String text;
	@XmlElement(name="appointment")
	private AppointmentModel appointment;
	@XmlElement(name="creator")
	private UserModel creator;
	
	public AlarmModel(){} /* for jabx */
	
	public AlarmModel(Date date, String text,
			AppointmentModel appointment, UserModel creator) {
		this.date = date;
		this.text = text;
		this.appointment = appointment;
		this.creator = creator;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
		return "AlarmModel [date=" + date + ", text=" + text + ", appointment="
				+ appointment + ", creator=" + creator + ", getDate()="
				+ getDate() + ", getText()=" + getText()
				+ ", getAppointment()=" + getAppointment() + ", getCreator()="
				+ getCreator() + "]";
	}
	
	
	

}
