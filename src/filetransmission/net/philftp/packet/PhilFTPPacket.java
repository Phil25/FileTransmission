package filetransmission.net.philftp.packet;

import java.util.Arrays;

import filetransmission.tools.ByteOps;

public class PhilFTPPacket extends PhilFTPHeader{

	public byte[] body;

	public PhilFTPPacket(int type, byte[] body){
		super(type, body.length);
		this.body = body;
	}

	public PhilFTPPacket(byte[] buffer){
		super(buffer);
		this.body = Arrays.copyOfRange(buffer, HEADER_SIZE, HEADER_SIZE +len);
	}

	public PhilFTPPacket(PhilFTPHeader header, byte[] body){
		super(header);
		this.body = body;
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

	@Override
	public String toString(){
		return
			super.toString() + "\n" +
			(body.length > 128 ? "[data too big to display]"  : Arrays.toString(body));
	}

}
