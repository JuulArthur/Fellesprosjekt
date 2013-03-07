package com.net;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		//Main Server instance
		final Server server = new Server(8080);
		
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
			int command = s.nextInt();
			switch (command) {
			case 1:
				server.setConnected(false);
				loop = false;
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
