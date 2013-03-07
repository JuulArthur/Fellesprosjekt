package com.server;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import com.server.net.Server;

public class Main {
	
	public static void main(String[] args) {
		
		Properties props = new Properties();
	    FileInputStream in = null;
	    
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
		final Server server = new Server(Integer.parseInt(props.getProperty("server.port", "8080")));
		
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
			System.out.println("2 - Send message to all clients");
			
			int command = s.nextInt();
			
			switch (command) {
			case 1:
				server.setConnected(false);
				loop = false;
				break;
			case 2:
				System.out.println("Not implemented");
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
