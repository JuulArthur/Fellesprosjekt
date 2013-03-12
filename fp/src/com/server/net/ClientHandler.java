package com.server.net;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.model.AppointmentModel;
import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.ServiceHandler;
import com.net.support.State;
import com.server.db.Factory;
import com.settings.Global;
import com.xml.JAXBMarshaller;

/**
 * Default class for clients connected to server.
 * 
 * Control logic flow here
 * @author perok
 *
 */
public class ClientHandler  extends ServiceHandler {	
	private Server server;
	
	Factory factory;
	JAXBMarshaller jaxbMarshaller;
	
	private static final Executor POOL = Executors.newFixedThreadPool(16);

	public ClientHandler(Socket client, Server server) {
		super(client);
		this.server = server;
		
		factory = server.getFactory();
		jaxbMarshaller = server.getJaxbMarshaller();
	}

	@Override
	public void onMessage(String message) {
		System.out.println( "Got message from client:" );
		System.out.println( message );
	}
	
	/**
	 * Recieves wrapper object and processes it.
	 * Should this be a queue system?
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public void onWrapper( MSGWrapper msgW ){
		if(Global.verbose) System.out.println("[ClientHandler] onWrapper: " + msgW + "--State: " + getState());
		/* Client is not logged in */
		try {
			
			switch (getState()) {
			case DISCONNECTED:
				/* Trying to log in */
				switch (msgW.getFlag()) {
				case LOGIN:
					Object o = msgW.getObjects().get(0);
					if(o instanceof UserModel){
						if(Global.verbose) System.out.println("[ClientHandler] onWrapper: Logging in " + o);
						
						UserModel um = (UserModel)o;
						
						/*
						 * Query for login.
						 * If query accepted, set the State to connected and send a RESPONSE back with acknowledge 
						 */
						boolean login = factory.checkPassword(um.getUsername(), um.getPassword());
						
						if(login){
							/* Set connected state*/
							setState(State.CONNECTED);
							
							ArrayList<Object> al = new ArrayList<Object>();
							al.add(factory.getUserModel(um.getUsername()));
							
							/* Send back an acknowledged state*/					
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.ACCEPT, al));
						}
						else{
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.DECLINE, null));
						}	
					}
					break;

				default:
					break;
				}
				
				//BREAK DISCONNECTED
				break;
			
			/* We are logged in*/
			case CONNECTED:
				
				/* What type of message */
				switch (msgW.getType()) {
				case REQUEST:
					
					Object o;
					switch (msgW.getFlag()) {
					case GET:
						o = msgW.getObjects().get(0);
						if(o instanceof UserModel){
							
						}
						break;
					case CREATE:
						o = msgW.getObjects().get(0);
						if(o instanceof UserModel){
							
						}
						else if(o instanceof AppointmentModel){
							factory.createAppointmentModel((AppointmentModel)o);
							
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlag.ACCEPT, null));
						}
						break;
					case UPDATE:
						o = msgW.getObjects().get(0);
						if(o instanceof UserModel){
							
						}
						break;
					case DELETE:
						o = msgW.getObjects().get(0);
						if(o instanceof UserModel){
							
						}
						break;
					case LOGOUT:
					default:
						break;
					}
					
					break; //BREAK REQUEST

				default:
					break;
				}
				
				//BREAK CONNECTED
				break;

			default:
				break;
			}	
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
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
