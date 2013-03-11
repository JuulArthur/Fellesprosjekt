package com.net.support;

/**
 * State flow for the program
 * @author perok
 *
 */
public enum State {
	CONNECTED, //Client is logged in and can send command
	DISCONNECTED, //Client is not connected
	CONNECTED_WAITING //We are waiting for a response from the server
}
