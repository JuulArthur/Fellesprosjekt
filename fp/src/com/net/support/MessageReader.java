package com.net.support;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.model.AlarmModel;
import com.model.AppointmentModel;
import com.model.CalendarModel;
import com.model.GroupModel;
import com.model.NotificationModel;
import com.model.RoomModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;

/**
 * Handles message reciving from the client
 * 
 * @author grp38
 *
 */
public class MessageReader implements Runnable {

	private static boolean verbose = false;
	
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
				// The size of the stream
				int size = this.stream.readInt();

				/* The UnMarshaller */
				JAXBContext ctx = JAXBContext.newInstance(MSGFlagVerb.class, MSGFlagSubject.class, MSGType.class, MSGWrapper.class,
						AlarmModel.class, AppointmentModel.class, CalendarModel.class, GroupModel.class, NotificationModel.class, RoomModel.class, UserModel.class);
				Unmarshaller um = ctx.createUnmarshaller();

				if(verbose)System.out.printf("Reading %d bytes%n", size);
				byte[] message = new byte[size];
				for (int x = 0; x < size; x++) {
					if(verbose)System.out.printf("reading byte %d of %d%n", x, size);
					message[x] = (byte) this.stream.read();
				}
				if(verbose)System.out.println("===DEBUG MESSAGE===");
				if(verbose)System.out.println(new String(message));
				if(verbose)System.out.println("===DEBUG END    ===");

				// Creating the object from the string
				final Object o = um.unmarshal(new StreamSource(new StringReader(new String(message))));
				
				//Send the message to the client
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if (o instanceof MSGWrapper) {
							client.onWrapper((MSGWrapper)o);
						}
						
						if(verbose) client.onMessage("Object recieved: " + (MSGWrapper)o);
					}
				};
				
				POOL.execute(runnable);
			}
		}catch (SocketException e){
			System.out.println("[MessageReader] Run: Socket is closed");
		}
		catch (EOFException e){
			System.out.println("[MessageReader] Run: Stream is closed");
		}
		catch (Exception e) {
			this.client.errorOnRead(e);
			throw new RuntimeException(e);
		}

	}

}

/*
System.out.println(size);
JAXBUnmarshaller jux = new JAXBUnmarshaller();

UserModel u = jux.UserModelUnmarshaller(this.stream);

System.out.println(u.getUsername());
System.out.println(u.getPassword());*/

/*
 * XML Reader from stream with filter
 */
/*
 XMLInputFactory xmlif = XMLInputFactory.newInstance();
 XMLEventReader xmler = xmlif.createXMLEventReader(this.stream);
 EventFilter filter = new EventFilter() {
     public boolean accept(XMLEvent event) {
         return true;//event.isStartElement();
     }
 };
 XMLEventReader xmlfer = xmlif.createFilteredReader(xmler, filter); 

 // Parse into typed objects
 JAXBContext ctx = JAXBContext.newInstance("com.model");
 Unmarshaller um = ctx.createUnmarshaller();
 int bugs = 0;
 while (xmlfer.peek() != null) {
      Object o = um.unmarshal(xmler);
      if (o instanceof UserModel) {
    	  UserModel bi = (UserModel) o;
          // process the bug instance
          bugs++;
      }
 }*/

//else {
