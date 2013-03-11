package com.net.support;

/**
 * The different states for the client
 * @author perok
 *
 */
public enum State {
	CONNECTING, //Client needs to log in. Do not change if login fails.
	CONNECTED, //Client is logged in and can send command
	DISCONNECTED //Client has lost connection or timed out
}
