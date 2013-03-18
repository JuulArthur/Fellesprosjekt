package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.MeetingPanel;

public class CreateAppointmentController implements ActionListener, IServerResponse{
	
	private MainGUI gui;
	private MeetingPanel view;
	private UserModel user;
	
	public CreateAppointmentController(MainGUI gui, MeetingPanel view){
		this.gui = gui;
		this.view = view;
		this.user = gui.getUserModel();
		
		this.view.saveBtnAddListener(this);
//		this.calendarView.addButtonMeetingAddListener(this);
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.getSaveButton()){
			int startTimeHours = Integer.parseInt(view.getStartText().substring(0,2));
			int startTimeMinutes = Integer.parseInt(view.getStartText().substring(3,5));
			int startTime = startTimeHours*60 + startTimeMinutes;
			int endTimeHours = Integer.parseInt(view.getEndText().substring(0,2));
			int endTimeMinutes = Integer.parseInt(view.getEndText().substring(3,5));
			int endTime = endTimeHours*60 + endTimeMinutes;
			JList participantList = view.getParticipantList();
			ArrayList<UserModel> participants = new ArrayList<UserModel>();
			for (int i = 0;i<participantList.getModel().getSize();i++){
				participants.add((UserModel) participantList.getModel().getElementAt(i));
			}
			AppointmentModel am = new AppointmentModel(System.currentTimeMillis(), startTime, endTime, user,
					view.getTittelText(), view.getDescriptionText(), view.getPlaceText(), Date date,
					participants);
			
			ArrayList<Object> alist = new ArrayList<Object>();
			alist.add(am);
			
			Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGIN);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGIN, alist));
		}
		// TODO Auto-generated method stub
		
	}
}
