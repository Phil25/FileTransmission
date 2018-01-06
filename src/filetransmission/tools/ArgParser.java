package filetransmission.tools;

import java.util.HashMap;
import java.util.Map;

public class ArgParser{

	final Map<String, String> params = new HashMap<>();

	public ArgParser(String[] args){
		if(args.length %2 != 0)
			return;
		for(int i = args.length -1; i > 0; i -= 2){
			String param = args[i];
			String option = args[i-1];
			if(option.charAt(0) == '-')
				params.put(option.substring(1), param);
		}
	}

	public Map<String, String> getParams(){
		return params;
	}

	public String get(String option){
		return params.get(option);
	}

	public int getAsInt(String option){
		return Integer.parseInt(this.get(option));
	}

	public boolean check(String[] keys){
		for(int i = 0; i < keys.length; i++)
			if(!params.containsKey(keys[i]))
				return false;
		return true;
	}

}
