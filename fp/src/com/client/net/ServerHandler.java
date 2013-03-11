package com.client.net;

import com.net.msg.MSGFlag;
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
	private MSGFlag currentFlag;

	public ServerHandler(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}
	
	
	public void setCurrentFlag(MSGFlag flag){
		currentFlag = flag;
	}
	
	/**
	 * Client/ server communication flow
	 */
	@Override
	public void onWrapper( MSGWrapper msgW ){
		if(Global.verbose) System.out.println("[ServerHandler] onWrapper");
		/* We are not connected and need to get a accept response message */
		if(getState() == State.DISCONNECTED){
			if(msgW.getType() == MSGType.RESPONSE ){
				if(msgW.getFlag() == MSGFlag.ACCEPT){
					System.out.println("We are logged in!");
					setState(State.CONNECTED);
				}
				else {
					System.out.println("Login failed");
				}
				
				currentFlag = null;	
			}
		}
		/* We are connected and waiting for a reply*/
		else if (getState() == State.CONNECTED_WAITING){
			if(msgW.getType() == MSGType.RESPONSE ){
				if(msgW.getFlag() == MSGFlag.ACCEPT){
					if(currentFlag == MSGFlag.LOGOUT){
						//LOG OUT
					}
					
					currentFlag = null;
					setState(State.CONNECTED);
				}
			}
		}
		
	}

}