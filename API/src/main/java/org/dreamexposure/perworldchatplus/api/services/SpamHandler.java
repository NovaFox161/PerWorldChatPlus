package org.dreamexposure.perworldchatplus.api.services;

import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Nova Fox on 7/4/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class SpamHandler {
    private static SpamHandler instance;

    private final HashMap<UUID, Long> messageTime = new HashMap<>();
    private final HashMap<UUID, String> messageContents = new HashMap<>();

    private SpamHandler() {} //Prevent initialization

    public static SpamHandler getHandler() {
        if (instance == null)
            instance = new SpamHandler();
        return instance;
    }

    public void addMessage(Player player, String message) {
        if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Time.Limit.Enabled").equalsIgnoreCase("True")) {
            messageTime.remove(player.getUniqueId());
            messageTime.put(player.getUniqueId(), System.currentTimeMillis());
        }
        if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Chat.Spam.Same.Limit.Enabled").equalsIgnoreCase("True")) {
            messageContents.remove(player.getUniqueId());
            messageContents.put(player.getUniqueId(), message);
        }
    }

    public void removeMessage(Player player) {
        messageTime.remove(player.getUniqueId());
        messageContents.remove(player.getUniqueId());
    }

    /**
     * Checks if the player has sent a message within the time limit.
     * @param player The player to check.
     * @return <code>true</code> if within time limit, else <code>false</code>.
     */
    public boolean withinTimeLimit(Player player) {
        if (messageTime.containsKey(player.getUniqueId())) {
            long now = System.currentTimeMillis();
            long limit = PerWorldChatPlusAPI.getApi().getPluginConfig().get().getLong("Chat.Spam.Time.Limit.MS");

            return now - messageTime.get(player.getUniqueId()) <= limit;
        }
        return false;
    }

    /**
     * Checks if the player has sent the same message again.
     * @param player The player to check.
     * @param message The message to compare
     * @return <code>true</code> if the message is the same, else <code>False</code>.
     */
    public boolean isSame(Player player, String message) {
        return messageContents.containsKey(player.getUniqueId()) && messageContents.get(player.getUniqueId()).equals(message);
    }
}