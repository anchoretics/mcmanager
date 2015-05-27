package io.github.anchoretics;

import io.github.anchoretics.Threads.MonitorThread;
import io.github.anchoretics.Threads.SocketIoThread;
import io.github.anchoretics.utils.MessageHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

	public static String TOKEN ;
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		String _iourl = getConfig().getString("settings.socketio-url");
		this.TOKEN = getConfig().getString("settings.token");
		if(_iourl == null){
			getLogger().warning("配置文件加载出错！");
			this.setEnabled(false);
		}else{
			try {
				//start socket.io-client thread
				new SocketIoThread(this, _iourl).start();
				new MonitorThread(this).start();
			} catch (Exception e) {
				getLogger().warning(e.getMessage());
			}
		}
		getLogger().info("An plugin is enabled!");
		//this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "");
		
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
				SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
						MessageHelper.getJsonMessage(MsgType.CHAT, e.getMessage(), e.getPlayer()));
			} catch (Exception e1) {
				getLogger().warning(e1.getStackTrace().toString());
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e){
		try {
			SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
					MessageHelper.getJsonMessage(MsgType.LOGIN, e.getJoinMessage(), e.getPlayer()));
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		try {
			SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
					MessageHelper.getJsonMessage(MsgType.COMMAND, e.getMessage(), e.getPlayer()));
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e){
		try {
			SocketIoThread.getSocket().emit(MsgType.EVENT_NAME, 
					MessageHelper.getJsonMessage(MsgType.LOGOUT, e.getQuitMessage(), e.getPlayer()));
		} catch (Exception e1) {
			getLogger().warning(e1.getStackTrace().toString());
		}
	}

	/**
	 * 血量根据等级进行变化
	 * @param e
	 */
	@EventHandler
	public void onPlayerLevelChange(PlayerLevelChangeEvent e){
		e.getPlayer().setHealth(20.0+(e.getNewLevel()*2));
		e.getPlayer().setMaxHealth(20.00+(e.getNewLevel()*2));
		e.getPlayer().saveData();
	}
	
}
