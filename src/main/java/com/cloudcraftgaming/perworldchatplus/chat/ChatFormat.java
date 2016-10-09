package com.cloudcraftgaming.perworldchatplus.chat;

import com.cloudcraftgaming.perworldchatplus.Main;
import com.cloudcraftgaming.perworldchatplus.data.WorldDataManager;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
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
            String format = getFormatTemplate(sender);
            if (ChatMessage.shouldBeGlobal(message, sender)) {
                format = getGlobalTemplate();
            }
            format = replacePlayerVariable(format);
            format = replacePlayerNameVariable(format, sender);
            format = replacePrefixVariable(format, sender);
            format = replaceSuffixVariable(format, sender);
            format = replaceMessageVariable(format, message);
            format = replaceWorldVariable(format, sender);

            //Factions replacers
            format = replaceFactionNameVariable(format, sender);
            format = replaceFactionTagVariable(format, sender);

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
    public static String getFormatTemplate(Player sender) {
        if (Main.plugin.getConfig().getString("Format.PerWorld").equalsIgnoreCase("True")) {
            if (Main.plugin.getConfig().contains("Format.Format." + sender.getWorld().getName())) {
                return Main.plugin.getConfig().getString("Format.Format." + sender.getWorld().getName());
            }
        }
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
     * Replaces the specified variable in the text with a specified replacement.
     * This is a general method for developers to use to replace variables not native to PerWorldChatPlus.
     * @param format The current format of the message.
     * @param _var The variable to look for within the format.
     * @param replaceWith The string to replace the specified variable with.
     * @return The message's format with the specified variable replaced with the specified text.
     */
    public static String replaceVariable(String format, String _var, String replaceWith) {
        if (format.contains(_var)) {
            return format.replaceAll(_var, replaceWith);
        } else {
            return format;
        }
    }
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
     * Replaces the player name variable with the sender's username.
     * @param format The current format of the message.
     * @param sender The sender of the message.
     * @return The message's format with the player variable replaced.
     */
    public static String replacePlayerNameVariable(String format, Player sender) {
        if (format.contains("%playername%")) {
            return format.replaceAll("%playername%", sender.getName());
        } else {
            return format;
        }
    }

    /**
     * Replaces the message variable with Bukkit's '%s' identifier.
     * Or the message if not using Bukkit's identifiers.
     * ('%s' is Bukkit's default and recognized pattern, this also allows other plugins to change the message).
     * @param format The current format of the message.
     * @return The message's format with the message variable replaced.
     */
    public static String replaceMessageVariable(String format, String message) {
        if (format.contains("%message%")) {
            if (format.contains("%player%") || format.contains("%s")) {
                return format.replaceAll("%message%", "%s");
            } else {
                return format.replaceAll("%message%", message);
            }
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
     * Replaces the prefix variable with the sender's world specific prefix.
     * @param format The current format of the message.
     * @param sender The sender of the message.
     * @return The message's format with the prefix variable replaced.
     */
    public static String replacePrefixVariable(String format, Player sender) {
        if (format.contains("%prefix%") && Main.plugin.getChat() != null) {
            String prefix = Main.plugin.getChat().getPlayerPrefix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
            return format.replaceAll("%prefix%", prefix);
        } else {
            return format;
        }
    }

    /**
     * Replaces the suffix variable with the sender's world specific suffix.
     * @param format The current format of the message.
     * @param sender The sender of the message.
     * @return The message's format with the suffix variable replaced.
     */
    public static String replaceSuffixVariable(String format, Player sender) {
        if (format.contains("%suffix%") && Main.plugin.getChat() != null) {
            String suffix = Main.plugin.getChat().getPlayerSuffix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
            return format.replaceAll("%suffix%", suffix);
        } else {
            return format;
        }
    }

    /**
     * Replaces the faction name variable with the name of the sender's faction.
     * @param format The current format of the message.
     * @param sender The sender of the message.
     * @return The message's format with the faction name variable replaced.
     */
    public static String replaceFactionNameVariable(String format, Player sender) {
        if (format.contains("%factionName%") && Main.plugin.hasFactions()) {
            MPlayer mSender = MPlayer.get(sender.getUniqueId());
            return format.replaceAll("%factionName%", mSender.getFaction().getName());
        } else {
            return format;
        }
    }

    public static String replaceFactionTagVariable(String format, Player sender) {
        if (format.contains("%factionTitle%") && Main.plugin.hasFactions()) {
            MPlayer mSender = MPlayer.get(sender.getUniqueId());
            return format.replaceAll("%factionTitle%", mSender.getTitle());
        } else {
            return format;
        }
    }
}
