package filetransmission.net.philtcp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PhilTCPClient{

	/*
		1. send packet out
		2. mark which packet ID to wait for
			2.1. disregard every packet of invalid ID
		3. start a timeout
			3.1. if ends, resend packet
		4. receive packet, check ID
			4.1 if incorrect, disregard
			4.2. if correct check checksum
				4.2.1. if incorrect, resend packet
		5. check if ACK is set
			5.1. if not, resend
	*/

	private static int ID_LIMIT = 1 << 7;

	private InetAddress address;
	private int port;
	private int id;
	private DatagramSocket sock;

	public PhilTCPClient(InetAddress address, int port) throws SocketException{
		this.address = address;
		this.port = port;
		this.id = 0;
		this.sock = new DatagramSocket();
	}

	/*public void send(byte[] data){
		PhilTCPPacket packet = new PhilTCPPacket(0, nextId(), data);
		packet.check = Check.get(packet);
		sendPacket(packet.getBytes());
	}

	private void sendPacket(byte[] buffer){
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		sock.send(packet);
		byte[] ack = new byte[PhilTCPHeader.PHILTCP_HEADER_SIZE];
		packet = new(ack, PhilTCPHeader.PHILTCP_HEADER_SIZE);
		sock.receive(packet);
	}*/

	private int nextId(){
		if(++id >= ID_LIMIT)
			id = 0;
		return id;
	}

}
