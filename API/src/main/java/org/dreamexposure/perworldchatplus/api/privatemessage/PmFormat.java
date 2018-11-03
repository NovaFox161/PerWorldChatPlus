package org.dreamexposure.perworldchatplus.api.privatemessage;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.WorldDataManager;

/**
 * Created by Nova Fox on 10/10/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
@SuppressWarnings({"WeakerAccess", "Duplicates"})
public class PmFormat {
	/**
	 * Determines the private message format for the sender. This combines all formatting methods.
	 *
	 * @param message  The message to format.
	 * @param sender   The sender of the message.
	 * @param receiver The receiver of the message.
	 * @return The private message's format.
	 */
	public static String determineMessageFormatForSender(String message, Player sender, Player receiver) {
		String format = getFormatTemplateForSender(sender);
		
		format = format.replace("{receiver}", receiver.getName()).replace("{sender}", sender.getName());
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverWorldVariable(format, receiver);
		
		
		if (PerWorldChatPlusAPI.getApi().papiCheck())
			format = PlaceholderAPI.setPlaceholders(receiver, format);
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	/**
	 * Determines the private message format for the receiver. This combines all formatting methods.
	 *
	 * @param message  The message to format.
	 * @param sender   The sender of the message.
	 * @param receiver The receiver of the message.
	 * @return The private message's format.
	 */
	public static String determineMessageFormatForReceiver(String message, Player sender, Player receiver) {
		String format = getFormatTemplateForReceiver(receiver);
		
		format = format.replace("{receiver}", receiver.getName()).replace("{sender}", sender.getName());
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverWorldVariable(format, receiver);
		
		
		if (PerWorldChatPlusAPI.getApi().papiCheck())
			format = PlaceholderAPI.setPlaceholders(receiver, format);
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	/**
	 * Determines the private message format for socialspy. This combines all formatting methods.
	 *
	 * @param message  The message to format.
	 * @param sender   The sender of the message.
	 * @param receiver The receiver of the message.
	 * @return The private message's format.
	 */
	public static String determineMessageFormatForSocialSpy(String message, Player sender, Player receiver) {
		String format = getFormatTemplateForSocialSpy();
		
		format = format.replace("{receiver}", receiver.getName()).replace("{sender}", sender.getName());
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverWorldVariable(format, receiver);
		
		if (PerWorldChatPlusAPI.getApi().papiCheck())
			format = PlaceholderAPI.setPlaceholders(receiver, format);
		
		
		return ChatColor.translateAlternateColorCodes('&', format);
	}
	
	//Format template getters
	
	/**
	 * Gets the default private message format template for the sender from file.
	 *
	 * @return The default private message format template.
	 */
	public static String getFormatTemplateForSender(Player sender) {
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Format.PerWorld").equalsIgnoreCase("True")) {
			if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().contains("PM.Format.Format." + sender.getWorld().getName() + ".From"))
				return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("PM.Format." + sender.getWorld().getName() + ".From");
		}
		return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("PM.Format.Default.From");
	}
	
	/**
	 * Gets the default private message format template for the receiver from file.
	 *
	 * @return The default private message format template.
	 */
	public static String getFormatTemplateForReceiver(Player receiver) {
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Format.PerWorld").equalsIgnoreCase("True")) {
			if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().contains("PM.Format.Format." + receiver.getWorld().getName() + ".To"))
				return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("PM.Format." + receiver.getWorld().getName() + ".To");
		}
		return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("PM.Format.Default.To");
	}
	
	/**
	 * Gets the default private message format template for socialspy from file.
	 *
	 * @return The default private message format template.
	 */
	public static String getFormatTemplateForSocialSpy() {
		return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("PM.Format.Spy");
	}
	
	//Variable replacers - General
	
	/**
	 * Replaces the specified variable in the text with a specified replacement.
	 * This is a general method for developers to use to replace variables not native to PerWorldChatPlus.
	 *
	 * @param format      The current format of the message.
	 * @param _var        The variable to look for within the format.
	 * @param replaceWith The string to replace the specified variable with.
	 * @return The message's format with the specified variable replaced with the specified text.
	 */
    @SuppressWarnings("unused")
    public static String replaceVariable(String format, String _var, String replaceWith) {
		if (format.contains(_var))
			return format.replaceAll(_var, replaceWith);
		else
			return format;
	}
	
	/**
	 * Replaces the message variable with the message.
	 *
	 * @param format The current format of the message.
	 * @return The message's format with the message variable replaced.
	 */
	public static String replaceMessageVariable(String format, String message) {
		if (format.contains("%message%"))
			return format.replaceAll("%message%", message);
		else
			return format;
	}
	
	//Variable replacers - Sender specific
	
	/**
	 * Replaces the sender world variable with the sender's world name or world alias.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message
	 * @return The message's format with the sender world variable replaced.
	 */
	public static String replaceSenderWorldVariable(String format, Player sender) {
		if (format.contains("%sender_world%"))
			return format.replaceAll("%sender_world%", WorldDataManager.getAlias(sender.getWorld().getName()));
		else
			return format;
	}
	
	//Variable replacers - Receiver specific
	
	/**
	 * Replaces the receiver world variable with the receiver's world name or world alias.
	 *
	 * @param format   The current format of the message.
	 * @param receiver The receiver of the message
	 * @return The message's format with the receiver world variable replaced.
	 */
	public static String replaceReceiverWorldVariable(String format, Player receiver) {
		if (format.contains("%receiver_world%"))
			return format.replaceAll("%world%", WorldDataManager.getAlias(receiver.getWorld().getName()));
		else
			return format;
	}
}
