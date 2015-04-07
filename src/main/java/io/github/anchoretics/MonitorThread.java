package io.github.anchoretics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MonitorThread extends Thread {
	
	private Plugin plugin;
	private String POST_URL;
	
	public MonitorThread(Plugin plugin, String post_url){
		this.plugin = plugin;
		this.POST_URL = post_url;
	}
	/*
	 * 
	 */
	private void postOnlinePlayers(){
		HttpPost httpPost = new HttpPost(POST_URL);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		//指定post数据的类型
		list.add(new BasicNameValuePair("type", "onlineUsers"));
		//获取在线玩家,存到post数据队列
		Player[] players = this.plugin.getServer().getOnlinePlayers();
		for(Player p : players){
			list.add(new BasicNameValuePair("username", p.getName()));
		}
		CloseableHttpClient client = WinHttpClients.createDefault();
		try {
			client.execute(httpPost).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				this.postOnlinePlayers();
				//sleep 1分钟
				Thread.sleep(1000 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
