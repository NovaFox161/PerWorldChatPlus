package com.cloudcraftgaming.perworldchatplus.chat;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.utils.ListManager;
import com.cloudcraftgaming.perworldchatplus.utils.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Nova Fox on 1/30/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * This class contains all methods for setting what players receive a chat message, what a chat message should contain,
 * how it should be formatted, etc.
 */
public class PlayerChatManager {
    /**
     * Determines what players will receive a message, based on the chat rules set in the config, or chat rules that are enabled per player.
     * This combines all methods for checking what players will receive the message. (Used by PerWorldChatPlus's main chat listener).
     *
     * @param recipients the recipients of the chat message.
     * @param message    the chat message.
     * @param sender     the sender of the chat message.
     * @return a Set of recipients of the chat message, filtering out players that should not receive the message.
     */
    public static Set<Player> determineChatRecipients(Set<Player> recipients, String message, Player sender) {
        recipients.add(sender);
        Set<Player> mentionReceivers = getAllMentionReceivers(recipients, message, sender);
        for (Player p : mentionReceivers) {
            recipients.add(p);
        }
        Set<Player> alertReceivers = getAllAlertReceivers(recipients, message);
        for (Player p : alertReceivers) {
            recipients.add(p);
        }
        Set<Player> spyReceivers = getAllSpyReceivers(recipients, sender);
        for (Player p : spyReceivers) {
            recipients.add(p);
        }
        Set<Player> sharesReceivers = getAllSharesReceivers(recipients, sender);
        for (Player p : sharesReceivers) {
            recipients.add(p);
        }
        Set<Player> globalReceivers = getAllGlobalReceivers(recipients, message, sender);
        for (Player p : globalReceivers) {
            recipients.add(p);
        }
        List<Player> removeMuted = getMutedReceivers();
        for (Player p : removeMuted) {
            if (recipients.contains(p)) {
                recipients.remove(p);
            }
        }


        if (!recipients.contains(sender)) {
            recipients.add(sender);
        }

        return recipients;
    }

    /**
     * Determines the message contents before it is sent based on settings and what the original message contained.
     * This combines all methods for checking what the message should contain. (Used by PerWorldChatPlus's main chat listener).
     * @param originalMessage The original message before sending.
     * @param sender The sender of the chat message.
     * @return a new string message to be sent as the chat message.
     */
    public static String determineMessageContents(String originalMessage, Player sender) {
        String newMessage = originalMessage;
        newMessage = filterSwears(newMessage, sender);
        newMessage = removeGlobalBypassString(newMessage);

        return newMessage;
    }


    //Chat receiver filtering.

    /**
     * Gets a set of players that were mentioned in the chat message.
     * @param recipients The current recipients of the message. (Important that this is current to avoid duplicates.
     * @param message The message to be sent.
     * @param sender The sender of the message.
     * @return A set of players that will receive the message based on its contents.
     */
    public static Set<Player> getAllMentionReceivers(Set<Player> recipients, String message, Player sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (wasMentioned(p, message)) {
                if (!recipients.contains(p)) {
                    recipients.add(p);
                }
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0f);
                if (shouldSendMentionNotice()) {
                    PlayerHandler.sendMentionNotice(p, sender);
                }
            }
        }
        return recipients;
    }
    /**
     * Gets a set of players that will receive the chat message based on it's contents (if they have an alert word that appears in the message.
     * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
     * @param message The message to be sent.
     * @return A set of players that will receive the message based on its contents.
     */
    public static Set<Player> getAllAlertReceivers(Set<Player> recipients, String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID pId = p.getUniqueId();
            if (Main.plugin.alerts.contains("Alerts." + pId)) {
                for (String word : Main.plugin.alerts.getStringList("Alerts." + pId)) {
                    if (message.contains(word)) {
                        if (!(recipients.contains(p))) {
                            recipients.add(p);
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0f);
                    }
                }
            }
        }
        return  recipients;
    }
    /**
     * Gets a set of players that will receive the message if they are spying on all chat globally or for sender's world.
     * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
     * @param sender The sender of the message.
     * @return A set of players that will receive the message based on if they are spying chat globally or the sender's world.
     */
    public static Set<Player> getAllSpyReceivers(Set<Player> recipients, Player sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (hasGlobalChatSpyEnabled(p)) {
                if  (!recipients.contains(p)) {
                    recipients.add(p);
                }
            } else if (hasWorldChatSpyEnabled(p)) {
                if (isSpyingOnWorld(p, sender.getWorld().getName())) {
                    if (!(recipients.contains(p))) {
                        recipients.add(p);
                    }
                }
            }
        }
        return recipients;
    }
    /**
     * Gets a set of players that will receive the message if the recipients are a world that shares its chat with the sender.
     * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
     * @param sender The sender of the message.
     * @return A set of players that will receive the message if the recipients are in a world that shares its chat with the sender.
     */
    public static Set<Player> getAllSharesReceivers(Set<Player> recipients, Player sender) {
        String worldFrom = sender.getWorld().getName();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!recipients.contains(p)) {
                if (isShared(worldFrom, p.getWorld().getName())) {
                    recipients.add(p);
                }
            }
        }
        return recipients;
    }
    /**
     * Gets a set of all players online if the message is supposed to be global (excluding players that are already receiving the message).
     * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
     * @param message The message to be sent (This is to check if it contains the override/bypass).
     * @param sender The sender of the message.
     * @return A set of all players that will receive the message if chat is supposed to be global.
     */
    public static Set<Player> getAllGlobalReceivers(Set<Player> recipients, String message, Player sender) {
       if (shouldBeGlobal(message, sender)) {
           for (Player p : Bukkit.getOnlinePlayers()) {
               if (!(recipients.contains(p))) {
                   recipients.add(p);
               }
           }
       }
        return recipients;
    }
    /**
     * Gets a set of players that SHOULD NOT receive the message because their chat is muted.
     * @return A set of players that SHOULD NOT receive the chat message.
     */
    public static List<Player> getMutedReceivers() {
       List<Player> muted = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (hasChatMuted(p)) {
                if (!muted.contains(p)) {
                    muted.add(p);
                }
            }
        }
        return muted;
    }


    //Chat message filtering.

    /**
     * Gets a new message that is cleaned of all blocked swear/curse words if enabled (Case insensitive).
     * @param message The original message to be sent (unfiltered).
     * @param sender The sender of the message (to be used if they did swear because an action may be taken).
     * @return A new message that is clean of all blocked swears.
     */
    public static String filterSwears(String message, Player sender) {
        String newMessage = message;
        boolean hasSworn = false;

        if (Main.plugin.getConfig().getString("Chat.Swear.Block").equalsIgnoreCase("True")) {
            String replacer = Main.plugin.getConfig().getString("Chat.Swear.Replace");
            List<String> blockedWords = Main.plugin.getConfig().getStringList("Chat.Swear.Blocked");
            for (String blockedWord : blockedWords) {
                if (message.toLowerCase().contains(blockedWord.toLowerCase())) {
                    newMessage = newMessage.replaceAll("(?i)" + blockedWord, replacer);
                    hasSworn = true;
                }
            }
        }
        if (hasSworn) {
            PlayerHandler.doStuffOnSwear(sender);
        }
        return newMessage;
    }

    /**
     * If the chat message contains the Global Override String, it will remove it so that it is not visible in chat.
     * @param message The original message to be sent.
     * @return A new message that does not contain  the Global Override String.
     */
    public static String removeGlobalBypassString(String message) {
        if (message.contains(Main.plugin.getConfig().getString("Global.Override"))) {
            String override = Main.plugin.getConfig().getString("Global.Override");
            message = message.replaceAll(override, "").trim();
        }
        return message;
    }




    //Booleans/Checkers

    /**
     * Checks if the player has their chat muted (Not receiving any chat messages).
     * @param player The player to check.
     * @return True if the player has their chat muted, else false.
     */
    public static boolean hasChatMuted(Player player) {
        UUID id = player.getUniqueId();
        if (Main.plugin.data.contains("Players." + id + ".ChatMute")) {
            return Main.plugin.data.getString("Players." + id + ".ChatMute").equalsIgnoreCase("True");
        }
        return false;
    }

    /**
     * Checks if the player is currently spying on all chat (seeing chat for all worlds, ignoring sharing).
     * @param player The player to check.
     * @return True if the player is currently spying on chat, else false.
     */
    public static boolean hasGlobalChatSpyEnabled(Player player) {
        UUID id = player.getUniqueId();
        if (Main.plugin.data.contains("Players." + id + ".Spy")) {
            return Main.plugin.data.getString("Players." + id + ".Spy").equalsIgnoreCase("True");
        }
        return false;
    }

    /**
     * Checks if the specified player has WorldChatSpy Enabled.
     * Check PlayerChatManager#isSpyingOnWorld(Player player, String worldName) to see if they are spying on the particular world.
     * @param player The player to check.
     * @return True if the player currently has World Chat Spy enabled, else false.
     */
    public static boolean hasWorldChatSpyEnabled(Player player) {
        return Main.plugin.data.getString("Players." + player.getUniqueId() + ".WorldSpy").equalsIgnoreCase("True");
    }

    /**
     * Checks if the specified player is currently spying on the specific world.
     * @param player The player to check.
     * @param worldName The name of the world the chat message came from.
     * @return True if the player is currently spying on that world, else false.
     */
    public static boolean isSpyingOnWorld(Player player, String worldName) {
        if (hasWorldChatSpyEnabled(player)) {
            if (Main.plugin.worldSpyYml.contains("Players." + player.getUniqueId())) {
                for (String spiedWorld : Main.plugin.worldSpyYml.getStringList("Players." + player.getUniqueId())) {
                    if (worldName.equalsIgnoreCase(spiedWorld)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player currently has Global Chat Bypass enabled. (Sending all chat messages globally by default).
     * @param player The player to check.
     * @return True if the player currently has Global Chat Bypass enabled, else false.
     */
    public static boolean hasGlobalBypassEnabled(Player player) {
        UUID id = player.getUniqueId();
        if (Main.plugin.data.contains("Players." + id + ".Bypass")) {
            return Main.plugin.data.getString("Players." + id + ".Bypass").equalsIgnoreCase("True");
        }
        return false;
    }

    /**
     * Checks if the player has the specified word defined in their alerts.
     * @param player The player to check.
     * @param word The alert word to check.
     * @return True if the player has the word as an alert word, else false.
     */
    public static boolean hasAlertWord(Player player, String word) {
        UUID id = player.getUniqueId();
        if (Main.plugin.alerts.contains("Alerts." + id)) {
            return Main.plugin.alerts.getStringList("Alerts." + id).contains(word);
        }
        return false;
    }

    /**
     * Checks if the player's name was mentioned in the chat message (Only if requirements were met).
     * @param player The player to check.
     * @param message The message to check.
     * @return True if the player was mentioned in the message, else false.
     */
    public static boolean wasMentioned(Player player, String message) {
        if (Main.plugin.getConfig().getString("Alert.Mention.OnName").equalsIgnoreCase("True")) {
            if (Main.plugin.getConfig().getString("Alert.Mention.RequirePermission").equalsIgnoreCase("True")) {
                if (player.hasPermission("pwcp.alert.mention")) {
                    if (Main.plugin.getConfig().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                        if (message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName())) {
                            return true;
                        }
                    } else {
                        if (message.contains(player.getName()) || message.contains(player.getDisplayName())) {
                            return true;
                        }
                    }
                }
            } else {
                if (Main.plugin.getConfig().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                    if (message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName())) {
                        return true;
                    }
                } else {
                    if (message.contains(player.getName()) || message.contains(player.getDisplayName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the plugin should send a notice to the player that was mentioned.
     * @return True if the player should be notified, else false.
     */
    public static boolean shouldSendMentionNotice() {
        return Main.plugin.getConfig().getString("Alert.Mention.SendNotice").equalsIgnoreCase("True");
    }

    /**
     * Checks if the chat message should be sent globally.
     * @param message The chat message to check.
     * @param sender The sender of the chat message.
     * @return True if the chat message should be global, else false.
     */
    public static boolean shouldBeGlobal(String message, Player sender) {
        if (Main.plugin.getConfig().getString("Global.Always Global").equalsIgnoreCase("True")) {
            return true;
        } else if (Main.plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")) {
            return true;
        } else if (hasGlobalBypassEnabled(sender)) {
            return true;
        } else if (message.contains(Main.plugin.getConfig().getString("Global.Override")) && sender.hasPermission("pwcp.bypass")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if the chat message should be shared to players in the world.
     * @param worldFrom The world the chat message came from.
     * @param worldTo The world the possible recipient is in.
     * @return True if the message should be shared to the possible receiver, else false.
     */
    public static boolean isShared(String worldFrom, String worldTo) {
        String sharesListName = ListManager.getWorldShareListName(worldFrom);
        List<String> sharesList = ListManager.getWorldShareList(worldFrom);
        if (sharesList != null && sharesListName != null) {
            if (sharesListName.equalsIgnoreCase(worldTo) || sharesList.contains(worldTo)) {
                return true;
            }
        }
        return false;
    }
}