package layout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class SavedMeetingPanel extends MainMeetingPanel implements IServerResponse {

	private JButton btnSendNotice;
	private JButton btnEdit;
	private JLabel lblKommer;
	private JScrollPane scrollPane;
	private JLabel lblKommerIkke;
	private JScrollPane scrollPane_1;
	private JList list;
	private JList list_1;
	private JFrame savedMeeting;
	
	public SavedMeetingPanel () {
		GridBagLayout gridBagLayout = (GridBagLayout) getLayout();
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 52, 0, 0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 60, 0, 0, 0};
		
		titteltextField.setEditable(false);
		stedTextField.setEditable(false);
		beskrivelseTextArea.setEditable(false);
		
		JButton btnAccept = new JButton("Godta");
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.anchor = GridBagConstraints.EAST;
		gbc_btnAccept.insets = new Insets(0, 0, 5, 5);
		gbc_btnAccept.gridx = 7;
		gbc_btnAccept.gridy = 4;
		add(btnAccept, gbc_btnAccept);
		
		JButton btnDecline = new JButton("Avsl\u00E5");
		GridBagConstraints gbc_btnDecline = new GridBagConstraints();
		gbc_btnDecline.anchor = GridBagConstraints.WEST;
		gbc_btnDecline.insets = new Insets(0, 0, 5, 0);
		gbc_btnDecline.gridx = 8;
		gbc_btnDecline.gridy = 4;
		add(btnDecline, gbc_btnDecline);
		
		lblKommer = new JLabel("Kommer");
		GridBagConstraints gbc_lblKommer = new GridBagConstraints();
		gbc_lblKommer.anchor = GridBagConstraints.WEST;
		gbc_lblKommer.insets = new Insets(0, 0, 5, 5);
		gbc_lblKommer.gridx = 7;
		gbc_lblKommer.gridy = 5;
		add(lblKommer, gbc_lblKommer);
		
		btnSendNotice = new JButton("Send ut m\u00F8teinnkalling");
		GridBagConstraints gbc_btnSendNotice = new GridBagConstraints();
		gbc_btnSendNotice.insets = new Insets(0, 0, 5, 5);
		gbc_btnSendNotice.ipady = 10;
		gbc_btnSendNotice.ipadx = 5;
		gbc_btnSendNotice.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnSendNotice.gridx = 5;
		gbc_btnSendNotice.gridy = 7;
		add(btnSendNotice, gbc_btnSendNotice);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 7;
		gbc_scrollPane.gridy = 6;
		add(scrollPane, gbc_scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		
		btnEdit = new JButton("Rediger");
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEdit.anchor = GridBagConstraints.NORTH;
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.ipady = 10;
		gbc_btnEdit.ipadx = 5;
		gbc_btnEdit.gridx = 5;
		gbc_btnEdit.gridy = 8;
		btnEdit.addActionListener(new editMeetingPanel());
		
		add(btnEdit, gbc_btnEdit);			
		
		lblKommerIkke = new JLabel("Kommer ikke");
		GridBagConstraints gbc_lblKommerIkke = new GridBagConstraints();
		gbc_lblKommerIkke.insets = new Insets(0, 0, 5, 5);
		gbc_lblKommerIkke.gridx = 7;
		gbc_lblKommerIkke.gridy = 7;
		add(lblKommerIkke, gbc_lblKommerIkke);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 7;
		gbc_scrollPane_1.gridy = 8;
		add(scrollPane_1, gbc_scrollPane_1);
		
		list_1 = new JList();
		scrollPane_1.setRowHeaderView(list_1);

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
			meetingPanel.setFrame(meetingFrame);
		
		}
	}

	@Override
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setFrame(JFrame frame) {
		this.savedMeeting = frame;
	}
	
}
