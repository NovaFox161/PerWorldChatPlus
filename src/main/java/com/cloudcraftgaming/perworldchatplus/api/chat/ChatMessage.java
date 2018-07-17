package com.cloudcraftgaming.perworldchatplus.api.chat;

import com.cloudcraftgaming.perworldchatplus.PerWorldChatPlusPlugin;
import com.cloudcraftgaming.perworldchatplus.api.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.api.utils.PlayerHandler;
import com.cloudcraftgaming.perworldchatplus.api.utils.Validator;
import com.cloudcraftgaming.perworldchatplus.internal.services.SpamHandler;
import com.cloudcraftgaming.perworldchatplus.internal.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 * <p>
 * This class is used to set a chat message's content and other related tasks.
 */
@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class ChatMessage {
	/**
	 * Determines the message contents before it is sent based on settings and what the original message contained.
	 * This combines all methods for checking what the message should contain. (Used by PerWorldChatPlus's main chat listener).
	 *
	 * @param originalMessage The original message before sending.
	 * @param sender          The sender of the chat message.
	 * @return a new string message to be sent as the chat message.
	 */
	public static String determineMessageContents(String originalMessage, Player sender) {
		String newMessage = originalMessage;
		newMessage = filterSwears(newMessage, sender);
		newMessage = filterAds(newMessage, sender);
		newMessage = filterSpam(newMessage, sender);
		newMessage = removeGlobalBypassString(newMessage);
		newMessage = makeMessageColorful(newMessage, sender);
		
		return newMessage;
	}
	
	//Chat message filtering.
	
	/**
	 * Gets a new message that is cleaned of all blocked swear/curse words if enabled (Case insensitive).
	 *
	 * @param message The original message to be sent (unfiltered).
	 * @param sender  The sender of the message (to be used if they did swear because an action may be taken).
	 * @return A new message that is clean of all blocked swears.
	 */
	public static String filterSwears(String message, Player sender) {
		String newMessage = message;
		boolean hasSworn = false;

        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Swear.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.swear")) {
                String replacer = PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Swear.Replace");
                List<String> blockedWords = PerWorldChatPlusPlugin.plugin.getConfig().getStringList("Chat.Swear.Blocked");
				for (String blockedWord : blockedWords) {
					if (newMessage.toLowerCase().contains(blockedWord.toLowerCase())) {
                        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Swear.Block.EntireMessage").equalsIgnoreCase("True")) {
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
		}
		return newMessage;
	}
	
	/**
	 * Gets a new message that is cleaned of all blocked ads (except for exempt ads) if enabled (Case insensitive).
	 * This is still in beta so it may not work fully or at all (Sorry).
	 *
	 * @param message The original message to be sent (unfiltered).
	 * @param sender  The sender of the message (to be used if they did advertise because action may be taken).
	 * @return A new message that is clean of all blocked ads.
	 */
	public static String filterAds(String message, Player sender) {
		String newMessage = message;
		boolean hasAdvertised = false;

        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.ad")) {
                String replacer = PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Replace");
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Block.Ip-Addresses").equalsIgnoreCase("True")) {
					String[] words = newMessage.split(" ");
					for (String word : words) {
						if (Validator.isIpAddress(word)) {
                            if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
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
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Block.Websites").equalsIgnoreCase("True")) {
					String[] words = newMessage.split(" ");
					for (String word : words) {
						if (Validator.isURL(word)) {
                            if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
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
		}
		if (hasAdvertised) {
			PlayerHandler.doStuffOnAdvertise(sender);
		}
		return newMessage;
	}

    /**
     * Filters all spam out of a message. This includes the following spam types:
	 *     Messages sent too often.
	 *     Same messages
     *     Capital letter spam.
     * @param _message The original message.
     * @param sender The sender of the message
     * @return The edited message.
     */
	public static String filterSpam(String _message, Player sender) {
		String newMessage = _message;
		boolean hasSpammed = false;

        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.spam")) {
			    //Check if messages are sent too often.
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Time.Limit.Enabled").equalsIgnoreCase("True")) {
			        if (SpamHandler.getHandler().withinTimeLimit(sender)) {
			            //Within time limit
                        hasSpammed = true;
                        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Time.Limit.Warn").equalsIgnoreCase("True")) {
                            //Warn
                            sender.sendMessage(MessageManager.getMessage("Chat.Spam.Time.Warn"));
                        }
                    }
                }

                //Check if the same message is being sent.
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Same.Limit.Enabled").equalsIgnoreCase("True")) {
			        if (SpamHandler.getHandler().isSame(sender, _message)) {
			            hasSpammed = true;
                    }
                }

				//Check if more caps than allowed
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Caps.Limit.Enabled").equalsIgnoreCase("True")) {
                    Double percentLimit = PerWorldChatPlusPlugin.plugin.getConfig().getDouble("Chat.Spam.Caps.Limit.Percent");
					int caps = 0;
					for (int i = 1; i < _message.length(); i++) {
						if (Character.isUpperCase(_message.charAt(i))) {
							caps++;
						}
					}
					//Check percent
					double percentCaps = (caps / _message.length()) * 100;
					if (percentCaps >= percentLimit) {
						hasSpammed = true;
                        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Spam.Caps.Limit.ToLower").equalsIgnoreCase("True")) {
							newMessage = newMessage.toLowerCase();
						}
					}
				}
			}
		}
		if (hasSpammed) {
			PlayerHandler.doStuffOnSpam(sender);
		}
		return newMessage;
	}
	
	/**
	 * If the chat message contains the Global Override String, it will remove it so that it is not visible in chat.
	 *
	 * @param message The original message to be sent.
	 * @return A new message that does not contain  the Global Override String.
	 */
	public static String removeGlobalBypassString(String message) {
        if (message.contains(PerWorldChatPlusPlugin.plugin.getConfig().getString("Global.Override"))) {
            String override = PerWorldChatPlusPlugin.plugin.getConfig().getString("Global.Override");
			message = message.replaceAll(override, "").trim();
		}
		return message;
	}
	
	/**
	 * Prefixes the chat message with the player's default chat color,
	 * and if they have permission, will translate color codes in their message.
	 *
	 * @param message The original message to be sent.
	 * @param sender  The sender of the message.
	 * @return A new message with colors.
	 */
	public static String makeMessageColorful(String message, Player sender) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Color.RequirePermission").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.chat.color")) {
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Color.StripWithoutPermission").equalsIgnoreCase("True")) {
					return ChatColor.stripColor(message);
				} else {
					return message;
				}
			}
		}
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Color.Auto").equalsIgnoreCase("True")) {
			message = PlayerDataManager.getChatColor(sender) + message;
		}
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Chat.Color.Translate").equalsIgnoreCase("True")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}
		return message;
	}
	
	//Checks/Booleans
	
	/**
	 * Checks if the chat message should be sent globally.
	 *
	 * @param message The chat message to check.
	 * @param sender  The sender of the chat message.
	 * @return True if the chat message should be global, else false.
	 */
	public static boolean shouldBeGlobal(String message, Player sender) {
        return PerWorldChatPlusPlugin.plugin.getConfig().getString("Global.Always Global").equalsIgnoreCase("True")
                || PerWorldChatPlusPlugin.plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")
				|| PlayerDataManager.hasGlobalBypassEnabled(sender)
                || (message.contains(PerWorldChatPlusPlugin.plugin.getConfig().getString("Global.Override")) && sender.hasPermission("pwcp.bypass"));
	}
	
	/**
	 * Checks if the player's name was mentioned in the chat message (Only if requirements were met).
	 *
	 * @param player  The player to check.
	 * @param message The message to check.
	 * @return True if the player was mentioned in the message, else false.
	 */
	public static boolean wasMentioned(Player player, String message) {
        if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Alert.Mention.OnName").equalsIgnoreCase("True")) {
            if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Alert.Mention.RequirePermission").equalsIgnoreCase("True")) {
				if (player.hasPermission("pwcp.alert.mention")) {
                    if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                        return message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName());
					} else {
                        return message.contains(player.getName()) || message.contains(player.getDisplayName());
					}
				}
			} else {
                if (PerWorldChatPlusPlugin.plugin.getConfig().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                    return message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName());
				} else {
                    return message.contains(player.getName()) || message.contains(player.getDisplayName());
				}
			}
		}
		return false;
	}
}