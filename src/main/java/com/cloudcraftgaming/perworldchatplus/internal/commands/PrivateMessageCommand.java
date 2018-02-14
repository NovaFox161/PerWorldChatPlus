package com.cloudcraftgaming.perworldchatplus.internal.commands;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.api.privatemessage.PmHandler;
import com.cloudcraftgaming.perworldchatplus.internal.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 10/12/16.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
@SuppressWarnings("deprecation")
public class PrivateMessageCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("privateMessage") || command.getName().equalsIgnoreCase("pm")
				|| command.getName().equalsIgnoreCase("message") || command.getName().equalsIgnoreCase("msg")
				|| command.getName().equalsIgnoreCase("whisper")) {
			if (Main.plugin.getConfig().getString("PM.Enabled").equalsIgnoreCase("True")) {
				if (sender instanceof Player) {
					if (sender.hasPermission("pwcp.pm")) {
						if (args.length < 1) {
							//Not enough args, need player to and message.
							sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.pm.args.few"));
						} else if (args.length == 1) {
							//Not enough args, need player to and message.
							sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.pm.args.few"));
						} else {
							String playerToSendToName = args[0];
							Player playerToSendTo = Bukkit.getPlayer(playerToSendToName);
							
							if (playerToSendTo != null) {
								//Get message together.
								String msg = "";
								for (int i = 1; i < args.length; i++) {
									String arg = args[i] + " ";
									msg = msg + arg;
								}
								PmHandler.sendPrivateMessage((Player) sender, playerToSendTo, msg);
								return true;
							} else {
								//Player is offline or does not exist.
								sender.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Notifications.Player.Offline"));
							}
						}
					} else {
						sender.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
					}
				} else {
					//Sender is not a player, not currently supported.
					sender.sendMessage(MessageManager.getPrefix() + MessageManager.getPlayerOnlyMessage());
				}
			}
		}
		return false;
	}
}