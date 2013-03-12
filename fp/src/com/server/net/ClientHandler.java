package com.server.net;

import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.ServiceHandler;
import com.net.support.State;
import com.settings.Global;

/**
 * Default class for clients connected to server.
 * 
 * Control logic flow here
 * @author perok
 *
 */
public class ClientHandler  extends ServiceHandler {	
	private Server server;
	
	private static final Executor POOL = Executors.newFixedThreadPool(16);

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
		if(Global.verbose) System.out.println("[ClientHandler] onWrapper: " + msgW + "--State: " + getState());
		/* Client is not logged in */
		if(getState() == State.DISCONNECTED ){
			if(msgW.getType() == MSGType.REQUEST){
				if(msgW.getFlag() == MSGFlag.LOGIN){
					Object o = msgW.getObjects().get(0);
					if(o instanceof UserModel){
						if(Global.verbose) System.out.println("[ClientHandler] onWrapper: Logging in " + o);
						
						UserModel um = (UserModel)o;
						/*
						 * Query for login.
						 * If query accepted, set the State to connected and send a RESPONSE back with acknowledge 
						 */
						
						boolean login = false;
						
						try {
							login = server.getFactory().checkPassword(um.getUsername(), um.getPassword());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(login){
							/* Set connected state*/
							setState(State.CONNECTED);
							
							/* Send back an acknowledged state*/					
							writeMessage(server.getJaxbMarshaller().getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.ACCEPT, null));
						}
						else{
							writeMessage(server.getJaxbMarshaller().getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.DECLINE, null));
						}
					}
					else
						System.out.println("[ERROR][ClientHandler] onWrapper: bad protocol");
				}
			}
			else {
				
			}
		}
		/* Client is logged in */
		else{
			if(msgW.getType() == MSGType.REQUEST){
				if(msgW.getFlag() == MSGFlag.GET){}
				if(msgW.getFlag() == MSGFlag.UPDATE){}
				if(msgW.getFlag() == MSGFlag.DELETE){}
				if(msgW.getFlag() == MSGFlag.LOGOUT){}
			}
		}
		
			
	}
}

/*
Runnable runnable = new Runnable() {
	@Override
	public void run() {
		try {
			login = server.getFactory().checkPassword(um.getUsername(), um.getPassword());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
};

POOL.execute(runnable);*/
