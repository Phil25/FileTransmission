package filetransmission.tools;

import filetransmission.net.philtcp.packet.*;

public class Checksum{

	private static final int CHECK_CONST = 123456789;

	private Checksum(){}

	public static int get(PhilTCPHeader packet){
		int res = CHECK_CONST;
		res += CHECK_CONST>>(packet.seq *CHECK_CONST);
		res += CHECK_CONST>>(packet.ack ? 1 : 0);
		res += CHECK_CONST>>(packet.len *CHECK_CONST);
		if(!(packet instanceof PhilTCPPacket))
			return res;
		PhilTCPPacket bodyPacket = (PhilTCPPacket)packet;
		for(byte b : bodyPacket.body)
			res += CHECK_CONST>>(b *CHECK_CONST);
		return res;
	}

	public static boolean check(PhilTCPHeader packet){
		return get(packet) == packet.check;
	}

}
