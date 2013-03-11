package com.net.support;

import java.net.Socket;

/**
 * For krytografi se; http://www.bouncycastle.org/
 * 
 * @author grp38
 *
 */
public abstract class ServiceHandler implements IClientHandler {

	private Socket socket;
	private MessageReader reader;
	private MessageWriter writer;
	 
	/**
	 * The state of the connection
	 */
	private State state;
	
	/*
	 * Have a last time used timestamp?
	 */

	public ServiceHandler(String host, int port) throws Exception {
		this(new Socket(host, port));
		
		/**
		 * Not logged in yet
		 */		
		setState(State.DISCONNECTED);
	}

	/**
	 * Sets up the communcation for the socket connection to the client.
	 * @param socket
	 */
	public ServiceHandler(Socket socket) {
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
			
			/**
			 * Not logged in yet
			 */	
			setState(State.DISCONNECTED);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public MessageReader getReader() {
		return reader;
	}

	/**
	 * Gets the MessageWriter instance.
	 * Handle message writing to client here.
	 * @return
	 */
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
		//Stacktrace for disconnect exception gets thrown to here.
		//System.out.println(e.getCause());
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

	/*
	 * Getters and setters
	 */
	
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
