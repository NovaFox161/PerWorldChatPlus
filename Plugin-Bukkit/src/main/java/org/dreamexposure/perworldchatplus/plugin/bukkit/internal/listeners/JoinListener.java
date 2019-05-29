package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.dreamexposure.novalib.api.bukkit.update.UpdateChecker;
import org.dreamexposure.perworldchatplus.api.data.PlayerDataManager;
import org.dreamexposure.perworldchatplus.api.data.WorldDataManager;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.PerWorldChatPlusPlugin;

@SuppressWarnings({"unused", "ConstantConditions"})
public class JoinListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinUpdateFiles(PlayerJoinEvent event) {
        if (PlayerDataManager.hasDataFile(event.getPlayer()))
            PlayerDataManager.updatePlayerDataFile(event.getPlayer());
        else
            PlayerDataManager.createPlayerDataFile(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void updateCheckOnJoin(PlayerJoinEvent event) {
        if (PerWorldChatPlusPlugin.get().config.get().getString("Updates.Check").equalsIgnoreCase("True")) {
            if (event.getPlayer().hasPermission("pwcp.notify.update")) {
                UpdateChecker.init(PerWorldChatPlusPlugin.plugin, 26601).requestUpdateCheck().whenComplete((result, exception) -> {
                    if (result.requiresUpdate()) {
                        event.getPlayer().sendMessage(String.format("An update is available! PerWorldChatPlus %s may be downloaded on SpigotMC", result.getNewestVersion()));
                        return;
                    }
                    
                    UpdateChecker.UpdateReason reason = result.getReason();
                    if (reason == UpdateChecker.UpdateReason.UP_TO_DATE) {
                        event.getPlayer().sendMessage(String.format("Your version of PerWorldChatPlus (%s) is up to date!", result.getNewestVersion()));
                    } else if (reason == UpdateChecker.UpdateReason.UNRELEASED_VERSION) {
                        event.getPlayer().sendMessage(String.format("Your version of PerWorldChatPlus (%s) is more recent than the one publicly available. Are you on a development build?", result.getNewestVersion()));
                    } else {
                        event.getPlayer().sendMessage("Could not check for a new version of PerWorldChatPlus. Reason: " + reason);
                    }
                });
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void devJoinCheck(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getUniqueId().toString().equals("2fff73e3-ddbb-48d5-90b9-e6d5342a6b3e") || player.getUniqueId().toString().equals("7ee45311-338a-45ee-aeeb-7e7a4d4a083e")) {
            if (PerWorldChatPlusPlugin.get().config.get().getString("Announce Dev Join").equalsIgnoreCase("True"))
                Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.GREEN + "The developer of PerWorldChatPlus has joined this server!");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void checkWorldFilesOnJoin(PlayerJoinEvent event) {
        for (World world : Bukkit.getWorlds()) {
            if (!WorldDataManager.hasWorldData(world.getName()))
                WorldDataManager.createWorldDataFile(world.getName());
        }
    }
}