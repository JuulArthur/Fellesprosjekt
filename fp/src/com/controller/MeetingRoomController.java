package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.RoomModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.server.db.Factory;
import com.settings.Global;
import com.sun.tools.javac.util.List;
import com.view.AddParticipantPanel;
import com.view.MeetingRoomPanel;

public class MeetingRoomController implements ActionListener, IServerResponse {

	private MainGUI gui;
	private MeetingRoomPanel view;
	private AppointmentModel appointment;
	private ArrayList<Object> alist = new ArrayList<Object>();

	

	public MeetingRoomController(MainGUI gui, MeetingRoomPanel view, AppointmentModel appointment) {
		this.gui = gui;
		this.view = view;
		this.appointment = appointment;
	
		view.setStartText(""+appointment.getStartTime());
		view.setEndText(""+appointment.getEndTime());
		
	}
	

	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		if (success) {
			JList rooms = new JList(al.toArray());
			view.setRoomList(rooms);
			
				
				
			}
			
		return false;
	}

	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == view.getSearch()){
			//hente ut en liste over ledige rom ut fra kapasitet og tidspunkt
			alist.add(view.getCapacity());
			alist.add(appointment.getStartTime());
			alist.add(appointment.getEndTime());
			Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.AVAILIBLEROOM, alist));
		
			}
			
		
		
		else if (e.getSource() == view.getChooseRoom()){
			//legge til valgte rom i appointmentModel og gå tilbake til MeetingPanel
			appointment.setPlace((String) view.getRoomList().getSelectedValue());
			
			
			
		}
		
		
		
	}


}

	
