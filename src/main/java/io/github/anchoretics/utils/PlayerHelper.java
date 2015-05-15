package io.github.anchoretics.utils;

import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerHelper {

	private static JSONObject jo = null;
	
	public static JSONObject getPlayerJson(Player player){
		if(jo == null){
			jo = new JSONObject();
		}
		//添加基本的数据
		try {
			jo.put("time", String.valueOf(System.currentTimeMillis()));
			jo.put("customname", player.getCustomName());
			jo.put("displayname", player.getDisplayName());
			jo.put("name", player.getName());
			jo.put("listname", player.getPlayerListName());
			jo.put("allowfly", player.getAllowFlight());
			jo.put("world", player.getWorld());
			jo.put("gamemode", player.getGameMode().name());
			jo.put("x", player.getLocation().getX());
			jo.put("y", player.getLocation().getY());
			jo.put("z", player.getLocation().getZ());
			jo.put("hostname", player.getAddress().getHostName());
			jo.put("hostaddress", player.getAddress().getHostString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jo;
	}
}
