package io.github.anchoretics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		sender.sendMessage(command.getName() + "," + label);
		getLogger().info("command:" + label);
		return true;
	}

	@Override
	public void onEnable() {
		getLogger().info("An插件已启用!");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	
	@Override
	public void onDisable() {
		getLogger().info("An插件已禁用!");
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
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			//logToFile("[" + dateFormat.format(date) + "] " +  e.getPlayer().getName() + ": " + e.getMessage());
			HttpTest.post("chat","[" + dateFormat.format(date) + "] " +  e.getPlayer().getName() + ": " + e.getMessage());
		}
	}
	
}
