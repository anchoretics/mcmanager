package io.github.anchoretics.Threads;


import io.github.anchoretics.Main;
import io.github.anchoretics.MsgType;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.JSONArray;

public class MonitorThread extends Thread {

	private Plugin plugin;
	private JSONArray ja;

	public MonitorThread(Plugin plugin) {
		if (plugin != null) {
			this.plugin = plugin;
			
			//服务端启动时执行一次，通知服务端已启动
			try {
				SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
						"{type:'"+MsgType.SERVER_START+"', token: '"+Main.TOKEN+"'}");
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
		ja = new JSONArray();
		for (Player player : players) {
			ja.put(player.getName());
		}
		try {
			SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
					"{type: '" + MsgType.ONLINEUSERS + "', users: " + ja.toString() + ", token:'"+Main.TOKEN+"'}");
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
