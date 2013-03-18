package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.client.MainGUI;
import com.model.UserModel;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.settings.Global;
import com.view.LogginPane;

public class LogginPaneController  implements ActionListener, IServerResponse{
	
	private MainGUI gui;
	private UserModel userModel;
	private LogginPane l_liew;
	
	public LogginPaneController(LogginPane l_view, UserModel userModel, MainGUI gui){
		this.gui = gui;
		this.userModel = userModel;
		this.l_liew = l_view;
		this.l_liew.addLogginButtonListener(this);
	}
	

		@Override
		public void actionPerformed(ActionEvent e) {	
			String username = l_liew.getUsernameField().getText();
			String password = l_liew.getPasswordField().getText();
			
			if(!(username.length() == 0 || password.length() == 0)){
				UserModel ums = new UserModel();
				ums.setPassword(password);
				ums.setUsername(username);
				
				ArrayList<Object> alist = new ArrayList<Object>();
				alist.add(ums);
				
				Global.sHandler.setCurrentFlag(MSGFlagVerb.LOGIN);
				Global.sHandler.setState(State.CONNECTED_WAITING);
				Global.sHandler.writeMessage(Global.jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGIN, alist));
			}		
	}


		@Override
		public boolean recievedObjectRespone(boolean success,
				ArrayList<Object> al) {				

			if(al.get(0) instanceof UserModel){
				this.userModel = (UserModel)al.get(0);
				gui.initCalendar();
				Global.respondGUI.remove(this);
			}
			return false;
		}
}
