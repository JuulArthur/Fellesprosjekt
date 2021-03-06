package com.view;

import javax.swing.JDialog;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;

import com.model.CalendarModel;

public class AddOtherCalendarsJDialog extends JDialog {
	private JComboBox comboBoxUserNames;
	private JComboBox comboBoxCalendars;
	private JButton btnAvbryt;
	private JButton btnNewButton;
	public AddOtherCalendarsJDialog() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{149, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblBrukernavn = new JLabel("Brukernavn:");
		GridBagConstraints gbc_lblBrukernavn = new GridBagConstraints();
		gbc_lblBrukernavn.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrukernavn.gridx = 0;
		gbc_lblBrukernavn.gridy = 0;
		getContentPane().add(lblBrukernavn, gbc_lblBrukernavn);
		
		comboBoxUserNames = new JComboBox();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		getContentPane().add(comboBoxUserNames, gbc_textField);
		comboBoxUserNames.setEditable(true);//textField.setColumns(10);
		
		JLabel lblKalender = new JLabel("Kalender:");
		GridBagConstraints gbc_lblKalender = new GridBagConstraints();
		gbc_lblKalender.insets = new Insets(0, 0, 5, 5);
		gbc_lblKalender.gridx = 0;
		gbc_lblKalender.gridy = 1;
		getContentPane().add(lblKalender, gbc_lblKalender);
		
		comboBoxCalendars = new JComboBox();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		getContentPane().add(comboBoxCalendars, gbc_textField_1);
		comboBoxCalendars.setEditable(true);
		
		btnAvbryt = new JButton("Avbryt");
		GridBagConstraints gbc_btnAvbryt = new GridBagConstraints();
		gbc_btnAvbryt.insets = new Insets(0, 0, 0, 5);
		gbc_btnAvbryt.gridx = 0;
		gbc_btnAvbryt.gridy = 4;
		getContentPane().add(btnAvbryt, gbc_btnAvbryt);
		
		btnNewButton = new JButton("Lagre");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 4;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		setVisible(true);
		this.pack();
	}

	public static void main (String args[]) {
        AddOtherCalendarsJDialog other= new AddOtherCalendarsJDialog();
        other.setLocationRelativeTo(null);
        other.setDefaultCloseOperation(other.DISPOSE_ON_CLOSE);
    }

	public JComboBox getComboBoxUserNames() {
		return comboBoxUserNames;
	}

	public JComboBox getComboBoxCalendars() {
		return comboBoxCalendars;
	}

	public JButton getBtnAvbryt() {
		return btnAvbryt;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}
}
