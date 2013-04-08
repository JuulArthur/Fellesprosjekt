/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    private int randomPortNr = 1000;

    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	super();
    	usedPorts.put(myPort, true);
    	//setter myport og myadress, resten er i abstractconnection
    	this.myPort = myPort;
    	this.myAddress = getIPv4Address();
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
            SocketTimeoutException {
    	if(state != State.CLOSED) {
    		throw new ConnectException("socket is not closed");
    	}
    	this.remotePort = remotePort;
    	this.remoteAddress = remoteAddress.getHostAddress();
    	try {
    		state = State.SYN_SENT;
    		// TODO: get more stuff done..! =P
    	} catch (Exception e) {
    		state = State.CLOSED;
    		throw new IOException("error contacting host " + e);
    	}
    }

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
        //throw new NotImplementedException();
        if (state != state.CLOSED){
        	throw new ConnectException("socket is not closed");
        }
        
        while (true){
        	state = state.LISTEN;
        	KtnDatagram syn = null;
        	syn = receivePacket(true);
        	while(!isValid(syn)){
        		syn = receivePacket(true);
        	}
        	
        	ConnectionImpl connection = new ConnectionImpl(randomPort());
        	connection.state = state.SYN_RCVD;
        	connection.remotePort = syn.getSrc_port();
			connection.remoteAddress = syn.getSrc_addr();
			
			connection.sendAck(syn, true);
			KtnDatagram synAck = connection.receiveAck();
			if (synAck!=null){
				connection.state = connection.state.ESTABLISHED;
				state = state.CLOSED;
				return connection;
			}
			else {
				return null;
			}
        }
        
    }

    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
        throw new NotImplementedException();
    }

    /**
     * Wait for incoming data.
     * 
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
        throw new NotImplementedException();
    }

    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
        throw new NotImplementedException();
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    protected boolean isValid(KtnDatagram packet) {
    	if(packet != null && packet.calculateChecksum() == packet.getChecksum() &&
    			isValidState(packet)) return true;
    	return false;
    }
    
    /**
     * Check if current state is valid for packet
     * 
     * All in all check:
     * 		- null-packets
     * 		- checksum
     * 		- retmoteaddress
     * 		- retmoreport
     * 		- seqno
     * 
     * @param packet
     * @return true if valid state
     */
    private boolean isValidState(KtnDatagram packet) {
    	// check if packet acks last packet
    	if(packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.SYN_ACK &&
    			packet.getAck() != lastDataPacketSent.getSeq_nr())
    		return false;
    	//if package is fin, data has to be null
    	if(packet.getFlag() == Flag.FIN && packet.getPayload() != null)
    		return false;
    	//if state is syn_sent package should be syn_ack and from right host
    	if(state == State.SYN_SENT) {
    		remotePort = packet.getSrc_port();
    		return (packet.getFlag() == Flag.SYN_ACK && remoteAddress.equals(packet.getSrc_addr()));
    	} else if (state == State.LISTEN)
    		return (packet.getFlag() == Flag.SYN);
    	//other packets port and source should be set, checking that
    	else if(packet.getSrc_addr() != remoteAddress && packet.getSrc_port() != remotePort)
    		return false;
    	//have to be ack
    	else if (state == State.SYN_RCVD)
    		return (packet.getFlag() == Flag.ACK);
    	//want ack or fin back
    	else if(state == State.FIN_WAIT_1 || state == State.FIN_WAIT_2)
    		return (packet.getFlag() == Flag.FIN || packet.getFlag() == Flag.ACK);
    	//has to be fin
    	else if (state == State.CLOSE_WAIT)
    		return (packet.getFlag() == Flag.FIN);
    	//need to check the rest, if not return true
    	return true;
    }
    
    private int randomPort(){
    	if(this.randomPortNr==9999){
    		randomPortNr=1000;
    	}
    	else{
    		randomPortNr++;
    	}
    	return randomPortNr;
    }
}
