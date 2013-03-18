package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.MeetingPanel;

public class CreateAppointmentController implements ActionListener, IServerResponse{
	
	private MainGUI main;
	private MeetingPanel view;
	
	public CreateAppointmentController(MainGUI gui, MeetingPanel view){
		this.main = main;
		this.view = view;
		
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
			AppointmentModel am = new AppointmentModel(System.currentTimeMillis(), startTime, endTime, UserModel host,
					String title, String text, String place, Date date,
					ArrayList<UserModel> members);
			
			ArrayList<Object> alist = new ArrayList<Object>();
			alist.add(am);
			
			Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGIN);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGIN, alist));
		}
		// TODO Auto-generated method stub
		
	}
}
