package com.cloudcraftgaming.perworldchatplus.chat;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.IPUtility;
import com.cloudcraftgaming.perworldchatplus.utils.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 *
 * This class is used to set a chat message's content and other related tasks.
 */
public class ChatMessage {
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
        newMessage = filterAds(newMessage, sender);
        newMessage = removeGlobalBypassString(newMessage);
        newMessage = makeMessageColorful(newMessage, sender);

        return newMessage;
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

        if (Main.plugin.getConfig().getString("Chat.Swear.Block.Enabled").equalsIgnoreCase("True")) {
            String replacer = Main.plugin.getConfig().getString("Chat.Swear.Replace");
            List<String> blockedWords = Main.plugin.getConfig().getStringList("Chat.Swear.Blocked");
            for (String blockedWord : blockedWords) {
                if (newMessage.toLowerCase().contains(blockedWord.toLowerCase())) {
                    if (Main.plugin.getConfig().getString("Chat.Swear.Block.EntireMessage").equalsIgnoreCase("True")) {
                        newMessage = replacer;
                        hasSworn = true;
                        break;
                    } else {
                        newMessage = newMessage.replaceAll("(?i)" + blockedWord, replacer);
                        hasSworn = true;
                    }
                }
            }
        }
        if (hasSworn) {
            PlayerHandler.doStuffOnSwear(sender);
        }
        return newMessage;
    }

    /**
     * Gets a new message that is cleaned of all blocked ads (except for exempt ads) if enabled (Case insensitive).
     * This is still in beta so it may not work fully or at all (Sorry).
     * @param message The original message to be sent (unfiltered).
     * @param sender The sender of the message (to be used if they did advertise because action may be taken).
     * @return A new message that is clean of all blocked ads.
     */
    public static String filterAds(String message, Player sender) {
        String newMessage = message;
        boolean hasAdvertised = false;

        if (Main.plugin.getConfig().getString("Chat.Ad.Block.Enabled").equalsIgnoreCase("True")) {
            String replacer = Main.plugin.getConfig().getString("Chat.Ad.Replace");
            if (Main.plugin.getConfig().getString("Chat.Ad.Block.Ip-Addresses").equalsIgnoreCase("True")) {
                String[] words = newMessage.split(" ");
                for (String word : words) {
                    if (IPUtility.isIpAddress(word)) {
                        if (Main.plugin.getConfig().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
                            newMessage = replacer;
                            hasAdvertised = true;
                            break;
                        } else {
                            newMessage = newMessage.replaceAll("(?i)" + word, replacer);
                            hasAdvertised = true;
                        }
                    }
                }
            }
            if (Main.plugin.getConfig().getString("Chat.Ad.Block.Websites").equalsIgnoreCase("True")) {
                String[] words = newMessage.split(" ");
                for (String word : words) {
                    if (IPUtility.isURL(word)) {
                        if (Main.plugin.getConfig().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
                            newMessage = replacer;
                            hasAdvertised = true;
                            break;
                        } else {
                            newMessage = newMessage.replaceAll("(?i)" + word, replacer);
                            hasAdvertised = true;
                        }
                    }
                }
            }
        }
        if (hasAdvertised) {
            PlayerHandler.doStuffOnAdvertise(sender);
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

    /**
     * Prefixes the chat message with the player's default chat color,
     * and if they have permission, will translate color codes in their message.
     * @param message The original message to be sent.
     * @param sender The sender of the message.
     * @return A new message with colors.
     */
    public static String makeMessageColorful(String message, Player sender) {
        if (Main.plugin.getConfig().getString("Chat.Color.RequirePermission").equalsIgnoreCase("True")) {
            if (!sender.hasPermission("pwcp.chat.color")) {
                if (Main.plugin.getConfig().getString("Chat.Color.StripWithoutPermission").equalsIgnoreCase("True")) {
                    return ChatColor.stripColor(message);
                } else {
                    return message;
                }
            }
        }
        if (Main.plugin.getConfig().getString("Chat.Color.Auto").equalsIgnoreCase("True")) {
            message = PlayerDataManager.getChatColor(sender) + message;
        }
        if (Main.plugin.getConfig().getString("Chat.Color.Translate").equalsIgnoreCase("True")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        return message;
    }

    //Checks/Booleans
    /**
     * Checks if the chat message should be sent globally.
     * @param message The chat message to check.
     * @param sender The sender of the chat message.
     * @return True if the chat message should be global, else false.
     */
    public static boolean shouldBeGlobal(String message, Player sender) {
        return Main.plugin.getConfig().getString("Global.Always Global").equalsIgnoreCase("True")
                || Main.plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")
                || PlayerDataManager.hasGlobalBypassEnabled(sender)
                || (message.contains(Main.plugin.getConfig().getString("Global.Override")) && sender.hasPermission("pwcp.bypass"));
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
}