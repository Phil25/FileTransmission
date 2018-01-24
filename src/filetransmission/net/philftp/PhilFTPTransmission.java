package filetransmission.net.philftp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import org.omg.CORBA.TIMEOUT;

import filetransmission.net.philftp.packet.PhilFTPPacket;
import filetransmission.tools.Checksum;

public abstract class PhilFTPTransmission extends Thread{

	private static int TIMEOUT = 5000;

	protected DatagramSocket in, out;

	public PhilFTPTransmission(int inPort, int outPort, InetAddress address, int port){
		in = inPort == 0 ? new DatagramSocket() : new DatagramSocket(inPort);
		out = outPort == 0 ? new DatagramSocket() : new DatagramSocket(outPort);
			out.setSoTimeout(TIMEOUT);
	}

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
	protected void send(PhilFTPPacket packet){
	}

	/*private void send(PhilFTPPacket packet){
		send(packet, true);
	}

	private void send(PhilFTPPacket packet, boolean recvAck){
		byte[] buffer = packet.getBytes();
		try{
			while(true){
				out.send(new DatagramPacket(buffer, buffer.length, address, port));
				if(!recvAck)
					break;
				PhilFTPPacket ackPacket = null;
				try{
					ackPacket = recv(false);
				}catch(SocketTimeoutException e){
					continue;
				}
				if(ackPacket == null)
					return;
				if(packet.checkAck(ackPacket) && Checksum.check(ackPacket))
					break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private PhilFTPPacket recv(){
		try{
			return recv(true);
		}catch(SocketTimeoutException e){}
		return null;
	}

	private PhilFTPPacket recv(boolean sendAck) throws SocketTimeoutException{
		try{
			byte[] header = new byte[PhilFTPHeader.HEADER_SIZE];
			DatagramPacket packet = new DatagramPacket(header, PhilFTPHeader.HEADER_SIZE);
			out.receive(packet);

			PhilFTPHeader packetHeader = new PhilFTPHeader(packet.getData());
			if(!sendAck || packetHeader.len == 0)
				return new PhilFTPPacket(packetHeader, null);

			byte[] body = new byte[packetHeader.len];
			packet = new DatagramPacket(body, packetHeader.len);
			out.receive(packet);
			return new PhilFTPPacket(packetHeader, packet.getData());
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}*/

	@Override
	public void run(){}

}
