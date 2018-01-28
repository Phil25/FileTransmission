package filetransmission.net.philtcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Random;

import filetransmission.net.philtcp.packet.PhilTCPHeader;
import filetransmission.net.philtcp.packet.PhilTCPPacket;
import filetransmission.tools.Checksum;

public class PhilTCPServer{

	private volatile int seq = 0;
	private volatile int lastSeq = 0;
	private volatile InetAddress returnAddress;
	private volatile int returnPort;

	private final int port;
	private DatagramSocket sock;

	public PhilTCPServer(int port) throws SocketException{
		this.port = port;
		this.sock = new DatagramSocket(port);
		System.out.println("Created PhilTCPServer on port " + sock.getLocalPort());
	}

	public PhilTCPServer() throws SocketException{
		this.sock = new DatagramSocket();
		this.port = this.sock.getLocalPort();
		System.out.println("Created PhilTCPServer on port " + this.port);
	}

	synchronized public PhilTCPPacket recv() throws IOException{
		PhilTCPPacket packet = recvPacket();
		boolean check = Checksum.check(packet);
		respond(packet.seq, check);
		return check ? packet : null;
	}

	synchronized private PhilTCPPacket recvPacket() throws IOException{
		byte[] msg = new byte[PhilTCPHeader.PACKET_SIZE_MAX];
		DatagramPacket packet = new DatagramPacket(msg, msg.length);
		sock.setSoTimeout(0);
		sock.receive(packet);
		returnAddress = packet.getAddress();
		returnPort = packet.getPort();
		return new PhilTCPPacket(packet.getData());
	}

	synchronized private void respond(int seq) throws IOException{
		respond(seq, true);
	}

	synchronized private void respond(int seq, boolean positive) throws IOException{
		PhilTCPHeader packet = new PhilTCPHeader(seq, positive, 0, 0);
		packet.check = Checksum.get(packet);
		byte[] data = packet.getBytes();
		DatagramPacket ack = new DatagramPacket(data, data.length, returnAddress, returnPort);
		sock.send(ack);
	}

}
