package filetransmission.net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FTClient{

	public FTClient(String path){
		Path file = Paths.get(path);
		byte[] data = null;
		try{
			data = Files.readAllBytes(file);
		}catch(IOException e){
			System.err.println("Cannot read file " + path);
			return;
		}
		System.out.println("Read file: " + path + ", size: " + data.length);
	}

}
