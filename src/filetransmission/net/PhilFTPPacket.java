package filetransmission.net;

import java.util.Arrays;

public class PhilFTPPacket extends PhilFTPHeader{

	public byte[] body;

	public PhilFTPPacket(int check, int id, int type, byte[] body){
		this(check, id, type, false, body);
	}

	public PhilFTPPacket(int check, int id, int type, boolean ack, byte[] body){
		super(check, id, type, ack, body.length);
		this.body = body;
	}

	public PhilFTPPacket(byte[] buffer){
		super(buffer);
		body = Arrays.copyOfRange(buffer, HEADER_SIZE, HEADER_SIZE +len);
	}

	public PhilFTPPacket(PhilFTPHeader header, byte[] body){
		super(header);
		this.body = body;
	}

	public byte[] getBytes(){
		byte[] header = super.getBytes();
		byte[] buffer = new byte[HEADER_SIZE +len];
		System.arraycopy(header, 0, buffer, 0, HEADER_SIZE);
		System.arraycopy(body, 0, buffer, HEADER_SIZE, len);
		return buffer;
	}

	public int getInt(){
		return toInt32(body, 0);
	}

	public String getString(){
		return new String(body);
	}

	public boolean checkAck(PhilFTPPacket other){
		return this.id == other.id && other.ack;
	}

	@Override
	public String toString(){
		return
			super.toString() + "\n" +
			(body.length > 128 ? "[data too big to display]"  : Arrays.toString(body));
	}

}
