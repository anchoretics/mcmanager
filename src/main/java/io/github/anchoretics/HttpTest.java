package io.github.anchoretics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpTest {
	
	public HttpTest(){
		
	}
	public static CloseableHttpClient client = WinHttpClients.createDefault();
	public static HttpPost httpPost = new HttpPost("http://fengkuang.de:3000/post/userlogin");
	
	public static void post(String title, String msg){

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		list.add(new BasicNameValuePair(title, msg));
		httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
		
		try {
			client.execute(httpPost).close();
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}

}
