package filetransmission.net;

import java.net.DatagramSocket;
import java.net.SocketException;

public class FTServer{

	private DatagramSocket in, out;

	public FTServer(int port){
		try{
			in = new DatagramSocket(port);
			out = new DatagramSocket();
		}catch(SocketException e){
			System.out.println("Cannot create FTServer: " + e.toString());
			return;
		}
	}

}
