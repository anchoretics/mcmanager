package io.github.anchoretics;

import java.net.URISyntaxException;

import org.bukkit.plugin.Plugin;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketIoThread extends Thread {

	private String SOCKET_URL;
	private Plugin plugin;
	// 插件全局socket变量
	private static Socket socket;

	public SocketIoThread(Plugin plugin, String url) {
		if(plugin != null){
			this.plugin = plugin;
			this.SOCKET_URL = url;
		}
	}

	@Override
	public void run() {

		try {
			SocketIoThread.setSocket(IO.socket(this.SOCKET_URL));
			
			getSocket().on("web user join", new Emitter.Listener() {
				public void call(Object... args) {
					JSONObject obj = (JSONObject)args[0];
					try {
						plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "]" + obj.get("msg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			getSocket().on("web user left", new Emitter.Listener() {
				public void call(Object... args) {

					JSONObject obj = (JSONObject)args[0];
					try {
						plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "]" + obj.get("msg"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			getSocket().on("web user message", new Emitter.Listener() {
				public void call(Object... args) {
					JSONObject obj = (JSONObject)args[0];
					try {
						plugin.getServer().broadcastMessage("[网站聊天室]" + obj.getString("username") + " : " + obj.getString("msg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			getSocket().connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public static Socket getSocket() {
		return socket;
	}

	public static void setSocket(Socket socket) {
		SocketIoThread.socket = socket;
	}

}
