package com.server.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ServerSocketFactory;

/**
 * Main SocketServer.
 * Handles all the clients and the server.
 * @author grp38
 *
 */
public class Server {

	private volatile boolean connected = true;
	
	/* All the clients connected to the server */
	private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
	
	/**
	 * Connected port number
	 */
	private int port;

	public Server(int port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * 
	 * @param connected Sets the state of the server. If set to false then all clients will be disconnected.
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
		if (!this.connected) {
			for (ClientHandler client : this.clients) {
				client.disconnect();
			}
		}
	}

	/**
	 * Running the ServerSocket. Every client connected gets a new ClientHandler socket on a new thread.
	 */
	public void start() {
		try {
			//ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(this.port);
			ServerSocket server = new ServerSocket(this.port);
			System.out.println("ServerSocket InetAddress: " + server.getInetAddress());
			System.out.println("ServerSocket port: " + server.getLocalPort());
			System.out.println("ServerSocket localAddress: " + server.getLocalSocketAddress());
			
			/* Loops and gives a new thread and socket to each client */
			while (server.isBound() && this.isConnected()) {
				Socket client = server.accept();
				
				System.out.printf("Client connected: %s%n", client.getInetAddress());
				
				ClientHandler serverClient = new ClientHandler(client, this);
				this.clients.add(serverClient);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param message
	 * @param source
	 */
	public void onMessage(String message, ClientHandler source) {
		for (ClientHandler client : this.clients) {
			if (!client.equals(source)) {
				client.onMessage(message);
			}
		}
	}

}
