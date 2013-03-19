package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.view.MainMeetingPanel;
import com.view.SavedMeetingPanel;

public class SavedMeetingPanelController  implements ActionListener {

	private MainGUI gui;
	private SavedMeetingPanel meetingPanel;

	
	public SavedMeetingPanelController(AppointmentModel appointment, SavedMeetingPanel newMeetingpanel){
		this.gui=gui;
		this.meetingPanel=newMeetingpanel;
		meetingPanel.setStede(mainPanel.getPlaceText());
		meetingPanel.setTitteltextField(mainPanel.getTittelText());
		meetingPanel.setStartText(mainPanel.getStartText());
		meetingPanel.setEndText(mainPanel.getEndText());
		meetingPanel.setBeskrivelseTextArea(mainPanel.getDescriptionText());
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
