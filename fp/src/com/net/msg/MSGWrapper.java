package com.net.msg;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MSGWrapper {
	
	/**
	 * ID for that communcation object. Used for multiple requests
	 */
	@XmlElement(name="ID")
	private int ID;
	
	/**
	 * What type of object it is
	 */
	@XmlElement(name="type")
	private MSGType type;
	
	/**
	 * Flag for 
	 */
	@XmlElement(name="flag")
	private MSGFlag flag;
	
	/**
	 * Who?
	 */
	@XmlElement(name="user")
	private String user;
	
	@XmlElement(name="objects")
	private ArrayList<Object> objects;

	/**
	 * JAXB demands no-args constructor
	 */
	public MSGWrapper() {
	}
	
	public MSGWrapper(int ID, MSGType type, MSGFlag flag, ArrayList<Object> objects) {
		this.ID = ID;
		this.type = type;
		this.flag = flag;
		this.objects = objects;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public MSGType getType() {
		return type;
	}

	public void setType(MSGType type) {
		this.type = type;
	}

	public MSGFlag getFlag() {
		return flag;
	}

	public void setFlag(MSGFlag flag) {
		this.flag = flag;
	}

	public ArrayList<Object> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<Object> objects) {
		this.objects = objects;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString(){
		String s = ID + " " + type + " " + flag + " ";
		if(objects != null)
			s += objects.size();
		return s;
	}
}
