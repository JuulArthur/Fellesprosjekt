package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.view.SavedMeetingPanel;

public class SavedMeetingPanelController  implements ActionListener {

	private MainGUI gui;
	private SavedMeetingPanel meetingPanel;

	
	public SavedMeetingPanelController(SavedMeetingPanel meetingPanel,AppointmentModel appointment, MainGUI gui){
		this.gui=gui;
		this.meetingPanel=meetingPanel;
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
