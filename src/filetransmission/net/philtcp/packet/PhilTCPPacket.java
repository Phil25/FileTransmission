package filetransmission.net.philtcp.packet;

import java.util.Arrays;

import filetransmission.tools.ByteOps;

public class PhilTCPPacket extends PhilTCPHeader{

	public byte[] body;

	public PhilTCPPacket(int seq, byte[] body){
		this(seq, false, 0, body);
	}

	public PhilTCPPacket(int seq, int check, byte[] body){
		this(seq, false, check, body);
	}

	public PhilTCPPacket(PhilTCPHeader header, byte[] body){
		this(header.seq, header.ack, header.check, body);
	}

	public PhilTCPPacket(int seq, boolean ack, int check, byte[] body){
		super(seq, ack, check, body.length);
		this.body = Arrays.copyOfRange(body, 0, body.length);
	}

	public PhilTCPPacket(byte[] buffer){
		super(buffer);
		this.body = Arrays.copyOfRange(buffer, HEADER_SIZE, HEADER_SIZE +len);
	}

	public byte[] getBytes(){
		return ByteOps.concat(super.getBytes(), body);
	}

	public int getInt(){
		return ByteOps.toInt32(body, 0);
	}

	public String getString(){
		return new String(body);
	}

	public boolean checkAck(PhilTCPPacket other){
		return this.seq == other.seq && other.ack;
	}

	@Override
	public String toString(){
		return
			super.toString() + "\n" +
			(body.length > 128 ? "[data too big to display]" : Arrays.toString(body));
	}

}
