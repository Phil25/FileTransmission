package filetransmission.client;

import java.net.InetAddress;

import filetransmission.net.philftp.PhilFTPClient;
import filetransmission.net.philtcp.PhilTCPClient;
import filetransmission.tools.ArgParser;

public class Client{

	public static void main(String args[]){
		ArgParser argParser = new ArgParser(args);
		if(!argParser.check(new String[]{"server", "port", "file"})){
			System.out.println("Usage: client -server <address> -port <num> -file <name>");
			return;
		}
		//new PhilFTPClient(argParser.get("server"), argParser.getAsInt("port"), argParser.get("file"));
		try{
			InetAddress address = InetAddress.getByName(argParser.get("server"));
			PhilTCPClient client = new PhilTCPClient(address, argParser.getAsInt("port"));
			int i = 0;
			while(i++ < 1000)
			client.send("This is just a test. Please do not be alarmed. How the hell am I supposed to write so much stuff so that it will go beyond one packet, of like 1024 bits in size? Well this, this can't be that hard right? I mean, it's just like writing a really short text where you don't even have to worry about the context. This should be super simple. And it is. But god damn it, I'm wasting so much time doing that. I still have to finish the SDB project and I still haven't even started. What the hell is wrong with me? I don't care about these dumb databases so much and I've grown to despise this 'more advanced' SQL. The syntax is so disgusting I can't even look at it anymore.This is just a test. Please do not be alarmed. How the hell am I supposed to write so much stuff so that it will go beyond one packet, of like 1024 bits in size? Well this, this can't be that hard right? I mean, it's just like writing a really short text where you don't even have to worry about the context. This should be super simple. And it is. But god damn it, I'm wasting so much time doing that. I still have to finish the SDB project and I still haven't even started. What the hell is wrong with me? I don't care about these dumb databases so much and I've grown to despise this 'more advanced' SQL. The syntax is so disgusting I can't even look at it anymore.This is just a test. Please do not be alarmed. How the hell am I supposed to write so much stuff so that it will go beyond one packet, of like 1024 bits in size? Well this, this can't be that hard right? I mean, it's just like writing a really short text where you don't even have to worry about the context. This should be super simple. And it is. But god damn it, I'm wasting so much time doing that. I still have to finish the SDB project and I still haven't even started. What the hell is wrong with me? I don't care about these dumb databases so much and I've grown to despise this 'more advanced' SQL. The syntax is so disgusting I can't even look at it anymore.This is just a test. Please do not be alarmed. How the hell am I supposed to write so much stuff so that it will go beyond one packet, of like 1024 bits in size? Well this, this can't be that hard right? I mean, it's just like writing a really short text where you don't even have to worry about the context. This should be super simple. And it is. But god damn it, I'm wasting so much time doing that. I still have to finish the SDB project and I still haven't even started. What the hell is wrong with me? I don't care about these dumb databases so much and I've grown to despise this 'more advanced' SQL. The syntax is so disgusting I can't even look at it anymore.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
