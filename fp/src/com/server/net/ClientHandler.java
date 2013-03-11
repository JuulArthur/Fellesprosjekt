package com.server.net;

import java.net.Socket;

import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.BaseClientHandler;

/**
 * Default class for clients connected to server.
 * 
 * Control logic flow here
 * @author perok
 *
 */
public class ClientHandler  extends BaseClientHandler {
	
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
					System.out.println("Trying to log in: " + o);
				}
			}
		}
		else {
			
		}
	}
}
