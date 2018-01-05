package filetransmission.client;

import filetransmission.net.FTClient;
import filetransmission.tools.ArgParser;

public class Client{

	private static ArgParser argParser = null;

	public static void main(String args[]){
		argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"server", "port", "file"})){
			System.out.println("Usage: client -server <address> -port <num> -file <name>");
			return;
		}
		FTClient client = new FTClient(argParser.get("file"));
	}

}
