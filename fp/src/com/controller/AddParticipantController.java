package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.AddParticipantPanel;
import com.view.MeetingPanel;

public class AddParticipantController implements IServerResponse, ActionListener {

	private MainGUI gui;
	private AddParticipantPanel p_view;
	private MeetingPanel m_view;
	private Type type;
	private DefaultListModel userListModel, groupListModel;
	ArrayList<Object> users;
	ArrayList<Object> groups;
	
	private MSGFlagVerb verb;
	private AppointmentModel model;	
	
	public AddParticipantController(MeetingPanel meeting) {
//		this.gui = gui;
		this.m_view = meeting;
		this.p_view = new AddParticipantPanel();
//		this.model = model;
		this.type = type.NOTHING;
		
		this.p_view.addButtonUserAddListener(this);
		this.p_view.addButtonGroupAddListener(this);
		//this.p_view.addButtonBackAddListener(this);
		
		userListModel = new DefaultListModel();
		groupListModel = new DefaultListModel();
			
		// Hente liste med users og groups
		users = new ArrayList<Object>();
		System.out.println("Oppretta ArrayList");
		
	//	users.add(model);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		System.out.println("SetFlag: GET\n");
		Global.sHandler.setState(State.CONNECTED_WAITING);
		System.out.println("setState: CONNECTED WAITING\n");
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLUSERS, users));
		System.out.println("shandler: et eller annet sent\n");		
		
		System.out.println("verb: GET");
		type = type.PEEPS;
		System.out.println("PEEPS");
		
		groups = new ArrayList<Object>();
		groups.add(model);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLGROUPS, groups));	
		type = type.GROUPS;
		System.out.println("GROUPS");
	}
	
	public AddParticipantPanel getParticipantPanel () {
		return	p_view;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// setter det i user og group combobox
		if (success) {
			if (al != null) {
				switch (type) {
				case PEEPS:
					p_view.setUserComboBox(al);
					System.out.println("setUserComboBox");
					break;
				case GROUPS:
					p_view.setGroupComboBox(al);
					System.out.println("setGroupComboBox");
					break;
				case NOTHING:
					break;
					// Its not gonna happen
				default:
					break;
				}
			}			
			else {
				System.out.println("Det fins ingen brukere og grupper i systemet");
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == p_view.getBtnAddUser()) {
			// Legger bruker til participantlist i MeetingPanel viewet
			System.out.println("AddUserbtn - Pressed");
			JList userList = m_view.getParticipantList();
			
			Object selectedUser = p_view.getUserComboBox().getSelectedItem();
			userListModel.addElement(selectedUser);
			
			userList.setModel(userListModel);
			m_view.setParticipantList(userList);
		}
		else if (e.getSource() == p_view.getBtnAddGroup()) {
			// legger gruppe til participantlist i meetingPanel viewet
			System.out.println("AddGroupbtn - Pressed");
			JList groupList = m_view.getParticipantList();
			
			Object selectedGroup = p_view.getGroupComboBox().getSelectedItem();
			userListModel.addElement(selectedGroup);
			
			groupList.setModel(groupListModel);
			m_view.setParticipantList(groupList);
		}
		else if (e.getSource() == p_view.getBackButton()) {
			System.out.println("Backbtn - Pressed");
			p_view.setVisible(false);
		}
//		else if (e.getSource() == p_view.getBackButton()) {
//			p_view.setVisible(false);
//		}
	}
}

enum Type{
	GROUPS,
	PEEPS,
	NOTHING
}
