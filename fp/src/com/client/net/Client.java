package com.client.net;

import com.net.support.BaseClientHandler;

public class Client extends BaseClientHandler {

	public Client(String host, int port) throws Exception {
		super( host, port );
	}

	public void onMessage( String message ) {
		System.out.printf( "Message read is -> %s%n", message );
	}

}