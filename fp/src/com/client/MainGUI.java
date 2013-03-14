package com.client;


import javax.swing.JFrame;

import layout.LogginPane;

import com.client.net.ServerHandler;
import com.settings.Global;
import com.xml.JAXBMarshaller;

public class MainGUI {
	
	public void startServer() throws Exception{
		Global.sHandler = new ServerHandler("localhost", 8078 ); //mel.is
		Global.jaxbMarshaller = new JAXBMarshaller();
		
		System.out.println("[Main] Connected to server");
	}
	
	public void init() throws Exception{
        startServer();

		JFrame frame = new JFrame("Prototype");
        LogginPane panel=new LogginPane();
        frame.getContentPane().add(panel.pane);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        Global.respondGUI = panel;
        
	}
	

	public static void main(String[] args) throws Exception {
		new MainGUI().init();
	}
}
