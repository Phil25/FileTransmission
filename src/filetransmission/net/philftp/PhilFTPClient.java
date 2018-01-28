package filetransmission.net.philftp;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import filetransmission.net.philftp.packet.*;
import filetransmission.net.philtcp.PhilTCPClient;

public class PhilFTPClient extends Thread{

	private byte[] data = null;
	private String path;
	private InetAddress address;
	private PhilTCPClient client;

	public PhilFTPClient(String address, int port, String path){
		this.path = path;
		Path file = Paths.get(path);
		try{
			this.address = InetAddress.getByName(address);
			data = Files.readAllBytes(file);
			client = new PhilTCPClient(this.address, port);
			this.start();
		}catch(Exception e){}
	}

	@Override
	public void run(){
		try{
			client.send(path);
			client.send(data);
		}catch(IOException e){}
	}

}
