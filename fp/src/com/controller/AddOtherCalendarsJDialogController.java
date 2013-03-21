package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.WindowConstants;

import com.model.CalendarModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.AddOtherCalendarsJDialog;

public class AddOtherCalendarsJDialogController implements IServerResponse, ActionListener{

	/* View */
	private AddOtherCalendarsJDialog  addOtherCalendars;
	
	/* Controller */
	private CalendarController calendarController;
	
	private MSGFlagVerb verb;
	private Requests requests; 
	
	//if color hasn't yet been set?
	private boolean isNew;
	
	private DefaultComboBoxModel<UserModel> defaultComboBoxModelUserModels;
	private DefaultComboBoxModel<CalendarModel> defaultComboBoxModelCalendarModels;
	
	private CalendarModel selectedCalendarModel;
	
	public AddOtherCalendarsJDialogController(CalendarController calendarController, boolean isNew){
		Global.respondGUI.add(this);
		defaultComboBoxModelCalendarModels = new DefaultComboBoxModel<>();
		defaultComboBoxModelUserModels = new DefaultComboBoxModel<>();
		
		this.isNew = isNew;
		this.calendarController = calendarController;
		addOtherCalendars = new AddOtherCalendarsJDialog();
		addOtherCalendars.setLocationRelativeTo(calendarController.getMain());
		addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		
		addOtherCalendars.getBtnAvbryt().addActionListener(this);
		addOtherCalendars.getBtnNewButton().addActionListener(this);
		
		
		addOtherCalendars.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
				Global.respondGUI.remove(this);
			}
		});
		
		this.addOtherCalendars.getComboBoxCalendars().setModel(defaultComboBoxModelCalendarModels);
		this.addOtherCalendars.getComboBoxCalendars().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCalendarModel = defaultComboBoxModelCalendarModels.getElementAt(addOtherCalendars.getComboBoxCalendars().getSelectedIndex());
				
			}
		});
		this.addOtherCalendars.getComboBoxUserNames().setModel(defaultComboBoxModelUserModels);
		this.addOtherCalendars.getComboBoxUserNames().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<CalendarModel> calendars = defaultComboBoxModelUserModels.getElementAt(addOtherCalendars.getComboBoxUserNames().getSelectedIndex()).getCalendars();
				if (calendars != null && calendars.size() > 0){
					defaultComboBoxModelCalendarModels = new DefaultComboBoxModel<>();
					for(CalendarModel cm : calendars)
						defaultComboBoxModelCalendarModels.addElement(cm);
					
					addOtherCalendars.getComboBoxCalendars().setModel(defaultComboBoxModelCalendarModels);
				}
			}
		});
		
		ArrayList<Object> al = new ArrayList<Object>();
		al.add(calendarController.getMain().getUserModel().getUsername());
		
		/*Ask for all users*/
		requests = Requests.REQUEST_ALLUSERS;
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET,
						MSGFlagSubject.ALLUSERS, al));
		
		addOtherCalendars.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addOtherCalendars.getBtnNewButton()){
			if(selectedCalendarModel != null)
				calendarController.addOtherCalenderModelItem(selectedCalendarModel);
			addOtherCalendars.setVisible(false);
			addOtherCalendars.dispose();
			Global.respondGUI.remove(this);
		}
		else if(e.getSource() == addOtherCalendars.getBtnAvbryt()){
			addOtherCalendars.setVisible(false);
			addOtherCalendars.dispose();
			Global.respondGUI.remove(this);
		}
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		if (success) {
			if(al != null) {
				switch(requests) {
				case REQUEST_ALLUSERS:
					for(Object o : al){
						defaultComboBoxModelUserModels.addElement((UserModel)o);
					}
					//addOtherCalendars.getComboBoxUserNames().add
					break;

				default:
					break;
				}
			}
		}
		return false;
	}

}

enum Requests {
	REQUEST_ALLUSERS
}
