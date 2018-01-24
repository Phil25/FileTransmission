package filetransmission.tools;

public final class ByteOps{

	private ByteOps(){}

	public static int toInt32(byte[] bytes, int pos){
		return
			bytes[pos] << 24 |
			(bytes[pos+1] & 0xFF) << 16 |
			(bytes[pos+2] & 0xFF) << 8 |
			(bytes[pos+3] & 0xFF);
	}

	public static void intToBytes(byte[] bytes, int pos, int val){
		bytes[pos] = (byte)(val >>> 24);
		bytes[pos+1] = (byte)(val >>> 16);
		bytes[pos+2] = (byte)(val >>> 8);
		bytes[pos+3] = (byte)val;
	}

	public static int toInt8(byte[] bytes, int pos){
		return bytes[pos] & 0xFF;
	}

	public static void intToByte(byte[] bytes, int pos, int val){
		bytes[pos] = (byte)val;
	}

	public static int get7Bits(byte[] bytes, int pos){
		return bytes[pos] & 127;
	}

	public static void set7Bits(byte[] bytes, int pos, int val){
		bytes[pos] &= 1 << 7;
		bytes[pos] |= (byte)val;
	}

	public static int get15Bits(byte[] bytes, int pos){
		return bytes[pos+1] << 8 & 0x7F00 | bytes[pos] & 0xFF;
	}

	public static void set15Bits(byte[] bytes, int pos, int val){
		bytes[pos] = (byte)(val & 0xFF);
		bytes[pos+1] = (byte)((val >> 8) & 0x7F);
	}

	public static int get16Bits(byte[] bytes, int pos){
		return ((bytes[pos] & 0xFF) << 8) | (bytes[pos+1] & 0xFF);
	}

	public static void set16Bits(byte[] bytes, int pos, int val){
		bytes[pos] = (byte)((val >> 8) & 0xFF);
		bytes[pos+1] = (byte)(val & 0xFF);
	}

	public static boolean getLastBit(byte[] bytes, int pos){
		return (bytes[pos] & 128) != 0;
	}

	public static void setLastBit(byte[] bytes, int pos, boolean b){
		if(b) bytes[pos] |= 1 << 7;
		else  bytes[pos] &= ~(1 << 7);
	}

	public static byte[] concat(byte[] arr1, byte[] arr2){
		int len = arr1.length +arr2.length;
		byte[] bytes = new byte[len];
		System.arraycopy(arr1, 0, bytes, 0, arr1.length);
		System.arraycopy(arr2, 0, bytes, arr1.length, len);
		return bytes;
	}

	public static String toString(byte b){
		return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
	}

}
