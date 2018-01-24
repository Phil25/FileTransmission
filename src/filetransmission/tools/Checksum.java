package filetransmission.tools;

import filetransmission.net.philtcp.packet.*;

public class Checksum{

	private Checksum(){}

	public static int get(PhilTCPPacket packet){
		int res = 0;
		res += packet.seq;
		res += packet.len;
		res += packet.ack ? 1 : 0;
		for(byte b : packet.body)
			res += b;
		return res;
	}

	public static boolean check(PhilTCPPacket packet){
		return get(packet) == packet.check;
	}

}
