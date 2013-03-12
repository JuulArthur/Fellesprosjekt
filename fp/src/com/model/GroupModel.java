package com.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GroupModel {

	@XmlElement(name="id")
	private int id;
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="members")
	private ArrayList<UserModel> members;
	
	/* for jabx */
	public GroupModel() {}
	
	public GroupModel(int id, String name,
			ArrayList<UserModel> members) {
		this.id = id;
		this.name = name;
		this.members = members;
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

	public ArrayList<UserModel> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<UserModel> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "GroupModel [id=" + id + ", name=" + name + ", members="
				+ members + "]";
	}
	
}
