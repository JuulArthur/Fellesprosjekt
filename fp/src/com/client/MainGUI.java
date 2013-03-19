package com.client;


import java.util.ArrayList;

import javax.swing.JFrame;

import com.view.CalendarJDialog;
import com.view.calendar.CalendarLayout;
import com.view.LogginPane;
import com.view.MainMeetingPanel;
import com.view.MeetingPanel;
import com.view.SavedMeetingPanel;

import com.client.net.ServerHandler;
import com.controller.CalendarController;
import com.controller.CreateAppointmentController;
import com.controller.IServerResponse;
import com.controller.LogginPaneController;
import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.model.UserModel;
import com.settings.Global;
import com.xml.JAXBMarshaller;

public class MainGUI extends JFrame{
	
	/* Views */
	private LogginPane logginView;
	private CalendarLayout calendarView;	
	private CalendarJDialog calendarJDialogView;
	private MeetingPanel createAppointmentView;
	private SavedMeetingPanel appointmentView;
	
	/* Controllers*/
	private LogginPaneController logginController;
	private CalendarController calendarController;
	private CreateAppointmentController createAppointmentController;
	
	
	/* Models */
	private UserModel userModel = null;
	private ArrayList<CalendarModel> calendarModels;
	private ArrayList<CalendarModel> subscribedCalendarModels;
	private ArrayList<NotificationModel> notificationsModels;
	private AlarmModel alarmModel;
	
	public void startServer() throws Exception{
		Global.sHandler = new ServerHandler("localhost", 8078 ); //mel.is
		Global.jaxbMarshaller = new JAXBMarshaller();
        Global.respondGUI = new ArrayList<IServerResponse>();
		
		System.out.println("[Main] Connected to server");
	}
	
	/*
	 *  INITS
	 * 
	 */
	public void initLoggin() throws Exception{
		startServer();

        this.logginView = new LogginPane();
        this.calendarView = new CalendarLayout();
		
		this.setTitle("Google Calendar. No rights reserved");
        this.getContentPane().add(logginView.pane);
        this.pack(); 
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
               
        logginController = new LogginPaneController(logginView, this);
        
        Global.respondGUI.add(logginController);
	}
	
	public void initCalendar(){		
		/* Init calendarView*/
		this.getContentPane().removeAll();
		this.getContentPane().add(calendarView);
		this.pack();
		
		/* Remove loggin */
		Global.respondGUI.remove(logginController);
		this.logginController = null;
		this.logginView = null;
		
		this.calendarModels = this.userModel.getCalendars();
		this.notificationsModels = this.userModel.getNotifications();
		this.subscribedCalendarModels = this.userModel.getSubscribedCalendars();
		
		/* Setup new controller */		
		this.calendarController = new CalendarController(this, calendarView);
		
        Global.respondGUI.add(calendarController);
	}
	
	public void initCreateAppointment(){
		this.getContentPane().removeAll();
		this.getContentPane().add(calendarView);
		this.pack();
		
		createAppointmentController = new CreateAppointmentController(this, createAppointmentView);
	}
	
	public void initAppointment(AppointmentModel inputAppointment){
		
	}
	
	public static void main(String[] args) throws Exception {
		new MainGUI().initLoggin();
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public ArrayList<CalendarModel> getCalendarModels() {
		return calendarModels;
	}

	public void setCalendarModels(ArrayList<CalendarModel> calendarModels) {
		this.calendarModels = calendarModels;
	}

	public AlarmModel getAlarmModel() {
		return alarmModel;
	}

	public void setAlarmModel(AlarmModel alarmModel) {
		this.alarmModel = alarmModel;
	}
	
	
}
