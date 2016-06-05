package com.cloudcraftgaming.perworldchatplus.listeners;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.chat.PlayerChatManager;
import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
	public ChatListener(Main instance) {
		plugin = instance;
	}
	Main plugin;
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			if (event.getMessage().contains(plugin.getConfig().getString("Global.Override")) && (player.hasPermission("pwcp.bypass"))) {

				//Set receivers and message.
				event.getRecipients().clear();
				Set<Player> receivers = PlayerChatManager.determineChatRecipients(event.getRecipients(), event.getMessage(), player);
				String message = PlayerChatManager.determineMessageContents(event.getMessage(), player);
				for (Player p : receivers) {
					event.getRecipients().add(p);
				}

				String gPrefix = plugin.getConfig().getString("Global.Prefix");
				String prefix = (ChatColor.translateAlternateColorCodes('&', gPrefix) + " " + ChatColor.RESET);

				//Send message
				event.setFormat(prefix + event.getFormat());
				event.setMessage(message);
			} else if (PlayerDataManager.getPlayerDataYml(player).getString("Bypass").equalsIgnoreCase("True")
					|| plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")
					|| plugin.getConfig().getString("Global.Always Global").equalsIgnoreCase("True")) {

				//Set receivers and message.
				event.getRecipients().clear();
				Set<Player> receivers = PlayerChatManager.determineChatRecipients(event.getRecipients(), event.getMessage(), player);
				String message = PlayerChatManager.determineMessageContents(event.getMessage(), player);
				for (Player p : receivers) {
					event.getRecipients().add(p);
				}
				String gPrefix = plugin.getConfig().getString("Global.Prefix");
				String prefix = (ChatColor.translateAlternateColorCodes('&', gPrefix) + " " + ChatColor.RESET);

				//Send message
				event.setFormat(prefix + event.getFormat());
				event.setMessage(message);
			} else {

				//Set receivers and message.
				event.getRecipients().clear();
				Set<Player> receivers = PlayerChatManager.determineChatRecipients(event.getRecipients(), event.getMessage(), player);
				String message = PlayerChatManager.determineMessageContents(event.getMessage(), player);
				for (Player p : receivers) {
					event.getRecipients().add(p);
				}

				//Send message
				event.setFormat(event.getFormat());
				event.setMessage(message);
			}
		}
	}
}