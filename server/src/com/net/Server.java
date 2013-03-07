package com.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ServerSocketFactory;

public class Server {

	private volatile boolean connected = true;
	private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
	private int port;

	public Server(int port) {
		this.port = port;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
		if (!this.connected) {
			for (ClientHandler client : this.clients) {
				client.disconnect();
			}
		}
	}

	public void start() {
		try {
			ServerSocket server = ServerSocketFactory.getDefault()
					.createServerSocket(this.port);
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

	public void onMessage(String message, ClientHandler source) {
		for (ClientHandler client : this.clients) {
			if (!client.equals(source)) {
				client.onMessage(message);
			}
		}
	}

}
