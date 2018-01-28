package filetransmission.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import filetransmission.net.philtcp.PhilTCPServer;
import filetransmission.net.philtcp.packet.PhilTCPPacket;

public class PhilFTPServer extends Thread{

	private int speed;
	private PhilTCPServer server;
	private FileOutputStream stream;

	public PhilFTPServer(int port, int speed) throws SocketException{
		this.speed = speed;
		this.server = new PhilTCPServer(port);
		this.stream = null;
		this.start();
	}

	@Override
	public void run(){
		try{
			PhilTCPPacket packet = null;
			while(true){
				packet = server.recv();
				if(packet == null)
					continue;
				if(stream == null){
					try{
						stream = new FileOutputStream(new File("incoming/" + packet.getString()));
					}catch(FileNotFoundException e){
						e.printStackTrace();
						break;
					}
				}else stream.write(packet.body);
			}
		}catch(IOException e){}
	}

}
