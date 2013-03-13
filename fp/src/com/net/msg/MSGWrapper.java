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
	 * Verb flag
	 */
	@XmlElement(name="flagV")
	private MSGFlagVerb flagVerb;
	
	/**
	 * Subject flag
	 */
	@XmlElement(name="flagS")
	private MSGFlagSubject flagSubject;
	
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
	
	public MSGWrapper(int ID, MSGType type, MSGFlagVerb flagV, MSGFlagSubject flagS, ArrayList<Object> objects) {
		this.ID = ID;
		this.type = type;
		this.flagVerb = flagV;
		this.flagSubject = flagS;
		this.objects = objects;
	}
	
	/**
	 * OldSchool initing for ghey protocol
	 * @param ID
	 * @param type
	 * @param flagV
	 * @param objects
	 */
	@Deprecated
	public MSGWrapper(int ID, MSGType type, MSGFlagVerb flagV, ArrayList<Object> objects) {
		this.ID = ID;
		this.type = type;
		this.flagVerb = flagV;
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

	public MSGFlagVerb getFlagVerb() {
		return flagVerb;
	}

	public void setFlagVerb(MSGFlagVerb flag) {
		this.flagVerb = flag;
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
	
	public MSGFlagSubject getFlagSubject() {
		return flagSubject;
	}

	public void setFlagSubject(MSGFlagSubject flagS) {
		this.flagSubject = flagS;
	}

	@Override
	public String toString(){
		String s = ID + " " + type + " " + flagVerb + " " + flagSubject + " ";
		if(objects != null)
			s += objects.size();
		return s;
	}
}
