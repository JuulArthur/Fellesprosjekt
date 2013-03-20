package com.server.net;

import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.GroupModel;
import com.model.NotificationModel;
import com.model.RoomModel;
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
	
	//private static final Executor POOL = Executors.newFixedThreadPool(16);

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
						//END LOGIN
						break;
					case LOGOUT:
						writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, null));
						disconnect();
						server.removeCLient(this);
						//END LOGOUT
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
										
					/* VERB */
					switch (verb) {
					case GET:
						
						al = new ArrayList<Object>();
						
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //String user, int appointmentid
							al.add(factory.getAlarmModel((String)msgW.getObjects().get(0), (Long)msgW.getObjects().get(1)));
							break;
							
						case CALENDAR:
							al.add(factory.getCalendarModel((Integer)msgW.getObjects().get(0)));			
							break;
							
						case APPOINTMENT: //int appointmentid
							al.add(factory.getAppointmentModel((Integer)msgW.getObjects().get(0)));
							break;
							
						case GROUP: //int groupid
							al.add(factory.getGroupModel((Integer)msgW.getObjects().get(0)));	
							break;
							
						case NOTIFICATION: //NotificationModel nm //TODO Deprecated
							al.add(factory.getNotificationModel((NotificationModel)msgW.getObjects().get(0)));
							break;
							
						case ROOM:
							al.add(factory.getRoomModel((Integer)msgW.getObjects().get(0)));
							break;
							
						case USER: //String username
							al.add(factory.getUserModel((String)msgW.getObjects().get(0)));
							break;
							
						case ALLGROUPS:
							al.add(factory.getEveryGroup());
							break;
						case ALLUSERS:
							al.add(factory.getEveryUser(msgW.getUser()));
							break;
						case ISSUMMONEDTO: // ArrayList users, long appointmentid
							factory.getIsSummonedTo((Long)msgW.getObjects().get(1));//((UserModel)msgW.getObjects().get(0));
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
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //AlarmModel
							factory.createAlarmModel((AlarmModel)msgW.getObjects().get(0));
							break;
			
						case CALENDAR: //CalendarModel
							factory.createCalendarModel((CalendarModel)msgW.getObjects().get(0));
							break;
							
						case APPOINTMENT: //AppointmentModel
							factory.createAppointmentModel((AppointmentModel)msgW.getObjects().get(0));
							break;
							
						case GROUP: //GroupeModel
							factory.createGroupModel((GroupModel)msgW.getObjects().get(0));
							break;
							
						case NOTIFICATION:
							for(Object nm : msgW.getObjects()){
								factory.createNotificationModel((NotificationModel)nm);
							}
							break;
							
						case ROOM:
							factory.createRoomModel((RoomModel)msgW.getObjects().get(0));
							break;
							
						case USER:
							factory.createUserModel((UserModel)msgW.getObjects().get(0));
							break;
						case ISSUMMONEDTO: // ArrayList users, long appointmentid
							factory.createIsSummonedTo((ArrayList<UserModel>)msgW.getObjects().get(0), (Long)msgW.getObjects().get(1));//((UserModel)msgW.getObjects().get(0));
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
						
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //AlarmModel
							factory.updateAlarmModel((AlarmModel)msgW.getObjects().get(0));
							break;
			
						case CALENDAR: //CalendarModel
							factory.updateCalendarModel((CalendarModel)msgW.getObjects().get(0));
							break;
							
						case APPOINTMENT: //AppointmentModel
							factory.updateAppointmentModel((AppointmentModel)msgW.getObjects().get(0));
							break;
							
						case GROUP:
							factory.UpdateGroupModel((GroupModel)msgW.getObjects().get(0));
							break;
							
						case NOTIFICATION:// NotificationModel
							factory.updateNotificationModel((NotificationModel)msgW.getObjects().get(0));
							break;
							
						case ROOM:
							factory.updateRoomModel((RoomModel)msgW.getObjects().get(0));
							break;
							
						case USER: //UserModel
							factory.updateUserModel((UserModel)msgW.getObjects().get(0));
							break;
							
						case ISSUMMONEDTO: // ArrayList users, long appointmentid
							factory.updateIsSummonedTo((ArrayList<UserModel>)msgW.getObjects().get(0), (Long)msgW.getObjects().get(1));//((UserModel)msgW.getObjects().get(0));
							break;
							
						case BELONGTO: //appointmentid, newCalID, oldCalId
							factory.updateBelongTo((Long)msgW.getObjects().get(0), (Long)msgW.getObjects().get(1), (Long)msgW.getObjects().get(2));

						default:
							break;
						}
						
						/* REPLY */
						if(success)
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.ACCEPT, subject, null));
						else{
							writeMessage(jaxbMarshaller.getXMLRepresentation(msgW.getID(), MSGType.RESPONSE, MSGFlagVerb.DECLINE, subject, null));
						}
						
						//END UPDATE
						break;
					case DELETE:
						
						/* SUBJECTT */
						switch (subject) {
						case ALARM: //Integer AppointmentId, String username
							factory.deleteAlarmModel((Integer)msgW.getObjects().get(0), (String)msgW.getObjects().get(1));
							break;
			
						case CALENDAR:	
							factory.deleteCalendarModel((Integer)msgW.getObjects().get(0));
							break;
							
						case APPOINTMENT: //int aid
							factory.deleteAppointmentModel((Integer)msgW.getObjects().get(0));
							break;
							
						case GROUP:
							factory.deleteGroupModel((Integer)msgW.getObjects().get(0));
							break;
							
						case NOTIFICATION: //STRING username, INTEGER AppointmentID
							factory.deleteNotificationModel((String)msgW.getObjects().get(0), (Integer)msgW.getObjects().get(1));
							break;
							
						case ROOM: //Integer id
							factory.deleteRoomModel((Integer)msgW.getObjects().get(0));
							break;
							
						case USER: //String username
							factory.deleteUserModel((String)msgW.getObjects().get(0));
							break;
							
						case ISSUMMONEDTO: //DELETE GIVEN PEOPLE FROM SUMMONEDLIST TO GIVEN APPOINTMENT
							factory.deleteIsSummonedToPeople((ArrayList<String>)msgW.getObjects().get(0), (Long)msgW.getObjects().get(0));
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
						
						//END DELETE
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
			e.printStackTrace();
		}
		catch (SQLException e) {
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
};

POOL.execute(runnable);*/
