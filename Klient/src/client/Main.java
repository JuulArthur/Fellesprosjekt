package client;

import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("Preparing client");
		Client client = new Client("localhost", 8080 );
		System.out.println("Client connected");
		Scanner s = new Scanner(System.in);
		boolean loop = true;

		while ( loop ) {
			System.out.println( "What do you want to do?" );
			System.out.println( "1 - Send message" );
			System.out.println( "2 - Exit" );

			int opcao = s.nextInt();

			switch( opcao ) {
			case 1:
				s.nextLine();
				System.out.println("Write your message");
				String message = s.nextLine();
				System.out.printf("Sending your message -> %s%n", message);
				client.writeMessage(message);
				break;
			case 2:
				client.disconnect();
				loop = false;
				break;
			default:
				System.out.println("I don't understand you");
			}
		}

		client.disconnect();
		
		System.exit(0);

	}

}