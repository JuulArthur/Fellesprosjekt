package com.server.net;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.GroupModel;
import com.model.NotificationModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
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
			
			MSGType type = msgW.getType();
			MSGFlagVerb verb = msgW.getFlagVerb();
			MSGFlagSubject subject = msgW.getFlagSubject();
			ArrayList<Object> al;
			
			
			switch (getState()) {
			case DISCONNECTED:
				
				switch (type) {
				case REQUEST:
					/* Trying to log in */
					switch (verb) {
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
								
								al = new ArrayList<Object>();
								al.add(factory.getUserModel(um.getUsername()));
								
								/* Send back an acknowledged state*/					
								writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, al));
							}
							else{
								writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.DECLINE, null));
							}	
						}
						break;

					default:
						break;
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
				switch (type) {
				case REQUEST:
					
					//Was the request a success?
					boolean success = true;
					
					Object o;
					
					/* VERB */
					switch (verb) {
					case GET:
						
						al = new ArrayList<Object>();
						
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //String user, int appointmentid
							al.add(factory.getAlarmModel((String)msgW.getObjects().get(0), (Integer)msgW.getObjects().get(1)));
							break;
							
						case CALENDAR:
							
							//CalendarModel cm = factory.getC
							
							break;
							
						case APPOINTMENT: //int appointmentid
							al.add(factory.getAppointmentModel((Integer)msgW.getObjects().get(0)));
							break;
							
						case GROUP:
							
							//GroupModel gm = factory.get
							
							break;
							
						case NOTIFICATION: //NotificationModel nm
							al.add(factory.getNotificationModel((NotificationModel)msgW.getObjects().get(0)));
							break;
							
						case ROOM:
							
							break;
							
						case USER: //String username
							al.add(factory.getUserModel((String)msgW.getObjects().get(0)));
							break;

						default:
							break;
						}
						
						/* REPLY */
						if(success)
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, subject, al));
						else{
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.DECLINE, subject, null));
						}
						
						al = null;
						
						//END GET
						break;
					case CREATE:
						/* We put the object in the database and ACCEPT or DECLINE
						 * 
						 * */
						al = new ArrayList<Object>();
						
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //AlarmModel
							factory.createAlarmModel((AlarmModel)msgW.getObjects().get(0));
							break;
			
						case CALENDAR:							
							break;
							
						case APPOINTMENT: //AppointmentModel
							factory.createAppointmentModel((AppointmentModel)msgW.getObjects().get(0));
							break;
							
						case GROUP:
							
							//factory.get
							
							break;
							
						case NOTIFICATION:
							factory.createNotificationModel((NotificationModel)msgW.getObjects().get(0));
							break;
							
						case ROOM:
							
							break;
							
						case USER:
							factory.createUserModel((UserModel)msgW.getObjects().get(0));
							break;

						default:
							break;
						}
						
						/* REPLY */
						if(success)
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, subject, null));
						else{
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.DECLINE, subject, null));
						}
						
						//END CREATE
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
						writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, null));
						disconnect();
						server.removeCLient(this);
						break;
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
