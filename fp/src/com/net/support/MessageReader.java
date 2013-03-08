package com.net.support;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Handles message reciving from the client
 * 
 * @author grp38
 *
 */
public class MessageReader implements Runnable {

	private static final Executor POOL = Executors.newFixedThreadPool(16);

	final private IClientHandler client;
	private DataInputStream stream;
	private boolean connected = true;

	public MessageReader(IClientHandler client, InputStream stream) {
		this.client = client;
		this.stream = new DataInputStream(stream);
		System.out.println("Reader is ready");
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	@Override
	public void run() {

		try {
			while (this.isConnected()) {
				//The size of the stream
				// If -1 --> XML
				int size = this.stream.readInt();
				
				if(size == -1){
					System.out.println("Reading XML data");
					size = this.stream.readInt();
					System.out.println("XML size: " + size);
					
					
				}
				//else {
					System.out.printf("Reading %d bytes%n", size);
					byte[] message = new byte[size];
					for (int x = 0; x < size; x++) {
						System.out.printf("reading byte %d of %d%n", x, size);
						message[x] = (byte) this.stream.read();
					}
					final String realMessage = new String(message);
	
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							client.onMessage(realMessage);
						}
					};
					POOL.execute(runnable);
				//}
			}
		} catch (Exception e) {
			this.client.errorOnRead(e);
			throw new RuntimeException(e);
		}

	}

}
