package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.MeetingPanel;

public class CreateAppointmentController implements ActionListener, IServerResponse{
	
	private MainGUI gui;
	private MeetingPanel view;
	private UserModel user;
	private AppointmentState appointmentState;
	private final static String[] MONTHS = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	public CreateAppointmentController(MainGUI gui, MeetingPanel view){
		this.gui = gui;
		this.view = view;
		this.user = gui.getUserModel();
		this.appointmentState = appointmentState.NOTHING;
		
		this.view.saveBtnAddListener(this);
//		this.calendarView.addButtonMeetingAddListener(this);
	}
	
	private int fromMonthTextToNumber(String month){
		for(int i=0;i<MONTHS.length;i++){
			if (MONTHS[i].equals(month)){
				return i+1;
			}
		}
		return -1;
	}
	
	
	private boolean checkTimeField(String text){
		if (text.length()!=5){
			return false;
		}
		try{
			Integer.parseInt(view.getStartText().substring(0,2));
			Integer.parseInt(view.getStartText().substring(3,5));
		}
		catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.getSaveButton()){
			int startTime;
			int endTime;
			int year;
			int day;
			if (checkTimeField(view.getStartText())){
				int startTimeHours = Integer.parseInt(view.getStartText().substring(0,2));
				int startTimeMinutes = Integer.parseInt(view.getStartText().substring(3,5));
				startTime = startTimeHours*60 + startTimeMinutes;
			}
			else{
				return;
			}
			if(checkTimeField(view.getEndText())){
				int endTimeHours = Integer.parseInt(view.getEndText().substring(0,2));
				int endTimeMinutes = Integer.parseInt(view.getEndText().substring(3,5));
				endTime = endTimeHours*60 + endTimeMinutes;
			}
			else{
				return;
			}
			JList participantList = view.getParticipantList();
			ArrayList<UserModel> participants = new ArrayList<UserModel>();
			for (int i = 0;i<participantList.getModel().getSize();i++){
				participants.add((UserModel) participantList.getModel().getElementAt(i));
			}
			String dateText = view.getDateText();
			int indexOfDot = dateText.indexOf('.');
			int month = fromMonthTextToNumber(dateText.substring(0,indexOfDot));
			if(month!=-1){
				day = Integer.parseInt(dateText.substring(indexOfDot+1));
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			else{
				return;
			}
			AppointmentModel am = new AppointmentModel(System.currentTimeMillis(), startTime, endTime, user,
					view.getTittelText(), view.getDescriptionText(), view.getPlaceText(), new Date(year,month,day),
					participants);
			
			ArrayList<Object> alist = new ArrayList<Object>();
			alist.add(am);
			
			appointmentState = appointmentState.NEW_APPOINTMENT;
			Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGIN);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.APPOINTMENT, alist));
		}
		
	}
}

enum AppointmentState {
	NEW_APPOINTMENT,
	NOTHING
}
