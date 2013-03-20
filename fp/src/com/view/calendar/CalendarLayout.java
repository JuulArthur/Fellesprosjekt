package com.view.calendar;

import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import com.model.*;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;



public class CalendarLayout extends JPanel {
	private JTextField textFieldManageCalendar;
	private CalendarModel model = new CalendarModel();
	
	private JTable table;
	private String[] time = {"07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00"};
	private JFrame calendarFrame;
	private JButton btnLoggUt;
	private JButton btnManageCalendar;
	private JButton btnShowOtherCalendars;
	private JButton btnLeggTilAvtale;
	private JButton buttonLastWeek;
	private JButton buttonNextWeek;
	private JList listNotification;
	private JList listCalendar;
	private JList listOtherCalendars;
	JLabel labelGivenWeek;
	
	public CalendarLayout() {
		setBorder(new EmptyBorder(0,15,0,15));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{202, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnLeggTilAvtale = new JButton("Legg til avtale");
		GridBagConstraints gbc_btnLeggTilAvtale = new GridBagConstraints();
		gbc_btnLeggTilAvtale.anchor = GridBagConstraints.WEST;
		gbc_btnLeggTilAvtale.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeggTilAvtale.gridx = 0;
		gbc_btnLeggTilAvtale.gridy = 1;
		add(btnLeggTilAvtale, gbc_btnLeggTilAvtale);
		
		
		buttonLastWeek = new JButton("<");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.anchor = GridBagConstraints.EAST;
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 1;
		add(buttonLastWeek, gbc_button_2);
		
		
		labelGivenWeek = new JLabel("Uke ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 1;
		add(labelGivenWeek, gbc_lblNewLabel_3);
		
		buttonNextWeek = new JButton(">");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.anchor = GridBagConstraints.WEST;
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 5;
		gbc_button_1.gridy = 1;
		add(buttonNextWeek, gbc_button_1);
		
		btnLoggUt = new JButton("logg ut");
		GridBagConstraints gbc_btnLoggUt = new GridBagConstraints();
		gbc_btnLoggUt.anchor = GridBagConstraints.WEST;
		gbc_btnLoggUt.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoggUt.gridx = 9;
		gbc_btnLoggUt.gridy = 1;
		add(btnLoggUt, gbc_btnLoggUt);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 11;
		gbc_label.gridy = 2;
		add(label, gbc_label);
		
		JLabel lblNotification = new JLabel("Notifikasjon");
		GridBagConstraints gbc_lblNotification = new GridBagConstraints();
		gbc_lblNotification.anchor = GridBagConstraints.WEST;
		gbc_lblNotification.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotification.gridx = 0;
		gbc_lblNotification.gridy = 3;
		add(lblNotification, gbc_lblNotification);
		
		JLabel lblMandag = new JLabel("mandag");
		GridBagConstraints gbc_lblMandag = new GridBagConstraints();
		gbc_lblMandag.anchor = GridBagConstraints.WEST;
		gbc_lblMandag.insets = new Insets(0, 0, 5, 5);
		gbc_lblMandag.gridx = 3;
		gbc_lblMandag.gridy = 3;
		add(lblMandag, gbc_lblMandag);
		
		JLabel lblTirsdag = new JLabel("tirsdag");
		GridBagConstraints gbc_lblTirsdag = new GridBagConstraints();
		gbc_lblTirsdag.anchor = GridBagConstraints.WEST;
		gbc_lblTirsdag.insets = new Insets(0, 0, 5, 5);
		gbc_lblTirsdag.gridx = 4;
		gbc_lblTirsdag.gridy = 3;
		add(lblTirsdag, gbc_lblTirsdag);
		
		JLabel lblOnsdag = new JLabel("onsdag");
		GridBagConstraints gbc_lblOnsdag = new GridBagConstraints();
		gbc_lblOnsdag.insets = new Insets(0, 0, 5, 5);
		gbc_lblOnsdag.gridx = 5;
		gbc_lblOnsdag.gridy = 3;
		add(lblOnsdag, gbc_lblOnsdag);
		
		JLabel lblTorsdag = new JLabel("torsdag");
		GridBagConstraints gbc_lblTorsdag = new GridBagConstraints();
		gbc_lblTorsdag.anchor = GridBagConstraints.WEST;
		gbc_lblTorsdag.insets = new Insets(0, 0, 5, 5);
		gbc_lblTorsdag.gridx = 6;
		gbc_lblTorsdag.gridy = 3;
		add(lblTorsdag, gbc_lblTorsdag);
		
		JLabel lblFredag = new JLabel("fredag");
		GridBagConstraints gbc_lblFredag = new GridBagConstraints();
		gbc_lblFredag.anchor = GridBagConstraints.WEST;
		gbc_lblFredag.insets = new Insets(0, 0, 5, 5);
		gbc_lblFredag.gridx = 7;
		gbc_lblFredag.gridy = 3;
		add(lblFredag, gbc_lblFredag);
		
		JLabel lblLrdag = new JLabel("l\u00F8rdag");
		GridBagConstraints gbc_lblLrdag = new GridBagConstraints();
		gbc_lblLrdag.insets = new Insets(0, 0, 5, 5);
		gbc_lblLrdag.gridx = 8;
		gbc_lblLrdag.gridy = 3;
		add(lblLrdag, gbc_lblLrdag);
		
		JLabel lblSndag = new JLabel("s\u00F8ndag");
		GridBagConstraints gbc_lblSndag = new GridBagConstraints();
		gbc_lblSndag.insets = new Insets(0, 0, 5, 5);
		gbc_lblSndag.gridx = 9;
		gbc_lblSndag.gridy = 3;
		add(lblSndag, gbc_lblSndag);
		
		listNotification = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 4;
		add(listNotification, gbc_list);
		
		JList listTime = new JList(time);
		listTime.enable(false);
		GridBagConstraints gbc_list_2 = new GridBagConstraints();
		gbc_list_2.gridheight = 8;
		gbc_list_2.insets = new Insets(0, 0, 5, 5);
		gbc_list_2.fill = GridBagConstraints.BOTH;
		gbc_list_2.gridx = 2;
		gbc_list_2.gridy = 4;
		add(listTime, gbc_list_2);
	
		
		table = new JTable(model);
		table.setRowSelectionAllowed(false);
		table.setRowHeight(17);
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridheight = 8;
		gbc_table.gridwidth = 7;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 3;
		gbc_table.gridy = 4;
		add(table, gbc_table);
		
		JLabel lblMyCalendars = new JLabel("Mine Kalendere");
		GridBagConstraints gbc_lblMyCalendars = new GridBagConstraints();
		gbc_lblMyCalendars.anchor = GridBagConstraints.WEST;
		gbc_lblMyCalendars.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyCalendars.gridx = 0;
		gbc_lblMyCalendars.gridy = 5;
		add(lblMyCalendars, gbc_lblMyCalendars);
	
		textFieldManageCalendar = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 6;
		add(textFieldManageCalendar, gbc_textField);
		textFieldManageCalendar.setColumns(10);
		
		btnManageCalendar = new JButton("+");	
		GridBagConstraints gbc_btnManageCalendar = new GridBagConstraints();
		gbc_btnManageCalendar.anchor = GridBagConstraints.WEST;
		gbc_btnManageCalendar.insets = new Insets(0, 0, 5, 5);
		btnManageCalendar.setPreferredSize(new Dimension(22, 20));
		gbc_btnManageCalendar.gridx = 1;
		gbc_btnManageCalendar.gridy = 6;
		add(btnManageCalendar, gbc_btnManageCalendar);
		
		listCalendar = new JList();
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 0;
		gbc_list_1.gridy = 7;
		add(listCalendar, gbc_list_1);
		
		JLabel lblNewLabel_2 = new JLabel("Andre kalender");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 8;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		btnShowOtherCalendars = new JButton("Vis andre kalendere");		
		GridBagConstraints gbc_btnShowOtherCalendars = new GridBagConstraints();
		gbc_btnShowOtherCalendars.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowOtherCalendars.gridx = 0;
		gbc_btnShowOtherCalendars.gridy = 9;
		add(btnShowOtherCalendars, gbc_btnShowOtherCalendars);
		
		listOtherCalendars = new JList();
		GridBagConstraints gbc_list_3 = new GridBagConstraints();
		gbc_list_3.insets = new Insets(0, 0, 5, 5);
		gbc_list_3.fill = GridBagConstraints.BOTH;
		gbc_list_3.gridx = 0;
		gbc_list_3.gridy = 11;
		add(listOtherCalendars, gbc_list_3);
		
	}
	
	public static void main(String[] args) {
		CalendarLayout calendarlayout = new CalendarLayout();
		JFrame frame = new JFrame("Kalender");
		frame.getContentPane().add(calendarlayout);
		frame.pack();
		frame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	public void addButtonManageCalendarAddListener(ActionListener al) {
		btnManageCalendar.addActionListener(al);
	}
	
	public void addButtonShowOtherCalendarsAddListener(ActionListener al) {
		btnShowOtherCalendars.addActionListener(al);
	}
	
	public void addButtonMeetingAddListener(ActionListener al){
		btnLeggTilAvtale.addActionListener(al);
	}
	
	public void addButtonLogoutAddListener(ActionListener al){
		btnLoggUt.addActionListener(al);
	}

	public JButton getBtnShowOtherCalendars() {
		return btnShowOtherCalendars;
	}

	public void setBtnShowOtherCalendars(JButton btnShowOtherCalendars) {
		this.btnShowOtherCalendars = btnShowOtherCalendars;
	}

	public JButton getbtnManageCalendar() {
		return btnManageCalendar;
	}

	public void setbtnManageCalendar(JButton btnManageCalendar) {
		this.btnManageCalendar = btnManageCalendar;
	}

	public JButton getBtnLeggTilAvtale() {
		return btnLeggTilAvtale;
	}

	public void setBtnLeggTilAvtale(JButton btnLeggTilAvtale) {
		this.btnLeggTilAvtale = btnLeggTilAvtale;
	}
	
	public JButton getBtnLoggUt() {
		return btnLoggUt;
	}
	
	public JTextField getTextFieldManageCalendar() {
		return textFieldManageCalendar;
	}

	public void setTextFieldManageCalendar(JTextField textFieldManageCalendar) {
		this.textFieldManageCalendar = textFieldManageCalendar;
	}

	public void setBtnLoggUt(JButton btnLoggUt) {
		this.btnLoggUt = btnLoggUt;
	}

	public JList getListNotification() {
		return listNotification;
	}

	public void setListNotification(JList listNotification) {
		this.listNotification = listNotification;
	}

	public JList getListCalendar() {
		return listCalendar;
	}

	public void setListCalendar(JList listCalendar) {
		this.listCalendar = listCalendar;
	}

	public JList getListOtherCalendars() {
		return listOtherCalendars;
	}

	public void setListOtherCalendars(JList listOtherCalendars) {
		this.listOtherCalendars = listOtherCalendars;
	}

	public JButton getButtonLastWeek() {
		return buttonLastWeek;
	}

	public JButton getButtonNextWeek() {
		return buttonNextWeek;
	}

	public JLabel getLabelGivenWeek() {
		return labelGivenWeek;
	}
	
	
	
	
}
