package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.server.db.Factory;
import com.settings.Global;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.view.SavedMeetingPanel;

public class SavedMeetingPanelController implements ActionListener,
		IServerResponse {

	private MainGUI gui;
	private SavedMeetingPanel meetingPanel;
	private AppointmentModel appointment;
	private ArrayList<Object> notificationQue = new ArrayList<Object>();
	private ToDO verb;
	private long oldCalID;

	public SavedMeetingPanelController(AppointmentModel appointment,
			SavedMeetingPanel newMeetingpanel, MainGUI gui) {
		oldCalID = -1;
		dummyconstrutor(appointment, newMeetingpanel, gui);
	}

	public SavedMeetingPanelController(long oldCalendarId,
			AppointmentModel appointment, SavedMeetingPanel newMeetingpanel,
			MainGUI gui) {
		this.oldCalID = oldCalendarId;
		dummyconstrutor(appointment, newMeetingpanel, gui);
	}

	private void dummyconstrutor(AppointmentModel appointment,
			SavedMeetingPanel newMeetingpanel, MainGUI gui) {
		this.gui = gui;
		this.appointment = appointment;
		this.meetingPanel = newMeetingpanel;
		verb = ToDO.NOTHING;
		meetingPanel.setTitteltextField(this.appointment.getTitle());
		meetingPanel.setDato("" + this.appointment.getDate());
		int hour = appointment.getStartTime()/60;
		int min = (appointment.getStartTime())-hour*60;
		meetingPanel.setStartText("" +hour+":"+min);
		 hour = appointment.getEndTime()/60;
		 min = (appointment.getEndTime())-(hour*60);
		meetingPanel.setEndText("" +hour+":"+min);
		meetingPanel.setStede(this.appointment.getPlace());
		meetingPanel.setBeskrivelseTextArea(this.appointment.getText());
		meetingPanel.setAlarmText("insert alarm here! ");
		meetingPanel.setCalendar(gui.getUserModel());
		addPersonToCommingAndNot();

		meetingPanel.getAvslag().addActionListener(this);
		meetingPanel.getGodta().addActionListener(this);
		meetingPanel.getRediger().addActionListener(this);
		meetingPanel.getAddcalendar().addActionListener(this);
		meetingPanel.getMooteinnkalling().addActionListener(this);
		meetingPanel.getReturnButton().addActionListener(this);
		
		
		if (!(appointment.getHost().getUsername().equals(gui.getUserModel().getUsername()))) {
			meetingPanel.getRediger().setVisible(false);
			meetingPanel.getMooteinnkalling().setVisible(false);
		}
		if (appointment.getSendNotification()) {
			meetingPanel.getMooteinnkalling().setEnabled(false);
		}
		if (meetingPanel.getComming().contains(gui.getUserModel())) {
			meetingPanel.getGodta().setEnabled(false);
		} else if (meetingPanel.getNotComming().contains(gui.getUserModel())) {
			meetingPanel.getAvslag().setEnabled(false);
		}
	}

	public void isComming() {

		System.out.println("your commin!");
		
		ArrayList<Object> que = new ArrayList<Object>();
		que.add(gui.getUserModel());
		que.add(appointment);
		// que har {bruker, appointment}
		Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE,
						MSGFlagSubject.ISCOMMING, que));

		meetingPanel.getComming().clear();
		meetingPanel.getNotComming().clear();
		
		meetingPanel.getAvslag().setEnabled(true);
		meetingPanel.getNotComming().removeElement(gui.getUserModel().getUsername());
		meetingPanel.getComming().addElement(gui.getUserModel().getUsername());
		smokeAndMirro();
		meetingPanel.getGodta().setEnabled(false);
		
		addPersonToCommingAndNot();

	}
	
	public void smokeAndMirro(){
		for (int i =0; i<appointment.getMembers().size();i++){
		meetingPanel.getNotComming().addElement(appointment.getMembers().get(i));
}
	}

	public void notComming() {

		ArrayList<Object> que = new ArrayList<Object>();
		que.add(gui.getUserModel());
		que.add(appointment);
		// que har {bruker, appointment}
		Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE,
						MSGFlagSubject.ISNOTCOMMING, que));

		meetingPanel.getComming().clear();
		meetingPanel.getNotComming().clear();
		meetingPanel.getGodta().setEnabled(true);
		meetingPanel.getComming().removeElement(gui.getUserModel().getUsername());
		meetingPanel.getNotComming().addElement(gui.getUserModel().getUsername());
		meetingPanel.getAvslag().setEnabled(false);
		addPersonToCommingAndNot();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == meetingPanel.getGodta()) {
			isComming();
		} else if (e.getSource() == meetingPanel.getAvslag()) {
			notComming();
		} else if (e.getSource() == meetingPanel.getRediger()) {
			gui.initCreateAppointment(this.appointment);
			Global.respondGUI.remove(this);


		} else if (e.getSource() == meetingPanel.getMooteinnkalling()) {
			System.out.println("pokemon");
			meetingPanel.getMooteinnkalling().setEnabled(false);
			appointment.setSendnotification(true);
			sendNotification();
		} else if (e.getSource() == meetingPanel.getAddCal()) {
			// her skal man legge til denne avtalen til en kalender.
			addAppointmentToCalender(appointment, (CalendarModel) meetingPanel
					.getCalendarList().getSelectedItem());

		}else if(e.getSource()==meetingPanel.getReturnButton()){
			gui.initCalendar();
			Global.respondGUI.remove(this);
		}

	}

	public void addAppointmentToCalender(AppointmentModel appointment,
			CalendarModel targetCal) {
		ArrayList<Object> objectQue = new ArrayList<Object>();

		if (oldCalID != -1) {
			String mothertickler;
			mothertickler=""+appointment.getId();
			objectQue.add(mothertickler);
			String flipp;
			flipp=""+targetCal.getId();
			objectQue.add(flipp);
			String flopp;
			flopp=""+oldCalID;
			
			objectQue.add(flopp);
			Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller
					.getXMLRepresentation(0, MSGType.REQUEST,
							MSGFlagVerb.UPDATE, MSGFlagSubject.BELONGTO,
							objectQue));
		} else {
			Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller
					.getXMLRepresentation(0, MSGType.REQUEST,
							MSGFlagVerb.CREATE, MSGFlagSubject.BELONGTO,
							objectQue));
		}
	}

	public SavedMeetingPanel getMeetingPanel() {
		return this.meetingPanel;
	}

	private void addPersonToCommingAndNot() {
		ArrayList<Object> que = new ArrayList<Object>();
		String mothertoucher;
		mothertoucher=""+appointment.getId();
		que.add(mothertoucher);
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET,
						MSGFlagSubject.ISSUMMONEDTO_ACCEPTED, que));

	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		if (success) {
			/* Do we have response objects? */
			{
				switch (verb) {
				case SENDING:
					verb = ToDO.NOTHING;
					break;
				case NOTHING:
					// The model has already been updated
					break;

				case ISACCEPTED:
					verb = ToDO.ISNOTACC;
					if(al!=null){
					for (Object com : al) {
						UserModel users = (UserModel)com;

						meetingPanel.getComming().addElement(users.getUsername());

					}
					}

					ArrayList<Object> que = new ArrayList<Object>();
					String mothertoucher;
					mothertoucher=""+appointment.getId();
					que.add(mothertoucher);
					Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
					Global.sHandler.setState(State.CONNECTED_WAITING);
					Global.sHandler.writeMessage(Global.jaxbMarshaller
							.getXMLRepresentation(0, MSGType.REQUEST,
									MSGFlagVerb.GET,
									MSGFlagSubject.ISSUMMONEDTO_DENIED, que));

					break;

				case ISNOTACC:
					if(al!=null){
					for (Object com : al) {
						UserModel users = (UserModel)com;
						meetingPanel.getNotComming().addElement(users.getUsername());
					}
					
					}
					
					verb = ToDO.NOTHING;
					break;

				default:
					break;
				}

			}
		} else { // Dårlig stemning
		}

		return false;
	}

	public void sendNotification() {
		for (int i = 0; i < appointment.getMembers().size(); i++) {
			UserModel currentuser = appointment.getMembers().get(i);
			NotificationModel notification = new NotificationModel(
					appointment.getText(), this.appointment, currentuser);
			this.notificationQue.add(notification);
		}

		verb = ToDO.SENDING;
		Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE,
						MSGFlagSubject.NOTIFICATION, notificationQue));
	}
}

enum ToDO {
	SENDING, NOTHING, ISACCEPTED, ISNOTACC
}
