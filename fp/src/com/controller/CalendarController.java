package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.client.MainGUI;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.AddOtherCalendarsJDialog;
import com.view.CalendarLayout;
import com.view.ManageCalendarsJDialog;
import com.view.MeetingPanel;

public class CalendarController implements ActionListener, IServerResponse{
	
	private MainGUI main;
	
	private CalendarLayout calendarView;
	
	private CalendarModel calendarModel;
	private ArrayList<NotificationModel> notificationsModels;
	
	
	public CalendarController(MainGUI main, CalendarLayout calendarView){
		this.main = main;
		this.calendarView = calendarView;
		
		this.calendarView.addButtonMeetingAddListener(this);
		this.calendarView.addButtonLogoutAddListener(this);
		this.calendarView.addButtonManageCalendarAddListener(this);
		this.calendarView.addButtonShowOtherCalendarsAddListener(this);
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
		else if (e.getSource() == calendarView.getbtnManageCalendar()) {
			ManageCalendarsJDialog manageCalendars = new ManageCalendarsJDialog();
			manageCalendars.setVisible(true);
			manageCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
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
		// TODO Auto-generated method stub
		return false;
	}

}
