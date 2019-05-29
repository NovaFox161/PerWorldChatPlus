package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.dreamexposure.novalib.api.NovaLibAPI;
import org.dreamexposure.novalib.api.bukkit.events.network.pubsub.PubSubReceiveEvent;
import org.dreamexposure.perworldchatplus.api.enums.MessageForm;

/**
 * @author NovaFox161
 * Date Created: 11/25/2018
 * For Project: PerWorldChatPlus
 * Author Website: https://www.novamaday.com
 * Company Website: https://www.dreamexposure.org
 * Contact: nova@dreamexposure.org
 */
public class PubSubListener implements Listener {
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPubSubReceive(PubSubReceiveEvent event) {
        if (event.getChannelName().equals("PerWorldChatPlus.Internal.Chat") && !event.getServerNameFrom().equals(NovaLibAPI.getApi().getServerName())) {
            switch (MessageForm.valueOf(event.getData().getString("MessageForm").toUpperCase())) {
                case CHAT:
                    //TODO: Handle chat message received.
                    break;
                case DIRECT_MESSAGE:
                    //TODO: Handle direct message received.
                    break;
            }
        }
    }
}
