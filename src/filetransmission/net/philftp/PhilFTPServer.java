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
		try{
			this.stream = new FileOutputStream(new File("test.pdf"));
			this.start();
		}catch(FileNotFoundException e){}
	}

	@Override
	public void run(){
		try{
			while(true){
				PhilTCPPacket packet = server.recv();
				if(packet != null)
					stream.write(packet.body);
			}
		}catch(IOException e){}
	}

}
