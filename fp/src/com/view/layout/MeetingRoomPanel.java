package com.view.layout;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JSpinner;

public class MeetingRoomPanel extends JPanel {

	private JFrame meetingRoomFrame;

	public MeetingRoomPanel () {
		/*
		 * JComboBox will show a drop down of numbers ranging from ? to ?
		 * JList, will add MeetingRooms there
		 * BtnChooseRoom will only activate if a JList object is targeted
		 * 
		 */
			
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 62, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_btnReturn.gridx = 4;
		gbc_btnReturn.gridy = 0;
		add(btnReturn, gbc_btnReturn);
		
		JLabel lblAntallPersoner = new JLabel("Antall personer");
		GridBagConstraints gbc_lblAntallPersoner = new GridBagConstraints();
		gbc_lblAntallPersoner.insets = new Insets(0, 0, 5, 5);
		gbc_lblAntallPersoner.gridx = 0;
		gbc_lblAntallPersoner.gridy = 1;
		add(lblAntallPersoner, gbc_lblAntallPersoner);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 1;
		add(spinner, gbc_spinner);
		
		JLabel lblLedigeRom = new JLabel("Ledige rom");
		GridBagConstraints gbc_lblLedigeRom = new GridBagConstraints();
		gbc_lblLedigeRom.anchor = GridBagConstraints.WEST;
		gbc_lblLedigeRom.insets = new Insets(0, 0, 5, 5);
		gbc_lblLedigeRom.gridx = 0;
		gbc_lblLedigeRom.gridy = 2;
		add(lblLedigeRom, gbc_lblLedigeRom);
		
		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 3;
		add(list, gbc_list);
		
		JButton btnChooseRoom = new JButton("Velg rom");
		GridBagConstraints gbc_btnChooseRoom = new GridBagConstraints();
		gbc_btnChooseRoom.anchor = GridBagConstraints.SOUTH;
		gbc_btnChooseRoom.insets = new Insets(0, 0, 5, 5);
		gbc_btnChooseRoom.gridx = 2;
		gbc_btnChooseRoom.gridy = 3;
		add(btnChooseRoom, gbc_btnChooseRoom);
		
	}
	
	class returnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			meetingRoomFrame.dispose();
		}
	}
	
	public void setFrame (JFrame frame) {
		this.meetingRoomFrame = frame;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
