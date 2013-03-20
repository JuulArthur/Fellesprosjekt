package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.WindowConstants;

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
	private AddParticipantPanel participantPanel;
	private MeetingPanel m_view;
	private DefaultListModel userListModel, groupListModel;
	ArrayList<Object> users;
	ArrayList<Object> groups;
	
	private MSGFlagVerb verb;	
	
	public AddParticipantController(MainGUI gui, MeetingPanel meeting) {
		this.gui = gui;
		this.m_view = meeting;
		participantPanel = new AddParticipantPanel();
		participantPanel.setLocationRelativeTo(null);
		participantPanel.setVisible(true);
		participantPanel.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	

		participantPanel.addButtonUserAddListener(this);
		participantPanel.addButtonGroupAddListener(this);
		participantPanel.addButtonBackAddListener(this);
				
		userListModel = new DefaultListModel();
		groupListModel = new DefaultListModel();
			
		// Hente liste med users og groups
		users = new ArrayList<Object>();
		System.out.println("Oppretta ArrayList");
		
		users.add(gui.getUserModel().getUsername());
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		System.out.println("SetFlag: GET\n");
		Global.sHandler.setState(State.CONNECTED_WAITING);
		System.out.println("setState: CONNECTED WAITING\n");
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLUSERS, users));
		System.out.println("shandler: et eller annet sent\n");		
		
		/*
		groups = new ArrayList<Object>();
		groups.add(model);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLGROUPS, null));	
		type = type.GROUPS;
		System.out.println("GROUPS");
		*/
	}
	
	public AddParticipantPanel getParticipantPanel () {
		return	participantPanel;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// setter det i user og group combobox
		if (success) {
			if (al != null) {
				participantPanel.setUserComboBox(al);
				// send ny spørring om alle grupper
				Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLGROUPS, null));	
				participantPanel.setGroupComboBox(al);
				return true;
			}
			else {
				System.out.println("Det fins ingen brukere og/eller grupper i systemet");
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == participantPanel.getBtnAddUser()) {
			// Legger bruker til participantlist i MeetingPanel viewet
			System.out.println("AddUserbtn - Pressed");
			JList userList = m_view.getParticipantList();
			
			Object selectedUser = participantPanel.getUserComboBox().getSelectedItem();
			userListModel.addElement(selectedUser);
			
			userList.setModel(userListModel);
			m_view.setParticipantList(userList);
		}
		else if (e.getSource() == participantPanel.getBtnAddGroup()) {
			// legger gruppe til participantlist i meetingPanel viewet
			System.out.println("AddGroupbtn - Pressed");
			JList groupList = m_view.getParticipantList();
			
			Object selectedGroup = participantPanel.getGroupComboBox().getSelectedItem();
			userListModel.addElement(selectedGroup);
			
			groupList.setModel(groupListModel);
			m_view.setParticipantList(groupList);
		}
		else if (e.getSource() == participantPanel.getBackButton()) {
			System.out.println("Backbtn - Pressed");
			participantPanel.setVisible(false);
			participantPanel.dispose();
		}
	}
}


