package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	public AddOtherCalendarsJDialogController(CalendarController calendarController, boolean isNew){
		Global.respondGUI.add(this);
		defaultComboBoxModelCalendarModels = new DefaultComboBoxModel<>();
		defaultComboBoxModelUserModels = new DefaultComboBoxModel<>();
		
		this.isNew = isNew;
		this.calendarController = calendarController;
		addOtherCalendars = new AddOtherCalendarsJDialog();
		addOtherCalendars.setLocationRelativeTo(calendarController.getMain());
		addOtherCalendars.setVisible(true);
		addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		
		this.addOtherCalendars.getComboBoxCalendars().setModel(defaultComboBoxModelCalendarModels);
		this.addOtherCalendars.getComboBoxUserNames().setModel(defaultComboBoxModelUserModels);
		
		ArrayList<Object> al = new ArrayList<Object>();
		al.add(calendarController.getMain().getUserModel().getUsername());
		
		/*Ask for all users*/
		requests = Requests.REQUEST_ALLUSERS;
		Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
		Global.sHandler.setState(State.CONNECTED_WAITING);
		Global.sHandler.writeMessage(Global.jaxbMarshaller
				.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET,
						MSGFlagSubject.ALLUSERS, al));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//don't know what to check
		if(e.getSource() != null) {
			//really just guessing
			String color = (String)e.getSource();
			ArrayList<Object> colors = new ArrayList<Object>(Arrays.asList(color));
			/* Send that shiit to server*/
			if(isNew == true){
				Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.CALENDAR, colors));
				verb = MSGFlagVerb.CREATE;
			}
			else{
				Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE, MSGFlagSubject.CALENDAR, colors));
				verb = MSGFlagVerb.UPDATE;
			}
		}
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		System.out.println(success);
		System.out.println(al);
		if (success) {
			if(al != null) {
				switch(requests) {
				case REQUEST_ALLUSERS:
					System.out.println("JDFHGHDSJFHJ");
					for(Object o : al){
						defaultComboBoxModelUserModels.addElement((UserModel)o);
						System.out.println("adding object");
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
