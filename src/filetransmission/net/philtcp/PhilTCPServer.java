package filetransmission.net.philtcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

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

	public PhilTCPServer(int port){
		this.port = port;
		try{
			this.sock = new DatagramSocket(port);
			System.out.println("Created PhilTCPServer on port " + sock.getLocalPort());
			recv();
		}catch(SocketException e){
		}catch(IOException e){}
	}

	public PhilTCPServer(){
		try{
			this.sock = new DatagramSocket();
		}catch(SocketException e){}
		this.port = this.sock.getLocalPort();
		System.out.println("Created PhilTCPServer on port " + sock.getLocalPort());
		try{
			recv();
		}catch(IOException e){}
	}

	synchronized private void recv() throws IOException{
		while(true){
			PhilTCPPacket packet = recvPacket();
			boolean check = Checksum.check(packet);
			respond(packet.seq, check);
			if(check)
				System.out.println("\"" + packet.getString() + "\"");
		}
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
