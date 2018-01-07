package filetransmission.net;

public class PhilFTPHeader{

	public final static int HEADER_SIZE = 13;

	public final static int TYPE_RECV_PORT = 0; // defines the receiving port
	public final static int TYPE_FILE_NAME = 1; // defines the file name
	public final static int TYPE_SPEED = 2; // sets the speed
	public final static int TYPE_DATA = 3; // regular data transfer

	public int check;	// checksum, big-endian
	public int id;		// id of the packet, big-endian
	public int type;	// type of the data, big-endian
	public boolean ack;	// acknowledgement of correct receival
	public int len;		// length of body, big-endian

	public PhilFTPHeader(int check, int id, int type, boolean ack, int len){
		this.check = check;
		this.id = id;
		this.type = type;
		this.ack = ack;
		this.len = len;
	}

	public PhilFTPHeader(byte[] buffer){
		this.check = toInt32(buffer, 0);
		this.id = toInt32(buffer, 4);
		this.type = getType(buffer, 8);
		this.ack = getAck(buffer, 8);
		this.len = toInt32(buffer, 9);
	}

	public PhilFTPHeader(PhilFTPHeader other){
		this.check = other.check;
		this.id = other.id;
		this.type = other.type;
		this.ack = other.ack;
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

	public void setAck(boolean ack){
		this.ack = ack;
	}

	public byte[] getBytes(){
		byte[] buffer = new byte[HEADER_SIZE];
		intToBytes(buffer, 0, check);
		intToBytes(buffer, 4, id);
		setType(buffer, 8, this.type);
		setAck(buffer, 8, this.ack);
		intToBytes(buffer, 9, len);
		return buffer;
	}

	private int getType(byte[] bytes, int pos){
		return bytes[pos] & 127;
	}

	private void setType(byte[] bytes, int pos, int type){
		bytes[pos] &= 1 << 7;
		bytes[pos] |= type;
	}

	private boolean getAck(byte[] bytes, int pos){
		return (bytes[pos] & 128) != 0;
	}

	private void setAck(byte[] bytes, int pos, boolean b){
		if(b) bytes[pos] |= 1 << 7;
		else  bytes[pos] &= ~(1 << 7);
	}

	@Override
	public String toString(){
		return
			String.format("%12s", "check : ") + this.check + "\n" +
			String.format("%12s", "id : ") + this.id + "\n" +
			String.format("%12s", "type : ") + this.type + "\n" +
			String.format("%12s", "ack : ") + this.ack + "\n" +
			String.format("%12s", "len : ") + this.len;
	}

}
