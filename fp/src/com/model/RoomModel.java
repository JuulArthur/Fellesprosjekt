package com.model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RoomModel {

	@XmlElement(name="roomnumber")
	private int roomNumber;
	@XmlElement(name="roomname")
	private String roomName;
	@XmlElement(name="capacity")
	private int capacity;
	@XmlElement(name="location")
	private String location;
	
	/* Thought here is to have a bool that will say if the room is
	 * available or not, when the appointment is over, the bool
	 * is flipped.
	 */
	
	@XmlElement(name="reserved")
	private boolean reserved;
	
	public RoomModel() {} /* for jaxb */
	
	public RoomModel(int roomNumber, String roomName,
			int capacity, String location) {
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.capacity = capacity;
		this.location = location;
		
		/* start with the room not reserved */
		this.reserved = false;
	}
	
	/* setters & getters */

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	@Override
	public String toString() {
		return "RoomModel [roomNumber=" + roomNumber + ", roomName=" + roomName
				+ ", capacity=" + capacity + ", location=" + location
				+ ", reserved=" + reserved + "]";
	}

}
