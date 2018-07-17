package com.cloudcraftgaming.perworldchatplus.internal.listeners;

import com.cloudcraftgaming.perworldchatplus.api.PerWorldChatPlusAPI;
import com.cloudcraftgaming.perworldchatplus.api.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.api.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.api.utils.MessageManager;
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
	public JoinListener() {
	}

	
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
		if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Check for Updates").equalsIgnoreCase("True")) {
			Player player = event.getPlayer();
			if (player.hasPermission("pwcp.notify.update")) {
                //TODO: Use my custom update API
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void devJoinCheck(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getUniqueId().toString().equals("2fff73e3-ddbb-48d5-90b9-e6d5342a6b3e") || player.getUniqueId().toString().equals("7ee45311-338a-45ee-aeeb-7e7a4d4a083e")) {
			if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Announce Dev Join").equalsIgnoreCase("True")) {
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