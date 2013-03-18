package com.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JList;

import com.controller.IServerResponse;

public class MeetingPanel extends MainMeetingPanel {
	private JLabel lblKalender;
	private JLabel lblDeltagere;
	private JButton btnSlett;
	private JButton btnLagre_1;
	private JButton addPerson;
	private JButton removePerson;
	private JButton btnopenStartCalendar;
	private JButton btnopenStopCalendar;
	private JButton btnAlarmCalendar;
	private JList participantList;
	private JButton btnChooseRoom;
	private JComboBox CalendarComboBox;
	private JFrame meetingFrame;

	public MeetingPanel() {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0};

		/* 
		 * TODO: Se pï¿½ Kalenderknappene
		 */
		
		btnopenStartCalendar = new JButton("\u00C5pne kalender");
		btnopenStartCalendar.addActionListener(new openStartCalendar());
		GridBagConstraints gbc_btnopenStartCalendar = new GridBagConstraints();
		gbc_btnopenStartCalendar.insets = new Insets(0, 0, 5, 5);
		gbc_btnopenStartCalendar.gridx = 6;
		gbc_btnopenStartCalendar.gridy = 2;
		add(btnopenStartCalendar, gbc_btnopenStartCalendar);

		btnopenStopCalendar = new JButton("\u00C5pne kalender");
		btnopenStopCalendar.addActionListener(new openStopCalendar());
		GridBagConstraints gbc_btnopenStopCalendar = new GridBagConstraints();
		gbc_btnopenStopCalendar.insets = new Insets(0, 0, 5, 5);
		gbc_btnopenStopCalendar.gridx = 6;
		gbc_btnopenStopCalendar.gridy = 3;
		add(btnopenStopCalendar, gbc_btnopenStopCalendar);

		btnAlarmCalendar = new JButton("\u00C5pne kalender");
		btnAlarmCalendar.addActionListener(new openAlarmCalendar());
		GridBagConstraints gbc_btnAlarmStartCalendar = new GridBagConstraints();
		gbc_btnAlarmStartCalendar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlarmStartCalendar.gridx = 6;
		gbc_btnAlarmStartCalendar.gridy = 5;
		add(btnAlarmCalendar, gbc_btnAlarmStartCalendar);

		btnChooseRoom = new JButton("+");
		btnChooseRoom.addActionListener(new chooseRoomAction());
		GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
		gbc_btnChooseRoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseRoom.gridx = 6;
		gbc_btnChooseRoom.gridy = 4;
		add(btnChooseRoom, gbc_btnChooseRoom);
		
		lblKalender = new JLabel("Kalender:");
		GridBagConstraints gbc_lblKalender = new GridBagConstraints();
		gbc_lblKalender.anchor = GridBagConstraints.WEST;
		gbc_lblKalender.insets = new Insets(0, 0, 5, 5);
		gbc_lblKalender.gridwidth = 2;
		gbc_lblKalender.gridx = 3;
		gbc_lblKalender.gridy = 7;
		add(lblKalender, gbc_lblKalender);
		
		CalendarComboBox = new JComboBox();
		CalendarComboBox.setPreferredSize(new Dimension(120, 30));
		GridBagConstraints gbc_CalendarComboBox = new GridBagConstraints();
		gbc_CalendarComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_CalendarComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_CalendarComboBox.gridx = 5;
		gbc_CalendarComboBox.gridy = 7;
		add(CalendarComboBox, gbc_CalendarComboBox);

		lblDeltagere = new JLabel("Deltagere:");
		GridBagConstraints gbc_lblDeltagere = new GridBagConstraints();
		gbc_lblDeltagere.anchor = GridBagConstraints.WEST;
		gbc_lblDeltagere.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeltagere.gridx = 3;
		gbc_lblDeltagere.gridy = 8;
		add(lblDeltagere, gbc_lblDeltagere);

		addPerson = new JButton("+");
		addPerson.addActionListener(new addNewPerson());
		addPerson.setPreferredSize(new Dimension(22, 20));
		GridBagConstraints gbc_addPerson = new GridBagConstraints();
		gbc_addPerson.anchor = GridBagConstraints.EAST;
		gbc_addPerson.insets = new Insets(0, 0, 5, 5);
		gbc_addPerson.gridx = 4;
		gbc_addPerson.gridy = 8;
		add(addPerson, gbc_addPerson);

		removePerson = new JButton("-");
		removePerson.setPreferredSize(new Dimension(22, 20));
		GridBagConstraints gbc_removePerson = new GridBagConstraints();
		gbc_removePerson.anchor = GridBagConstraints.WEST;
		gbc_removePerson.insets = new Insets(0, 0, 5, 5);
		gbc_removePerson.gridx = 5;
		gbc_removePerson.gridy = 8;
		add(removePerson, gbc_removePerson);

		JScrollPane deltagerScrollPane = new JScrollPane(); 
		GridBagConstraints gbc_deltagerScrollPane = new GridBagConstraints();
		gbc_deltagerScrollPane.gridwidth = 2;
		gbc_deltagerScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_deltagerScrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_deltagerScrollPane.gridx = 3;
		gbc_deltagerScrollPane.gridy = 9;
		add(deltagerScrollPane, gbc_deltagerScrollPane);
		
		participantList = new JList();
		deltagerScrollPane.setViewportView(participantList);
		
		btnLagre_1 = new JButton("Lagre");
		GridBagConstraints gbc_btnLagre_1 = new GridBagConstraints();
		gbc_btnLagre_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnLagre_1.ipady = 10;
		gbc_btnLagre_1.ipadx = 5;
		gbc_btnLagre_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnLagre_1.gridx = 6;
		gbc_btnLagre_1.gridy = 11;
		btnLagre_1.addActionListener(new saveAction());
		add(btnLagre_1, gbc_btnLagre_1);
		
		btnSlett = new JButton("Slett");
		GridBagConstraints gbc_btnSlett = new GridBagConstraints();
		gbc_btnSlett.insets = new Insets(0, 0, 5, 0);
		gbc_btnSlett.ipady = 10;
		gbc_btnSlett.ipadx = 5;
		gbc_btnSlett.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSlett.gridx = 7;
		gbc_btnSlett.gridy = 11;
		add(btnSlett, gbc_btnSlett);	

	}

	private void setText(JTextField textField, String day, String clockTime) {
		textField.setText(day + " Klokken: " + clockTime);
	}
/*
 * Oppretter en instans av CalendarJDialog, og legger til en knapp du kan trykke pï¿½ nï¿½r du er ferdig.
 * Nï¿½r du trykker den knappen vil riktig textfield bli oppdatert.
 */
	
	
	private void createCalenderDialog (final JTextField textField) {
		final CalendarJDialog calendarDialog = new CalendarJDialog();
		calendarDialog.setVisible(true);
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 4;
		gbc_btnExit.gridy = 4;
		final JButton btnChangesDone = new JButton("Ferdig");
		calendarDialog.getContentPane().add(btnChangesDone, gbc_btnExit);
		calendarDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);	

		btnChangesDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setText(textField, calendarDialog.getdayChosen(), calendarDialog.getclockTime());					
				calendarDialog.dispose();
			}
		});			
	}	
	public static void main(String[] args) {
		MeetingPanel meetingPanel = new MeetingPanel();
		JFrame frame = new JFrame("Avtale/Møte");
		frame.getContentPane().add(meetingPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	
	class chooseRoomAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MeetingRoomPanel meetingRoom = new MeetingRoomPanel();
			JFrame meetingRoomFrame = new JFrame("Legg til brukere og/eller grupper");
			meetingRoomFrame.getContentPane().add(meetingRoom);
			meetingRoomFrame.pack();
			meetingRoomFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			meetingRoomFrame.setVisible(true);
			meetingRoomFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			meetingRoom.setFrame(meetingRoomFrame);

		}
	}
	
	class saveAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			SavedMeetingPanel savedMeetingPanel = new SavedMeetingPanel();
			JFrame savedMeetingFrame = new JFrame("Legg til brukere og/eller grupper");
			savedMeetingFrame.getContentPane().add(savedMeetingPanel);
			savedMeetingFrame.pack();
			savedMeetingFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			savedMeetingFrame.setVisible(true);
			savedMeetingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		
	}

	class openAlarmCalendar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createCalenderDialog((alarmTextField));
		}
	}		
	class openStopCalendar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createCalenderDialog(sluttTextField);
		}
	}	

	class openStartCalendar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createCalenderDialog(startTextField);
		}	
	}

	
	
	class addNewPerson implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			AddParticipantPanel participantPanel = new AddParticipantPanel();
			JFrame participantFrame = new JFrame("Legg til brukere og/eller grupper");
			participantFrame.getContentPane().add(participantPanel);
			participantFrame.pack();
			participantFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			participantFrame.setVisible(true);
			participantFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);			
		}
	}
	class backAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

			
		}
}
