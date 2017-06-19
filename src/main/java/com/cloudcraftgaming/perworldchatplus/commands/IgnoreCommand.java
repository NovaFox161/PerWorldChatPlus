package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/9/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
@SuppressWarnings("unused, deprecation")
public class IgnoreCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("Ignore")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("pwcp.ignore")) {
					if (args.length < 1) {
						player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notification.Args.TooFew"));
					} else if (args.length == 1) {
						String toIgnoreName = args[0];
						Player toIgnore = Bukkit.getPlayer(toIgnoreName);
						if (toIgnore != null) {
							if (!player.getName().equals(toIgnore.getName())) {
								if (PlayerDataManager.isIgnoringPlayer(player, toIgnore)) {
									//Ignoring, unignore them.
									if (PlayerDataManager.unignorePlayer(player, toIgnore)) {
										String msgOr = MessageManager.getMessage("Command.Ignore.Stop.Success");
										String msg = msgOr.replaceAll("%player%", toIgnore.getDisplayName());
										player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
									} else {
										player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Ignore.Stop.Failure"));
									}
								} else {
									//Ignore player.
									if (PlayerDataManager.ignorePlayer(player, toIgnore)) {
										String msgOr = MessageManager.getMessage("Command.Ignore.Start.Success");
										String msg = msgOr.replaceAll("%player%", toIgnore.getDisplayName());
										player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
									} else {
										player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Ignore.Start.Failure"));
									}
								}
							} else {
								player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.Ignore.Self"));
							}
						} else {
							player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Player.Offline"));
						}
					} else {
						player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notification.Args.TooMany"));
					}
				} else {
					sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
				}
			} else {
				sender.sendMessage(MessageManager.getPrefix() + MessageManager.getPlayerOnlyMessage());
			}
		}
		return false;
	}
}