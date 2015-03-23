package io.github.anchoretics;

import java.net.URISyntaxException;

import org.bukkit.plugin.Plugin;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class BgThread extends Thread {

	private String POST_URL;
	private Plugin plugin;

	public BgThread(Plugin plugin) {
		if(plugin != null){
			this.plugin = plugin;
			this.POST_URL = plugin.getConfig().getString("settings.post-url");
		}
	}

	@Override
	public void run() {
		final Socket socket;
		try {
			socket = IO.socket("http://127.0.0.1:3000/");
			socket.connect();
			socket.on("user join", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						System.out.println("玩家[" + obj.get("msg") + "]从网页端登录");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			socket.on("web message", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						System.out.println("[网页端]" + obj.getString("username") + " : " + obj.getString("msg"));
						plugin.getServer().broadcastMessage("[网页端]" + obj.getString("username") + " : " + obj.getString("msg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
