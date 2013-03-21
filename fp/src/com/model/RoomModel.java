package com.model;

import java.beans.PropertyChangeSupport;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomModel {
	
	public final static String ROOMNUMBER_PROPERTY = "RoomNumber";
	public final static String ROOMNAME_PROPERTY = "RoomName";
	public final static String CAPACITY_PROPERTY = "Capacity";
	public final static String LOCATION_PROPERTY = "Location";
	
	@XmlTransient
	private PropertyChangeSupport changeSupport;

	//fjerne romnummer og gjoere romnavn til primary key i databasen
	@XmlElement(name="roomnumber")
	private long roomNumber;
	@XmlElement(name="roomname")
	private String roomName;
	@XmlElement(name="capacity")
	private int capacity;
	@XmlElement(name="location")
	private String location;

	public RoomModel() {} /* for jaxb */
	
	public RoomModel(long roomNumber, String roomName,
			int capacity, String location) {
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.capacity = capacity;
		this.location = location;
		
	}
	
	/* setters & getters */

	public long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(long roomNumber) {
		long oldValue = this.roomNumber;
		this.roomNumber = roomNumber;
		changeSupport.firePropertyChange(ROOMNUMBER_PROPERTY, oldValue, roomNumber);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		String oldValue = this.roomName;
		this.roomName = roomName;
		changeSupport.firePropertyChange(ROOMNAME_PROPERTY, oldValue, roomName);
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		int oldValue = this.capacity;
		this.capacity = capacity;
		changeSupport.firePropertyChange(CAPACITY_PROPERTY, oldValue, capacity);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		String oldValue = this.location;
		this.location = location;
		changeSupport.firePropertyChange(LOCATION_PROPERTY, oldValue, location);
	}

	@Override
	public String toString() {
		return "RoomModel [roomNumber=" + roomNumber + ", roomName=" + roomName
				+ ", capacity=" + capacity + ", location=" + location
				+ "]";
	}

}
