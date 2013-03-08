package com.server.net;

import java.net.Socket;

import com.net.support.BaseClientHandler;

/**
 * Default class for clients.
 * @author perok
 *
 */
public class ClientHandler  extends BaseClientHandler {
	
	private Server server;

	public ClientHandler(Socket client, Server server) {
		super(client);
		this.server = server;
	}

	@Override
	public void onMessage(String message) {
		System.out.println( "Got message from client:" );
		System.out.println( message );
	}
}
