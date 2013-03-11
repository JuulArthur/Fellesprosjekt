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

	public ServerHandler(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}
	
	@Override
	public void onWrapper( MSGWrapper msgW ){
		/* We are not connected and need to get a accept response message */
		if(getState() == State.DISCONNECTED)
			if(msgW.getType() == MSGType.RESPONSE ){
				if(msgW.getFlag() == MSGFlag.ACCEPT){
					System.out.println("We are logged in!");
				}
				else {
					System.out.println("Login failed");
				}
					
			}
	}

}