package com.cloudcraftgaming.perworldchatplus.api.utils;

import com.cloudcraftgaming.perworldchatplus.api.PerWorldChatPlusAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 5/30/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 * <p>
 * This class handles different player related functions when needed.
 */
public class PlayerHandler {
    /**
     * This function checks if the player should be kicked if they have sworn, among other things.
     *
     * @param player The player who is being checked.
     */
    public static void doStuffOnSwear(final Player player) {
        if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Swear.Kick.Enabled").equalsIgnoreCase("True")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(PerWorldChatPlusAPI.getApi().getPlugin(), () -> {
                final String msgOr = MessageManager.getMessages().get().getString("Chat.Swear.Kick.PLayer");

                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', msgOr));

                if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Swear.Kick.Announce").equalsIgnoreCase("True")) {
                    String anOr = MessageManager.getMessages().get().getString("Chat.Swear.Kick.Announcement");
                    String announcement = anOr.replaceAll("%player%", player.getName());
                    Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', announcement));
                }
            });
        }
    }

    /**
     * This function checks if the player should be kicked if they have advertised on the server.
     *
     * @param player The player who is being checked.
     */
    public static void doStuffOnAdvertise(final Player player) {
        if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Ad.Kick.Enabled").equalsIgnoreCase("True")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(PerWorldChatPlusAPI.getApi().getPlugin(), () -> {
                player.kickPlayer(MessageManager.getMessage("Chat.Ad.Kick.Player"));

                if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Swear.Ad.Announce").equalsIgnoreCase("True")) {
                    String anOr = MessageManager.getMessages().get().getString("Chat.Ad.Kick.Announcement");
                    String announcement = anOr.replaceAll("%player%", player.getName());
                    Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', announcement));
                }
            });
        }
    }

    public static void doStuffOnSpam(final Player player) {
        if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Spam.Kick.Enabled").equalsIgnoreCase("True")) {

            Bukkit.getScheduler().scheduleSyncDelayedTask(PerWorldChatPlusAPI.getApi().getPlugin(), () -> {
                player.kickPlayer(MessageManager.getMessage("Chat.Spam.Kick.Player"));

                if (PerWorldChatPlusAPI.getApi().getConfig().get().getString("Chat.Spam.Kick.Announce").equalsIgnoreCase("True")) {
                    String anOr = MessageManager.getMessages().get().getString("Chat.Spam.Kick.Announcement");
                    String announcement = anOr.replaceAll("%player%", player.getName());
                    Bukkit.broadcastMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', announcement));
                }
            });
        }
    }

    /**
     * Sends a notice to the player telling them they were mentioned in a chat message.
     *
     * @param player The player that was mentioned.
     * @param sender The sender of the message.
     */
    public static void sendMentionNotice(Player player, Player sender) {
        String msgOr = MessageManager.getMessages().get().getString("Mention.Notice");
        String msg = msgOr.replaceAll("%sender%", sender.getDisplayName());
        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
    }
}