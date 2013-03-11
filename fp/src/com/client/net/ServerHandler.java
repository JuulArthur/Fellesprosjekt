package com.client.net;

import com.net.msg.MSGWrapper;
import com.net.support.BaseClientHandler;

/**
 * Default handler for connection to server.
 * 
 * Control logic here
 * 
 * @author perok
 *
 */
public class ServerHandler extends BaseClientHandler {

	public ServerHandler(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}
	
	@Override
	public void onWrapper( MSGWrapper msgW ){
		
	}

}