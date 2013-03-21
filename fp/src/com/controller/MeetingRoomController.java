package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.WindowConstants;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
//import com.sun.tools.javac.util.List;
import com.view.MainMeetingPanel;
import com.view.MeetingRoomPanel;

public class MeetingRoomController implements ActionListener, IServerResponse {

	private MainGUI gui;
	private MeetingRoomPanel view;
	private AppointmentModel appointment;
	private MainMeetingPanel meeting;
	private ArrayList<Object> alist = new ArrayList<Object>();

	

	public MeetingRoomController(MainGUI gui, MainMeetingPanel meeting) {
		this.gui = gui;
		this.view = new MeetingRoomPanel();
		//this.appointment = appointment;
		this.meeting = meeting;
		
		view.setStartText(""+meeting.getStartText());
		view.setEndText(""+meeting.getEndText());
			
		
		view.setLocationRelativeTo(gui);
		
		view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	
		view.setVisible(true);

		
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
			alist.add(meeting.getStartText());
			alist.add(meeting.getEndText());
			Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET, MSGFlagSubject.AVAILIBLEROOM, alist));
		
			}
			
		
		
		else if (e.getSource() == view.getChooseRoom()){
			//legge til valgte rom i appointmentModel og g� tilbake til MeetingPanel
			appointment.setPlace((String) view.getRoomList().getSelectedValue());
			
			
			
		}
		
		
		
	}


}

	
