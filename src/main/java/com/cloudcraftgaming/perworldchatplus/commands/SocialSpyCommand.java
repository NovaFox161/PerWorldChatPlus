package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/7/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class SocialSpyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("SocialSpy")) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                if (player.hasPermission("pwcp.socialspy")) {
                    if (PlayerDataManager.hasSocialSpyEnabled(player)) {
                        //Disable social spy
                        PlayerDataManager.setSocialSpy(player, false);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.SocialSpy.Disabled"));
                    } else {
                        //Enable social spy
                        PlayerDataManager.setSocialSpy(player, true);
                        player.sendMessage(MessageManager.getPrefix() + MessageManager.getMessage("Command.SocialSpy.Enabled"));
                    }
                } else {
                    player.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
                }
            } else {
                //Player only
                sender.sendMessage(MessageManager.getPrefix() + MessageManager.getPlayerOnlyMessage());
            }
        }
        return false;
    }
}