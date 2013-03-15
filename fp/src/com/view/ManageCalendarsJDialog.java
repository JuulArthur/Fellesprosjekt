package com.view;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ManageCalendarsJDialog extends JDialog {
	public ManageCalendarsJDialog() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblChoosecolor = new JLabel("Velg farge p\u00E5 kalender:");
		GridBagConstraints gbc_lblChoosecolor = new GridBagConstraints();
		gbc_lblChoosecolor.anchor = GridBagConstraints.WEST;
		gbc_lblChoosecolor.gridwidth = 2;
		gbc_lblChoosecolor.insets = new Insets(5, 5, 5, 0);
		gbc_lblChoosecolor.gridx = 0;
		gbc_lblChoosecolor.gridy = 0;
		getContentPane().add(lblChoosecolor, gbc_lblChoosecolor);
		
		JComboBox ColorsComboBox = new JComboBox();
		GridBagConstraints gbc_ColorsComboBox = new GridBagConstraints();
		gbc_ColorsComboBox.insets = new Insets(0, 5, 5, 5);
		gbc_ColorsComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_ColorsComboBox.gridx = 0;
		gbc_ColorsComboBox.gridy = 1;
		getContentPane().add(ColorsComboBox, gbc_ColorsComboBox);
		
		JCheckBox VisibleCheckBox = new JCheckBox("Gj\u00F8r kalender synlig");
		GridBagConstraints gbc_VisibleCheckBox = new GridBagConstraints();
		gbc_VisibleCheckBox.insets = new Insets(0, 5, 5, 5);
		gbc_VisibleCheckBox.gridx = 0;
		gbc_VisibleCheckBox.gridy = 2;
		getContentPane().add(VisibleCheckBox, gbc_VisibleCheckBox);
		
		JButton btnAvbryt = new JButton("Avbryt");
		GridBagConstraints gbc_btnAvbryt = new GridBagConstraints();
		gbc_btnAvbryt.insets = new Insets(10, 0, 5, 5);
		gbc_btnAvbryt.gridx = 0;
		gbc_btnAvbryt.gridy = 3;
		getContentPane().add(btnAvbryt, gbc_btnAvbryt);
		
		JButton btnLagre = new JButton("Lagre");
		GridBagConstraints gbc_btnLagre = new GridBagConstraints();
		gbc_btnLagre.insets = new Insets(10, 0, 5, 0);
		gbc_btnLagre.gridx = 1;
		gbc_btnLagre.gridy = 3;
		getContentPane().add(btnLagre, gbc_btnLagre);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
