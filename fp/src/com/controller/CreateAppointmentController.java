package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JList;

import com.client.MainGUI;
import com.model.AlarmModel;
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
	private AppointmentModel am;
	private AppointmentState appointmentState;
	private final static String[] MONTHS = {"Januar","Februar","Mars","April","Mai","Juni","Juli","August","September","Oktober","November","Desember"};
	ArrayList<Object> alist;
	private ArrayList<String> uninvitedParticipants;
	
	public CreateAppointmentController(MainGUI gui, MeetingPanel view){
		this.gui = gui;
		this.view = view;
		this.user = gui.getUserModel();
		alist  = new ArrayList<Object>();
		uninvitedParticipants = new ArrayList<String>();
		this.appointmentState = appointmentState.NOTHING;
		
		this.view.saveBtnAddListener(this);
		this.view.returnBtnAddListener(this);
	}
	
	public CreateAppointmentController(MainGUI gui, MeetingPanel view, AppointmentModel am){
		this.gui = gui;
		this.view = view;
		this.user = gui.getUserModel();
		this.am = am;
		alist  = new ArrayList<Object>();
		this.appointmentState = appointmentState.NOTHING;
		
		this.view.saveBtnAddListener(this);
		this.view.returnBtnAddListener(this);
	}
	
	private String fromMonthTextToNumber(String month){
		for(int i=0;i<MONTHS.length;i++){
			MONTHS[0].equals("January");
			if (MONTHS[i].equals(month)){
				return Integer.toString(i+1);
			}
		}
		return Integer.toString(-1);
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
		if(appointmentState == appointmentState.NEW_APPOINTMENT){
			if(alist.size()>1){
				System.out.println("inne");
				alist.remove(0);
				System.out.println(alist);
				
				appointmentState = appointmentState.NEW_ALARM;
				Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.ALARM, alist));
				return true;
			}
			else{
				appointmentState = appointmentState.SUMMONING;
				
				Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.ISSUMMONEDTO, alist));
				return true;
			}
		}
		else if (appointmentState == appointmentState.NEW_ALARM){
			gui.initAppointment(am);
			return true;
		}
		else if (appointmentState == appointmentState.NOTHING){
			return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.getSaveButton()){
			int startTime;
			int endTime;
			int year;
			String day;
			String month;
			String date;
			String title = view.getTittelText();
			if(title.length()==0){
				System.out.println("title");
				return;
			}
			if (checkTimeField(view.getStartText())){
				int startTimeHours = Integer.parseInt(view.getStartText().substring(0,2));
				int startTimeMinutes = Integer.parseInt(view.getStartText().substring(3,5));
				startTime = startTimeHours*60 + startTimeMinutes;
			}
			else{
				System.out.println("start");
				return;
			}
			if(checkTimeField(view.getEndText())){
				int endTimeHours = Integer.parseInt(view.getEndText().substring(0,2));
				int endTimeMinutes = Integer.parseInt(view.getEndText().substring(3,5));
				endTime = endTimeHours*60 + endTimeMinutes;
			}
			else{
				System.out.println("end");
				return;
			}
			JList participantList = view.getParticipantList();
			ArrayList<UserModel> participants = new ArrayList<UserModel>();
			for (int i = 0;i<participantList.getModel().getSize();i++){
				participants.add((UserModel) participantList.getModel().getElementAt(i));
			}
			String dateText = view.getDateText();
			int indexOfDot = dateText.indexOf(".");
			if(indexOfDot!=-1){
				month = fromMonthTextToNumber(dateText.substring(indexOfDot+1));
				day = dateText.substring(0,indexOfDot);
				if (day.length()==1){
					day = "0"+day;
				}
				if (month.length()==1){
					month = "0"+month;
				}
				year = Calendar.getInstance().get(Calendar.YEAR);
				date = (Integer.toString(year)+"-"+month+"-"+day);
			}
			else{
				System.out.println("date");
				return;
			}
			
			if (am!=null){
				this.am.setDate(Date.valueOf(date));
				this.am.setStartTime(startTime);
				this.am.setEndTime(endTime);
				this.am.setTitle(title);
				this.am.setText(view.getDescriptionText());
				this.am.setPlace(view.getPlaceText());
				for(UserModel member:participants){
					if(!am.memberContains(member)){
						am.addMember(member);
					}
				}
				for(UserModel member: am.getMembers()){
					if (!participants.contains(member)){
						uninvitedParticipants.add(member.getName());
						am.removeMember(member);
					}
				}
				appointmentState = appointmentState.UPDATE_APPOINTMENT;
			}
			else{
				AppointmentModel am = new AppointmentModel(System.currentTimeMillis(), startTime, endTime, user,
						title, view.getDescriptionText(), view.getPlaceText(), Date.valueOf(date),
						participants);
				this.am = am;
			}
			alist.add(am);
			String alarmTimeText = view.getAlarmText();
			System.out.println(alarmTimeText);
			System.out.println(checkTimeField(alarmTimeText));
			if(checkTimeField(alarmTimeText)){
				int alarmHours = Integer.parseInt(alarmTimeText.substring(0,2));
				int alarmMinutes = Integer.parseInt(alarmTimeText.substring(3,5));
				int alarmTime = alarmHours*60 + alarmMinutes;
				AlarmModel alm = new AlarmModel(Date.valueOf(date),"Alarm til avtale med tittel: "+title, alarmTime, am, gui.getUserModel());
				appointmentState = appointmentState.NEW_ALARM;
				alist.add(alm);
			}
			else if (alarmTimeText.length()!=0){
				System.out.println("alarm");
			}
			if(this.am.getMembers().size()!=0){
				this
			}
			if(appointmentState == appointmentState.UPDATE_APPOINTMENT){
				appointmentState = appointmentState.NEW_APPOINTMENT;
				
				Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE, MSGFlagSubject.APPOINTMENT, alist));
				return;
			}
			appointmentState = appointmentState.NEW_APPOINTMENT;
			
			Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.APPOINTMENT, alist));
		}
		else if(e.getSource() == view.getReturnButton()){
			if (this.am==null){
				gui.initCalendar();
			}
			else{
				gui.initAppointment(this.am);
			}
		}
		
	}
}

enum AppointmentState {
	NEW_APPOINTMENT,
	NEW_ALARM,
	UPDATE_APPOINTMENT,
	SUMMONING,
	UNSUMMONING,
	NOTHING
}
