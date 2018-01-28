package filetransmission.net.philftp;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import filetransmission.net.philftp.packet.*;
import filetransmission.net.philtcp.PhilTCPClient;
import filetransmission.net.philtcp.PhilTCPServer;
import filetransmission.tools.Checksum;

public class PhilFTPClient extends Thread{

	private byte[] data = null;
	private String path;
	private InetAddress address;
	PhilTCPClient client;
	PhilTCPServer server;

	public PhilFTPClient(String address, int port, String path){
		Path file = Paths.get(path);
		try{
			this.address = InetAddress.getByName(address);
			data = Files.readAllBytes(file);
			client = new PhilTCPClient(this.address, port);
			server = new PhilTCPServer();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		this.start();
	}

	@Override
	public void run(){
	}

}
