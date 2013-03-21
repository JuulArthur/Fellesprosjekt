package com.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarModel extends AbstractTableModel {
	
	public final static String ID_PROPERTY = "Id";
	public final static String APPOINTMENTS_PROPERTY = "Appointments";
	public final static String APPOINTMENTSSUMMONEDTO_PROPERTY = "appointmentsSummonedTo";
	public final static String OWNER_PROPERTY = "Owner";
	public final static String NAME_PROPERTY = "Name";
	public final static String CALENDAR_PROPERTY = "Calendar";
	
	@XmlTransient
	private PropertyChangeSupport changeSupport;

	@XmlElement(name = "id")
	private long id;
	@XmlElement(name = "appointments")
	private ArrayList<AppointmentModel> appointments;
	@XmlElement(name = "owner")
	private String owner;
	@XmlElement(name = "name")
	private String name;

	private String[][] calendar = new String[13][7];

	/* for jabx */
	public CalendarModel() {
		changeSupport = new PropertyChangeSupport(this);
	}

	public CalendarModel(long id, String name,
			ArrayList<AppointmentModel> appointments, String owner) {

		changeSupport = new PropertyChangeSupport(this);
		this.id = id;
		this.appointments = appointments;
		this.owner = owner;
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
		ArrayList<AppointmentModel> oldValue = this.appointments;
		this.appointments = appointments;
		changeSupport.firePropertyChange(APPOINTMENTS_PROPERTY, oldValue, appointments);
	}

	public void addAppointment(AppointmentModel appointment) {
		if (!this.appointments.contains(appointment))
			this.appointments.add(appointment);
	}

	public void removeAppointment(AppointmentModel appointment) {
		if (this.appointments.contains(appointment))
			this.appointments.remove(appointment);
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		String oldValue = this.owner;
		this.owner = owner;
		changeSupport.firePropertyChange(OWNER_PROPERTY, oldValue, owner);
	}

	public String[][] getCalendar() {
		return calendar;
	}

	public void setCalendar(String[][] calendar) {
		String[][] oldValue = this.calendar;
		this.calendar = calendar;
		changeSupport.firePropertyChange(CALENDAR_PROPERTY, oldValue, calendar);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		long oldValue = this.id;
		this.id = id;
		changeSupport.firePropertyChange(ID_PROPERTY, oldValue, id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		changeSupport.firePropertyChange(NAME_PROPERTY, oldValue, name);
	}

	@Override
	public String toString() {
		String s =  "CalendarModel [id=" + id + ", appointments=" + appointments
				+ ", owner=" + owner + ", name=" + name + ", calendar="
				+ Arrays.toString(calendar) + "]";
		return getName();
	}

}
