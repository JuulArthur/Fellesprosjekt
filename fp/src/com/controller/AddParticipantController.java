package com.controller;

import java.util.ArrayList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.AddParticipantPanel;

public class AddParticipantController implements IServerResponse {

	private MainGUI gui;
	private AddParticipantPanel p_view;
	private CreateAppointmentController appointmentController;
	private Type type;
	
	private MSGFlagVerb verb;
	private AppointmentModel model;	
	
	public AddParticipantController(MainGUI gui, CreateAppointmentController cc, AppointmentModel model) {
		this.gui = gui;
		this.appointmentController = cc;
		this.model = model;
		this.type = type.NOTHING;
		
		// Hente liste med users og groups
		ArrayList<Object> users = new ArrayList<Object>();
		users.add(model);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLUSERS, users));
		verb = MSGFlagVerb.GET;	
		type = type.PEEPS;
		
		ArrayList<Object> groups = new ArrayList<Object>();
		groups.add(model);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLUSERS, groups));
		verb = MSGFlagVerb.GET;	
		type = type.GROUPS;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// setter det i user og group combobox
		if (success) {
			if (al != null) {
				switch (type) {
				case PEEPS:
					p_view.setUserComboBox(al);
					break;
				case GROUPS:
					p_view.setGroupComboBox(al);
					break;
				default:
					break;
				}
			}
			
			else {
			//dno
			}
		}
		
		return false;
	}
}

enum Type{
	GROUPS,
	PEEPS,
	NOTHING
}
