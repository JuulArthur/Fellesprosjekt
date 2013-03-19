package com.controller;

import java.util.ArrayList;

import com.client.MainGUI;
import com.view.AddParticipantPanel;

public class AddParticipantController implements IServerResponse {

	private MainGUI gui;
	private AddParticipantPanel p_view;
	
	public AddParticipantController(MainGUI gui, AddParticipantPanel p_view) {
		this.gui = gui;
		this.p_view = p_view;
		
		
	}


	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}
}
