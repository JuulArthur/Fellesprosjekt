package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.view.SavedMeetingPanel;

public class SavedMeetingPanelController implements ActionListener, IServerResponse {

	private MainGUI gui;
	private SavedMeetingPanel meetingPanel;
	private AppointmentModel appointment;

	public SavedMeetingPanelController(AppointmentModel appointment,
			SavedMeetingPanel newMeetingpanel, MainGUI gui) {
		this.gui = gui;
		this.appointment = appointment;
		this.meetingPanel = newMeetingpanel;
		System.out.println(gui);
		System.out.println(appointment);
		System.out.println(meetingPanel);
		meetingPanel.setTitteltextField(this.appointment.getText());
		meetingPanel.setStartText("" + this.appointment.getStartTime());
		meetingPanel.setEndText("" + this.appointment.getEndTime());
		meetingPanel.setStede(this.appointment.getPlace());
		meetingPanel.setBeskrivelseTextArea(this.appointment.getText());

		meetingPanel.getAvslag().addActionListener(this);
		meetingPanel.getGodta().addActionListener(this);
		meetingPanel.getRediger().addActionListener(this);

		if (!(appointment.getHost() == gui.getUserModel())) {
			meetingPanel.getRediger().setVisible(false);
		}
		if(appointment.getSendNotification()){
			meetingPanel.getMooteinnkalling().setEnabled(false);
		}
		if (meetingPanel.getComming().contains(gui.getUserModel())) {
			meetingPanel.getGodta().setEnabled(false);
		} else if (meetingPanel.getNotComming().contains(gui.getUserModel())) {
			meetingPanel.getAvslag().setEnabled(false);
		}

	}

	public void isCommint() {
		meetingPanel.getNotComming().removeElement(gui.getUserModel());
		meetingPanel.getComming().addElement(gui.getUserModel());
		meetingPanel.getGodta().setEnabled(false);

	}

	public void notComming() {
		meetingPanel.getComming().removeElement(gui.getUserModel());
		meetingPanel.getNotComming().addElement(gui.getUserModel());
		meetingPanel.getAvslag().setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == meetingPanel.getGodta()) {
			isCommint();
		} else if (e.getSource() == meetingPanel.getAvslag()) {
			notComming();
		} else if (e.getSource() == meetingPanel.getRediger()) {
			System.out.println("ring han Juul");
		} else if(e.getSource() == meetingPanel.getMooteinnkalling()){
			appointment.setSendnotification(true);
			
		}
	}
	public SavedMeetingPanel getMeetingPanel(){
		return this.meetingPanel;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}
}
