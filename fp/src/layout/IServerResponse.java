package layout;

import java.util.ArrayList;
import javax.swing.JFrame;

public interface IServerResponse {
	public boolean recievedObjectRespone(ArrayList<Object> al);
	
	public void setFrame (JFrame frame);
}
