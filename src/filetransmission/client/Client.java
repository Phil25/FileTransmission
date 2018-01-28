package filetransmission.client;


import filetransmission.net.philftp.PhilFTPClient;
import filetransmission.tools.ArgParser;

public class Client{

	public static void main(String args[]){
		ArgParser argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"server", "port", "file"})){
			System.out.println("Usage: client -server <address> -port <num> -file <name>");
			return;
		}
		try{
			new PhilFTPClient(argParser.get("server"), argParser.getAsInt("port"), argParser.get("file"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
