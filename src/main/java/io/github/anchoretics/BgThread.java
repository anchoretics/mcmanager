package io.github.anchoretics;

import java.net.URISyntaxException;

import org.bukkit.plugin.Plugin;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class BgThread extends Thread {

	private String SOCKET_URL;
	private Plugin plugin;

	public BgThread(Plugin plugin, String url) {
		if(plugin != null){
			this.plugin = plugin;
			this.SOCKET_URL = url;
		}
	}

	@Override
	public void run() {

		final Socket socket;
		try {
			socket = IO.socket(this.SOCKET_URL);
			socket.on("user join", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						System.out.println("玩家[" + obj.get("username") + "]" + obj.get("msg"));
						plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "]" + obj.get("msg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			socket.on("user left", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						System.out.println("玩家[" + obj.get("username") + "]" + obj.get("msg"));
						plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "]" + obj.get("msg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			socket.on("web message", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						System.out.println("[网站聊天室]" + obj.getString("username") + " : " + obj.getString("msg"));
						plugin.getServer().broadcastMessage("[网站聊天室]" + obj.getString("username") + " : " + obj.getString("msg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

}
