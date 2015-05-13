package io.github.anchoretics;


import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;

public class MonitorThread extends Thread {

	private Plugin plugin;

	public MonitorThread(Plugin plugin) {
		if (plugin != null) {
			this.plugin = plugin;
			
			//服务端启动时执行一次，通知服务端已启动
			try {
				SocketIoThread.getSocket().emit("game server started", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 发送在线用户数据
	 */
	private void postOnlinePlayers() {
		// 获取在线玩家,存到post数据队列
		Player[] players = this.plugin.getServer().getOnlinePlayers();
		JSONArray ja = new JSONArray();
		for (Player player : players) {
			ja.put(player.getName());
		}
		try {
			SocketIoThread.getSocket().emit("game server onlineUsers", ja);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				this.postOnlinePlayers();
				// sleep 1分钟
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
