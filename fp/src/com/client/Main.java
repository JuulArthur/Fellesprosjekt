package com.client;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import com.client.net.Client;
import com.model.UserModel;
import com.xml.JAXBMarshaller;

//import com.xml.XML;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("Preparing client");
		Client client = new Client("localhost", 8078 ); //82.194.219.227 8078
		System.out.println("Client connected");
		Scanner s = new Scanner(System.in);
		boolean loop = true;
		
		/**
		 * Object for creating XML from the models
		 */
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();

		while ( loop ) {
			System.out.println( "What do you want to do?" );
			System.out.println( "1 - Send message" );
			System.out.println( "2 - To cool for XML");
			System.out.println( "3 - Exit" );
			
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
				s.nextLine();
				UserModel um = new UserModel();
				System.out.println("Write username");
				um.setUsername(s.nextLine());
				System.out.println("Write password");
				um.setPassword(s.nextLine());
				
				/* Make a bytearray of the XML*/
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				jaxbMarshaller.jaxbModeltoXML(um, baos);
				client.writeMessage(baos.toString()); //byte[] data = bos.toByteArray(); better solution?
				break;
			case 3:
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