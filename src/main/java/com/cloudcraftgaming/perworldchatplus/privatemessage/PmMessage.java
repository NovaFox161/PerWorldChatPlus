package com.cloudcraftgaming.perworldchatplus.privatemessage;

import com.cloudcraftgaming.perworldchatplus.chat.ChatMessage;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 10/10/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PmMessage {
	/**
	 * Determines the message contents before it is sent based on settings and what the original message contained.
	 * This combines all methods for checking what the message should contain.
	 *
	 * @param originalMessage The original message before sending.
	 * @param sender          The sender of the chat message.
	 * @return a new string message to be sent as the chat message.
	 */
	public static String determineMessageContents(String originalMessage, Player sender) {
		String newMessage = originalMessage;
		newMessage = ChatMessage.filterSwears(newMessage, sender);
		newMessage = ChatMessage.filterAds(newMessage, sender);
		newMessage = ChatMessage.makeMessageColorful(newMessage, sender);

		return newMessage;
	}
}