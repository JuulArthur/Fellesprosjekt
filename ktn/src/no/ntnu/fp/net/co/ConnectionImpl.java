/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections
			.synchronizedMap(new HashMap<Integer, Boolean>());
	private int randomPortNr = 1000;
	private final int MAXRECEIVES = 30;
	private int rereceives = 0;

	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		super();
		usedPorts.put(myPort, true);
		// setter myport og myadress, resten er i abstractconnection
		this.myPort = myPort;
		this.myAddress = getIPv4Address();
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
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
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		if (state != State.CLOSED) {
			throw new ConnectException("socket is not closed");
		}
		this.remotePort = remotePort;
		this.remoteAddress = remoteAddress.getHostAddress();
		try {
			state = State.SYN_SENT;
			KtnDatagram sent = constructInternalPacket(Flag.SYN);
			simplySendPacket(sent);
			lastDataPacketSent = sent;
			KtnDatagram receive = receiveAck();
			lastValidPacketReceived = receive;
			if (isValid(receive)) {
				sent = constructInternalPacket(Flag.ACK);
				sendAck(sent, false);
				lastDataPacketSent = sent;
				state = State.ESTABLISHED;
			} else {
				throw new ConnectException("no valid ack received");
			}
		} catch (Exception e) {
			state = State.CLOSED;
			e.printStackTrace();
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

		// throw new NotImplementedException();
		if (state != state.CLOSED) {
			throw new ConnectException("socket is not closed");
		}

		while (true) {
			state = state.LISTEN;
			KtnDatagram syn = null;
			syn = receivePacket(true);
			while (!isValid(syn)) {
				syn = receivePacket(true);
			}

			ConnectionImpl connection = new ConnectionImpl(randomPort());
			connection.state = state.SYN_RCVD;
			connection.remotePort = syn.getSrc_port();
			connection.remoteAddress = syn.getSrc_addr();

			connection.sendAck(syn, true);
			lastDataPacketSent = syn;

			KtnDatagram synAck = connection.receiveAck();
			if (synAck != null) {
				lastValidPacketReceived = synAck;
				connection.state = connection.state.ESTABLISHED;
				state = state.CLOSED;
				return connection;
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
		if (state != State.ESTABLISHED)
			throw new ConnectException("No connection exists");

		KtnDatagram sendDatagram = constructDataPacket(msg);

		KtnDatagram recievedDatagram = sendDataPacketWithRetransmit(sendDatagram);

		if (!isValid(recievedDatagram)) {
			throw new IOException("No ack was recieved from the send operation");
		}

		lastValidPacketReceived = recievedDatagram;

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
		KtnDatagram packet = null;

		try {
			packet = receivePacket(false);
		} catch (EOFException e) {
			// fikk en FIN
			state = State.CLOSE_WAIT;
			throw new EOFException();
		}

		if (packet == null) { // got timeout
			if (rereceives < this.MAXRECEIVES) {
				rereceives++;
				String msg = receive();
				rereceives = 0;
				return msg;
			} else {
				state = State.CLOSED;
				throw new ConnectException();
			}
		} else { // packet got received
			if (isValid(packet)) {
				if (lastValidPacketReceived != null
						&& packet.getSeq_nr() - 1 != lastValidPacketReceived
								.getSeq_nr()) {
					System.out.println("1");
					sendAck(lastValidPacketReceived, false);
					return receive();
				} else {
					System.out.println("2");
					sendAck(packet, false);
					lastValidPacketReceived = packet;
					return (String) packet.getPayload();
				}
			} else {
				if (lastValidPacketReceived != null) { // checking if this was
														// the first received
														// package
					System.out.println("3");
					sendAck(lastValidPacketReceived, false); // Requests a
																// resend
					return receive();
				}
				return receive();
			}
		}
	}

	/*
	 * if (state != State.CLOSED) { KtnDatagram thisPacket = null;
	 * 
	 * /* if (thisPacket.getFlag() == Flag.SYN) { KtnDatagram thisInternalPacket
	 * = constructInternalPacket(Flag.SYN_ACK); sendAck(thisInternalPacket,
	 * true); lastDataPacketSent = thisInternalPacket; KtnDatagram recivedAck =
	 * receiveAck();
	 * 
	 * if (isValid(recivedAck)) { lastValidPacketReceived = recivedAck; state =
	 * State.ESTABLISHED; String result = "Har godtatt ACK"; return result; } }
	 * 
	 * else if (thisPacket.getFlag() == Flag.FIN) { KtnDatagram
	 * thisInternalDatagram = constructInternalPacket(Flag.ACK);
	 * sendAck(thisInternalDatagram, false); lastDataPacketSent =
	 * thisInternalDatagram; KtnDatagram newInternalDatagram =
	 * constructInternalPacket(Flag.FIN);
	 * sendDataPacketWithRetransmit(newInternalDatagram); lastDataPacketSent =
	 * newInternalDatagram;
	 * 
	 * return "er litt usikker paa hva som skjer, har naa i teorien closed()"; }
	 */
	// We are in an established state
	/*
	 * if(state == State.ESTABLISHED) try { /** We want packets with data. So we
	 * need reveivePacket(false)
	 */
	/*
	 * thisPacket = receivePacket(false); System.out.println("PACKAGE: " +
	 * thisPacket.getFlag());
	 * 
	 * if (isValid(thisPacket)) { lastValidPacketReceived = thisPacket; /** We
	 * have a packet that needs to be ACKED. Flag.NONE is a data packet
	 */
	/*
	 * if (thisPacket.getFlag() == Flag.NONE) { /** SendPacket handles
	 * construction of ACK package according to recieved package
	 */

	/*
	 * sendAck(thisPacket, false); lastDataPacketSent = thisPacket; return
	 * thisPacket.toString(); } } } catch (EOFException e) { //EOF means
	 * connection close
	 * System.out.println("EOFException: Probably a close request");
	 * if(thisPacket != null) sendAck(thisPacket, false);
	 * 
	 * KtnDatagram close = receivePacket(true); System.out.println("1234" +
	 * close.getFlag()); } }
	 * 
	 * return null; }
	 * 
	 * /** Close the connection.
	 * 
	 * @see Connection#close()
	 */

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		if (state != State.ESTABLISHED) {
			throw new ConnectException("No connection exists");
		}
		// Send Packet with FIN
		try {
			KtnDatagram internalFin = constructInternalPacket(Flag.FIN);
			state = State.FIN_WAIT_1;
			KtnDatagram receivedfin = null;

			while (true) {
				simplySendPacket(internalFin);
				KtnDatagram finack = receiveAck();

				// Received FIN
				if (isValid(finack) && finack.getFlag() == Flag.FIN) {
					state = State.FIN_WAIT_2;
					break;
				}
				// Received ACK
				else if (isValid(finack) && finack.getFlag() == Flag.ACK) {
					state = State.LAST_ACK;
					receivedfin = finack;
					break;
				}
			}

			if (state == State.FIN_WAIT_2) {
				while (true) {
					// Waiting for FIN
					while (!isValid(receivedfin)) {
						receivedfin = receivePacket(true);
					}
					// Sending ack
					sendAck(receivedfin, false);
					state = state.CLOSE_WAIT;
					receivedfin = receiveAck();

					// break if timeout
					if (receivedfin == null) {
						break;
					}
				}
			}

			// No need to wait for FIN
			else if (state == State.LAST_ACK) {
				while (true) {
					sendAck(receivedfin, false);
					state = State.CLOSE_WAIT;
					receivedfin = receiveAck();

					if (receivedfin == null) {
						break;
					}
				}
			}

			// State is now closed, and the port is removed
			usedPorts.remove(myPort);
			state = State.CLOSED;

		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		if (packet != null // Checksum only for data packets
				&& packet.calculateChecksum() == packet.getChecksum()
				&& isValidState(packet))
			return true;
		return false;
	}

	/**
	 * Check if current state is valid for packet
	 * 
	 * All in all check: - null-packets - checksum - retmoteaddress -
	 * retmoreport - seqno
	 * 
	 * @param packet
	 * @return true if valid state
	 */
	private boolean isValidState(KtnDatagram packet) {
		// check if packet acks last packet
		if ((packet.getFlag() == Flag.ACK || packet.getFlag() == Flag.SYN_ACK)
				&& packet.getAck() != lastDataPacketSent.getSeq_nr()) {
			return false;
		}

		// if package is fin, data has to be null
		if (packet.getFlag() == Flag.FIN && packet.getPayload() != null) {
			System.out.println("VALIDSTATECHECK FAIL 2");

			return false;
		}
		// if state is syn_sent package should be syn_ack and from right host
		if (state == State.SYN_SENT) {
			remotePort = packet.getSrc_port();
			return (packet.getFlag() == Flag.SYN_ACK && remoteAddress
					.equals(packet.getSrc_addr()));
		} else if (state == State.LISTEN)
			return (packet.getFlag() == Flag.SYN);
		// other packets port and source should be set, checking that
		else if (packet.getSrc_addr() != remoteAddress
				&& packet.getSrc_port() != remotePort)
			return false;
		// have to be ack
		else if (state == State.SYN_RCVD)
			return (packet.getFlag() == Flag.ACK);
		// want ack or fin back
		else if (state == State.FIN_WAIT_1 || state == State.FIN_WAIT_2)
			return (packet.getFlag() == Flag.FIN || packet.getFlag() == Flag.ACK);
		// has to be fin
		else if (state == State.CLOSE_WAIT)
			return (packet.getFlag() == Flag.FIN);
		// need to check the rest, if not return true
		return true;
	}

	private int randomPort() {

		do {
			if (this.randomPortNr == 9999) {
				randomPortNr = 1000;
			}
			randomPortNr++;
		} while (!available(randomPortNr));

		return randomPortNr;
	}

	/**
	 * Checks to see if a specific port is available.
	 * 
	 * @param port
	 *            the port to check for availability
	 */
	public static boolean available(int port) {
		// if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
		// throw new IllegalArgumentException("Invalid start port: " + port);
		// }

		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}
