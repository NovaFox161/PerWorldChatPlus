package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nova Fox on 1/27/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * This is just a util class that handles the help command and nothing more.
 */
class HelpCommand {
	/**
	 * Sends the help command page to the sender.
	 *
	 * @param sender The sender of the help command and receiver of the help messages.
	 * @param page   The page number that the sender wishes to view.
	 */
	static void helpCommand(CommandSender sender, String page) {
		if (page.equals("1")) {
			sender.sendMessage(ChatColor.GOLD + "-~-" + ChatColor.BLUE + " PerWorldChatPlus Help page 1/3" + ChatColor.GOLD + "-~-");
			sender.sendMessage(ChatColor.GREEN + "/pwc spy" + ChatColor.LIGHT_PURPLE + " - Allows you to see all chat messages.");
			sender.sendMessage(ChatColor.GREEN + "/pwc worldspy <on/off/world>" + ChatColor.LIGHT_PURPLE + " - Allows you to spy on a specific world.");
			sender.sendMessage(ChatColor.GREEN + "/pwc bypass" + ChatColor.LIGHT_PURPLE + " - Allows you to send global chat messages.");
			sender.sendMessage(ChatColor.GREEN + "/pwc alert <word>" + ChatColor.LIGHT_PURPLE + " - Adds/Removes a word from your alert list.");
			sender.sendMessage(ChatColor.GREEN + "/pwc mute" + ChatColor.LIGHT_PURPLE + " - Mutes/Unmutes your chat.");
			sender.sendMessage(ChatColor.GOLD + "Use: '/pwc help 2' for more commands");
		} else if (page.equalsIgnoreCase("2")) {
			sender.sendMessage(ChatColor.GOLD + "-~-" + ChatColor.BLUE + " PerWorldChatPlus Help page 2/3" + ChatColor.GOLD + "-~-");
			sender.sendMessage(ChatColor.GREEN + "/pwc timedglobal (time)" + ChatColor.LIGHT_PURPLE + " - Turns on/off Timed Global Chat.");
			sender.sendMessage(ChatColor.GREEN + "/pwc set alias <world> <alias>" + ChatColor.LIGHT_PURPLE + " - Sets the alias for the world.");
			sender.sendMessage(ChatColor.GREEN + "/pwc set color <COLOR> (player)" + ChatColor.LIGHT_PURPLE + " - Sets your default chat color.");
			sender.sendMessage(ChatColor.GREEN + "/chatcolor" + ChatColor.LIGHT_PURPLE + " - Opens a GUI for selecting your default ChatColor.");
			sender.sendMessage(ChatColor.GREEN + "/cc" + ChatColor.LIGHT_PURPLE + " - Opens a GUI for selecting your default ChatColor.");
			sender.sendMessage(ChatColor.GOLD + "Use: '/pwc help 3' for more commands");
		} else if (page.equalsIgnoreCase("3")) {
			sender.sendMessage(ChatColor.GOLD + "-~-" + ChatColor.BLUE + " PerWorldChatPlus Help page 3/3" + ChatColor.GOLD + "-~-");
			sender.sendMessage(ChatColor.GREEN + "/global (message)" + ChatColor.LIGHT_PURPLE + " - Sends a single global message.");
			sender.sendMessage(ChatColor.GREEN + "/globalChat (message)" + ChatColor.LIGHT_PURPLE + " - Sends a single Global Message.");
			sender.sendMessage(ChatColor.GREEN + "/pm <player> <message>" + ChatColor.LIGHT_PURPLE + " - Sends the specified player a private message.");
			sender.sendMessage(ChatColor.GREEN + "/r <message>" + ChatColor.LIGHT_PURPLE + " - Replies to the last player you private messaged.");
			sender.sendMessage(ChatColor.GREEN + "/socialspy" + ChatColor.LIGHT_PURPLE + " - Allows you to see all private messages, even if they are not for you.");
			sender.sendMessage(ChatColor.GOLD + "End of PerWorldChatPlus Help");
		} else {
			String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
			sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
		}
	}
}