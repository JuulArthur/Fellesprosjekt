package com.net.msg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum MSGFlagVerb {
	LOGIN,
	LOGOUT,
	UPDATE,
	DELETE,
	CREATE,
	GET,
	ACCEPT,
	DECLINE,
	ERROR
}
