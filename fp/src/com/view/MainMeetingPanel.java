package com.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MainMeetingPanel extends JPanel {

	
	protected JTextField startTextField;
	protected JTextField sluttTextField;
	protected JTextField stedTextField;
	protected JTextField alarmTextField;
	private JLabel lblTittel;
	protected JTextField titteltextField;
	private JLabel lblAvtale;
	private JButton btnReturn;
	private JLabel lblBeskrivelse;

	private JFrame meetingFrame;
	protected JTextArea beskrivelseTextArea;
	
	public MainMeetingPanel () {
		setBorder(new EmptyBorder(10,10,10,10));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 2, 65, 72, 43, 90, 159, 0};
		gridBagLayout.rowHeights = new int[]{23, 33, 7, 20, 20, 20, 30, 35, 77, 32};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		setLayout(gridBagLayout);

		lblAvtale = new JLabel("Avtale/M\u00F8te");
		lblAvtale.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAvtale = new GridBagConstraints();
		gbc_lblAvtale.insets = new Insets(0, 0, 5, 5);
		gbc_lblAvtale.gridx = 3;
		gbc_lblAvtale.gridy = 0;
		add(lblAvtale, gbc_lblAvtale);

		btnReturn = new JButton("Tilbake");		
		btnReturn.addActionListener(new returnAction());
		
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.anchor = GridBagConstraints.WEST;
		gbc_btnReturn.insets = new Insets(0, 0, 5, 0);
		gbc_btnReturn.gridx = 6;
		gbc_btnReturn.gridy = 0;
		add(btnReturn, gbc_btnReturn);

		lblTittel = new JLabel("Tittel");
		GridBagConstraints gbc_lblTittel = new GridBagConstraints();
		gbc_lblTittel.anchor = GridBagConstraints.WEST;
		gbc_lblTittel.gridwidth = 2;
		gbc_lblTittel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTittel.gridx = 3;
		gbc_lblTittel.gridy = 1;
		add(lblTittel, gbc_lblTittel);

		titteltextField = new JTextField();
		GridBagConstraints gbc_titteltextField = new GridBagConstraints();
		gbc_titteltextField.anchor = GridBagConstraints.WEST;
		gbc_titteltextField.fill = GridBagConstraints.VERTICAL;
		gbc_titteltextField.insets = new Insets(0, 0, 5, 5);
		gbc_titteltextField.gridx = 5;
		gbc_titteltextField.gridy = 1;
		titteltextField.setColumns(15);
		add(titteltextField, gbc_titteltextField);

		JLabel lblStart = new JLabel("Start:");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.anchor = GridBagConstraints.WEST;
		gbc_lblStart.gridwidth = 2;
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 3;
		gbc_lblStart.gridy = 2;
		add(lblStart, gbc_lblStart);

		startTextField = new JTextField();
		GridBagConstraints gbc_startTextField = new GridBagConstraints();
		gbc_startTextField.anchor = GridBagConstraints.WEST;
		gbc_startTextField.fill = GridBagConstraints.VERTICAL;
		gbc_startTextField.insets = new Insets(0, 0, 5, 5);
		gbc_startTextField.gridx = 5;
		gbc_startTextField.gridy = 2;
		startTextField.setColumns(15);
		startTextField.setEditable(false);
		add(startTextField, gbc_startTextField);

		JLabel lblSlutt = new JLabel("Slutt:");
		GridBagConstraints gbc_lblSlutt = new GridBagConstraints();
		gbc_lblSlutt.anchor = GridBagConstraints.WEST;
		gbc_lblSlutt.gridwidth = 2;
		gbc_lblSlutt.insets = new Insets(0, 0, 5, 5);
		gbc_lblSlutt.gridx = 3;
		gbc_lblSlutt.gridy = 3;
		add(lblSlutt, gbc_lblSlutt);

		sluttTextField = new JTextField();
		GridBagConstraints gbc_sluttTextField = new GridBagConstraints();
		gbc_sluttTextField.anchor = GridBagConstraints.WEST;
		gbc_sluttTextField.insets = new Insets(0, 0, 5, 5);
		gbc_sluttTextField.gridx = 5;
		gbc_sluttTextField.gridy = 3;
		sluttTextField.setColumns(15);
		sluttTextField.setEditable(false);
		add(sluttTextField, gbc_sluttTextField);

		JLabel lblSted = new JLabel("Sted:");
		GridBagConstraints gbc_lblSted = new GridBagConstraints();
		gbc_lblSted.anchor = GridBagConstraints.WEST;
		gbc_lblSted.gridwidth = 2;
		gbc_lblSted.insets = new Insets(0, 0, 5, 5);
		gbc_lblSted.gridx = 3;
		gbc_lblSted.gridy = 4;
		add(lblSted, gbc_lblSted);

		stedTextField = new JTextField();
		GridBagConstraints gbc_stedTextField = new GridBagConstraints();
		gbc_stedTextField.anchor = GridBagConstraints.WEST;
		gbc_stedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_stedTextField.gridx = 5;
		gbc_stedTextField.gridy = 4;
		add(stedTextField, gbc_stedTextField);
		stedTextField.setColumns(15);

		JLabel lblAlarm = new JLabel("Alarm:");
		GridBagConstraints gbc_lblAlarm = new GridBagConstraints();
		gbc_lblAlarm.anchor = GridBagConstraints.WEST;
		gbc_lblAlarm.gridwidth = 2;
		gbc_lblAlarm.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlarm.gridx = 3;
		gbc_lblAlarm.gridy = 5;
		add(lblAlarm, gbc_lblAlarm);

		alarmTextField = new JTextField();
		GridBagConstraints gbc_alarmTextField = new GridBagConstraints();
		gbc_alarmTextField.anchor = GridBagConstraints.WEST;
		gbc_alarmTextField.insets = new Insets(0, 0, 5, 5);
		gbc_alarmTextField.gridx = 5;
		gbc_alarmTextField.gridy = 5;
		add(alarmTextField, gbc_alarmTextField);
		alarmTextField.setColumns(15);
		alarmTextField.setEditable(false);
		
		lblBeskrivelse = new JLabel("Beskrivelse: ");
		GridBagConstraints gbc_lblBeskrivelse = new GridBagConstraints();
		gbc_lblBeskrivelse.anchor = GridBagConstraints.WEST;
		gbc_lblBeskrivelse.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeskrivelse.gridx = 3;
		gbc_lblBeskrivelse.gridy = 6;
		add(lblBeskrivelse, gbc_lblBeskrivelse);

		beskrivelseTextArea = new JTextArea(5, 10);
		beskrivelseTextArea.setSize(new Dimension(40, 50));
		GridBagConstraints gbc_beskrivelseTextArea = new GridBagConstraints();
		gbc_beskrivelseTextArea.insets = new Insets(0, 0, 5, 5);
		gbc_beskrivelseTextArea.fill = GridBagConstraints.BOTH;
		gbc_beskrivelseTextArea.gridx = 5;
		gbc_beskrivelseTextArea.gridy = 6;
		add(beskrivelseTextArea, gbc_beskrivelseTextArea);

	}
		
	class returnAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			meetingFrame.dispose();
		}
	}
	public static void main(String args[]){
		JFrame frame = new JFrame();
		MainMeetingPanel panel = new MainMeetingPanel();
	}
	
}
