package filetransmission.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import filetransmission.net.PhilFTPHeader;
import filetransmission.net.PhilFTPPacket;
import filetransmission.tools.Checksum;

public class PhilFTPClient extends Thread{

	private byte[] data = null;
	private int speed;
	private int id;
	private int port;
	private String path;
	private DatagramSocket in, out;
	private InetAddress address;

	public PhilFTPClient(String address, int port, String path){
		this.path = path;
		Path file = Paths.get(path);
		try{
			this.address = InetAddress.getByName(address);
			data = Files.readAllBytes(file);
			in = new DatagramSocket();
			this.port = in.getPort();
			out = new DatagramSocket(port);
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		this.start();
	}

	@Override
	public void run(){
		send(newPacket(PhilFTPHeader.TYPE_FILE_NAME, path));
		speed = recv().getInt();
		send(newPacket(PhilFTPHeader.TYPE_RECV_PORT, "" + port));
	}

	private void send(PhilFTPPacket packet){
		byte[] buffer = packet.getBytes();
		try{
			while(true){
				out.send(new DatagramPacket(buffer, buffer.length, address, port));
				PhilFTPPacket ackPacket = recv(false);
				if(packet.checkAck(ackPacket) && Checksum.check(ackPacket))
					break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private PhilFTPPacket recv(){
		return recv(true);
	}

	private PhilFTPPacket recv(boolean sendAck){
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
			return null;
		}

	}

	private PhilFTPPacket newPacket(int type, String data){
		return newPacket(type, data.getBytes());
	}

	private PhilFTPPacket newPacket(int type, byte[] buffer){
		PhilFTPPacket packet = new PhilFTPPacket(0, id++, type, buffer);
		packet.setChecksum(Checksum.get(packet));
		return packet;
	}

}
