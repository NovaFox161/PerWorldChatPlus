package com.cloudcraftgaming.perworldchatplus.api.chat;

import com.cloudcraftgaming.perworldchatplus.api.PerWorldChatPlusAPI;
import com.cloudcraftgaming.perworldchatplus.api.utils.MessageManager;
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
 * <p>
 * The manager class for Timed Global Chat. This class contains the functions for everything Timed Global chat related.
 */
public class TimedGlobalChatManager {
    private int timedRequest = 0;

    private static TimedGlobalChatManager instance;

    private TimedGlobalChatManager() {
    } //Stop initialization

    /**
     * Gets the instance of the TimedGlobalChatManager
     *
     * @return The TimedGlobalChatManager
     */
    public static TimedGlobalChatManager getManager() {
        if (instance == null) {
            instance = new TimedGlobalChatManager();
        }
        return instance;
    }

    /**
     * Turns on Timed Global Chat for the specified amount of time.
     *
     * @param sender sender of the command
     * @param time   (int) amount of time for the Timed Global Chat to be on.
     */
    public void turnOnTimedGlobal(final CommandSender sender, Integer time) {
        final String prefix = MessageManager.getPrefix();
        BukkitScheduler scheduler = PerWorldChatPlusAPI.getApi().getPlugin().getServer().getScheduler();
        PerWorldChatPlusAPI.getApi().getConfig().get().set("Global.TimedGlobal.On", true);
        PerWorldChatPlusAPI.getApi().getConfig().save();
        Random random = new Random(999999999);
        final Integer requestNumber = random.nextInt();
        timedRequest = requestNumber;
        String turnedOnMsgOr = MessageManager.getMessages().get().getString("Command.TimedGlobal.TurnedOn");
        String turnedOnMsg = turnedOnMsgOr.replaceAll("%time%", time.toString());
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', turnedOnMsg));
        if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
            String announceMsgOr = MessageManager.getMessages().get().getString("Command.TimedGlobal.Announce.On");
            String announceMsg = announceMsgOr.replaceAll("%time%", time.toString());
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceMsg));
            }
        }
        scheduler.scheduleSyncDelayedTask(PerWorldChatPlusAPI.getApi().getPlugin(), () -> {
            if (timedRequest == requestNumber) {
                PerWorldChatPlusAPI.getApi().getConfig().get().set("Global.TimedGlobal.On", false);
                PerWorldChatPlusAPI.getApi().getConfig().save();
                timedRequest = 0;
                String offMsg = MessageManager.getMessages().get().getString("Command.TimedGlobal.TurningOff");
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', offMsg));
                if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
                    String announceOff = MessageManager.getMessages().get().getString("Command.TimedGlobal.Announce.Off");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceOff));
                    }
                }
            }
        }, 20L * 60 * time);
    }

    /**
     * Turns off Timed Global Chat.
     *
     * @param sender the sender of the command.
     */
    public void turnOffTimedGlobal(CommandSender sender) {
        String prefix = MessageManager.getPrefix();
        PerWorldChatPlusAPI.getApi().getConfig().get().set("Global.TimedGlobal.On", false);
        PerWorldChatPlusAPI.getApi().getConfig().save();
        timedRequest = 0;
        String offMsg = MessageManager.getMessages().get().getString("Command.TimedGlobal.TurnedOff");
        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', offMsg));
        if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Global.TimedGlobal.Announce").equalsIgnoreCase("True")) {
            String announceOff = MessageManager.getMessages().get().getString("Command.TimedGlobal.Announce.Off");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', announceOff));
            }
        }
    }
}
