package io.github.anchoretics.utils;

import io.github.anchoretics.MsgType;

import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageHelper {

	public static JSONObject getJsonMessage(MsgType.Type type,String msg, Player player){
		JSONObject jo = PlayerHelper.getPlayerJson(player);
		try {
			jo.put("type", type.name().toLowerCase());
			jo.put("msg", msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jo;
	}
}
