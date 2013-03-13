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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class MeetingPanel extends MainMeetingPanel {
	private JLabel lblKalender;
	private JComboBox kalenderComboBox;
	private JLabel lblDeltagere;
	private JButton btnSlett;
	private JButton btnLagre_1;
	private JButton addPerson;
	private JButton removePerson;
	private JButton btnopenStartCalendar;
	private JButton btnopenStopCalendar;
	private JList participantList;
	

	public MeetingPanel() {

		/* 
		 * Mangler noe funksjonalitet:
		 * TODO: 
		 * - Kalender for alarm
		 * - Knapp for sted som fører til MeetingRoomPanel 
		 */
		
		btnopenStartCalendar = new JButton("\u00C5pne kalender");
		btnopenStartCalendar.addActionListener(new openStartCalendar());
		GridBagConstraints gbc_btnopenStartCalendar = new GridBagConstraints();
		gbc_btnopenStartCalendar.insets = new Insets(0, 0, 5, 0);
		gbc_btnopenStartCalendar.gridx = 6;
		gbc_btnopenStartCalendar.gridy = 2;
		add(btnopenStartCalendar, gbc_btnopenStartCalendar);

		btnopenStopCalendar = new JButton("\u00C5pne kalender");
		btnopenStopCalendar.addActionListener(new openStopCalendar());

		GridBagConstraints gbc_btnopenStopCalendar = new GridBagConstraints();
		gbc_btnopenStopCalendar.insets = new Insets(0, 0, 5, 0);
		gbc_btnopenStopCalendar.gridx = 6;
		gbc_btnopenStopCalendar.gridy = 3;
		add(btnopenStopCalendar, gbc_btnopenStopCalendar);

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
		btnLagre_1.addActionListener(new saveAction());
		add(btnLagre_1, gbc_btnLagre_1);
		JScrollPane deltagerScrollPane = new JScrollPane(); 
		GridBagConstraints gbc_deltagerScrollPane = new GridBagConstraints();
		gbc_deltagerScrollPane.gridwidth = 2;
		gbc_deltagerScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_deltagerScrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_deltagerScrollPane.gridx = 3;
		gbc_deltagerScrollPane.gridy = 10;
		add(deltagerScrollPane, gbc_deltagerScrollPane);
		
		participantList = new JList();
		deltagerScrollPane.setViewportView(participantList);

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
	
	class saveAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SavedMeetingPanel savedMeetingPanel = new SavedMeetingPanel();
			JFrame savedMeetingFrame = new JFrame("Legg til brukere og/eller grupper");
			savedMeetingFrame.getContentPane().add(savedMeetingPanel);
			savedMeetingFrame.pack();
			savedMeetingFrame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			savedMeetingFrame.setVisible(true);
			savedMeetingFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			savedMeetingPanel.setFrame(savedMeetingFrame);
		}
		
	}

	class openStopCalendar implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			createCalenderDialog((getSluttTextField()));
		}
	}	

	class openStartCalendar implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			createCalenderDialog(getStartTextField());
		}	
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Avtale");
		frame.getContentPane().add(new MeetingPanel());
		frame.pack();
		frame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
