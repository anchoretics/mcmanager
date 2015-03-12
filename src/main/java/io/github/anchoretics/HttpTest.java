package io.github.anchoretics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bukkit.entity.Player;

public class HttpTest {

	public static String POST_URL;
	public HttpTest(){
		
	}
	public static void init(String post_url){
		POST_URL = post_url;
	}
	public static enum Type{
		CHAT,
		LOGIN,
		LOGOUT,
		COMMAND
	}
	
	public static CloseableHttpClient client = WinHttpClients.createDefault();
	public static HttpPost httpPost ;
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void post(Type type, String msg, Player player, String... options) throws Exception{
		if(httpPost == null)
			httpPost = new HttpPost(POST_URL);
		
		Date date = new Date();
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		//添加基本的数据
		list.add(new BasicNameValuePair("type", type.name().toLowerCase()));
		list.add(new BasicNameValuePair("message", msg));
		list.add(new BasicNameValuePair("time", dateFormat.format(date)));
		list.add(new BasicNameValuePair("customname", player.getCustomName()));
		list.add(new BasicNameValuePair("displayname", player.getDisplayName()));
		list.add(new BasicNameValuePair("name", player.getName()));
		list.add(new BasicNameValuePair("listname", player.getPlayerListName()));
		list.add(new BasicNameValuePair("allowfly", String.valueOf(player.getAllowFlight())));
		list.add(new BasicNameValuePair("world", player.getWorld().getName()));
		list.add(new BasicNameValuePair("gamemode", player.getGameMode().name()));
		list.add(new BasicNameValuePair("location_x", String.valueOf(player.getLocation().getX())));
		list.add(new BasicNameValuePair("location_y", String.valueOf(player.getLocation().getY())));
		list.add(new BasicNameValuePair("location_z", String.valueOf(player.getLocation().getZ())));
		list.add(new BasicNameValuePair("hostname", player.getAddress().getHostName()));
		list.add(new BasicNameValuePair("hostaddress", player.getAddress().getHostString()));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
			client.execute(httpPost).close();
		} catch (Exception e) {
			throw new Exception(e.getStackTrace().toString());
		}
	}

}
