package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.WindowConstants;

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
	
	//if color hasn't yet been set?
	private boolean isNew;
	
	public AddOtherCalendarsJDialogController(CalendarController calendarController, boolean isNew){
		this.isNew = isNew;
		this.calendarController = calendarController;
		addOtherCalendars = new AddOtherCalendarsJDialog();
		addOtherCalendars.setLocationRelativeTo(calendarController.getMain());
		addOtherCalendars.setVisible(true);
		addOtherCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//don't know what to check
		if(arg0.getSource() != null) {
			//really just guessing
			String color = (String)arg0.getSource();
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
		if (success) {
			if(al != null) {
				switch(verb) {
				case CREATE:
					//create new color ?
					break;
				case UPDATE:
					//update given color
					break;
				default:
					break;
				}
			}
		}
		return false;
	}

}
