package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.utils.ChatColorInventory;

/**
 * Created by Nova Fox on 7/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class ChatColorCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("ChatColor")) {
			if (sender.hasPermission("pwcp.chat.color")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.openInventory(ChatColorInventory.getChatColorInventory());
					String msg = MessageManager.getMessages().get().getString("Command.ChatColor.Open");
					player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
				} else {
					sender.sendMessage(MessageManager.getPrefix() + MessageManager.getPlayerOnlyMessage());
					return false;
				}
			} else {
				sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
				return false;
			}
		}
		return false;
	}
}