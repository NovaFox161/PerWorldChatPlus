package com.cloudcraftgaming.perworldchatplus.internal.services;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.entity.Player;

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
        if (instance == null) {
            instance = new SpamHandler();
        }
        return instance;
    }

    public void addMessage(Player player, String message) {
        if (Main.plugin.getConfig().getString("Chat.Spam.Time.Limit.Enabled").equalsIgnoreCase("True")) {
            if (messageTime.containsKey(player.getUniqueId())) {
                messageTime.remove(player.getUniqueId());
            }
            messageTime.put(player.getUniqueId(), System.currentTimeMillis());
        }
        if (Main.plugin.getConfig().getString("Chat.Spam.Same.Limit.Enabled").equalsIgnoreCase("True")) {
            if (messageContents.containsKey(player.getUniqueId())) {
                messageContents.remove(player.getUniqueId());
            }
            messageContents.put(player.getUniqueId(), message);
        }
    }

    public void removeMessage(Player player) {
        if (messageTime.containsKey(player.getUniqueId())) {
            messageTime.remove(player.getUniqueId());
        }
        if (messageContents.containsKey(player.getUniqueId())) {
            messageContents.remove(player.getUniqueId());
        }
    }
}