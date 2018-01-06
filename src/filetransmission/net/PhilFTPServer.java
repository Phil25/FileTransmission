package filetransmission.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PhilFTPServer extends Thread{

	private DatagramSocket in, out;
	private int speed;

	public PhilFTPServer(int port, int speed){
		try{
			in = new DatagramSocket(port);
			out = new DatagramSocket();
		}catch(SocketException e){
			System.out.println("Cannot create PhilFTPServer: " + e.toString());
			return;
		}
		this.speed = speed;
		this.start();
	}

	@Override
	public void run(){
		DatagramPacket packet = null;
		while(true){
			byte[] buffer = new byte[speed];
		}
	}

}
