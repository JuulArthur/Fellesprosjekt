package com.net.msg;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MSGWrapper {
	@XmlElement(name="ID")
	private int ID;
	@XmlElement(name="type")
	private MSGType type;
	@XmlElement(name="flag")
	private MSGFlag flag;
	
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
	
	@Override
	public String toString(){
		return ID + " " + type + " " + flag + " " + objects.size();
	}
}
