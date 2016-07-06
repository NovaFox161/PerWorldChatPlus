package com.cloudcraftgaming.perworldchatplus.listeners;

import com.cloudcraftgaming.perworldchatplus.chat.ChatFormat;
import com.cloudcraftgaming.perworldchatplus.chat.ChatMessage;
import com.cloudcraftgaming.perworldchatplus.chat.ChatRecipients;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		if (!event.isCancelled()) {
			//Set receivers, message, and format.
			event.getRecipients().clear();
			Set<Player> receivers = ChatRecipients.determineChatRecipients(event.getRecipients(), event.getMessage(), sender);
			String message = ChatMessage.determineMessageContents(event.getMessage(), sender);
			String format = ChatFormat.determineMessageFormat(event.getFormat(), event.getMessage(), sender);

			for (Player p : receivers) {
				event.getRecipients().add(p);
			}

			//Send message
			event.setFormat(format);
			event.setMessage(message);
		}
	}
}