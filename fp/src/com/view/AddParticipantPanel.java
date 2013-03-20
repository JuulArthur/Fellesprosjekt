package com.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;

import com.controller.IServerResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddParticipantPanel extends JPanel {

	private JComboBox userComboBox;
	private JComboBox groupComboBox;
	private JButton btnAddGroup;
	private JButton btnAddUser;
	private JButton btnGoBack;
	
	public AddParticipantPanel () {
		/*
		 * One add button for username, one for a whole group
		 * JComboBox which will show drop-down of usernames,
		 * and one for groups.
		 * 
		 */	
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblLeggTilDeltagere = new JLabel("Legg til deltagere");
		GridBagConstraints gbc_lblLeggTilDeltagere = new GridBagConstraints();
		gbc_lblLeggTilDeltagere.gridwidth = 2;
		gbc_lblLeggTilDeltagere.insets = new Insets(5,5, 5, 5);
		gbc_lblLeggTilDeltagere.gridx = 0;
		gbc_lblLeggTilDeltagere.gridy = 0;
		add(lblLeggTilDeltagere, gbc_lblLeggTilDeltagere);
		
		btnGoBack = new JButton("tilbake");
		GridBagConstraints gbc_btnGoBack = new GridBagConstraints();
		gbc_btnGoBack.anchor = GridBagConstraints.EAST;
		gbc_btnGoBack.gridwidth = 2;
		gbc_btnGoBack.insets = new Insets(5, 5, 5, 0);
		gbc_btnGoBack.gridx = 3;
		gbc_btnGoBack.gridy = 0;
		add(btnGoBack, gbc_btnGoBack);
		
		JLabel lblBrukernavn = new JLabel("Brukernavn");
		GridBagConstraints gbc_lblBrukernavn = new GridBagConstraints();
		gbc_lblBrukernavn.insets = new Insets(5,5, 5, 5);
		gbc_lblBrukernavn.gridx = 0;
		gbc_lblBrukernavn.gridy = 2;
		add(lblBrukernavn, gbc_lblBrukernavn);
		
		userComboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5,5, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 3;
		add(userComboBox, gbc_comboBox);
		
		btnAddUser = new JButton("legg til");
		GridBagConstraints gbc_btnAddUser = new GridBagConstraints();
		gbc_btnAddUser.insets = new Insets(5,5, 5, 5);
		gbc_btnAddUser.gridx = 2;
		gbc_btnAddUser.gridy = 3;
		add(btnAddUser, gbc_btnAddUser);;
		
		JLabel lblGruppe = new JLabel("Gruppe");
		GridBagConstraints gbc_lblGruppe = new GridBagConstraints();
		gbc_lblGruppe.insets = new Insets(5,5, 5, 5);
		gbc_lblGruppe.gridx = 0;
		gbc_lblGruppe.gridy = 4;
		add(lblGruppe, gbc_lblGruppe);
		
		groupComboBox = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets =new Insets(5, 5, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 0;
		gbc_comboBox_1.gridy = 5;
		add(groupComboBox, gbc_comboBox_1);
		
		btnAddGroup = new JButton("legg til");
		GridBagConstraints gbc_btnaddGroup = new GridBagConstraints();
		gbc_btnaddGroup.insets = new Insets(5, 5, 5, 5);
		gbc_btnaddGroup.gridx = 2;
		gbc_btnaddGroup.gridy = 5;
		add(btnAddGroup, gbc_btnaddGroup);
		
	}

	public void addButtonUserAddListener(ActionListener al) {
		btnAddUser.addActionListener(al);
	}

	public void addButtonGroupAddListener(ActionListener al) {
		btnAddGroup.addActionListener(al);
	}

	public void addButtonBackAddListener (ActionListener al) {
		btnGoBack.addActionListener(al);
	}
	
	public JButton getBackButton() {
		return btnGoBack;
	}
	
	public JButton getBtnAddGroup() {
		return btnAddGroup;
	}

	public JButton getBtnAddUser() {
		return btnAddUser;
	}

	
	public JComboBox getUserComboBox() {
		return userComboBox;
	}

	public void setUserComboBox(ArrayList<Object> users) {
		for (Object user : users) {
			userComboBox.addItem(user);
		}
	}

	public JComboBox getGroupComboBox() {
		return groupComboBox;
	}

	public void setGroupComboBox(ArrayList<Object> groups) {
		for(Object group : groups) {
			groupComboBox.addItem(group);
		}
	}

}
	
