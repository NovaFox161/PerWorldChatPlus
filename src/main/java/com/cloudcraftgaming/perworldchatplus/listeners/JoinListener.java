package com.cloudcraftgaming.perworldchatplus.listeners;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {
	public JoinListener(Main instance) {
		plugin = instance;
	}
	Main plugin;
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoinUpdateFiles(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UUID Id = player.getUniqueId();
		if (!(plugin.data.contains("Players." + Id + ".Spy"))) {
			plugin.data.set("Players." + Id + ".Spy", false);
			plugin.saveCustomConfig(plugin.data, plugin.dataFile);
		}
		if (!(plugin.data.contains("Players." + Id + ".Bypass"))) {
			plugin.data.set("Players." + Id + ".Bypass", false);
			plugin.saveCustomConfig(plugin.data, plugin.dataFile);
		}
		if (!(plugin.data.contains("Players." + Id + ".WorldSpy"))) {
			plugin.data.set("Players." + Id + ".WorldSpy", false);
			plugin.saveCustomConfig(plugin.data, plugin.dataFile);
		}
		plugin.data.set("Players." + Id + ".ChatMute", false);
		plugin.saveCustomConfig(plugin.data, plugin.dataFile);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void updateCheckOnJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
			Player player = event.getPlayer();
			if (player.hasPermission("pwcp.notify.update")) {
				plugin.updateChecker = new UpdateChecker(plugin, "http://dev.bukkit.org/bukkit-plugins/per-world-chat-plus/files.rss");
				if (plugin.updateChecker.UpdateNeeded()) {
					player.sendMessage(ChatColor.GREEN + "A new update for PerWorldChatPlus is available! Version: "
							+ ChatColor.BLUE + plugin.updateChecker.getVersion());
					player.sendMessage(ChatColor.GREEN + "Download it from: " + ChatColor.BLUE + plugin.updateChecker.getLink());
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void devJoinCheck(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getUniqueId().toString().equals("2fff73e3-ddbb-48d5-90b9-e6d5342a6b3e")
				|| player.getUniqueId().toString().equals("7ee45311-338a-45ee-aeeb-7e7a4d4a083e")) {
			if (plugin.getConfig().getString("Announce Dev Join").equalsIgnoreCase("True")) {
				Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.GREEN + "The developer of PerWorldChatPlus has joined this server!");
			}
		}
	}
}
