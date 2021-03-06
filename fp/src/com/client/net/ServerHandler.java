package com.client.net;

import java.util.ArrayList;

import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.ServiceHandler;
import com.net.support.State;
import com.settings.Global;

/**
 * Default handler for connection to server.
 * 
 * Control logic here
 * 
 * @author perok
 *
 */
public class ServerHandler extends ServiceHandler {
	/**
	 * the current flag type. Used for knowing the context of a response
	 */
	private MSGFlagVerb currentVerbFlag;

	public ServerHandler(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}
	
	
	public void setCurrentFlag(MSGFlagVerb flag){
		currentVerbFlag = flag;
	}
	
	/**
	 * Client/ server communication flow
	 */
	@Override
	public void onWrapper( MSGWrapper msgW ){
		if(Global.verbose) System.out.println("[ServerHandler] onWrapper");
		
		MSGType type = msgW.getType();
		MSGFlagVerb verb = msgW.getFlagVerb();
		MSGFlagSubject subject = msgW.getFlagSubject();
		ArrayList<Object> al;
		System.out.println(type);
		System.out.println(verb);
		System.out.println(subject);
		/* We are not connected and need to get a accept response message */
		switch (getState()) {
		case DISCONNECTED:
			
			switch (type) {
			case RESPONSE:
				
				switch (verb) {
				case ACCEPT:
					
					switch (currentVerbFlag) {
					case LOGIN:						
						System.out.println("[ServerHandler] User: " +  ((UserModel)msgW.getObjects().get(0)).getUsername() + " is logged in");
												
						propagateResponseToGUI(true, msgW.getObjects());
						
						break;
					case LOGOUT:
						disconnect();
						break;
					default:
						break;
					}
					
					//END ACCEPT
					break;
					
				case DECLINE:
					System.out.println("Login failed");
					//END DECLINE
					break;
				default:
					break;
				}
				
				currentVerbFlag = null;	
				//END RESPONSE
				break;

			default:
				break;
			}
			
			//END DISCONNECTED
			break;
		
		/* We are connected and waiting for a reply*/
		case CONNECTED_WAITING:
			
			switch (type) {
			case RESPONSE:
				
				switch (verb) {
				case ACCEPT:
					
					/*
					 * We have received an RESPONSE ACCEPT. What we demanded have been accepted.
					 * There are two scenarios where we must to something extra:
					 * 	1. We requested a logout
					 * 	2. We requested a GET for an object
					 * 
					 * else we just notify GUI that the action was accepted
					 */
					//TODO Can this be done any better?
					
					switch (currentVerbFlag) {
					case LOGOUT:
						//We can now terminate
						System.out.println("[ServerHandeler] onWrapper: ACCEPTED LOGOUT FROM SERVER");
						disconnect();
						System.out.println("Terminating");
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						System.exit(0);
						break;
						
					default:
						System.out.println("[ServerHandeler] onWrapper: ACCEPTED FROM SERVER");
						//SEND THE ACCEPT BACK TO SERVER
						// if msgW.getObjects.get(0) contains something, return it. the context will fix casting
						setState(State.CONNECTED);
						currentVerbFlag = null;

						propagateResponseToGUI(true, msgW.getObjects());

						if(subject != null){							
							//WHAT WE DID HAS BEEN ACCEPTED
							//TODO respond to UI
						}
						break;
					}	
					/* Must always null out a response*/
					
					//END ACCEPT
					break;
				case DECLINE: 
					//Something failed on the server
					System.out.println("[ServerHandeler] onWrapper: CONNECTED RESPONSE DECLINE");
					break;

				default:
					break;
				}
				
				//END RESPONSE
				break;

			default:
				break;
			}
			
			//END CONNECTED_WAITING
			break;

		default:
			break;
		}
	}
	
	/**
	 * Propagating reponses
	 * if true
	 * 	propagate
	 */
	private void propagateResponseToGUI(boolean success, ArrayList<Object> o){
		//Message have been recieved and we need to be in a connected state again
		setState(State.CONNECTED);

		if(Global.respondGUI != null) //Remove when we are done with CLI client
		for(int i = Global.respondGUI.size() - 1; i >= 0; i--){
			boolean propagate = Global.respondGUI.get(i).recievedObjectRespone(success, o);
			
			if(!propagate)
				break;
		}
	}

}