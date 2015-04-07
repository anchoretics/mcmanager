package io.github.anchoretics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() {
		getLogger().info("An plugin is enabled!");
		getServer().getPluginManager().registerEvents(this, this);
		String _url = getConfig().getString("settings.post-url");
		String _iourl = getConfig().getString("settings.socketio-url");
		if(_url == null || _iourl == null){
			getLogger().warning("配置文件加载出错！");
		}else{
			HttpPostTool.init(_url);
			try {
				//start socket.io-client thread
				new SocketIoThread(this, _iourl).start();
			} catch (Exception e) {
				getLogger().warning(e.getMessage());
			}
		}
	}
	
	@Override
	public void onDisable() {
		getLogger().info("An plugin is disabled!");
	}
	
	
	public void logToFile(String message) {
		try {
			File dataFolder = getDataFolder();
			if(!dataFolder.exists()) {
				dataFolder.mkdir();
			}

			File saveTo = new File(getDataFolder(), "chatlog.txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			} 
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(message);
			pw.flush();
			pw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(!e.isCancelled()) {
			try {
				HttpPostTool.post(HttpPostTool.Type.CHAT, e.getMessage(), e.getPlayer(), e.getFormat());
			} catch (Exception e1) {
				getLogger().warning(e1.getStackTrace().toString());
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e){
		try {
			this.getServer().broadcastMessage("/whitelist add " + System.currentTimeMillis());
			HttpPostTool.post(HttpPostTool.Type.LOGIN, e.getJoinMessage(), e.getPlayer());
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		try {
			HttpPostTool.post(HttpPostTool.Type.COMMAND, e.getMessage(), e.getPlayer());
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e){
		try {
			HttpPostTool.post(HttpPostTool.Type.LOGOUT, e.getQuitMessage(), e.getPlayer());
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}
}
