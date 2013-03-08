package com.net.support;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.stream.StreamResult;

import com.xml.XML;

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

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void writeMessage( String message ) {
		this.queue.offer(message);
	}

	@Override
	public void run() {

		while ( this.isConnected() ) {
			try {
				String message = this.queue.poll( 1 , TimeUnit.SECONDS);
				if ( message != null ) {
					byte[] bytes = message.getBytes();
					this.stream.writeInt( bytes.length );
					this.stream.write( bytes );
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch ( IOException e ) {
				this.client.errorOnWrite(e);
			}
		}

	}
	
	/*
	public void writeXML(){
		try {
			//We are XML
			this.stream.writeInt(-1);
			XML xml = new XML();
			StreamResult sr = xml.saveToXML();
			
			//this.stream.write(sr.getOutputStream());
			//if ( message != null ) {
				//byte[] bytes = message.getBytes();
				//this.stream.writeInt( bytes.length );
				//this.stream.write( bytes );
			//}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch ( IOException e ) {
			this.client.errorOnWrite(e);
		}
	}*/


}
