package filetransmission.net;

public class PhilFTPHeader{

	public final static int HEADER_SIZE = 13; // 4 4-byte integers

	public final static int TYPE_RECV_PORT = 0; // defines the receiving port
	public final static int TYPE_FILE_NAME = 1; // defines the file name
	public final static int TYPE_SPEED = 2; // sets the speed
	public final static int TYPE_DATA = 3; // regular data transfer

	public int check;	// checksum, big-endian
	public int id;		// id of the packet, big-endian
	public int type;	// type of the data, big-endian
	public int len;		// length of body, big-endian

	public PhilFTPHeader(int check, int id, int type, int len){
		this.check = check;
		this.id = id;
		this.type = type;
		this.len = len;
	}

	public PhilFTPHeader(byte[] buffer){
		this.check = toInt32(buffer, 0);
		this.id = toInt32(buffer, 4);
		this.type = buffer[8];
		this.len = toInt32(buffer, 9);
	}

	public PhilFTPHeader(PhilFTPHeader other){
		this.check = other.check;
		this.id = other.id;
		this.type = other.type;
		this.len = other.len;
	}

	protected int toInt32(byte[] buffer, int start){ // big-endian
		return
			buffer[start] << 24 |
			(buffer[start+1] & 0xFF) << 16 |
			(buffer[start+2] & 0xFF) << 8 |
			(buffer[start+3] & 0xFF);
	}

	protected void intToBytes(byte[] buffer, int start, int val){
		buffer[start] = (byte)(val >>> 24);
		buffer[start+1] = (byte)(val >>> 16);
		buffer[start+2] = (byte)(val >>> 8);
		buffer[start+3] = (byte)val;
	}

	public void setChecksum(int check){
		this.check = check;
	}

	public byte[] getBytes(){
		byte[] buffer = new byte[HEADER_SIZE];
		intToBytes(buffer, 0, check);
		intToBytes(buffer, 4, id);
		buffer[8] = (byte)type;
		intToBytes(buffer, 9, len);
		return buffer;
	}

	@Override
	public String toString(){
		return
			String.format("%12s", "check : ") + this.check + "\n" +
			String.format("%12s", "id : ") + this.id + "\n" +
			String.format("%12s", "type : ") + this.type + "\n" +
			String.format("%12s", "len : ") + this.len;
	}

}
