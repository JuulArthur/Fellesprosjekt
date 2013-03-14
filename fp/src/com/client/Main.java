package com.client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import com.client.net.ServerHandler;
import com.model.AppointmentModel;
import com.model.UserModel;
import com.net.msg.MSGFlagSubject;
import com.net.msg.MSGFlagVerb;
import com.net.msg.MSGType;
import com.net.support.State;
import com.xml.JAXBMarshaller;

//import com.xml.XML;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("Preparing client");
		ServerHandler serverH = new ServerHandler("localhost", 8078 ); //mel.is
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
			System.out.println( "3 - TEST: Create DB appointment");
			
			int command = s.nextInt();

			switch( command ) {
			case 1:
				s.nextLine();
				UserModel ums = new UserModel();
				System.out.println("Write username");
				ums.setUsername(s.nextLine());
				System.out.println("Write password");
				ums.setPassword(s.nextLine());
				
				ArrayList<Object> alist = new ArrayList<Object>();
				alist.add(ums);
				
				serverH.setCurrentFlag(MSGFlagVerb.LOGIN);
				serverH.writeMessage(jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGIN, alist));
				
				break;
			case 2:
				serverH.setCurrentFlag(MSGFlagVerb.LOGOUT);
				serverH.writeMessage(jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.LOGOUT, null));
				//serverH.disconnect();
				loop = false;
				break;
			case 3:
				if(serverH.getState() == State.DISCONNECTED)
					continue;
				
				java.util.Date time = new java.util.Date();
				System.out.println(time.getTime());
				
				AppointmentModel am = new AppointmentModel(1337, 2343, 23432, 
						new UserModel("perok", "hei", null, null, null, null, 1), 
						
						"NO SO SEXEHTIME", "best time of the day", "room", new Date(time.getTime()), null);
				ArrayList<Object> aalist = new ArrayList<Object>();
				aalist.add(am);
				serverH.setCurrentFlag(MSGFlagVerb.CREATE);
				serverH.setState(State.CONNECTED_WAITING);
				serverH.writeMessage(jaxbMarshaller.getXMLRepresentation(0, MSGType.REQUEST, MSGFlagVerb.UPDATE,MSGFlagSubject.APPOINTMENT, aalist));
				break;
				
			default:
				System.out.println("I don't understand you");
			}
		}

		serverH.disconnect();
		
		System.exit(0);

	}

}