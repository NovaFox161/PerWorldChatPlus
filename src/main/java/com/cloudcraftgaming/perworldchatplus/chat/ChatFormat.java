package com.cloudcraftgaming.perworldchatplus.chat;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.data.WorldDataManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 *
 * This class is to be used to set the chat message's format and to do other format related tasks.
 */
public class ChatFormat {

    /**
     * Determines the chat message format. This combines all formatting methods.
     * @param message The message to format.
     * @param sender The sender of the message.
     * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
     */
    public static String determineMessageFormat(String originalFormat, String message, Player sender) {
        if (Main.plugin.getConfig().getString("Format.Enabled").equalsIgnoreCase("True")) {
            String format = getDefaultTemplate();
            if (ChatMessage.shouldBeGlobal(message, sender)) {
                format = getGlobalTemplate();
            }
            format = replacePlayerVariable(format);
            format = replaceMessageVariable(format);
            format = replaceWorldVariable(format, sender);
            format = replaceGlobalVariable(format);

            return ChatColor.translateAlternateColorCodes('&', format);
        } else {
            return originalFormat;
        }
    }



    //Format template getters

    /**
     * Gets the default chat format template from file.
     * @return The default chat format template.
     */
    public static String getDefaultTemplate() {
        return Main.plugin.getConfig().getString("Format.Format.Default");
    }

    /**
     * Gets the global chat format template.
     * @return The global chat format template.
     */
    public static String getGlobalTemplate() {
        return Main.plugin.getConfig().getString("Format.Format.Global");
    }


    //Format variable replacers

    /**
     * Replaces the player variable with Bukkit's '%s' identifier.
     * ('%s' is Bukkit's default and recognized pattern, this also allows other plugins to change the message).
     * @param format The current format of the message.
     * @return The message's format with the player variable replaced.
     */
    public static String replacePlayerVariable(String format) {
        if (format.contains("%player%")) {
            return format.replaceAll("%player%", "%s");
        } else {
            return format;
        }
    }

    /**
     * Replaces the message variable with Bukkit's '%s' identifier.
     * ('%s' is Bukkit's default and recognized pattern, this also allows other plugins to change the message).
     * @param format The current format of the message.
     * @return The message's format with the message variable replaced.
     */
    public static String replaceMessageVariable(String format) {
        if (format.contains("%message%")) {
            return format.replaceAll("%message%", "%s");
        } else {
            return format;
        }
    }

    /**
     * Replaces the world variable with the sender's world name or world alias.
     * @param format The current format of the message.
     * @param sender The sender of the message
     * @return The message's format with the world variable replaced.
     */
    public static String replaceWorldVariable(String format, Player sender) {
        if (format.contains("%world%")) {
            return format.replaceAll("%world%", WorldDataManager.getAlias(sender.getWorld().getName()));
        } else {
            return format;
        }
    }

    /**
     * Replaces the global variable with the global prefix.
     * @param format The current format of the message.
     * @return The message's format with the global variable replaced.
     */
    public static String replaceGlobalVariable(String format) {
        if (format.contains("%global%")) {
            String gPrefixOr = Main.plugin.getConfig().getString("Global.Prefix");
            String gPrefix = ChatColor.translateAlternateColorCodes('&', gPrefixOr) + ChatColor.RESET;
            return format.replaceAll("%global%", gPrefix);
        } else {
            return format;
        }
    }
}
