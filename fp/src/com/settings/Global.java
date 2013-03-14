package com.settings;

import java.util.ArrayList;


import com.client.net.ServerHandler;
import com.controller.IServerResponse;
import com.xml.JAXBMarshaller;

public class Global {
	public static final boolean verbose = true;
	public static ServerHandler sHandler;
	public static JAXBMarshaller jaxbMarshaller;
	public static ArrayList<IServerResponse> respondGUI;
}
