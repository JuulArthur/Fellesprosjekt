package com.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.view.SavedMeetingPanel;

public class SavedMeetingPanelController  implements ActionListener {

	private MainGUI gui;
	private SavedMeetingPanel meetingPanel;
	private AppointmentModel appointment;

	
	public SavedMeetingPanelController(AppointmentModel appointment, SavedMeetingPanel newMeetingpanel, MainGUI gui){
		this.gui=gui;
		this.appointment=appointment;
		this.meetingPanel=newMeetingpanel;
		meetingPanel.setStede(this.appointment.getPlace());
		meetingPanel.setTitteltextField(this.appointment.getText());
		meetingPanel.setStartText(""+this.appointment.getStartTime());
		meetingPanel.setEndText(""+this.appointment.getEndTime());
		meetingPanel.setBeskrivelseTextArea(this.appointment.getText());
		
		if(!(appointment.getHost()==gui.getUserModel())){
			meetingPanel.setVisible(false);
			meetingPanel.setVisible(false);
		}
		if(meetingPanel.getComming().contains(gui.getUserModel())){
			meetingPanel.getGodta().setEnabled(false);
		}
		else if (meetingPanel.getNotComming().contains(gui.getUserModel())){
			meetingPanel.getAvslag().setEnabled(false);
		}
		
		
		
	}

	public void isCommint(){
		meetingPanel.getNotComming().removeElement(gui.getUserModel());
		meetingPanel.getComming().addElement(gui.getUserModel());
	}

	public void notComming(){
		meetingPanel.getComming().removeElement(gui.getUserModel());
		meetingPanel.getNotComming().addElement(gui.getUserModel());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
