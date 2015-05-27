package io.github.anchoretics.Threads;

import io.github.anchoretics.MsgType;

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
		}if(url != null){
			this.SOCKET_URL = url;
		}
	}

	@Override
	public void run() {

		try {
			socket = IO.socket(this.SOCKET_URL);
			
			//只监听一个事件，所有消息都用这个事件接收，减小开销
			socket.on(MsgType.EVENT_NAME, new Emitter.Listener() {
				public void call(Object... arg0) {
					JSONObject obj = (JSONObject)arg0[0];
					try {
						//根据消息内容的类型处理
						String type = obj.getString("type");
						if(type.equals(MsgType.WEB_USER_LOGIN)){
							plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "] 离开网站聊天室");
						}
						if(type.equals(MsgType.WEB_USER_LOGOFF)){
							plugin.getServer().broadcastMessage("玩家[" + obj.get("username") + "] 进入网站聊天室");
						}
						if(type.equals(MsgType.WEB_USER_CHAT)){
							plugin.getServer().broadcastMessage("[网]" + obj.getString("username") + " : " + obj.getString("msg"));
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public static Socket getSocket() {
		while(socket==null){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return socket;
	}

	public static void setSocket(Socket socket) {
		SocketIoThread.socket = socket;
	}

}
