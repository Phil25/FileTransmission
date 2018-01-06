package filetransmission.tools;

import filetransmission.net.PhilFTPPacket;

public class Checksum{

	private Checksum(){}

	public static int get(PhilFTPPacket packet){
		int res = 0;
		res += packet.id;
		res += packet.type;
		res += packet.len;
		for(byte b : packet.body)
			res += b;
		return res;
	}

	public static boolean check(PhilFTPPacket packet){
		return get(packet) == packet.check;
	}

}
