package org.dreamexposure.perworldchatplus.api.chat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.PlayerDataManager;
import org.dreamexposure.perworldchatplus.api.data.WorldShares;
import org.dreamexposure.perworldchatplus.api.utils.PlayerHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Nova Fox on 6/6/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 */
@SuppressWarnings("WeakerAccess")
public class ChatRecipients {
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
        recipients.addAll(mentionReceivers);
		Set<Player> alertReceivers = getAllAlertReceivers(recipients, message);
        recipients.addAll(alertReceivers);
		Set<Player> spyReceivers = getAllSpyReceivers(recipients, sender);
        recipients.addAll(spyReceivers);
		Set<Player> sharesReceivers = getAllSharesReceivers(recipients, sender);
        recipients.addAll(sharesReceivers);
		Set<Player> globalReceivers = getAllGlobalReceivers(recipients, message, sender);
        recipients.addAll(globalReceivers);
		List<Player> removeMuted = getMutedReceivers();
		for (Player p : removeMuted) {
			if (!recipients.contains(p))
                continue;
            recipients.remove(p);
		}
		List<Player> removeIgnoring = getIgnoredReceivers(sender);
		for (Player p : removeIgnoring) {
            recipients.remove(p);
        }


        recipients.add(sender);
		
		return recipients;
	}
	
	//Chat receiver filtering.
	
	/**
	 * Gets a set of players that were mentioned in the chat message.
	 *
	 * @param recipients The current recipients of the message. (Important that this is current to avoid duplicates.
	 * @param message    The message to be sent.
	 * @param sender     The sender of the message.
	 * @return A set of players that will receive the message based on its contents.
	 */
	public static Set<Player> getAllMentionReceivers(Set<Player> recipients, String message, Player sender) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (ChatMessage.wasMentioned(p, message)) {
                recipients.add(p);
				String soundName = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Alert.Mention.Sound");
				Sound sound = Sound.valueOf(soundName);
				p.playSound(p.getLocation(), sound, 1f, 0f);
				if (shouldSendMentionNotice())
					PlayerHandler.sendMentionNotice(p, sender);
			}
		}
		return recipients;
	}
	
	/**
	 * Gets a set of players that will receive the chat message based on it's contents (if they have an alert word that appears in the message.
	 *
	 * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
	 * @param message    The message to be sent.
	 * @return A set of players that will receive the message based on its contents.
	 */
	public static Set<Player> getAllAlertReceivers(Set<Player> recipients, String message) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (PlayerDataManager.getPlayerDataYml(p).contains("Alerts")) {
				for (String word : PlayerDataManager.getPlayerDataYml(p).getStringList("Alerts")) {
					if (message.contains(word)) {
                        recipients.add(p);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0f);
					}
				}
			}
		}
		return recipients;
	}
	
	/**
	 * Gets a set of players that will receive the message if they are spying on all chat globally or for sender's world.
	 *
	 * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
	 * @param sender     The sender of the message.
	 * @return A set of players that will receive the message based on if they are spying chat globally or the sender's world.
	 */
	public static Set<Player> getAllSpyReceivers(Set<Player> recipients, Player sender) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (PlayerDataManager.hasGlobalChatSpyEnabled(p)) {
                recipients.add(p);
			} else if (PlayerDataManager.hasWorldChatSpyEnabled(p)) {
				if (PlayerDataManager.isSpyingOnWorld(p, sender.getWorld().getName()))
                    recipients.add(p);
			}
		}
		return recipients;
	}
	
	/**
	 * Gets a set of players that will receive the message if the recipients are a world that shares its chat with the sender.
	 *
	 * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
	 * @param sender     The sender of the message.
	 * @return A set of players that will receive the message if the recipients are in a world that shares its chat with the sender.
	 */
	public static Set<Player> getAllSharesReceivers(Set<Player> recipients, Player sender) {
		String worldFrom = sender.getWorld().getName();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!recipients.contains(p)) {
				if (WorldShares.get().isShared(worldFrom, p.getWorld().getName()))
					recipients.add(p);
			}
		}
		return recipients;
	}
	
	/**
	 * Gets a set of all players online if the message is supposed to be global (excluding players that are already receiving the message).
	 *
	 * @param recipients The current recipients of the message (Important that this is current to avoid duplicates).
	 * @param message    The message to be sent (This is to check if it contains the override/bypass).
	 * @param sender     The sender of the message.
	 * @return A set of all players that will receive the message if chat is supposed to be global.
	 */
	public static Set<Player> getAllGlobalReceivers(Set<Player> recipients, String message, Player sender) {
		if (ChatMessage.shouldBeGlobal(message, sender))
            recipients.addAll(Bukkit.getOnlinePlayers());
		return recipients;
	}
	
	/**
	 * Gets a set of players that SHOULD NOT receive the message because their chat is muted.
	 *
	 * @return A set of players that SHOULD NOT receive the chat message.
	 */
	public static List<Player> getMutedReceivers() {
		List<Player> muted = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (PlayerDataManager.hasChatMuted(p)) {
				if (!muted.contains(p))
					muted.add(p);
			}
		}
		return muted;
	}
	
	/**
	 * Gets a set of players that SHOULD NOT receive the message because they are ignoring the sender.
	 *
	 * @param sender The sender of the chat message.
	 * @return A set of players that SHOULD NOT receive the chat message.
	 */
	public static List<Player> getIgnoredReceivers(Player sender) {
		List<Player> ignored = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (PlayerDataManager.isIgnoringPlayer(p, sender)) {
				if (!ignored.contains(p))
					ignored.add(p);
			}
		}
		return ignored;
	}
	
	//Checkers/Booleans
	/**
	 * Checks if the plugin should send a notice to the player that was mentioned.
	 *
	 * @return True if the player should be notified, else false.
	 */
	public static boolean shouldSendMentionNotice() {
		return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getBoolean("Alert.Mention.SendNotice");
	}
}
