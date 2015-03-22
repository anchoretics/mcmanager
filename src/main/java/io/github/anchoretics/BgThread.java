package io.github.anchoretics;


import org.bukkit.plugin.Plugin;

public class BgThread extends Thread {
	

	private String POST_URL;
	private Plugin plugin;
	
	public BgThread(Plugin plugin) {
		this.plugin = plugin;
		this.POST_URL = plugin.getConfig().getString("settings.post-url");
	}
	
	@Override
	public void run() {

		
	}
	
}
