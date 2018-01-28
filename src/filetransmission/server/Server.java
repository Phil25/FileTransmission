package filetransmission.server;

import java.net.SocketException;

import filetransmission.net.PhilFTPServer;
import filetransmission.tools.ArgParser;

public class Server{

	private static ArgParser argParser = null;

	public static void main(String args[]){
		argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"port", "speed"})){
			System.out.println("Usage: -port <num> -speed <initial speed in KB/s>");
			return;
		}
		try{
			new PhilFTPServer(argParser.getAsInt("port"), argParser.getAsInt("speed"));
		}catch(SocketException e){
			e.printStackTrace();
		}
	}

}
