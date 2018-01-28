package filetransmission.net.philtcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

import filetransmission.net.philtcp.packet.*;
import filetransmission.tools.Checksum;

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

	private static int SEQ_LIMIT = 1 << 15;
	private static int WINDOW_SIZE = 64;

	private volatile int seq = 0;
	private volatile int ackSeq = 0;
	private volatile int lastAckSeq = 0;

	private InetAddress address;
	private int port;
	private int id;
	private DatagramSocket sock;
	private ArrayList<PhilTCPPacket> history;

	public PhilTCPClient(InetAddress address, int port) throws SocketException{
		this.address = address;
		this.port = port;
		this.id = 0;
		this.sock = new DatagramSocket();
		this.history = new ArrayList<PhilTCPPacket>();
	}

	synchronized public void send(String data) throws IOException{
		this.send(data.getBytes());
	}

	synchronized public void send(byte[] data) throws IOException{
		byte[][] chunks = splitToChunks(data, PhilTCPHeader.BODY_SIZE_MAX);
		for(int i = 0; i < chunks.length; i++){
			PhilTCPPacket packet = new PhilTCPPacket(seq, chunks[i]);
			send(packet);
		}
	}

	synchronized private void send(PhilTCPPacket philPacket) throws IOException{
		byte[] data = philPacket.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		System.out.println("\tOUTGOING");
		System.out.println(philPacket);
		sock.send(packet);

		byte[] buffer = new byte[PhilTCPHeader.HEADER_SIZE];
		DatagramPacket ackpack = new DatagramPacket(buffer, buffer.length);
		while(true){
			try{
				sock.setSoTimeout(64);
				sock.receive(ackpack);
				PhilTCPPacket ack = new PhilTCPPacket(ackpack.getData());
				System.out.println("\tINCOMING");
				System.out.println(ack);
				if(ack.seq == seq && ack.ack && Checksum.check(ack))
					break;
			}catch(SocketTimeoutException e){}
			sock.send(packet);
		}
		if(++seq >= SEQ_LIMIT)
			seq = 0;
	}

	private void sendPacket(PhilTCPPacket packet){
		byte[] data = packet.getBytes();
		System.out.println(packet);
		try{
			sock.send(new DatagramPacket(data, data.length, address, port));
		}catch(IOException e){}
	}

	private byte[][] splitToChunks(final byte[] data, final int size){
		final int len = data.length;
		final byte[][] chunks = new byte[(len +size -1)/size][];
		int chunkCount = 0, chunkEnd = 0;
		for(int i = 0; i +size <= len; i += size){
			chunkEnd += size;
			chunks[chunkCount++] = Arrays.copyOfRange(data, i, chunkEnd);
		}
		if(chunkEnd < len)
			chunks[chunkCount] = Arrays.copyOfRange(data, chunkEnd, len);
		return chunks;
	}

	private int nextSeq(){
		int i = seq++;
		seq %= SEQ_LIMIT;
		return i;
	}

}
