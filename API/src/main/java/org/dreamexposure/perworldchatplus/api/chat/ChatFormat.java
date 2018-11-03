package org.dreamexposure.perworldchatplus.api.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.WorldDataManager;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 * <p>
 * This class is to be used to set the chat message's format and to do other format related tasks.
 */
@SuppressWarnings({"WeakerAccess", "unused", "Duplicates"})
public class ChatFormat {
	
	/**
	 * Determines the chat message format. This combines all formatting methods.
	 *
	 * @param format The format to use.
	 * @param message The message to format.
	 * @param sender  The sender of the message.
	 * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
	 */
	public static String determineMessageFormat(String format, String message, Player sender) {
		if (PerWorldChatPlusAPI.getApi().papiCheck())
			format = PlaceholderAPI.setPlaceholders(sender, format);
		
		format = replaceMessageVariable(format, message, false);
		format = replaceWorldVariable(format, sender);
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	/**
	 * Determines the chat message format. This combines all formatting methods. This method allows direct control of using global formatting or not.
	 * Use ChatFormat#determineMessageFormat(String, String, Player) to automatically allow PWCP to determine which format to use.
	 *
	 * @param format The format for the message.
	 * @param message        The message that was sent.
	 * @param sender         The sender of the message.
	 * @param global         Whether or not to use the global formatting.
	 * @return The chat message's format. Can be used directly in chatEvent#setFormat(format)
	 */
	public static String determineMessageFormat(String format, String message, Player sender, boolean global, boolean ignoreBukkit) {
			format = replaceMessageVariable(format, message, ignoreBukkit);
			format = replaceWorldVariable(format, sender);
		
		if (PerWorldChatPlusAPI.getApi().papiCheck())
			format = PlaceholderAPI.setPlaceholders(sender, format);
		
		return ChatColor.translateAlternateColorCodes('&', format);
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
		if (format.contains(_var))
			return format.replaceAll(_var, replaceWith);
        return format;
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
			if (format.contains("%s")) {
                if (!ignoreBukkit)
					return format.replaceAll("%message%", "%s");
                else
					return format.replaceAll("%message%", message);
			} else {
				return format.replaceAll("%message%", message);
			}
        }
			return format;
	}
	
	/**
	 * Replaces the world variable with the sender's world name or world alias.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message
	 * @return The message's format with the world variable replaced.
	 */
	public static String replaceWorldVariable(String format, Player sender) {
		if (format.contains("%world%"))
			return format.replaceAll("%world%", WorldDataManager.getAlias(sender.getWorld().getName()));
        return format;
	}
}
