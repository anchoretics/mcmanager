package io.github.anchoretics;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MonitorThread extends Thread {

	private Plugin plugin;
	private String POST_URL;
	private HttpPost httpPost;
	private CloseableHttpClient client;

	public MonitorThread(Plugin plugin, String post_url) {
		if (plugin != null) {
			this.plugin = plugin;
			this.POST_URL = post_url;
			httpPost = new HttpPost(POST_URL);
			client = WinHttpClients.createDefault();
			
			//服务端启动时执行一次，通知服务端已启动
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("type", "serverStarted"));
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
			try {
				client.execute(httpPost).close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 发送在线用户数据
	 */
	private void postOnlinePlayers() {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		// 指定post数据的类型
		list.add(new BasicNameValuePair("type", "onlineUsers"));
		// 获取在线玩家,存到post数据队列
		Player[] players = this.plugin.getServer().getOnlinePlayers();
		for (Player player : players) {
			list.add(new BasicNameValuePair("username", player.getName()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
			client.execute(httpPost).close();
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
