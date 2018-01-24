package filetransmission.client;

import filetransmission.net.philftp.PhilFTPClient;
import filetransmission.tools.ArgParser;
import filetransmission.tools.ByteOps;

public class Client{

	private static ArgParser argParser = null;

	public static void main(String args[]){
		argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"server", "port", "file"})){
			System.out.println("Usage: client -server <address> -port <num> -file <name>");
			return;
		}
		new PhilFTPClient(argParser.get("server"), argParser.getAsInt("port"), argParser.get("file"));
	}

}
