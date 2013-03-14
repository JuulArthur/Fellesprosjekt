package com.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.model.support.SqlDateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentModel {

	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "startTime")
	private int startTime;
	@XmlElement(name = "endTime")
	private int endTime;
	@XmlElement(name = "host")
	private UserModel host;
	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "text")
	private String text;
	@XmlElement(name = "place")
	private String place; // private RoomModel place
	@XmlElement(name = "isDeleted")
	private boolean isDeleted; // should be hidden or not
	@XmlJavaTypeAdapter(SqlDateAdapter.class)//@XmlElement(name = "date")
	private Date date;
	@XmlElement(name = "members")
	private ArrayList<UserModel> members;

	public AppointmentModel() {
	} /* for jaxb */

	public AppointmentModel(int id, int startTime, int endTime, UserModel host,
			String title, String text, String place, Date date,
			ArrayList<UserModel> members) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.host = host;
		this.title = title;
		this.text = text;
		this.place = place;
		this.date = date;
		this.members = members;

		/* not hidden when made */
		this.isDeleted = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public UserModel getHost() {
		return host;
	}

	public void setHost(UserModel host) {
		this.host = host;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<UserModel> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<UserModel> members) {
		this.members = members;
	}

	public void addMember(UserModel member) {
		if (!this.members.contains(member))
			this.members.add(member);
	}

	public void removeMember(UserModel member) {
		if (this.members.contains(member))
			this.members.remove(member);
	}

	@Override
	public String toString() {
		return "AppointmentModel [id=" + id + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", host=" + host + ", title="
				+ title + ", text=" + text + ", place=" + place
				+ ", isDeleted=" + isDeleted + ", date=" + date + ", members="
				+ members + "]";
	}

}
