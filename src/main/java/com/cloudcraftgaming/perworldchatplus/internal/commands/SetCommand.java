package com.cloudcraftgaming.perworldchatplus.internal.commands;

import com.cloudcraftgaming.perworldchatplus.api.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.api.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.internal.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 6/13/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 */
class SetCommand {
	
	@SuppressWarnings("deprecation")
	static void setCommand(CommandSender sender, String[] args) {
		if (sender.hasPermission("pwcp.set")) {
			if (args.length < 3) {
				//pwc set <var>
				String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
				sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			} else if (args.length == 3) {
				//pwc set <var> <var2>
				String type = args[1];
				String value = args[2];
				if (type.equalsIgnoreCase("color") || type.equalsIgnoreCase("ChatColor")) {
					if (sender.hasPermission("pwcp.chat.color")) {
						if (sender instanceof Player) {
							try {
								ChatColor color = ChatColor.valueOf(value);
								PlayerDataManager.setChatColor((Player) sender, color);
								//Send message.
								String msgOr = MessageManager.getMessageYml().getString("Command.Set.Color.Self");
								String msg = msgOr.replace("%color%", color + color.name());
								sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
							} catch (IllegalArgumentException e) {
								//Color invalid.
								String msgOr = MessageManager.getMessageYml().getString("Notification.Color.Invalid");
								sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
							}
						} else {
							//Player only, use more args.
							String msgOr = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
							sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
						}
					} else {
						//Does not have permission.
						sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
					}
				} else {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
					sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
				}
			} else if (args.length == 4) {
				//pwc set <var> <var2> <value>
				String type = args[1];
				String type2 = args[2];
				String value = args[3];
				
				if (type.equalsIgnoreCase("alias")) {
					if (WorldDataManager.hasWorldData(type2)) {
						WorldDataManager.setAlias(type2, value);
						String msgOr = MessageManager.getMessageYml().getString("Command.Set.Alias");
						String msg = msgOr.replaceAll("%world%", type2).replaceAll("%alias%", value);
						sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.World.DoesNotExist");
						sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (type.equalsIgnoreCase("color") || type.equalsIgnoreCase("ChatColor")) {
					if (sender.hasPermission("pwcp.chat.color.other")) {
						Player target = Bukkit.getPlayer(value);
						if (target != null) {
							try {
								ChatColor color = ChatColor.valueOf(type2);
								PlayerDataManager.setChatColor(target, color);
								
								String msgOr = MessageManager.getMessageYml().getString("Command.Set.Color.Other");
								String msg = msgOr.replace("%player%", target.getDisplayName()).replace("%color%", color + color.name());
								sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
							} catch (IllegalArgumentException e) {
								String msgOr = MessageManager.getMessageYml().getString("Notification.Color.Invalid");
								sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
							}
						} else {
							String msg = MessageManager.getMessageYml().getString("Notifications.Player.Offline");
							sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
						}
					} else {
						sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
					}
				} else {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
					sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
				}
			} else {
				String msg = MessageManager.getMessageYml().getString("Notification.Args.TooMany");
				sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			}
		} else {
			sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
		}
	}
}