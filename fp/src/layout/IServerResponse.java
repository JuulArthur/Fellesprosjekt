package layout;

import java.util.ArrayList;
import javax.swing.JFrame;

public interface IServerResponse {
	public boolean recievedObjectRespone(boolean success, ArrayList<Object> al);
	
	public void setFrame (JFrame frame);
}
