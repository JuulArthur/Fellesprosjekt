package layout;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.model.UserModel;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.settings.Global;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogginPane extends JPanel implements IServerResponse {
	public JPanel pane;
	private JTextField usernameField;
	private JLabel usernameLabel;
	private JTextField passwordField;
	private JLabel passwordLabel;
	private JButton loggin;
	private JFrame loginFrame;
	
	public LogginPane(){
		pane= new JPanel();
		GridBagLayout gbl_pane = new GridBagLayout();
		gbl_pane.columnWidths = new int[]{55, 55, 206, 6, 67, 0};
		gbl_pane.rowHeights = new int[]{23, 0, 0, 0, 40, 0, 0, 14, 0};
		gbl_pane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pane.setLayout(gbl_pane);


		usernameLabel=new JLabel();

		usernameLabel.setText("Brukernavn");
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.WEST;
		gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLabel.gridx = 1;
		gbc_usernameLabel.gridy = 1;
		pane.add(usernameLabel, gbc_usernameLabel);

		usernameField=new JTextField();
		usernameField.setColumns(20);
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.anchor = GridBagConstraints.WEST;
		gbc_usernameField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameField.gridx = 2;
		gbc_usernameField.gridy = 1;
		pane.add(usernameField, gbc_usernameField);
		passwordLabel = new JLabel();
		passwordLabel.setText("Passord");
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.anchor = GridBagConstraints.WEST;
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.gridx = 1;
		gbc_passwordLabel.gridy = 2;
		pane.add(passwordLabel, gbc_passwordLabel);
		passwordField=new JTextField();
		passwordField.setColumns(20);

		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		pane.add(passwordField, gbc_passwordField);
		loggin = new JButton("Logg in");
		loggin.addActionListener(new LoginButtonListener());
		GridBagConstraints gbc_loggin = new GridBagConstraints();
		gbc_loggin.anchor = GridBagConstraints.NORTHWEST;
		gbc_loggin.insets = new Insets(0, 0, 5, 5);
		gbc_loggin.gridx = 2;
		gbc_loggin.gridy = 3;
		pane.add(loggin, gbc_loggin);
	}


	class LoginButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			if(!(username.length() == 0 || password.length() == 0)){
				UserModel ums = new UserModel();
				ums.setPassword(password);
				ums.setUsername(username);
				
				ArrayList<Object> alist = new ArrayList<Object>();
				alist.add(ums);
				
				Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGIN);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGIN, alist));
			}
		}
		
	}


	@Override
	public boolean recievedObjectRespone(ArrayList<Object> al) {
		if(al.get(0) instanceof UserModel){
			loginFrame.dispose();
			CalendarLayout calendarlayout = new CalendarLayout();
			JFrame frame = new JFrame("Kalender");
			frame.getContentPane().add(calendarlayout);
			frame.pack();
			frame.setLocationRelativeTo(null);		// Places the JFrame in the middle of the screen
			frame.setVisible(true);
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			
			Global.respondGUI.add(calendarlayout);
		}
		return false;
	}


	@Override
	public void setFrame(JFrame frame) {
		this.loginFrame = frame;
	}
	
/*	public static void main (String args[]) {
        JFrame frame = new JFrame("Prototype");
        LogginPane panel=new LogginPane();
        frame.getContentPane().add(panel.pane);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }
*/	
}
