package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.privatemessage.PmHandler;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.PerWorldChatPlusPlugin;

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
            if (PerWorldChatPlusPlugin.plugin.getConfig().getString("PM.Enabled").equalsIgnoreCase("True")) {
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
                                StringBuilder msg = new StringBuilder();
								for (int i = 1; i < args.length; i++) {
									String arg = args[i] + " ";
                                    msg.append(arg);
								}
                                PmHandler.sendPrivateMessage((Player) sender, playerToSendTo, msg.toString());
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