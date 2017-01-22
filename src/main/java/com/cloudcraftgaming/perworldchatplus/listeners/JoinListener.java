package com.cloudcraftgaming.perworldchatplus.listeners;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("unused")
public class JoinListener implements Listener {
	public JoinListener(Main instance) {
		plugin = instance;
	}
	private Main plugin;
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoinUpdateFiles(PlayerJoinEvent event) {
		if (PlayerDataManager.hasDataFile(event.getPlayer())) {
			PlayerDataManager.updatePlayerDataFile(event.getPlayer());
		} else {
			PlayerDataManager.createPlayerDataFile(event.getPlayer());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void updateCheckOnJoin(PlayerJoinEvent event) {
		if (plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
			Player player = event.getPlayer();
			if (player.hasPermission("pwcp.notify.update")) {
				UpdateChecker.checkForUpdates(player);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void devJoinCheck(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getUniqueId().toString().equals("2fff73e3-ddbb-48d5-90b9-e6d5342a6b3e") || player.getUniqueId().toString().equals("7ee45311-338a-45ee-aeeb-7e7a4d4a083e")) {
			if (plugin.getConfig().getString("Announce Dev Join").equalsIgnoreCase("True")) {
				Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.GREEN + "The developer of PerWorldChatPlus has joined this server!");
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void checkWorldFilesOnJoin(PlayerJoinEvent event) {
		for (World world : Bukkit.getWorlds()) {
			if (!WorldDataManager.hasWorldData(world.getName())) {
				WorldDataManager.createWorldDataFile(world.getName());
			}
		}
	}
}