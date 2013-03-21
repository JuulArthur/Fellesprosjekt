package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.WindowConstants;

import com.client.MainGUI;
import com.model.UserModel;
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
	private boolean ifGroups;
	private UserModel usermodel;
	
	ArrayList<Object> users;	
	private MSGFlagVerb verb;	
	
	public AddParticipantController(MainGUI gui, MeetingPanel meeting) {
		ifGroups = false;
		this.gui = gui;
		this.m_view = meeting;
		
		participantPanel = new AddParticipantPanel();
		participantPanel.setLocationRelativeTo(null);
		participantPanel.setVisible(true);
		participantPanel.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		participantPanel.pack();
		
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
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLUSERS, users));
		
	}
	
	public AddParticipantPanel getParticipantPanel () {
		return	participantPanel;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// setter det i user og group combobox
		if (success) {
			if (al != null && !ifGroups) {
				participantPanel.setUserComboBox(al);
				ifGroups = true;
				// send ny spørring om alle grupper
				Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.ALLGROUPS, null));	
				return true;
			}
			if (al != null && ifGroups) {
				participantPanel.setGroupComboBox(al);
				return true;
			}
			else {
				System.out.println("Det fins ingen brukere og/eller grupper i systemet");
				return false;
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == participantPanel.getBtnAddUser()) {
			// Legger bruker til participantlist i MeetingPanel viewet
			JList userList = m_view.getParticipantList();
			UserModel selectedUser = (UserModel) participantPanel.getUserComboBox().getSelectedItem();
			
			if (!userListModel.contains(selectedUser)) {
				userListModel.addElement(selectedUser);
				userList.setModel(userListModel);
				m_view.setParticipantList(userList);
		//		participantPanel.getUserComboBox().removeItem(selectedUser);
			}
		}
		else if (e.getSource() == participantPanel.getBtnAddGroup()) {
			// legger gruppe til participantlist i meetingPanel viewet
			JList groupList = m_view.getParticipantList();
			
			Object selectedGroup = participantPanel.getGroupComboBox().getSelectedItem();

			if (!groupListModel.contains(selectedGroup)) {
				groupListModel.addElement(selectedGroup);
				groupList.setModel(groupListModel);
				m_view.setParticipantList(groupList);
		//		participantPanel.getGroupComboBox().removeItem(selectedGroup);
			}			
		}
		else if (e.getSource() == participantPanel.getBackButton()) {
			System.out.println("Backbtn - Pressed");
			participantPanel.setVisible(false);
			participantPanel.dispose();
		}
	}
}


