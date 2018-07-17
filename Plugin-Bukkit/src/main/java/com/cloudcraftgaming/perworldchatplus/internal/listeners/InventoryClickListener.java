package com.cloudcraftgaming.perworldchatplus.internal.listeners;

import com.cloudcraftgaming.perworldchatplus.api.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.internal.utils.ChatColorInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Nova Fox on 7/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class InventoryClickListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (ChatColorInventory.isChatColorInventory(event.getInventory())) {
			Player player = (Player) event.getWhoClicked();
			ItemStack clicked = event.getCurrentItem();
			if (clicked != null && !clicked.getType().equals(Material.AIR)) {
				if (player.hasPermission("pwcp.chat.color")) {
					player.closeInventory();
					ChatColorInventory.setChatColor(player, clicked);
					event.setCancelled(true);
				} else {
					player.closeInventory();
					player.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
					event.setCancelled(true);
				}
			}
		}
	}
}