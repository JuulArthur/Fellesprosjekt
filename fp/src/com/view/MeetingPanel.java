package com.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
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
import com.model.UserModel;

public class MeetingPanel extends MainMeetingPanel {
	private JLabel lblKalender;
	private JLabel lblDeltagere;
	private JLabel lblDate;
	private JButton btnSlett;
	private JButton btnLagre_1;
	private JButton addPerson;
	private JButton removePerson;
	private JButton btnChooseDate;
	private JButton btnAlarmCalendar;
	private JList participantList;
	private JButton btnChooseRoom;
	private JComboBox CalendarComboBox;
	private JFrame meetingFrame;
	private JTextField dateTextField;
	private JLabel lblFormat;
	private int posY;
	private DefaultListModel model;
	private DefaultListSelectionModel myListSelectionModel;

	public MeetingPanel() {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};

		/* 
		 * TODO: Se pï¿½ Kalenderknappene
		 */
		posY = 3;
		setFormatLabel(posY);
		posY = 4;
		setFormatLabel(posY);
		posY = 6;
		setFormatLabel(posY);
		
		dateTextField = new JTextField();
		GridBagConstraints gbc_dateTextField = new GridBagConstraints();
		gbc_dateTextField.anchor = GridBagConstraints.WEST;
		gbc_dateTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dateTextField.gridx = 5;
		gbc_dateTextField.gridy = 2;
		dateTextField.setColumns(15);
		dateTextField.setEditable(false);
		add(dateTextField, gbc_dateTextField);
		
		lblDate = new JLabel("Dato:");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.WEST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridwidth = 2;
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 2;
		add(lblDate, gbc_lblDate);
	
		btnChooseDate = new JButton("Velg dato");
		btnChooseDate.addActionListener(new OpenChooseDate());
		GridBagConstraints gbc_btnChooseDate = new GridBagConstraints();
		gbc_btnChooseDate.anchor = GridBagConstraints.WEST;
		gbc_btnChooseDate.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseDate.gridx = 6;
		gbc_btnChooseDate.gridy = 2;
		add(btnChooseDate, gbc_btnChooseDate);
		
		btnChooseRoom = new JButton("+");
		btnChooseRoom.addActionListener(new chooseRoomAction());
		GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
		gbc_btnChooseRoom.anchor = GridBagConstraints.WEST;
		gbc_btnChooseRoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseRoom.gridx = 6;
		gbc_btnChooseRoom.gridy = 5;
		add(btnChooseRoom, gbc_btnChooseRoom);

		lblDeltagere = new JLabel("Deltagere:");
		GridBagConstraints gbc_lblDeltagere = new GridBagConstraints();
		gbc_lblDeltagere.anchor = GridBagConstraints.WEST;
		gbc_lblDeltagere.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeltagere.gridx = 3;
		gbc_lblDeltagere.gridy = 9;
		add(lblDeltagere, gbc_lblDeltagere);

		addPerson = new JButton("+");
		addPerson.setPreferredSize(new Dimension(41, 20));
		GridBagConstraints gbc_addPerson = new GridBagConstraints();
		gbc_addPerson.anchor = GridBagConstraints.EAST;
		gbc_addPerson.insets = new Insets(0, 0, 5, 5);
		gbc_addPerson.gridx = 3;
		gbc_addPerson.gridy = 10;
		add(addPerson, gbc_addPerson);

		removePerson = new JButton("-");
		removePerson.setPreferredSize(new Dimension(41, 20));
		GridBagConstraints gbc_removePerson = new GridBagConstraints();
		gbc_removePerson.anchor = GridBagConstraints.WEST;
		gbc_removePerson.insets = new Insets(0, 0, 5, 5);
		gbc_removePerson.gridx = 4;
		gbc_removePerson.gridy = 10;
		add(removePerson, gbc_removePerson);

		JScrollPane deltagerScrollPane = new JScrollPane(); 
		GridBagConstraints gbc_deltagerScrollPane = new GridBagConstraints();
		gbc_deltagerScrollPane.gridwidth = 2;
		gbc_deltagerScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_deltagerScrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_deltagerScrollPane.gridx = 3;
		gbc_deltagerScrollPane.gridy = 11;
		add(deltagerScrollPane, gbc_deltagerScrollPane);

		
		model = new DefaultListModel();
		participantList = new JList(model);
		deltagerScrollPane.setViewportView(participantList);
		myListSelectionModel = new DefaultListSelectionModel();
		myListSelectionModel.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		
		participantList.setSelectionModel(myListSelectionModel);
		
		lblKalender = new JLabel("Kalender:");
		GridBagConstraints gbc_lblKalender = new GridBagConstraints();
		gbc_lblKalender.anchor = GridBagConstraints.WEST;
		gbc_lblKalender.insets = new Insets(0, 0, 5, 5);
		gbc_lblKalender.gridwidth = 2;
		gbc_lblKalender.gridx = 3;
		gbc_lblKalender.gridy = 7;
		add(lblKalender, gbc_lblKalender);
		
		CalendarComboBox = new JComboBox();
		CalendarComboBox.setPreferredSize(new Dimension(50, 30));
		GridBagConstraints gbc_CalendarComboBox = new GridBagConstraints();
		gbc_CalendarComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_CalendarComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_CalendarComboBox.gridx = 5;
		gbc_CalendarComboBox.gridy = 7;
		add(CalendarComboBox, gbc_CalendarComboBox);
		
		btnLagre_1 = new JButton("Lagre");
		GridBagConstraints gbc_btnLagre_1 = new GridBagConstraints();
		gbc_btnLagre_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnLagre_1.ipady = 10;
		gbc_btnLagre_1.ipadx = 5;
		gbc_btnLagre_1.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnLagre_1.gridx = 5;
		gbc_btnLagre_1.gridy = 12;
//		btnLagre_1.addActionListener(new saveAction());
		add(btnLagre_1, gbc_btnLagre_1);
		
		btnSlett = new JButton("Slett");
		GridBagConstraints gbc_btnSlett = new GridBagConstraints();
		gbc_btnSlett.insets = new Insets(0, 0, 5, 5);
		gbc_btnSlett.ipady = 10;
		gbc_btnSlett.ipadx = 5;
		gbc_btnSlett.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSlett.gridx = 6;
		gbc_btnSlett.gridy = 12;
		add(btnSlett, gbc_btnSlett);	

	}

	private void setText(JTextField textField, String day) {
		textField.setText(day);
	}
	
	private void setFormatLabel(int posY) {
		lblFormat = new JLabel("(tt:mm)");
		GridBagConstraints gbc_lblFormat1 = new GridBagConstraints();
		gbc_lblFormat1.anchor = GridBagConstraints.WEST;
		gbc_lblFormat1.insets = new Insets(0, 0, 5, 5);
		gbc_lblFormat1.gridwidth = 2;
		gbc_lblFormat1.gridx = 4;
		gbc_lblFormat1.gridy = posY;
		add(lblFormat, gbc_lblFormat1);
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
				setText(textField, calendarDialog.getdayChosen());					
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
	
//	class saveAction implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			SavedMeetingPanel savedMeetingPanel = new SavedMeetingPanel();
//			JFrame savedMeetingFrame = new JFrame("Legg til brukere og/eller grupper");
//			savedMeetingFrame.getContentPane().add(savedMeetingPanel);
//			savedMeetingFrame.pack();
//			savedMeetingFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
//			savedMeetingFrame.setVisible(true);
//			savedMeetingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		}
//		
//	}
	
	public void saveBtnAddListener(ActionListener al) {
		btnLagre_1.addActionListener(al);
	}
	
	public void removePersonBtnAddListener(ActionListener al) {
		removePerson.addActionListener(al);
	}
	
	public void addPersonBtnAddListener(ActionListener al) {
		addPerson.addActionListener(al);
	}
			
	class OpenChooseDate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createCalenderDialog(dateTextField);
		}
	}		
	
	public JButton getSaveButton(){
		return btnLagre_1;
	}
	
	public JList getParticipantList() {
		return participantList;
	}
	
	public String getDateText(){
		return dateTextField.getText();
	}

	public void setParticipantList(JList participantList) {
		this.participantList = participantList;
	}

	public JButton getChooseRomButton() {
		return this.btnChooseRoom;
	}
	
	public JButton getRemovePersonBtn(){
		return this.removePerson;
	}
	
	public UserModel getSelectedParticipant(){
		int i = participantList.getAnchorSelectionIndex();
		return (UserModel) model.getElementAt(i);
	}
	
	public void deleteParticipant(){
		int i = participantList.getAnchorSelectionIndex();
		model.remove(i);
	}
	
	public JButton getAddParticipantButton(){
		return this.addPerson;
	}
	
	public void setDateText(String text){
		this.dateTextField.setText(text);
	}

}
