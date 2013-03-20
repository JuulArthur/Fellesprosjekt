package com.model;

import java.beans.PropertyChangeSupport;
import java.sql.Date;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.model.support.SqlDateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppointmentModel {

	private final static String ID_PROPERTY = "Id";
	private final static String STARTTIME_PROPERTY = "StartTime";
	private final static String ENDTIME_PROPERTY = "EndTime";
	private final static String HOST_PROPERTY = "Host";
	private final static String TITLE_PROPERTY = "Title";
	private final static String TEXT_PROPERTY = "Text";
	private final static String PLACE_PROPERTY = "Place";
	private final static String ISDELETED_PROPERTY = "IsDeleted";
	private final static String DATE_PROPERTY = "Date";
	private final static String MEMBERS_PROPERTY = "Members";
	private final static String ISSENDTOUTNOTI_PROPERY = "isSendtOutNoti";
	private final static String ISACCEPTED_PROPERTY = "KJFHKDJSGJHBGJHB";
	private final static String ISSEEN_PROPERTY = "HJGKJSDKJLIGGH";
	
	@XmlTransient
	public PropertyChangeSupport changeSupport;
	
	@XmlElement(name = "id")
	private long id;
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
	@XmlElement(name = "isAccepted")
	private boolean isAccepted;
	@XmlElement(name = "isSeen")
	private boolean isSeen;
	
	@XmlElement(name = "isSendtOutNoti")
	private boolean isSendtOutNoti;

	public AppointmentModel() {
	} /* for jaxb */

	public AppointmentModel(long id, int startTime, int endTime, UserModel host,
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
		this.isSendtOutNoti = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		long oldValue = this.id;
		this.id = id;
		changeSupport.firePropertyChange(ID_PROPERTY, oldValue, id);
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		int oldValue = this.startTime;
		this.startTime = startTime;
		changeSupport.firePropertyChange(STARTTIME_PROPERTY, oldValue, startTime);
		
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		int oldValue = this.endTime;
		this.endTime = endTime;
		changeSupport.firePropertyChange(ENDTIME_PROPERTY, oldValue, endTime);
	}

	public UserModel getHost() {
		return host;
	}

	public void setHost(UserModel host) {
		UserModel oldValue = this.host;
		this.host = host;
		changeSupport.firePropertyChange(HOST_PROPERTY, oldValue, host);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		String oldValue = this.title;
		this.title = title;
		changeSupport.firePropertyChange(TITLE_PROPERTY, oldValue, title);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldValue = this.text;
		this.text = text;
		changeSupport.firePropertyChange(TEXT_PROPERTY, oldValue, text);
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		String oldValue = this.place;
		this.place = place;
		changeSupport.firePropertyChange(PLACE_PROPERTY, oldValue, place);
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setSendnotification(boolean setNotification){
		boolean oldValue = this.isSendtOutNoti;
		this.isSendtOutNoti = setNotification;
		changeSupport.firePropertyChange(ISDELETED_PROPERTY, oldValue, isSendtOutNoti);
	}
	public boolean getSendNotification(){
		return this.isSendtOutNoti;
	}
	
	public void setDeleted(boolean isDeleted) {
		boolean oldValue = this.isDeleted;
		this.isDeleted = isDeleted;
		changeSupport.firePropertyChange(ISDELETED_PROPERTY, oldValue, isDeleted);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		Date oldValue = this.date;
		this.date = date;
		changeSupport.firePropertyChange(DATE_PROPERTY, oldValue, date);
	}

	public ArrayList<UserModel> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<UserModel> members) {
		ArrayList<UserModel> oldValue = this.members;
		this.members = members;
		changeSupport.firePropertyChange(MEMBERS_PROPERTY, oldValue, members);
	}
	
	public void addMembers(UserModel member){
		this.members.add(member);
	}
	
	public boolean memberContains(UserModel member){
		return this.members.contains(member);
	}

	public void addMember(UserModel member) {
		if (!this.members.contains(member))
			this.members.add(member);
	}

	public void removeMember(UserModel member) {
		if (this.members.contains(member))
			this.members.remove(member);
	}
	

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		boolean oldValue = this.isSeen;
		this.isAccepted = isAccepted;
		changeSupport.firePropertyChange(ISACCEPTED_PROPERTY, oldValue, isSeen);
	}

	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		boolean oldValue = this.isSeen;
		this.isSeen = isSeen;
		changeSupport.firePropertyChange(ISSEEN_PROPERTY, oldValue, isSeen);
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
