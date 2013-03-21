package com.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.WindowConstants;

import com.client.MainGUI;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.RoomModel;
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

	private DefaultListModel<RoomModel> defaultListModel;

	public MeetingRoomController(MainGUI gui, MainMeetingPanel meeting) {
		this.gui = gui;
		this.view = new MeetingRoomPanel();
		// this.appointment = appointment;
		this.meeting = meeting;
		defaultListModel = new DefaultListModel<RoomModel>();

		view.setStartText("" + meeting.getStartText());
		view.setEndText("" + meeting.getEndText());

		view.getSearch().addActionListener(this);

		view.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {

			}

			public void windowClosing(WindowEvent e) {
				Global.respondGUI.remove(this);
			}
		});

		view.getRoomList().setCellRenderer(new RoomListRenderer());

		view.setLocationRelativeTo(gui);

		view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		view.setVisible(true);

	}

	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		if (success) {
			defaultListModel = new DefaultListModel<RoomModel>();
			for (Object o : al)
				defaultListModel.addElement((RoomModel) o);

			view.getRoomList().setModel(defaultListModel);
		}

		return false;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == view.getSearch()) {
			// hente ut en liste over ledige rom ut fra kapasitet og tidspunkt
			alist.add(view.getCapacity());
			alist.add(createNumValue(meeting.getStartText()));
			alist.add(createNumValue(meeting.getEndText()));
			Global.sHandler.setCurrentFlag(MSGFlagVerb.GET);
			Global.sHandler.setState(State.CONNECTED_WAITING);
			Global.sHandler.writeMessage(Global.jaxbMarshaller
					.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.GET,
							MSGFlagSubject.AVAILIBLEROOM, alist));

		} else if (e.getSource() == view.getChooseRoom()) {
			// legge til valgte rom i appointmentModel og g� tilbake til
			// MeetingPanel
			appointment
					.setPlace((String) view.getRoomList().getSelectedValue());
		}
	}

	private int createNumValue(String s) {
		String b[] = s.split("\\:");

		int a = Integer.parseInt(b[0]);
		a *= 60;
		a += Integer.parseInt(b[1]);

		return a;
	}

}

class RoomListRenderer extends DefaultListCellRenderer {

	// private static ImageIcon maleIcon;
	// private static ImageIcon femaleIcon;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);

		RoomModel room = (RoomModel) value;

		lbl.setText("<html><b>" + room.getRoomName() + "</b></html>");// <br/><i>" + calendar.getOwner() +"</i></html>");

		return lbl;
	}
}
