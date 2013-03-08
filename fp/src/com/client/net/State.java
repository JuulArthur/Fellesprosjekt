package com.client.net;

/**
 * The different states for the client
 * @author perok
 *
 */
public enum State {
	Connecting, //Client needs to log in. Do not change if login fails.
	Connected, //Client is logged in and can send command
	Disconnected //Client has lost connection or timed out
}
