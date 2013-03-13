package com.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarModel extends AbstractTableModel {

	@XmlElement(name="id")
	private int id;
	@XmlElement(name = "appointments")
	private ArrayList<AppointmentModel> appointments;
	@XmlElement(name = "owner")
	private UserModel owner;
	@XmlElement(name = "followers")
	private ArrayList<UserModel> followers;
	@XmlElement(name="name")
	private String name;

	private String[][] calendar = new String[13][7];

	/* for jabx */
	public CalendarModel() {
	}

	public CalendarModel(int id, String name, ArrayList<AppointmentModel> appointments,
			UserModel owner, ArrayList<UserModel> followers) {
		this.id = id;
		this.appointments = appointments;
		this.owner = owner;
		this.followers = followers;
		this.name = name;
	}

	public int getColumnCount() {
		return calendar[0].length;
	}

	public int getRowCount() {
		return calendar.length;
	}

	public Object getValueAt(int row, int column) {
		return calendar[row][column];
	}

	public void setValueAt(Object value, int row, int column) {
		calendar[row][column] = (String) value;
	}

	public ArrayList<AppointmentModel> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<AppointmentModel> appointments) {
		this.appointments = appointments;
	}

	public void addAppointment(AppointmentModel appointment) {
		if (!this.appointments.contains(appointment))
			this.appointments.add(appointment);
	}

	public void removeAppointment(AppointmentModel appointment) {
		if (this.appointments.contains(appointment))
			this.appointments.remove(appointment);
	}

	public UserModel getOwner() {
		return owner;
	}

	public void setOwner(UserModel owner) {
		this.owner = owner;
	}

	public ArrayList<UserModel> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<UserModel> followers) {
		this.followers = followers;
	}

	public void addFollower(UserModel follower) {
		if (!this.followers.contains(follower))
			this.followers.add(follower);
	}

	public void removeFollower(UserModel follower) {
		if (this.followers.contains(follower))
			this.followers.remove(follower);
	}

	/* do we really want this? */
	public String[][] getCalendar() {
		return calendar;
	}

	public void setCalendar(String[][] calendar) {
		this.calendar = calendar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CalendarModel [id=" + id + ", appointments=" + appointments
				+ ", owner=" + owner + ", followers=" + followers + ", name="
				+ name + ", calendar=" + Arrays.toString(calendar) + "]";
	}

}
