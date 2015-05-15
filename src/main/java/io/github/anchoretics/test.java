package io.github.anchoretics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {
	public static void main(String[] args){
//		new SocketIoThread(null,"http://127.0.0.1:3000/").start();
		JSONObject jo = new JSONObject();
		try {
			jo.put("a", 123);
			jo.put("b", true);
			jo.put("b", "asd");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
