package io.github.anchoretics.utils;

import io.github.anchoretics.Main;

import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageHelper {

	public static JSONObject getJsonMessage(String type,String msg, Player player){
		JSONObject jo = PlayerHelper.getPlayerJson(player);
		try {
			jo.put("type", type);
			jo.put("msg", msg);
			jo.put("token", Main.TOKEN);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jo;
	}
}
