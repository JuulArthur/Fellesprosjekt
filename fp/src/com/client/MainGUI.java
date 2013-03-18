package com.client;


import java.util.ArrayList;

import javax.swing.JFrame;

import com.view.CalendarJDialog;
import com.view.CalendarLayout;
import com.view.LogginPane;
import com.view.MainMeetingPanel;
import com.view.MeetingPanel;

import com.client.net.ServerHandler;
import com.controller.CalendarController;
import com.controller.CreateAppointmentController;
import com.controller.IServerResponse;
import com.controller.LogginPaneController;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.settings.Global;
import com.xml.JAXBMarshaller;

public class MainGUI extends JFrame{
	
	LogginPane logginView;
	LogginPaneController logginController;
	CalendarController calendarController;
	CreateAppointmentController createAppointmentController;
	
	private CalendarLayout calendarView;
	private MeetingPanel createAppointmentView;
	
	CalendarJDialog calendarJDialogView;
	
	private UserModel userModel;
	
	public void startServer() throws Exception{
		Global.sHandler = new ServerHandler("localhost", 8078 ); //mel.is
		Global.jaxbMarshaller = new JAXBMarshaller();
        Global.respondGUI = new ArrayList<IServerResponse>();
		
		System.out.println("[Main] Connected to server");
	}
	
	public void initLoggin() throws Exception{
		
		startServer();

        logginView = new LogginPane();
        calendarView = new CalendarLayout();
		
		this.setTitle("Hei");
        this.getContentPane().add(logginView.pane);
        this.pack(); 
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
               
        logginController = new LogginPaneController(logginView, userModel, this);
        
        Global.respondGUI.add(logginController);
	}
	
	public void initCalendar(){
		this.getContentPane().removeAll();
		this.getContentPane().add(calendarView);
		this.pack();
		
		calendarController = new CalendarController(this, calendarView);
		
		
	}
	
	public void initCreateAppointment(){
		this.getContentPane().removeAll();
		this.getContentPane().add(calendarView);
		this.pack();
		
		createAppointmentController = new CreateAppointmentController(this, createAppointmentView);
		
		
	}
	
	public static void main(String[] args) throws Exception {
		new MainGUI().initLoggin();
	}
}
