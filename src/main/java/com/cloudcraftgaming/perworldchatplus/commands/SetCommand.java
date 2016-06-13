package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Nova Fox on 6/13/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 */
public class SetCommand {

    protected static void setCommand(CommandSender sender, String[] args) {
        if (sender.hasPermission("pwcp.set")) {
            if (args.length < 3) {
                //pwc set <var>
                String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
                sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
            } else if (args.length == 3) {
                //pwc set <var> <var2>
                String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
                sender.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
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
