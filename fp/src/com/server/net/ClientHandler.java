package com.server.net;

import java.net.Socket;

import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.ServiceHandler;
import com.net.support.State;

/**
 * Default class for clients connected to server.
 * 
 * Control logic flow here
 * @author perok
 *
 */
public class ClientHandler  extends ServiceHandler {
	
	private Server server;

	public ClientHandler(Socket client, Server server) {
		super(client);
		this.server = server;
	}

	@Override
	public void onMessage(String message) {
		System.out.println( "Got message from client:" );
		System.out.println( message );
	}
	
	/**
	 * Recieves wrapper object and processes it.
	 * Should this be a queue system?
	 */
	@Override
	public void onWrapper( MSGWrapper msgW ){
		if(msgW.getType() == MSGType.REQUEST){
			if(msgW.getFlag() == MSGFlag.LOGIN){
				Object o = msgW.getObjects().get(0);
				if(o instanceof UserModel){
					/*
					 * Query for login.
					 * If query accepted, set the State to connected and send a RESPONSE back with acknowledge 
					 */
					System.out.println("CLIENTHANDLER: Trying to log in: " + o);
					
					/* Set connected state*/
					setState(State.CONNECTED);
					
					/* Send back an acknowledged state*/					
					writeMessage(server.getJaxbMarshaller().getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.ACCEPT, null));

				}
			}
		}
		else {
			
		}
	}
}
