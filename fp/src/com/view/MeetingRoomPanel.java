package com.view;


import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class MeetingRoomPanel extends JDialog {

	private JFrame meetingRoomFrame;
	private JTextField textField;
	private JTextField textField_1;
	private JList list;
	private JButton btnChooseRoom;
	private JButton btnSk; 
	private JSpinner spinner;

	public MeetingRoomPanel () {
		/*
		 * JComboBox will show a drop down of numbers ranging from ? to ?
		 * JList, will add MeetingRooms there
		 * BtnChooseRoom will only activate if a JList object is targeted
		 * 
		 */
			
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 62, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMterom = new JLabel("M\u00F8terom");
		lblMterom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMterom = new GridBagConstraints();
		gbc_lblMterom.insets = new Insets(0, 0, 5, 5);
		gbc_lblMterom.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMterom.gridx = 0;
		gbc_lblMterom.gridy = 0;
		add(lblMterom, gbc_lblMterom);
		
		JButton btnReturn = new JButton("tilbake");
		btnReturn.addActionListener(new returnAction());
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.insets = new Insets(0, 0, 5, 0);
		gbc_btnReturn.gridx = 3;
		gbc_btnReturn.gridy = 0;
		add(btnReturn, gbc_btnReturn);
		
		JLabel lblAntallPersoner = new JLabel("Antall personer");
		GridBagConstraints gbc_lblAntallPersoner = new GridBagConstraints();
		gbc_lblAntallPersoner.insets = new Insets(0, 0, 5, 5);
		gbc_lblAntallPersoner.gridx = 0;
		gbc_lblAntallPersoner.gridy = 1;
		add(lblAntallPersoner, gbc_lblAntallPersoner);
		
		spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 1;
		add(spinner, gbc_spinner);
		
		btnSk = new JButton("s\u00F8k");
		GridBagConstraints gbc_btnSk = new GridBagConstraints();
		gbc_btnSk.anchor = GridBagConstraints.WEST;
		gbc_btnSk.insets = new Insets(0, 0, 5, 0);
		gbc_btnSk.gridx = 3;
		gbc_btnSk.gridy = 1;
		add(btnSk, gbc_btnSk);
		
		JLabel lblStart = new JLabel("Start");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.anchor = GridBagConstraints.WEST;
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 0;
		gbc_lblStart.gridy = 2;
		add(lblStart, gbc_lblStart);
		
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(5);
		textField.setEditable(false);
		
		JLabel lblNewLabel_1 = new JLabel("(tt:mm)");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 2;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("Slutt");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(5);
		textField_1.setEditable(false);
		
		JLabel lblNewLabel_2 = new JLabel("(tt:mm)");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 3;
		gbc_lblNewLabel_2.gridy = 3;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblLedigeRom = new JLabel("Ledige rom");
		GridBagConstraints gbc_lblLedigeRom = new GridBagConstraints();
		gbc_lblLedigeRom.anchor = GridBagConstraints.WEST;
		gbc_lblLedigeRom.insets = new Insets(0, 0, 5, 5);
		gbc_lblLedigeRom.gridx = 0;
		gbc_lblLedigeRom.gridy = 4;
		add(lblLedigeRom, gbc_lblLedigeRom);
		
		list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 5;
		add(list, gbc_list);
		
		btnChooseRoom = new JButton("Velg rom");
		GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
		gbc_btnChooseRoom.anchor = GridBagConstraints.SOUTH;
		gbc_btnChooseRoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseRoom.gridx = 2;
		gbc_btnChooseRoom.gridy = 5;
		add(btnChooseRoom, gbc_btnChooseRoom);
		
		this.pack();
		
	}
	
	class returnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			meetingRoomFrame.dispose();
		}
	}
	public void setStartText(String textField) {
		this.textField.setText(textField);
	}
	
	public void setEndText(String textField_1) {
		this.textField_1.setText(textField_1);
	}
	
	public void setRoomList(JList list){
		this.list = list;
	}
	
	public JList getRoomList(){
		return list;
	}
	
	public JButton getChooseRoom() {
		return btnChooseRoom;
	}
	public JButton getSearch() {
		return btnSk;
	}
	public int getCapacity(){
		return (Integer)spinner.getValue();
	}
	
	
	
	public void setFrame (JFrame frame) {
		this.meetingRoomFrame = frame;
	}

}
