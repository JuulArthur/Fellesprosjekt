package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.client.MainGUI;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.AddOtherCalendarsJDialog;
import com.view.calendar.CalendarLayout;
import com.view.calendar.CalendarListRenderer;
import com.view.ManageCalendarsJDialog;
import com.view.MeetingPanel;

public class CalendarController implements ActionListener, IServerResponse{
	
	private MainGUI main;
	
	private CalendarLayout calendarView;
	
	//private ArrayList<CalendarModel> calendarModels;
	private ArrayList<NotificationModel> notificationsModels;
	
	private UserModel userModel;
	
	private ToDo toDo;
	private Object tempO;
	
	private DefaultListModel<CalendarModel> dListModelCalendarModels;
	
	public CalendarController(MainGUI main, CalendarLayout calendarView){
		/* Get the views */
		this.main = main;
		this.calendarView = calendarView;
		
		/* Add all calendars to the model */
		this.dListModelCalendarModels = new DefaultListModel<CalendarModel>();
		if(main.getCalendarModels() != null)
			for(CalendarModel cm : main.getCalendarModels())
				this.dListModelCalendarModels.addElement(cm);
		
		/* Get the UserModel */
		this.userModel = main.getUserModel();
		
		/* Add listeners */
		this.calendarView.addButtonMeetingAddListener(this);
		this.calendarView.addButtonLogoutAddListener(this);
		this.calendarView.addButtonManageCalendarAddListener(this);
		this.calendarView.addButtonShowOtherCalendarsAddListener(this);
		
		/* We are currently waiting for nothing*/
		toDo = toDo.NOTHING;
		
		/* Add the models to the views*/
		this.calendarView.getListCalendar().setModel(dListModelCalendarModels);
		
		/*Add renderes*/
		this.calendarView.getListCalendar().setCellRenderer(new CalendarListRenderer());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == calendarView.getBtnLeggTilAvtale()){
			MeetingPanel meetingPanel = new MeetingPanel();
			JFrame meetingFrame = new JFrame("Avtale/Møte");
			meetingFrame.getContentPane().add(meetingPanel);
			meetingFrame.pack();
			meetingFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			meetingFrame.setVisible(true);
			meetingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		}
		/* Pluss sign popup on calendars*/
		else if (e.getSource() == calendarView.getbtnManageCalendar()) {
			if(calendarView.getTextFieldManageCalendar().getText().length() > 0){

				/* Is it a new calendar? */
				String c_name = calendarView.getTextFieldManageCalendar().getText();
				CalendarModel newCalendar = null;
				boolean newCal = true;
				
				/*Check if the name we typed already exists */
				if(dListModelCalendarModels != null)
					
					for(int i = 0; i < dListModelCalendarModels.size(); i++){
						if(dListModelCalendarModels.get(i).getName().equals(c_name)){
							newCalendar = dListModelCalendarModels.get(i);
							newCal = false;
							break;
						}
					}
				
				if(newCal == true){
					newCalendar = new CalendarModel();
					newCalendar.setName(c_name);
					long unixTime = System.currentTimeMillis() / 1000L;
					newCalendar.setId(unixTime);
					newCalendar.setOwner(userModel.getUsername());
				}
				
				/*Edit that shiiit*/
				ManageCalendarsJDialog manageCalendars = new ManageCalendarsJDialog(newCalendar);
				manageCalendars.setModal(true);
				manageCalendars.setVisible(true);
				manageCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
				
				
				
				ArrayList<Object> al = new ArrayList<Object>();
				al.add(newCalendar);		
				
				tempO = newCal;
				
				/* Send that shiit to server*/
				if(newCal == true){
					Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
					Global.sHandler.setState(State.CONNECTED_WAITING);
					Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.CALENDAR, al));
					toDo = ToDo.NEW_CALENDAR;
				}
				else{
					Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
					Global.sHandler.setState(State.CONNECTED_WAITING);
					Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE, MSGFlagSubject.CALENDAR, al));
				}
			}
		}
		else if (e.getSource() == calendarView.getBtnShowOtherCalendars()) {
			AddOtherCalendarsJDialog  addOtherCalendars = new AddOtherCalendarsJDialog();
			addOtherCalendars.setVisible(true);
			addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
			
		}
		
		else if (e.getSource() == calendarView.getBtnLoggUt()){
			System.out.println("[CalendarControll] actionPerformed: Sent logout");
			Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGOUT);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGOUT, null));
		}
		
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {		
		/*Check if last request was successful*/
		if(success){
			/* Do we have response objects? */
			if(al != null){
				if(al.get(0) instanceof UserModel){
					//this.userModel = (UserModel)al.get(0);
					//gui.initCalendar();
					Global.respondGUI.remove(this);
					//Do not propagate
					return false;
				}
			}
			else{
				switch (toDo) {
				case NEW_CALENDAR:
					//TODO
					//calendarView.getListCalendar().add(tempO);
					tempO = null;
					
					break;

				default:
					break;
				}
				
				toDo = ToDo.NOTHING;
				tempO = null;
			}
		}
		else { //Dårlig stemning
		}	
		return true;		
	}
}

/**
 * Responsetype enum
 * @author perok
 *
 */
enum ToDo {
	NEW_CALENDAR,
	UPDATECALENDAR,
	NOTHING
}
