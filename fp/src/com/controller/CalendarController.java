package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
import com.view.calendar.NotificationListRenderer;
import com.view.ManageCalendarsJDialog;
import com.view.MeetingPanel;

public class CalendarController implements ActionListener, IServerResponse, PropertyChangeListener{
	
	private MainGUI main;
	
	/* Views */
	private CalendarLayout calendarView;	
	
	/* Models */
	private DefaultListModel dListModelCalendarModels;
	private DefaultListModel dListModelOtherCalendarModels;
	private DefaultListModel dListModelNotificationsModels;
	private UserModel userModel;
	
	/* Div */
	private ToDo toDo;
	private Object tempO;
	private CalendarModel selectedCalenderModel;
	
	public CalendarController(MainGUI main, CalendarLayout calendarView){
		/* Get the views */
		this.main = main;
		this.calendarView = calendarView;
		
		/* Add all calendars to the model */
		this.dListModelCalendarModels = new DefaultListModel();
		this.dListModelOtherCalendarModels = new DefaultListModel();
		this.dListModelNotificationsModels = new DefaultListModel();
		
		if(main.getCalendarModels() != null)
			for(CalendarModel cm : main.getCalendarModels())
				this.dListModelCalendarModels.addElement(cm);
		
		if(main.getSubscribedCalendarModels() != null)
			for(CalendarModel cm : main.getSubscribedCalendarModels())
				this.dListModelOtherCalendarModels.addElement(cm);
		
		if(main.getNotificationsModels() != null)
			for(NotificationModel nm : main.getNotificationsModels())
				this.dListModelOtherCalendarModels.addElement(nm);
		
		/* Get the UserModel */
		this.userModel = main.getUserModel();
		
		/* Add listeners */
		this.calendarView.addButtonMeetingAddListener(this);
		this.calendarView.addButtonLogoutAddListener(this);
		this.calendarView.addButtonManageCalendarAddListener(this);
		this.calendarView.addButtonShowOtherCalendarsAddListener(this);
		
		final JList listCalender = this.calendarView.getListCalendar();
		final JTextField txt = this.calendarView.getTextFieldManageCalendar();
		
		this.calendarView.getListCalendar().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){
					selectedCalenderModel = (CalendarModel)dListModelCalendarModels.getElementAt(listCalender.getSelectedIndex());
					txt.setText("");
				}	
			}
		});
		
		/* We are currently waiting for nothing*/
		toDo = toDo.NOTHING;
		
		/* Add the models to the views*/
		this.calendarView.getListCalendar().setModel(dListModelCalendarModels);
		this.calendarView.getListOtherCalendars().setModel(dListModelOtherCalendarModels);
		this.calendarView.getListNotification().setModel(dListModelNotificationsModels);
		
		/*Add renderes*/
		this.calendarView.getListCalendar().setCellRenderer(new CalendarListRenderer());
		this.calendarView.getListOtherCalendars().setCellRenderer(new CalendarListRenderer());
		this.calendarView.getListNotification().setCellRenderer(new NotificationListRenderer());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == calendarView.getBtnLeggTilAvtale()){
			Global.respondGUI.remove(this);
			main.initCreateAppointment();
		}
		/* Pluss sign popup on calendars*/
		else if (e.getSource() == calendarView.getbtnManageCalendar()) {
			//Algorithm
			//1. Is the TextFIeld selected?
				//Is there any text?
			//Is an JList selected?
			
			boolean isNew = false;
			CalendarModel sCalendar = null;
			
			String c_name = calendarView.getTextFieldManageCalendar().getText();
			
			if(c_name.length() > 0){
				isNew = true;
				sCalendar = new CalendarModel();
				sCalendar.setName(c_name);
				long unixTime = System.currentTimeMillis() / 1000L;
				sCalendar.setId(unixTime);
				sCalendar.setOwner(userModel.getUsername());
				
			}
			else {
				if(selectedCalenderModel != null){
					sCalendar = selectedCalenderModel;
				}
			}
			
			if(sCalendar != null){
				ManageCalendarsJDialogController manageCalanderController = new ManageCalendarsJDialogController(isNew, sCalendar, this);	
				Global.respondGUI.add(manageCalanderController);
			}
			
			
		}
		else if (e.getSource() == calendarView.getBtnShowOtherCalendars()) {
			AddOtherCalendarsJDialog  addOtherCalendars = new AddOtherCalendarsJDialog();
			addOtherCalendars.setVisible(true);
			addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
			
		}
		
		else if (e.getSource() == calendarView.getBtnLoggUt()){
			System.out.println("[CalendarControll] actionPerformed: Sent logout");
			toDo = ToDo.EXIT;
			Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGOUT);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGOUT, null));
		}
		else if(e.getSource() == calendarView.getButtonNextWeek()){
			
		}
		else if(e.getSource() == calendarView.getButtonLastWeek()){
			
		}
		
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {		
		/*Check if last request was successful*/
		if(success){
			/* Do we have response objects? */
			if(al != null){
				if(al.get(0) instanceof UserModel){
					//Do not propagate
					return false;
				}
			}
			else{
				switch (toDo) {
				case EXIT:
					System.exit(0);
					break;

				default:
					break;
				}
				
				/* Nullify */
				toDo = ToDo.NOTHING;
				tempO = null;
			}
		}
		else { //Dårlig stemning
		}	
		return true;		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Adds the given CalenderModel to the defaultListModel and ArrayList and nullifies the ManageCalendar textfield
	 * @param model
	 */
	public void addCalenderModelItem(CalendarModel model){
		main.getCalendarModels().add(model);
		dListModelCalendarModels.addElement(model);
		calendarView.getTextFieldManageCalendar().setText("");		
	}

	
	/* GETTERS AND SETTERS */
	public DefaultListModel getdListModelCalendarModels() {
		return dListModelCalendarModels;
	}

	public void setdListModelCalendarModels(
			DefaultListModel dListModelCalendarModels) {
		this.dListModelCalendarModels = dListModelCalendarModels;
	}

	public MainGUI getMain() {
		return main;
	}
	
	
}

/**
 * Responsetype enum
 * @author perok
 *
 */
enum ToDo {
	EXIT,
	NOTHING
}
