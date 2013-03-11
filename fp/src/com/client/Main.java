package com.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.client.net.Client;
import com.model.UserModel;
import com.net.msg.MSGFlag;
import com.net.msg.MSGType;
import com.net.msg.MSGWrapper;
import com.xml.JAXBMarshaller;

//import com.xml.XML;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("Preparing client");
		Client client = new Client("localhost", 8078 ); //mel.is
		System.out.println("Client connected");
		Scanner s = new Scanner(System.in);
		boolean loop = true;
		
		/**
		 * Object for creating XML from the models
		 */
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();

		while ( loop ) {
			System.out.println( "What do you want to do?" );
			System.out.println( "1 - Login" );
			System.out.println( "2 - Exit" );
			
			int command = s.nextInt();

			switch( command ) {
			case 1:
				s.nextLine();
				UserModel ums = new UserModel();
				System.out.println("Write username");
				ums.setUsername(s.nextLine());
				System.out.println("Write password");
				ums.setPassword(s.nextLine());
				
				ArrayList<Object> alist = new ArrayList<>();
				alist.add(ums);
				
				MSGWrapper wrapper = new MSGWrapper(0, MSGType.REQUEST, MSGFlag.LOGIN, alist);
				
				ByteArrayOutputStream baoss = new ByteArrayOutputStream();
				jaxbMarshaller.jaxbModeltoXML(wrapper, baoss);
				
				System.out.println("==DEBUG==");
				System.out.println(baoss.toString());
				System.out.println("== END ==");
				
				client.writeMessage(baoss.toString());
				
				
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