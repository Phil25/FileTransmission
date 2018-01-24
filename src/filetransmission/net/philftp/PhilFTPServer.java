package filetransmission.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class PhilFTPServer extends Thread{

	private int speed;

	public PhilFTPServer(int port, int speed){
		this.speed = speed;
		this.start();
	}

	@Override
	public void run(){
		DatagramPacket packet = null;
		byte[] buffer = new byte[speed];
	}

}
