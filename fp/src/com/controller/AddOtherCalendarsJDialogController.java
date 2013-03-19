package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.WindowConstants;

import com.view.AddOtherCalendarsJDialog;

public class AddOtherCalendarsJDialogController implements IServerResponse, ActionListener{

	/* View */
	AddOtherCalendarsJDialog  addOtherCalendars;
	
	public AddOtherCalendarsJDialogController(){
		addOtherCalendars = new AddOtherCalendarsJDialog();
		addOtherCalendars.setVisible(true);
		addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}

}
