package io.github.anchoretics;

import io.github.anchoretics.Threads.SocketIoThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {
	public static void main(String[] args){
		JSONArray ja = new JSONArray();
		ja.put("a");
		ja.put("b");
		ja.put("c");
		
		new SocketIoThread(null,"http://127.0.0.1:3000/").start();
		JSONObject jo = new JSONObject();
		try {
			jo.put("type", MsgType.LOGIN);
			jo.put("name", "anchor2");
			jo.put("msg", "login on test");
			jo.put("time", String.valueOf(System.currentTimeMillis()));
			jo.put("token", "2fsaakEAk3");
//			jo.put("users", ja);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, jo);
		
//		SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, jo);
		try {
			Thread.sleep(2000);
			SocketIoThread.getSocket().disconnect();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
