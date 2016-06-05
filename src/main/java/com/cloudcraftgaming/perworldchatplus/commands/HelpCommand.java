package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nova Fox on 1/27/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * This is just a util class that handles the help command and nothing more.
 */
public class HelpCommand {

    /**
     * Sends the help command page to the sender.
     * @param sender The sender of the help command and receiver of the help messages.
     * @param page The page number that the sender wishes to view.
     */
    protected static void helpCommand(CommandSender sender, String page) {
        if (page.equals("1")) {
            sender.sendMessage(ChatColor.GOLD + "-~-" + ChatColor.BLUE + " PerWorldChatPlus Help page 1/1" + ChatColor.GOLD + "-~-");
            sender.sendMessage(ChatColor.GREEN + "/pwc spy" + ChatColor.LIGHT_PURPLE + " - Allows you to see all chat messages.");
            sender.sendMessage(ChatColor.GREEN + "/pwc worldspy <on/off/world>" + ChatColor.LIGHT_PURPLE + " - Allows you to spy on a specific world.");
            sender.sendMessage(ChatColor.GREEN + "/pwc bypass" + ChatColor.LIGHT_PURPLE + " - Allows you to send global chat messages.");
            sender.sendMessage(ChatColor.GREEN + "/pwc alert <word>" + ChatColor.LIGHT_PURPLE + " - Adds/Removes a word from your alert list.");
            sender.sendMessage(ChatColor.GREEN + "/pwc timedglobal (time)" + ChatColor.LIGHT_PURPLE + " - Turns on/off Timed Global Chat.");
            sender.sendMessage(ChatColor.GREEN + "/global (message)" + ChatColor.LIGHT_PURPLE + " - Sends a single global message.");
            sender.sendMessage(ChatColor.GREEN + "/globalChat (message)" + ChatColor.LIGHT_PURPLE + " - Sends a single Global Message.");
        } else {
            String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
            sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}
