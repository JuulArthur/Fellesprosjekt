package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.RoomModel;
import com.view.MeetingRoomPanel;



public class MeetingRoomController implements ActionListener, IServerResponse{

	private MainGUI gui;
	private MeetingRoomPanel view;
	private AppointmentModel appointment;
	private RoomModel room;
	
	public MeetingRoomController(MainGUI gui, MeetingRoomPanel view, AppointmentModel appointment) {
		this.gui = gui;
		this.view = view;
		this.appointment = appointment;
		view.setStartText(""+appointment.getStartTime());
		view.setEndText(""+appointment.getEndTime());
		
	}
	
	
	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getSearch()){
			
			
		}
		
		else if (e.getSource() == view.getChooseRoom()){
			
			
		}
		
		
	}

}
