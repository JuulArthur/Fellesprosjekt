package com.server;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import com.server.net.Server;
import com.xml.JAXBMarshaller;

public class Main {
	

	
	public static void main(String[] args) throws InterruptedException {
		
		Properties props = new Properties();
	    FileInputStream in = null;
	    
	    JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
	    
		/**
		 * Load the properties
		 */
		try{
			in = new FileInputStream("server.properties");
        	props.load(in);
		}
		catch(Exception e){
			
		}
		
		//Main Server instance
		final Server server = new Server(Integer.parseInt(props.getProperty("server.port", "8078")));
		
		//Start server on another thread
		Runnable r = new Runnable() {
			public void run() {
				server.start();
			};
		};
		new Thread(r).start();

		Scanner s = new Scanner(System.in);
		boolean loop = true;

		
		while (loop) {
			System.out.println("What do you want to do?:");
			System.out.println("1 - Exit");
			//System.out.println("2 - Send User Model");
			
			int command = s.nextInt();
			
			switch (command) {
			case 1:
				server.setConnected(false);
				loop = false;
				Thread.sleep(1001);
				break;
			case 2:
				break;
			default:
				System.out.println("I don't understand you");
			}
		}
		
		System.out.println("Server terminated.");
		System.out.println("Good bye.");
		System.exit(0);
	}

}
