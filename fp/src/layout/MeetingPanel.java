package layout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import layout.CalendarJDialog.CalendarModel;
import layout.CalendarJDialog.MouseclickedClass;

public class MeetingPanel extends JPanel {
	private JTextField startTextField;
	private JTextField sluttTextField;
	private JTextField stedTextField;
	private JTextField alarmTextField;
	private JLabel lblTittel;
	private JTextField titteltextField;
	private JLabel lblAvtale;
	private JButton btnReturn;
	private JLabel lblKalender;
	private JComboBox kalenderComboBox;
	private JLabel lblBeskrivelse;
	private JScrollPane beskrivelseScrollPane;
	private JScrollPane deltagerScrollPane;

	private JTextArea beskrivelseTextArea;
	private JTextArea deltagerTextArea;
	private JLabel lblDeltagere;
	private JButton btnLagre;
	private JButton btnSlett;
	private JButton btnLagre_1;
	private JList list;
	private JButton addPerson;
	private JButton removePerson;
	private AddParticipantPanel addParticipant;
	private JButton btnopenStartCalendar;
	private JButton btnopenStopCalendar;
	
	private JFrame meetingFrame;

	public MeetingPanel() {

		/* 
		 * Mangler noe funksjonalitet:
		 * TODO: 
		 * - Kalender for alarm
		 * - Knapp for sted som fører til MeetingRoomPanel 
		 */
		setBorder(new EmptyBorder(10,10,10,10));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 2, 65, 72, 43, 90, 159, 0};
		gridBagLayout.rowHeights = new int[]{23, 33, 7, 20, 20, 20, 30, 35, 77, 32};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
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

		btnopenStartCalendar = new JButton("\u00C5pne kalender");
		btnopenStartCalendar.addActionListener(new openStartCalendar());

		GridBagConstraints gbc_btnopenStartCalendar = new GridBagConstraints();
		gbc_btnopenStartCalendar.insets = new Insets(0, 0, 5, 0);
		gbc_btnopenStartCalendar.gridx = 6;
		gbc_btnopenStartCalendar.gridy = 2;
		add(btnopenStartCalendar, gbc_btnopenStartCalendar);

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

		btnopenStopCalendar = new JButton("\u00C5pne kalender");
		btnopenStopCalendar.addActionListener(new openStopCalendar());

		GridBagConstraints gbc_btnopenStopCalendar = new GridBagConstraints();
		gbc_btnopenStopCalendar.insets = new Insets(0, 0, 5, 0);
		gbc_btnopenStopCalendar.gridx = 6;
		gbc_btnopenStopCalendar.gridy = 3;
		add(btnopenStopCalendar, gbc_btnopenStopCalendar);

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

		lblKalender = new JLabel("Kalender:");
		GridBagConstraints gbc_lblKalender = new GridBagConstraints();
		gbc_lblKalender.anchor = GridBagConstraints.WEST;
		gbc_lblKalender.insets = new Insets(0, 0, 5, 5);
		gbc_lblKalender.gridwidth = 2;
		gbc_lblKalender.gridx = 3;
		gbc_lblKalender.gridy = 6;
		add(lblKalender, gbc_lblKalender);

		kalenderComboBox = new JComboBox();
		kalenderComboBox.setPreferredSize(new Dimension(120, 30));
		GridBagConstraints gbc_kalenderComboBox = new GridBagConstraints();
		gbc_kalenderComboBox.anchor = GridBagConstraints.WEST;
		gbc_kalenderComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_kalenderComboBox.gridx = 5;
		gbc_kalenderComboBox.gridy = 6;
		add(kalenderComboBox, gbc_kalenderComboBox);

		lblBeskrivelse = new JLabel("Beskrivelse: ");
		GridBagConstraints gbc_lblBeskrivelse = new GridBagConstraints();
		gbc_lblBeskrivelse.anchor = GridBagConstraints.WEST;
		gbc_lblBeskrivelse.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeskrivelse.gridwidth = 2;
		gbc_lblBeskrivelse.gridx = 3;
		gbc_lblBeskrivelse.gridy = 7;
		add(lblBeskrivelse, gbc_lblBeskrivelse);

		beskrivelseTextArea = new JTextArea(5, 10);
		JScrollPane beskrivelseScrollPane = new JScrollPane(beskrivelseTextArea); 
		GridBagConstraints gbc_beskrivelseScrollPane = new GridBagConstraints();
		gbc_beskrivelseScrollPane.gridwidth = 2;
		gbc_beskrivelseScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_beskrivelseScrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_beskrivelseScrollPane.gridx = 3;
		gbc_beskrivelseScrollPane.gridy = 8;
		add(beskrivelseScrollPane, gbc_beskrivelseScrollPane);

		lblDeltagere = new JLabel("Deltagere:");
		GridBagConstraints gbc_lblDeltagere = new GridBagConstraints();
		gbc_lblDeltagere.anchor = GridBagConstraints.WEST;
		gbc_lblDeltagere.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeltagere.gridx = 3;
		gbc_lblDeltagere.gridy = 9;
		add(lblDeltagere, gbc_lblDeltagere);

		addPerson = new JButton("+");
		addPerson.addActionListener(new addNewPerson());
		addPerson.setPreferredSize(new Dimension(22, 20));
		GridBagConstraints gbc_addPerson = new GridBagConstraints();
		gbc_addPerson.insets = new Insets(0, 0, 5, 5);
		gbc_addPerson.gridx = 4;
		gbc_addPerson.gridy = 9;
		add(addPerson, gbc_addPerson);

		removePerson = new JButton("-");
		removePerson.setPreferredSize(new Dimension(22, 20));
		GridBagConstraints gbc_removePerson = new GridBagConstraints();
		gbc_removePerson.anchor = GridBagConstraints.WEST;
		gbc_removePerson.insets = new Insets(0, 0, 5, 5);
		gbc_removePerson.gridx = 5;
		gbc_removePerson.gridy = 9;
		add(removePerson, gbc_removePerson);

		btnLagre_1 = new JButton("Lagre");
		GridBagConstraints gbc_btnLagre_1 = new GridBagConstraints();
		gbc_btnLagre_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnLagre_1.ipady = 10;
		gbc_btnLagre_1.ipadx = 5;
		gbc_btnLagre_1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnLagre_1.gridx = 6;
		gbc_btnLagre_1.gridy = 9;
		add(btnLagre_1, gbc_btnLagre_1);

		deltagerTextArea = new JTextArea(5, 10);
		JScrollPane deltagerScrollPane = new JScrollPane(deltagerTextArea); 
		GridBagConstraints gbc_deltagerScrollPane = new GridBagConstraints();
		gbc_deltagerScrollPane.gridwidth = 2;
		gbc_deltagerScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_deltagerScrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_deltagerScrollPane.gridx = 3;
		gbc_deltagerScrollPane.gridy = 10;
		add(deltagerScrollPane, gbc_deltagerScrollPane);

		btnSlett = new JButton("Slett");
		GridBagConstraints gbc_btnSlett = new GridBagConstraints();
		gbc_btnSlett.ipady = 10;
		gbc_btnSlett.ipadx = 5;
		gbc_btnSlett.anchor = GridBagConstraints.WEST;
		gbc_btnSlett.gridx = 6;
		gbc_btnSlett.gridy = 10;
		add(btnSlett, gbc_btnSlett);	

	}

	private void setText(JTextField textField, String day, String clockTime) {
		textField.setText(day + " Klokken: " + clockTime);
	}
/*
 * Oppretter en instans av CalendarJDialog, og legger til en knapp du kan trykke på når du er ferdig.
 * Når du trykker den knappen vil riktig textfield bli oppdatert.
 */
	public void setFrame (JFrame frame) {
		this.meetingFrame = frame;
	}
	
	
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
	
	class returnAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			meetingFrame.dispose();
		}
	}

	class openStopCalendar implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
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
			participantPanel.setMyFrame(participantFrame);
			
		}
	}
	class backAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

			
		}
		
	

}
