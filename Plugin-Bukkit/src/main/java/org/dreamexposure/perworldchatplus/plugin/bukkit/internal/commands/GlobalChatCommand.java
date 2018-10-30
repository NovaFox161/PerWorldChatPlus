package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.chat.ChatFormat;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.PerWorldChatPlusPlugin;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * Just the global chat command class. Nothing important here.
 */
public class GlobalChatCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("global") || command.getName().equalsIgnoreCase("GlobalChat")) {
			String prefix = MessageManager.getPrefix();
			if (sender instanceof Player) {
				if (sender.hasPermission("pwcp.global")) {
					Player player = (Player) sender;
					if (args.length < 1) {
						String msgOr = MessageManager.getMessages().get().getString("Command.Global.AddMessage");
						player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', msgOr));
                    } else {
						String gPrefix = PerWorldChatPlusPlugin.get().config.get().getString("Global.Prefix");
                        StringBuilder msg1 = new StringBuilder();
						for (String arg : args) {
							arg = arg + " ";
                            msg1.append(arg);
						}
                        String format = ChatFormat.determineMessageFormat(gPrefix, msg1.toString(), player, true, true);

                        Bukkit.broadcastMessage(format + " " + ChatColor.translateAlternateColorCodes('&', msg1.toString()).trim());
					}
				} else {
					sender.sendMessage(prefix + MessageManager.getNoPermMessage());
					return false;
				}
			} else {
				sender.sendMessage(prefix + MessageManager.getPlayerOnlyMessage());
				return false;
			}
		}
		return false;
	}
}