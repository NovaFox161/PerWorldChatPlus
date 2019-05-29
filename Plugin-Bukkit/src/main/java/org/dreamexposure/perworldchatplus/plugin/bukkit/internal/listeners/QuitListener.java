package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.dreamexposure.perworldchatplus.api.services.SpamHandler;

/**
 * Created by Nova Fox on 7/4/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        SpamHandler.getHandler().removeMessage(event.getPlayer());
    }
}