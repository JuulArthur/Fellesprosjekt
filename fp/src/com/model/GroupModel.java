package com.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupModel {
	public final static String ID_PROPERTY = "Id";
	public final static String NAME_PROPERTY = "Name";
	public final static String MEMBERS_PROPERTY = "Members";
	
	private PropertyChangeSupport changeSupport;
	

	@XmlElement(name = "id")
	private int id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "members")
	private ArrayList<UserModel> members;

	/* for jabx */
	public GroupModel() {
	}

	public GroupModel(int id, String name, ArrayList<UserModel> members) {
		this.id = id;
		this.name = name;
		this.members = members;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		int oldValue = this.id;
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

	public ArrayList<UserModel> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<UserModel> members) {
		ArrayList<UserModel> oldValue = this.members;
		this.members = members;
		changeSupport.firePropertyChange(MEMBERS_PROPERTY, oldValue, members);
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
		return "GroupModel [id=" + id + ", name=" + name + ", members="
				+ members + "]";
	}

}
