package com.cloudcraftgaming.perworldchatplus.chat;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

/**
 * Created by Nova Fox on 1/25/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * The manager class for Timed Global Chat. This class contains the functions for everything Timed Global chat related.
 */
public class TimedGlobalManager {
    /**
     * Turns on Timed Global Chat for the specified amount of time.
     * @param sender sender of the command
     * @param time (int) amount of time for the Timed Global Chat to be on.
     */
    public static void TurnOnTimedGlobal(final CommandSender sender, Integer time) {
        final String prefix = MessageManager.getPrefix();
        BukkitScheduler scheduler = Main.plugin.getServer().getScheduler();
        Main.plugin.getConfig().set("Global.TimedGlobal.On", true);
        Main.plugin.saveConfig();
        Random random = new Random(999999999);
        final Integer requestNumber = random.nextInt();
        Main.plugin.timedRequest = requestNumber;
        String turnedOnMsgOr = MessageManager.getMessageYml().getString("Command.TimedGlobal.TurnedOn");
        String turnedOnMsg = turnedOnMsgOr.replaceAll("%time%", time.toString());
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', turnedOnMsg));
        if (Main.plugin.getConfig().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
            String announceMsgOr = MessageManager.getMessageYml().getString("Command.TimedGlobal.Announce.On");
            String announceMsg = announceMsgOr.replaceAll("%time%", time.toString());
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceMsg));
            }
        }
        scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                if (Main.plugin.timedRequest.equals(requestNumber)) {
                    Main.plugin.getConfig().set("Global.TimedGlobal.On", false);
                    Main.plugin.saveConfig();
                    Main.plugin.timedRequest = 0;
                    String offMsg = MessageManager.getMessageYml().getString("Command.TimedGlobal.TurningOff");
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', offMsg));
                    if (Main.plugin.getConfig().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
                        String announceOff = MessageManager.getMessageYml().getString("Command.TimedGlobal.Announce.Off");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceOff));
                        }
                    }
                }
            }
        }, 20L * 60 * time);
    }

    /**
     * Turns off Timed Global Chat.
     * @param sender the sender of the command.
     */
    public static void TurnOffTimedGlobal(CommandSender sender) {
        String prefix = MessageManager.getPrefix();
        Main.plugin.getConfig().set("Global.TimedGlobal.On", false);
        Main.plugin.saveConfig();
        Main.plugin.timedRequest = 0;
        String offMsg = MessageManager.getMessageYml().getString("Command.TimedGlobal.TurnedOff");
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', offMsg));
        if (Main.plugin.getConfig().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
            String announceOff = MessageManager.getMessageYml().getString("Command.TimedGlobal.Announce.Off");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceOff));
            }
        }
    }
}
