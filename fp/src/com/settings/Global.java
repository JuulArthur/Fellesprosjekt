package com.settings;

import java.util.ArrayList;

import layout.IServerResponse;

import com.client.net.ServerHandler;
import com.xml.JAXBMarshaller;

public class Global {
	public static final boolean verbose = true;
	public static ServerHandler sHandler;
	public static JAXBMarshaller jaxbMarshaller;
	public static ArrayList<IServerResponse> respondGUI;
}
