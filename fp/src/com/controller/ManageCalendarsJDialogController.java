package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.WindowConstants;

import com.model.CalendarModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.ManageCalendarsJDialog;

public class ManageCalendarsJDialogController implements IServerResponse, ActionListener{
	private ManageCalendarsJDialog manageCalendars;
	
	private CalendarController calendarController;
	
	private CalendarModel model;
	
	private MSGFlagVerb verb;
	private boolean isNew;

	public ManageCalendarsJDialogController(boolean isNew, CalendarModel model, CalendarController cc) {
		this.calendarController = cc;
		this.model = model;
		this.isNew = isNew;
		
		manageCalendars = new ManageCalendarsJDialog(model);
		//manageCalendars.setModal(true);
		
		manageCalendars.getBtnAvbryt().addActionListener(this);
		manageCalendars.getBtnLagre().addActionListener(this);
		
		manageCalendars.setLocationRelativeTo(calendarController.getMain());
		manageCalendars.setVisible(true);
		manageCalendars.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		/*Check if last request was successful*/
		if(success){
			/* Do we have response objects? */
			if(al == null){
				switch (verb) {
				case UPDATE:
					manageCalendars.setVisible(false);
					manageCalendars.dispose();
					Global.respondGUI.remove(this);
					break;

				default:
					break;
				}
			}
			else{
				switch (verb) {
				case CREATE:
					calendarController.addCalenderModelItem(model);	
					break;

				default:
					break;
				}
				manageCalendars.setVisible(false);
				manageCalendars.dispose();
				Global.respondGUI.remove(this);
			}
		}
		else { //Dårlig stemning
		}		
		
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == manageCalendars.getBtnLagre()){
			ArrayList<Object> al = new ArrayList<Object>();
			al.add(model);
			
			/* Send that shiit to server*/
			if(isNew == true){
				Global.sHandler.setCurrentFlag(MSGFlagVerb.CREATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.CREATE, MSGFlagSubject.CALENDAR, al));
				verb = MSGFlagVerb.CREATE;
			}
			else{
				Global.sHandler.setCurrentFlag(MSGFlagVerb.UPDATE);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE, MSGFlagSubject.CALENDAR, al));
				verb = MSGFlagVerb.UPDATE;
			}
		}
		else if(e.getSource() == manageCalendars.getBtnAvbryt()){
			manageCalendars.setVisible(false);
			manageCalendars.dispose();
		}
		
	}
}
