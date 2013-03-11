package layout;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;

public class MeetingRoomPanel extends JPanel {


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
		
		JButton btnTilbake = new JButton("tilbake");
		GridBagConstraints gbc_btnTilbake = new GridBagConstraints();
		gbc_btnTilbake.insets = new Insets(0, 0, 5, 0);
		gbc_btnTilbake.gridx = 4;
		gbc_btnTilbake.gridy = 0;
		add(btnTilbake, gbc_btnTilbake);
		
		JLabel lblAntallPersoner = new JLabel("Antall personer");
		GridBagConstraints gbc_lblAntallPersoner = new GridBagConstraints();
		gbc_lblAntallPersoner.insets = new Insets(0, 0, 5, 5);
		gbc_lblAntallPersoner.gridx = 0;
		gbc_lblAntallPersoner.gridy = 1;
		add(lblAntallPersoner, gbc_lblAntallPersoner);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);
		
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
