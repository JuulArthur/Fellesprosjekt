package com.client.net;

import com.model.UserModel;
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
	private MSGFlagVerb currentFlag;

	public ServerHandler(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}
	
	
	public void setCurrentFlag(MSGFlagVerb flag){
		currentFlag = flag;
	}
	
	/**
	 * Client/ server communication flow
	 */
	@Override
	public void onWrapper( MSGWrapper msgW ){
		if(Global.verbose) System.out.println("[ServerHandler] onWrapper");
		
		/* We are not connected and need to get a accept response message */
		switch (getState()) {
		case DISCONNECTED:
			
			switch (msgW.getType()) {
			case RESPONSE:
				
				switch (msgW.getFlagVerb()) {
				case ACCEPT:
					setState(State.CONNECTED);
					
					UserModel  userModel = (UserModel)msgW.getObjects().get(0);
					
					System.out.println("User: " + userModel.getName() + " " + userModel.getSurname() + " is logged in.");
					
					//END ACCEPT
					break;
					
				case DECLINE:
					System.out.println("Login failed");
					//END DECLINE
					break;
				default:
					break;
				}
				
				currentFlag = null;	
				//END RESPONSE
				break;

			default:
				break;
			}
			
			//END DISCONNECTED
			break;
		
		/* We are connected and waiting for a reply*/
		case CONNECTED_WAITING:
			
			switch (msgW.getType()) {
			case RESPONSE:
				
				switch (msgW.getFlagVerb()) {
				case ACCEPT:
					
					
					switch (currentFlag) {
					case LOGOUT:
						//We can now terminate
						disconnect();
						break;

					default:
						break;
					}					
					
					/* Must always null out a response*/
					currentFlag = null;
					setState(State.CONNECTED);
					
					//END ACCEPT
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

}