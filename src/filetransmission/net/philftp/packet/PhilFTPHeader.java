package filetransmission.net.philftp.packet;

import filetransmission.tools.ByteOps;

public class PhilFTPHeader{

	public final static int HEADER_SIZE = 5;

	public final static int TYPE_RECV_PORT = 0; // defines the receiving port
	public final static int TYPE_FILE_NAME = 1; // defines the file name
	public final static int TYPE_SPEED = 2; // sets the speed
	public final static int TYPE_DATA = 3; // regular data transfer

	public int type;	// type of the data, big-endian, 8-bit
	public int len;		// length of body, big-endian, 32-bit

	public PhilFTPHeader(int type, int len){
		this.type = type;
		this.len = len;
	}

	public PhilFTPHeader(byte[] buffer){
		this.type = ByteOps.toInt8(buffer, 0);
		this.len = ByteOps.toInt32(buffer, 1);
	}

	public PhilFTPHeader(PhilFTPHeader other){
		this.type = other.type;
		this.len = other.len;
	}

	public byte[] getBytes(){
		byte[] buffer = new byte[HEADER_SIZE];
		ByteOps.intToByte(buffer, 0, type);
		ByteOps.intToBytes(buffer, 1, len);
		return buffer;
	}

	@Override
	public String toString(){
		return
			String.format("%12s", "type : ") + this.type + "\n" +
			String.format("%12s", "len : ") + this.len;
	}

}
