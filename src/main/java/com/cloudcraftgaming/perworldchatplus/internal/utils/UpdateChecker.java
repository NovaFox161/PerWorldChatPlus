package com.cloudcraftgaming.perworldchatplus.internal.utils;

import com.arsenarsen.updater.Updater;
import com.cloudcraftgaming.perworldchatplus.PerWorldChatPlusPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class UpdateChecker {
	public static void checkForUpdates() {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
            PerWorldChatPlusPlugin.plugin.getLogger().info("Checking for updates...");
            Updater updater = new Updater(PerWorldChatPlusPlugin.plugin, 92965);
			Updater.UpdateAvailability upAv = updater.checkForUpdates();
			if (upAv == Updater.UpdateAvailability.UPDATE_AVAILABLE) {
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Download Updates").equalsIgnoreCase("True")) {
                    PerWorldChatPlusPlugin.plugin.getLogger().info("Attempting to download new PerWorldChatPlus version...");
					updater.update();
				} else {
                    PerWorldChatPlusPlugin.plugin.getLogger().info("New Version of PerWorldChatPlus found: " + updater.getLatest());
                    PerWorldChatPlusPlugin.plugin.getLogger().info("Download at: https://dev.bukkit.org/projects/per-world-chat-plus");
				}
			} else if (upAv == Updater.UpdateAvailability.NO_UPDATE) {
                PerWorldChatPlusPlugin.plugin.getLogger().info("No new updates found! You are using the latest version of PerWorldChatPlus!");
			} else {
                PerWorldChatPlusPlugin.plugin.getLogger().severe("Failed to retrieve updates! Reason: " + upAv.name() + ". Please report this to Shades161 (NovaFox161) on her Dev Bukkit!!");
			}
		} else {
            PerWorldChatPlusPlugin.plugin.getLogger().info("Update checking disabled! Please enable to stay up to date on the latest version of PerWorldChatPlus!");
		}
	}
	
	public static void checkForUpdates(Player p) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
            PerWorldChatPlusPlugin.plugin.getLogger().info("Checking for updates...");
            Updater updater = new Updater(PerWorldChatPlusPlugin.plugin, 92965);
			Updater.UpdateAvailability upAv = updater.checkForUpdates();
			if (upAv == Updater.UpdateAvailability.UPDATE_AVAILABLE) {
                PerWorldChatPlusPlugin.plugin.getLogger().info("New Version of PerWorldChatPlus found: " + updater.getLatest());
                PerWorldChatPlusPlugin.plugin.getLogger().info("Download at: https://dev.bukkit.org/projects/per-world-chat-plus");
				p.sendMessage(ChatColor.GREEN + "New Version of PerWorldChatPlus found: " + updater.getLatest());
				p.sendMessage(ChatColor.BLUE + "Download at: https://dev.bukkit.org/projects/per-world-chat-plus");
			} else if (upAv == Updater.UpdateAvailability.NO_UPDATE) {
                PerWorldChatPlusPlugin.plugin.getLogger().info("No new updates found! You are using the latest version of PerWorldChatPlus!");
				p.sendMessage(ChatColor.GREEN + "PerWorldChatPlus is up to date!");
			} else {
                PerWorldChatPlusPlugin.plugin.getLogger().severe("Failed to retrieve updates! Reason: " + upAv.name() + ". Please report this to Shades161 (NovaFox161) on her Dev Bukkit!!");
			}
		} else {
            PerWorldChatPlusPlugin.plugin.getLogger().info("Update checking disabled! Please enable to stay up to date on the latest version of PerWorldChatPlus!");
		}
	}
}