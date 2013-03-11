package com.client.net;

import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.net.support.ServiceHandler;
import com.net.support.State;

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
		/* We are not connected and need to get a accept response message */
		if(getState() == State.DISCONNECTED){
			if(msgW.getType() == MSGType.RESPONSE ){
				if(msgW.getFlag() == MSGFlag.ACCEPT){
					System.out.println("We are logged in!");
				}
				else {
					System.out.println("Login failed");
				}
				
				currentFlag = null;	
			}
		}
		/* We are connected */
		else{
			if(msgW.getType() == MSGType.RESPONSE ){
				if(msgW.getFlag() == MSGFlag.ACCEPT){}
			}
		}
		
	}

}