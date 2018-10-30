package org.dreamexposure.perworldchatplus.api.privatemessage;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
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
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderVariable(format, sender);
		format = replaceSenderDisplayNameVariable(format, sender);
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverVariable(format, receiver);
		format = replaceReceiverDisplayNameVariable(format, receiver);
		format = replaceReceiverPrefixVariable(format, receiver);
		format = replaceReceiverSuffixVariable(format, receiver);
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
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderVariable(format, sender);
		format = replaceSenderDisplayNameVariable(format, sender);
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverVariable(format, receiver);
		format = replaceReceiverDisplayNameVariable(format, receiver);
		format = replaceReceiverPrefixVariable(format, receiver);
		format = replaceReceiverSuffixVariable(format, receiver);
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
		
		//Replace general vars
		format = replaceMessageVariable(format, message);
		
		//Replace sender related vars
		format = replaceSenderVariable(format, sender);
		format = replaceSenderDisplayNameVariable(format, sender);
		format = replaceSenderPrefixVariable(format, sender);
		format = replaceSenderSuffixVariable(format, sender);
		format = replaceSenderWorldVariable(format, sender);
		
		//Replace receiver related vars
		format = replaceReceiverVariable(format, receiver);
		format = replaceReceiverDisplayNameVariable(format, receiver);
		format = replaceReceiverPrefixVariable(format, receiver);
		format = replaceReceiverSuffixVariable(format, receiver);
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
	 * Replaces the sender variable with the sender's display name.
	 *
	 * @param format The current format of the message.
	 * @return The message's format with the sender variable replaced.
	 */
	public static String replaceSenderVariable(String format, Player sender) {
		if (format.contains("%sender%"))
			return format.replaceAll("%sender%", sender.getName());
		else
			return format;
	}
	
	/**
	 * Replaces the sender name variable with the sender's username Use PmFormat##replaceSenderVariable(String, Player) to replace with display name.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the sender name variable replaced.
	 */
	public static String replaceSenderDisplayNameVariable(String format, Player sender) {
		if (format.contains("%sender_display_name%"))
			return format.replaceAll("%sender_name%", sender.getDisplayName());
		else
			return format;
	}
	
	/**
	 * Replaces the sender prefix variable with the sender's world specific prefix.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the sender prefix variable replaced.
	 */
	public static String replaceSenderPrefixVariable(String format, Player sender) {
		if (format.contains("%sender_prefix%") && PerWorldChatPlusAPI.getApi().getChat() != null) {
			String prefix = PerWorldChatPlusAPI.getApi().getChat().getPlayerPrefix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
			return format.replaceAll("%sender_prefix%", prefix);
		} else
			return format;
	}
	
	/**
	 * Replaces the sender suffix variable with the sender's world specific suffix.
	 *
	 * @param format The current format of the message.
	 * @param sender The sender of the message.
	 * @return The message's format with the sender suffix variable replaced.
	 */
	public static String replaceSenderSuffixVariable(String format, Player sender) {
		if (format.contains("%sender_suffix%") && PerWorldChatPlusAPI.getApi().getChat() != null) {
			String suffix = PerWorldChatPlusAPI.getApi().getChat().getPlayerSuffix(sender.getWorld().getName(), Bukkit.getOfflinePlayer(sender.getUniqueId()));
			return format.replaceAll("%sender_suffix%", suffix);
		} else
			return format;
	}
	
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
	 * Replaces the receiver variable with the receiver's display name.
	 *
	 * @param receiver The receiver of the message.
	 * @param format   The current format of the message.
	 * @return The message's format with the receiver variable replaced.
	 */
	public static String replaceReceiverVariable(String format, Player receiver) {
		if (format.contains("%receiver%"))
			return format.replaceAll("%receiver%", receiver.getName());
		else
			return format;
	}
	
	/**
	 * Replaces the receiver name variable with the receiver's username Use PmFormat#replaceReceiverVariable(String, Player) (String, Player)} (String, Player)} to replace with display name.
	 *
	 * @param format   The current format of the message.
	 * @param receiver The sender of the message.
	 * @return The message's format with the sender name variable replaced.
	 */
	public static String replaceReceiverDisplayNameVariable(String format, Player receiver) {
		if (format.contains("%receiver_display_name%"))
			return format.replaceAll("%receivername%", receiver.getDisplayName());
		else
			return format;
	}
	
	/**
	 * Replaces the receiver prefix variable with the receiver's world specific prefix.
	 *
	 * @param format   The current format of the message.
	 * @param receiver The receiver of the message.
	 * @return The message's format with the receiver prefix variable replaced.
	 */
	public static String replaceReceiverPrefixVariable(String format, Player receiver) {
		if (format.contains("%receiver_prefix%") && PerWorldChatPlusAPI.getApi().getChat() != null) {
			String prefix = PerWorldChatPlusAPI.getApi().getChat().getPlayerPrefix(receiver.getWorld().getName(), Bukkit.getOfflinePlayer(receiver.getUniqueId()));
			return format.replaceAll("%receiver_prefix%", prefix);
		} else
			return format;
	}
	
	/**
	 * Replaces the receiver suffix variable with the receiver's world specific suffix.
	 *
	 * @param format   The current format of the message.
	 * @param receiver The receiver of the message.
	 * @return The message's format with the receiver suffix variable replaced.
	 */
	public static String replaceReceiverSuffixVariable(String format, Player receiver) {
		if (format.contains("%receiver_suffix%") && PerWorldChatPlusAPI.getApi().getChat() != null) {
			String suffix = PerWorldChatPlusAPI.getApi().getChat().getPlayerSuffix(receiver.getWorld().getName(), Bukkit.getOfflinePlayer(receiver.getUniqueId()));
			return format.replaceAll("%receiver_suffix%", suffix);
		} else
			return format;
	}
	
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
