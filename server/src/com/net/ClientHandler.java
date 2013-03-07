package com.net;

import java.net.Socket;

import com.net.support.BaseClientHandler;

//import chat.support.BaseMessageClient;

public class ClientHandler  extends BaseClientHandler {

	private Server server;

	public ClientHandler(Socket client, Server server) {
		super(client);
		this.server = server;
	}

	//@Override
	public void onMessage(String message) {
		System.out.println( "Got message from client:" );
		System.out.println( message );
		//this.server.onMessage(message, this);
	}

}
