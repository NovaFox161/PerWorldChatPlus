package com.cloudcraftgaming.perworldchatplus.api.chat;

import com.cloudcraftgaming.perworldchatplus.PerWorldChatPlusPlugin;
import com.cloudcraftgaming.perworldchatplus.api.data.WorldDataManager;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 * <p>
 * This class is to be used to set the chat message's format and to do other format related tasks.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ChatFormat {
	
	/**
	 * Determines the chat message format. This combines all formatting methods.
     * Use {@link ChatFormat#determineMessageFormat(String, String, Player, Boolean)} to manually control use of the global prefix.
	 *
	 * @param message The message to format.
	 * @param sender  The sender of the message.
	 * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
	 */
	public static String determineMessageFormat(String originalFormat, String message, Player sender) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Enabled").equalsIgnoreCase("True")) {
			String format = getFormatTemplate(sender);
			if (ChatMessage.shouldBeGlobal(message, sender)) {
				format = getGlobalTemplate();
			}
			format = replacePlayerVariable(format, sender, false);
			format = replacePlayerNameVariable(format, sender);
			format = replacePrefixVariable(format, sender);
			format = replaceSuffixVariable(format, sender);
			format = replaceMessageVariable(format, message, false);
			format = replaceWorldVariable(format, sender);
			
			//Factions replacers
			format = replaceFactionNameVariable(format, sender);
			format = replaceFactionTagVariable(format, sender);
			
			return ChatColor.translateAlternateColorCodes('&', format);
		} else {
			return originalFormat;
		}
	}
	
	/**
	 * Determines the chat message format. This combines all formatting methods. This method allows direct control of using global formatting or not.
     * Use {@link ChatFormat#determineMessageFormat(String, String, Player)} to automatically allow PWCP to determine which format to use.
	 *
	 * @param originalFormat The message to format.
	 * @param message        The sender of the message.
	 * @param sender         The sender of the message.
	 * @param global         Whether or not to use the global formatting.
	 * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
	 */
	public static String determineMessageFormat(String originalFormat, String message, Player sender, Boolean global) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Enabled").equalsIgnoreCase("True")) {
			String format = getFormatTemplate(sender);
			if (global) {
				format = getGlobalTemplate();
			}
			format = replacePlayerVariable(format, sender, false);
			format = replacePlayerNameVariable(format, sender);
			format = replacePrefixVariable(format, sender);
			format = replaceSuffixVariable(format, sender);
			format = replaceMessageVariable(format, message, false);
			format = replaceWorldVariable(format, sender);
			
			//Factions replacers
			format = replaceFactionNameVariable(format, sender);
			format = replaceFactionTagVariable(format, sender);
			
			return ChatColor.translateAlternateColorCodes('&', format);
		} else {
			return originalFormat;
		}
	}
	
	/**
	 * Determines the chat message format. This combines all formatting methods. This method allows direct control of using global formatting or not.
     * Use {@link ChatFormat#determineMessageFormat(String, String, Player)} to automatically allow PWCP to determine which format to use.
	 *
	 * @param originalFormat The message to format.
	 * @param message        The sender of the message.
	 * @param sender         The sender of the message.
	 * @param global         Whether or not to use the global formatting.
	 * @param ignoreBukkit   Whether or not to support Bukkit's '%s' vars.
	 * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
	 */
	public static String determineMessageFormat(String originalFormat, String message, Player sender, Boolean global, Boolean ignoreBukkit) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Enabled").equalsIgnoreCase("True")) {
			String format = getFormatTemplate(sender);
			if (global) {
				format = getGlobalTemplate();
			}
			format = replacePlayerVariable(format, sender, ignoreBukkit);
			format = replacePlayerNameVariable(format, sender);
			format = replacePrefixVariable(format, sender);
			format = replaceSuffixVariable(format, sender);
			format = replaceMessageVariable(format, message, ignoreBukkit);
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
	 *
	 * @return The default chat format template.
	 */
	public static String getFormatTemplate(Player sender) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.PerWorld").equalsIgnoreCase("True")) {
            if (PerWorldChatPlusPlugin.plugin.getConfig().contains("Format.Format." + sender.getWorld().getName())) {
                return PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Format." + sender.getWorld().getName());
			}
		}
        return PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Format.Default");
	}
	
	/**
	 * Gets the global chat format template.
	 *
	 * @return The global chat format template.
	 */
	public static String getGlobalTemplate() {
        return PerWorldChatPlusPlugin.plugin.getConfig().getString("Format.Format.Global");
	}
	
	//Format variable replacers
	
	/**
	 * Replaces the specified variable in the text with a specified replacement.
	 * This is a general method for developers to use to replace variables not native to PerWorldChatPlus.
	 *
	 * @param format      The current format of the message.
	 * @param _var        The variable to look for within the format.
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
	 *
	 * @param format The current format of the message.
	 * @return The message's format with the player variable replaced.
	 */
	public static String replacePlayerVariable(String format, Player sender, Boolean ignoreBukkit) {
		if (format.contains("%player%")) {
			if (!ignoreBukkit) {
				return format.replaceAll("%player%", "%s");
			} else {
				return format.replaceAll("%player%", sender.getName());
			}
		} else {
			return format;
		}
	}
	
	/**
	 * Replaces the player name variable with the sender's username.
	 *
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
	 *
	 * @param format The current format of the message.
	 * @return The message's format with the message variable replaced.
	 */
	public static String replaceMessageVariable(String format, String message, Boolean ignoreBukkit) {
		if (format.contains("%message%")) {
			if (format.contains("%player%") || format.contains("%s")) {
				if (!ignoreBukkit) {
					return format.replaceAll("%message%", "%s");
				} else {
					return format.replaceAll("%message%", message);
				}
			} else {
				return format.replaceAll("%message%", message);
			}
		} else {
			return format;
		}
	}
	
	/**
	 * Replaces the world variable with the sender's world name or world alias.
	 *
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
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the prefix variable replaced.
	 */
	public static String replacePrefixVariable(String format, Player sender) {
        if (format.contains("%prefix%") && PerWorldChatPlusPlugin.plugin.getChat() != null) {
            String prefix = PerWorldChatPlusPlugin.plugin.getChat().getPlayerPrefix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
			return format.replaceAll("%prefix%", prefix);
		} else {
			return format;
		}
	}
	
	/**
	 * Replaces the suffix variable with the sender's world specific suffix.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the suffix variable replaced.
	 */
	public static String replaceSuffixVariable(String format, Player sender) {
        if (format.contains("%suffix%") && PerWorldChatPlusPlugin.plugin.getChat() != null) {
            String suffix = PerWorldChatPlusPlugin.plugin.getChat().getPlayerSuffix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
			return format.replaceAll("%suffix%", suffix);
		} else {
			return format;
		}
	}
	
	/**
	 * Replaces the faction name variable with the name of the sender's faction.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the faction name variable replaced.
	 */
	public static String replaceFactionNameVariable(String format, Player sender) {
        if (format.contains("%factionName%") && PerWorldChatPlusPlugin.plugin.hasFactions()) {
			MPlayer mSender = MPlayer.get(sender.getUniqueId());
			return format.replaceAll("%factionName%", mSender.getFaction().getName());
		} else {
			return format;
		}
	}
	
	/**
	 * Replaces the faction tag variable with the name of the sender's faction tag.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the faction tag variable replaced.
	 */
	public static String replaceFactionTagVariable(String format, Player sender) {
        if (format.contains("%factionTitle%") && PerWorldChatPlusPlugin.plugin.hasFactions()) {
			MPlayer mSender = MPlayer.get(sender.getUniqueId());
			return format.replaceAll("%factionTitle%", mSender.getTitle());
		} else {
			return format;
		}
	}
}
