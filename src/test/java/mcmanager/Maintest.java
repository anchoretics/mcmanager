package mcmanager;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

public class Maintest {

	@Test
	public void test() throws Exception {
		CloseableHttpClient client = WinHttpClients.createDefault();
		HttpPost httpPost ;
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		//添加基本的数据
		list.add(new BasicNameValuePair("type", "chat"));
		list.add(new BasicNameValuePair("message", "chat content"));
		list.add(new BasicNameValuePair("time", String.valueOf(System.currentTimeMillis())));
		list.add(new BasicNameValuePair("customname", "anchor"));
		list.add(new BasicNameValuePair("displayname", ""));
		list.add(new BasicNameValuePair("name", "anchor"));
		list.add(new BasicNameValuePair("listname", "anchor"));
		list.add(new BasicNameValuePair("allowfly", "true"));
		list.add(new BasicNameValuePair("world", "world"));
		list.add(new BasicNameValuePair("gamemode", "gamemode"));
		list.add(new BasicNameValuePair("location_x", "1"));
		list.add(new BasicNameValuePair("location_y", "2"));
		list.add(new BasicNameValuePair("location_z", "3"));
		list.add(new BasicNameValuePair("hostname", "127.0.0.1"));
		list.add(new BasicNameValuePair("hostaddress", "127.0.0.1"));
		httpPost = new HttpPost("http://127.0.0.1:3000/post/data");
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
			client.execute(httpPost).close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	

}
