package com.net.support;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Handles message writing to the client
 * 
 * @author grp38
 *
 */
public class MessageWriter implements Runnable {

	private IClientHandler client;
	private DataOutputStream stream;
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private volatile boolean connected = true;

	public MessageWriter( IClientHandler client, OutputStream stream ) {
		this.client = client;
		this.stream = new DataOutputStream( stream );

		System.out.println("Writer is ready");
	}

	/**
	 * Return the connections state
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Change the connections state
	 * @param connected
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	/**
	 * Adds a String to the messaging queue
	 * The String must be formatted from JAXB
	 * 
	 * @param message
	 */
	public void writeMessage( String message ) {
		this.queue.offer(message);
	}

	/**
	 * Change to BufferedOutputStream?
	 */
	@Override
	public void run() {
		while ( this.isConnected() ) {
			try {
				String message = this.queue.poll( 1 , TimeUnit.SECONDS);
				if ( message != null ) {
					byte[] bytes = message.getBytes();
					/* Send the length of the bytes */
					this.stream.writeInt( bytes.length );
					
					/* Send the bytes */
					this.stream.write( bytes );
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch ( IOException e ) {
				this.client.errorOnWrite(e);
			}
		}
	}
}
