

import com.google.gson.Gson;

public final class MessageGenerator {


	    public static Message generateMessage(String data){
	        Gson gson = new Gson();
	        return gson.fromJson(data, Message.class);
	    }
}

