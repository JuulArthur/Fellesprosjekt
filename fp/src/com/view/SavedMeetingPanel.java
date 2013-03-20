package com.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.controller.IServerResponse;

public class SavedMeetingPanel extends MainMeetingPanel {

	private JButton btnSendNotice;
	private JButton btnEdit;
	private JLabel lblKommer;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JLabel lblKommerIkke;
	private JList Comming;
	private JList NotComming;
	private JFrame savedMeeting;
	private JLabel lblNewLabel;
	private ButtonGroup buttongrp;
	private JTextField dateTextField;
	private JLabel lblDate;
	private JButton btnAccept;
	private JButton btnDecline ;
	private DefaultListModel notCommingList;
	private DefaultListModel commingList;
	private JButton leggTilKalender;
	
	
	public SavedMeetingPanel () {
		notCommingList=new DefaultListModel();
		commingList =new DefaultListModel();
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 52, 0, 0, 0, 0, 0, 75, 0, 0, 0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 60, 0, 0, 0};
		
		titteltextField.setEditable(false);
		stedTextField.setEditable(false);
		beskrivelseTextArea.setEditable(false);
		
		buttongrp = new ButtonGroup();
		
		startTextField.setEditable(false);
		sluttTextField.setEditable(false);
		alarmTextField.setEditable(false);
		
		
		lblDate = new JLabel("Dato:");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.WEST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 2;
		add(lblDate, gbc_lblDate);
		
		dateTextField = new JTextField();
		GridBagConstraints gbc_dateTextField = new GridBagConstraints();
		gbc_dateTextField.anchor = GridBagConstraints.WEST;
		gbc_dateTextField.insets = new Insets(0, 0, 5, 5);
		gbc_dateTextField.gridx = 5;
		gbc_dateTextField.gridy = 2;
		dateTextField.setColumns(15);
		dateTextField.setEditable(false);
		add(dateTextField, gbc_dateTextField);
	
		btnAccept = new JButton("Godta");
		buttongrp.add(btnAccept);
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.EAST;
		gbc_btnAccept.insets = new Insets(0, 0, 5, 5);
		gbc_btnAccept.gridx = 7;
		gbc_btnAccept.gridy = 2;
		add(btnAccept, gbc_btnAccept);
		
		btnDecline = new JButton("Avsl\u00E5");
		buttongrp.add(btnDecline);
		GridBagConstraints gbc_btnDecline = new GridBagConstraints();
		gbc_btnDecline.anchor = GridBagConstraints.WEST;
		gbc_btnDecline.insets = new Insets(0, 0, 5, 5);
		gbc_btnDecline.gridx = 8;
		gbc_btnDecline.gridy = 2;
		
		add(btnDecline, gbc_btnDecline);
		
		leggTilKalender = new JButton("Legg til i valgte Kalender");
		GridBagConstraints dick = new GridBagConstraints();
		dick.gridx=5;
		dick.gridy=12;
		add(leggTilKalender, dick);
		
		
		lblKommer = new JLabel("Kommer");
		GridBagConstraints gbc_lblKommer = new GridBagConstraints();
		gbc_lblKommer.anchor = GridBagConstraints.WEST;
		gbc_lblKommer.insets = new Insets(0, 0, 5, 5);
		gbc_lblKommer.gridx = 7;
		gbc_lblKommer.gridy = 3;
		add(lblKommer, gbc_lblKommer);
		
		lblKommerIkke = new JLabel("Kommer ikke");
		GridBagConstraints gbc_lblKommerIkke = new GridBagConstraints();
		gbc_lblKommerIkke.anchor = GridBagConstraints.WEST;
		gbc_lblKommerIkke.insets = new Insets(0, 0, 5, 5);
		gbc_lblKommerIkke.gridx = 7;
		gbc_lblKommerIkke.gridy = 7;
		add(lblKommerIkke, gbc_lblKommerIkke);
		
	
		scrollPane2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane2 = new GridBagConstraints();
		gbc_scrollPane2.gridheight = 2;
		gbc_scrollPane2.gridwidth = 2;
		gbc_scrollPane2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane2.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane2.gridx = 7;
		gbc_scrollPane2.gridy = 10;
		add(scrollPane2, gbc_scrollPane2);
		
		NotComming = new JList(notCommingList);
		scrollPane2.setViewportView(NotComming);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 7;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		commingList= new DefaultListModel();
		Comming = new JList(commingList);
		scrollPane.setViewportView(Comming);
		
		lblNewLabel = new JLabel("Legge avtale til:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 7;
		add(lblNewLabel, gbc_lblNewLabel);
		JComboBox calenderList = new JComboBox();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 1;
		c.ipadx =100;
		c.gridx=5;
		c.gridy=7;
		add(calenderList,c);
		
		
		btnSendNotice = new JButton("Send ut m\u00F8teinnkalling");
		btnSendNotice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
				
				
				GridBagConstraints gbc_btnSendNotice = new GridBagConstraints();
				gbc_btnSendNotice.insets = new Insets(0, 0, 5, 5);
				gbc_btnSendNotice.ipady = 10;
				gbc_btnSendNotice.ipadx = 5;
				gbc_btnSendNotice.anchor = GridBagConstraints.SOUTHEAST;
				gbc_btnSendNotice.gridx = 5;
				gbc_btnSendNotice.gridy = 13;
				add(btnSendNotice, gbc_btnSendNotice);
		
		btnEdit = new JButton("Rediger");
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEdit.anchor = GridBagConstraints.NORTH;
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.ipady = 10;
		gbc_btnEdit.ipadx = 5;
		gbc_btnEdit.gridx = 5;
		gbc_btnEdit.gridy = 14;
		btnEdit.addActionListener(new editMeetingPanel());
		
		
		add(btnEdit, gbc_btnEdit);

	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Avtale");
		frame.getContentPane().add(new SavedMeetingPanel());
		frame.pack();
		frame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	class editMeetingPanel implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			MeetingPanel meetingPanel = new MeetingPanel();
			JFrame meetingFrame = new JFrame();
			meetingFrame.getContentPane().add(meetingPanel);
			meetingFrame.pack();
			meetingFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			meetingFrame.setVisible(true);
			meetingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);		
		}
	}
	
	public DefaultListModel getComming(){
		return this.commingList;
	}

	public JButton getGodta(){
		return this.btnAccept;
	}
	
	public JButton getRediger(){
		return this.btnEdit;
	}

	public JButton getAvslag(){
		return this.btnDecline;
	}
	
	public JButton getMooteinnkalling(){
		return this.btnSendNotice;
	}
	
	public DefaultListModel getNotComming(){
		return this.notCommingList;
	}
	public JButton getAddcalendar(){
		return this.leggTilKalender;
	}
}
