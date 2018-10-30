package org.dreamexposure.perworldchatplus.api.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.PlayerDataManager;
import org.dreamexposure.perworldchatplus.api.services.SpamHandler;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;
import org.dreamexposure.perworldchatplus.api.utils.PlayerHandler;
import org.dreamexposure.perworldchatplus.api.utils.Validator;

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
		
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Swear.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.swear")) {
				String replacer = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Swear.Replace");
				List<String> blockedWords = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getStringList("Chat.Swear.Blocked");
				for (String blockedWord : blockedWords) {
					if (newMessage.toLowerCase().contains(blockedWord.toLowerCase())) {
						if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Swear.Block.EntireMessage").equalsIgnoreCase("True")) {
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
		
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.ad")) {
				String replacer = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Replace");
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Block.Ip-Addresses").equalsIgnoreCase("True")) {
					String[] words = newMessage.split(" ");
					for (String word : words) {
						if (Validator.isIpAddress(word)) {
							if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
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
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Block.Websites").equalsIgnoreCase("True")) {
					String[] words = newMessage.split(" ");
					for (String word : words) {
						if (Validator.isURL(word)) {
							if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Ad.Block.EntireMessage").equalsIgnoreCase("True")) {
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
		
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Block.Enabled").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.bypass.spam")) {
			    //Check if messages are sent too often.
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Time.Limit.Enabled").equalsIgnoreCase("True")) {
			        if (SpamHandler.getHandler().withinTimeLimit(sender)) {
			            //Within time limit
                        hasSpammed = true;
						if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Time.Limit.Warn").equalsIgnoreCase("True")) {
                            //Warn
                            sender.sendMessage(MessageManager.getMessage("Chat.Spam.Time.Warn"));
                        }
                    }
                }

                //Check if the same message is being sent.
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Same.Limit.Enabled").equalsIgnoreCase("True")) {
			        if (SpamHandler.getHandler().isSame(sender, _message)) {
			            hasSpammed = true;
                    }
                }

				//Check if more caps than allowed
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Caps.Limit.Enabled").equalsIgnoreCase("True")) {
					double percentLimit = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getDouble("Chat.Spam.Caps.Limit.Percent");
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
						if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Caps.Limit.ToLower").equalsIgnoreCase("True")) {
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
		if (message.contains(PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Global.Override"))) {
			String override = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Global.Override");
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
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Color.RequirePermission").equalsIgnoreCase("True")) {
			if (!sender.hasPermission("pwcp.chat.color")) {
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Color.StripWithoutPermission").equalsIgnoreCase("True")) {
					return ChatColor.stripColor(message);
				} else {
					return message;
				}
			}
		}
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Color.Auto").equalsIgnoreCase("True")) {
			message = PlayerDataManager.getChatColor(sender) + message;
		}
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Color.Translate").equalsIgnoreCase("True")) {
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
		return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Global.Always Global").equalsIgnoreCase("True")
				|| PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")
				|| PlayerDataManager.hasGlobalBypassEnabled(sender)
				|| (message.contains(PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Global.Override")) && sender.hasPermission("pwcp.bypass"));
	}
	
	/**
	 * Checks if the player's name was mentioned in the chat message (Only if requirements were met).
	 *
	 * @param player  The player to check.
	 * @param message The message to check.
	 * @return True if the player was mentioned in the message, else false.
	 */
	public static boolean wasMentioned(Player player, String message) {
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Alert.Mention.OnName").equalsIgnoreCase("True")) {
			if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Alert.Mention.RequirePermission").equalsIgnoreCase("True")) {
				if (player.hasPermission("pwcp.alert.mention")) {
					if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                        return message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName());
					} else {
                        return message.contains(player.getName()) || message.contains(player.getDisplayName());
					}
				}
			} else {
				if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Alert.Mention.RequireAtSymbol").equalsIgnoreCase("True")) {
                    return message.contains("@" + player.getName()) || message.contains("@" + player.getDisplayName());
				} else {
                    return message.contains(player.getName()) || message.contains(player.getDisplayName());
				}
			}
		}
		return false;
	}
}