package com.client;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.client.net.ServerHandler;
import com.controller.AddParticipantController;
import com.controller.CalendarController;
import com.controller.CreateAppointmentController;
import com.controller.IServerResponse;
import com.controller.LogginPaneController;
import com.controller.MeetingRoomController;
import com.controller.SavedMeetingPanelController;
import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.NotificationModel;
import com.model.RoomModel;
import com.model.UserModel;
import com.settings.Global;
import com.view.AddParticipantPanel;
import com.view.CalendarJDialog;
import com.view.LogginPane;
import com.view.MeetingPanel;
import com.view.MeetingRoomPanel;
import com.view.SavedMeetingPanel;
import com.view.calendar.CalendarLayout;
import com.xml.JAXBMarshaller;

public class MainGUI extends JFrame{
	
	/* Views */
	private LogginPane logginView;
	private CalendarLayout calendarView;	
	private CalendarJDialog calendarJDialogView;
	private MeetingPanel createAppointmentView;
	private SavedMeetingPanel appointmentView;
	
//	private AddParticipantPanel participantView;
	
	/* Controllers*/
	private LogginPaneController logginController;
	private CalendarController calendarController;
	private CreateAppointmentController createAppointmentController;
	private SavedMeetingPanelController appointmentController;	
	private AddParticipantController participantController;
	private MeetingRoomController meetingroomController;
	
	/* Models */
	private UserModel userModel = null;
	private ArrayList<CalendarModel> calendarModels;
	private ArrayList<CalendarModel> subscribedCalendarModels;
	private ArrayList<NotificationModel> notificationsModels;
	private AlarmModel alarmModel;
	private RoomModel roomModel;
	
	public void startServer() throws Exception{
		Global.sHandler = new ServerHandler("localhost", 8078 ); //mel.is
		Global.jaxbMarshaller = new JAXBMarshaller();
        Global.respondGUI = new ArrayList<IServerResponse>();
		
		System.out.println("[Main] Connected to server");
	}
	
	/*
	 *  INITS
	 */
	public void initLoggin() throws Exception{
		startServer();

        this.logginView = new LogginPane();
        this.calendarView = new CalendarLayout();
        this.createAppointmentView = new MeetingPanel();
		this.appointmentView = new SavedMeetingPanel();
//		this.participantView = new AddParticipantPanel();
		
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
		if(this.calendarModels == null) //If there are no calendars from before on the user
			this.calendarModels = new ArrayList<CalendarModel>();
		this.notificationsModels = this.userModel.getNotifications();
		this.subscribedCalendarModels = this.userModel.getSubscribedCalendars();
		if(this.subscribedCalendarModels == null)
			this.subscribedCalendarModels = new ArrayList<CalendarModel>();
		
		/* Setup new controller */		
		this.calendarController = new CalendarController(this, calendarView);
		
        Global.respondGUI.add(calendarController);
	}
	
	// -----test----
/*	
	public void initParticipantPanel (MeetingPanel view) {
		System.out.println("initParticipantPanel");
		this.getContentPane().add(participantView);
		this.pack();
		this.participantController = new AddParticipantController(view);
	
		Global.respondGUI.add(participantController);
		
	}
	*/
	public void initCreateAppointment(){
		
		this.getContentPane().removeAll();
		this.getContentPane().add(createAppointmentView);
		this.pack();
		
		this.createAppointmentController = new CreateAppointmentController(this, createAppointmentView);
		Global.respondGUI.add(createAppointmentController);
	}
	
	public void initCreateAppointment(AppointmentModel am){
		
		this.getContentPane().removeAll();
		this.getContentPane().add(createAppointmentView);
		this.pack();
		
		this.createAppointmentController = new CreateAppointmentController(this, createAppointmentView, am);
		Global.respondGUI.add(createAppointmentController);
	}
	
	public void initAppointment(AppointmentModel inputAppointment){
		this.getContentPane().removeAll();
		this.appointmentController= new SavedMeetingPanelController(inputAppointment, appointmentView , this);
		this.getContentPane().add(appointmentController.getMeetingPanel());
		this.pack();
		
		Global.respondGUI.add(appointmentController);
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

	public ArrayList<CalendarModel> getSubscribedCalendarModels() {
		return subscribedCalendarModels;
	}

	public void setSubscribedCalendarModels(
			ArrayList<CalendarModel> subscribedCalendarModels) {
		this.subscribedCalendarModels = subscribedCalendarModels;
	}

	public ArrayList<NotificationModel> getNotificationsModels() {
		return notificationsModels;
	}

	public void setNotificationsModels(
			ArrayList<NotificationModel> notificationsModels) {
		this.notificationsModels = notificationsModels;
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
