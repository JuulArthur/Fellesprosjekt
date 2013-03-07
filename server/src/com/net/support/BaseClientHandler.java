package com.net.support;

import java.net.Socket;

public abstract class BaseClientHandler implements IClientHandler {

	private Socket socket;
	private MessageReader reader;
	private MessageWriter writer;

	public BaseClientHandler(String host, int port) throws Exception {
		this(new Socket(host, port));
	}

	/**
	 * Sets up the communcation for the socket connection to the client.
	 * @param socket
	 */
	public BaseClientHandler(Socket socket) {
		try {
			this.socket = socket;
			
			System.out.println("Creating reader");
			System.out.printf("Socket - %s%n", socket);
			System.out.printf("Input - %s%n", this.socket.getInputStream());
			System.out.printf("Output - %s%n", this.socket.getOutputStream());
			this.reader = new MessageReader(this, this.socket.getInputStream());
			System.out.println("Creating writer");
			this.writer = new MessageWriter(this, this.socket.getOutputStream());
			
			new Thread(this.reader).start();
			new Thread(this.writer).start();
			
			System.out.println("Started threads");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public MessageReader getReader() {
		return reader;
	}

	public MessageWriter getWriter() {
		return writer;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void errorOnWrite( Exception e ) {
		System.out.println( "An error happened while writting" );
		e.printStackTrace();
	}

	public void writeMessage( String message ) {
		this.getWriter().writeMessage(message);
	}

	@Override
	public void errorOnRead(Exception e) {
		System.out.println( "An error happened while reading" );
		e.printStackTrace();		
	}	

	/**
	 * Disconnect connection to client. Takes 1 second.
	 */
	public void disconnect() {
		this.reader.setConnected(false);
		this.writer.setConnected(false);
		try {
			Thread.sleep(1000);
			this.socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
