package filetransmission.server;

import filetransmission.tools.ArgParser;

public class Server{

	ArgParser argParser = null;

	public static void main(String args[]){
		argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"port", "speed"})){
			System.out.println("Usage: -port <num> -speed <initial speed in KB/s>");
			return;
		}
	}

}
