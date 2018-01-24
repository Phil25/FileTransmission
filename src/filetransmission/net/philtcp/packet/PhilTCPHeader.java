package filetransmission.net.philtcp.packet;

import filetransmission.tools.ByteOps;

public class PhilTCPHeader{

	public final static int PACKET_SIZE_MAX = 1024;
	public final static int HEADER_SIZE = 8;
	public final static int BODY_SIZE_MAX = PACKET_SIZE_MAX -HEADER_SIZE;

	public int seq;		// sequence number of the packet, big-endian, 15-bit
	public boolean ack;	// acknowledgement of correct receival
	public int check;	// checksum, big-endian, 32-bit
	public int len;		// length of body, big-endian, 16-bit

	public PhilTCPHeader(int seq, boolean ack, int check, int len){
		this.seq = seq;
		this.ack = ack;
		this.check = check;
		this.len = len;
	}

	public PhilTCPHeader(byte[] buffer){
		this.seq = ByteOps.get15Bits(buffer, 0);
		this.ack = ByteOps.getLastBit(buffer, 1);
		this.check = ByteOps.toInt32(buffer, 2);
		this.len = ByteOps.get16Bits(buffer, 6);
	}

	public PhilTCPHeader(PhilTCPHeader other){
		this.seq = other.seq;
		this.ack = other.ack;
		this.check = other.check;
		this.len = other.len;
	}

	public byte[] getBytes(){
		byte[] buffer = new byte[HEADER_SIZE];
		ByteOps.get7Bits(buffer, 0);
		ByteOps.getLastBit(buffer, 1);
		ByteOps.toInt32(buffer, 2);
		ByteOps.get16Bits(buffer, 6);
		return buffer;
	}

	@Override
	public String toString(){
		return
			String.format("%12s", "seq : ") + this.seq + "\n" +
			String.format("%12s", "ack : ") + this.ack + "\n" +
			String.format("%12s", "check : ") + this.check + "\n" +
			String.format("%12s", "len : ") + this.len;
	}

}
