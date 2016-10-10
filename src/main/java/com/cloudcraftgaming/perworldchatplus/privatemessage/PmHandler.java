package com.cloudcraftgaming.perworldchatplus.privatemessage;

import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Hashtable;

/**
 * Created by Nova Fox on 10/10/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PmHandler {
    /**
     * Sends a Private Message to the specified receiver from the specified sender.
     * @param sender The sender of the message.
     * @param receiver The receiver of the message.
     * @param message The message to send.
     * @return <code>true</code> if successful, else <code>false</code>.
     */
    public static Boolean sendPrivateMessage(Player sender, Player receiver, String message) {
        if (!PlayerDataManager.isIgnoringPlayer(receiver, sender)) {
            PlayerDataManager.setMessagingPlayer(sender, receiver);
            PlayerDataManager.setMessagingPlayer(receiver, sender);

            String newMessage = PmMessage.determineMessageContents(message, sender);
            Hashtable<Player, PmRecipientType> recipients = PmRecipients.determineMessageRecipients(sender, receiver);

            String formatSender = PmFormat.determineMessageFormatForSender(newMessage, sender, receiver);
            String formatReceiver = PmFormat.determineMessageFormatForReceiver(newMessage, sender, receiver);
            String formatSpy = PmFormat.determineMessageFormatForSocialSpy(newMessage, sender, receiver);

            for (Player p : recipients.keySet()) {
                if (recipients.get(p).equals(PmRecipientType.REAL_SENDER)) {
                    p.sendMessage(formatSender);
                } else if (recipients.get(p).equals(PmRecipientType.REAL_RECIPIENT)) {
                    p.sendMessage(formatReceiver);
                } else if (recipients.get(p).equals(PmRecipientType.SPY)) {
                    p.sendMessage(formatSpy);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Sends a Private Message from the specified sender to the last player they were messaging with.
     * @param sender The sender of the message.
     * @param message The message to send.
     * @return <code>true</code> if successful, else <code>false</code>.
     */
    public static Boolean sendPrivateMessage(Player sender, String message) {
        if (PlayerDataManager.isMessagingPlayer(sender)) {
            Player receiver = Bukkit.getPlayer(PlayerDataManager.getMessagingWith(sender));

            if (!PlayerDataManager.isIgnoringPlayer(receiver, sender)) {
                String newMessage = PmMessage.determineMessageContents(message, sender);
                Hashtable<Player, PmRecipientType> recipients = PmRecipients.determineMessageRecipients(sender, receiver);

                String formatSender = PmFormat.determineMessageFormatForSender(newMessage, sender, receiver);
                String formatReceiver = PmFormat.determineMessageFormatForReceiver(newMessage, sender, receiver);
                String formatSpy = PmFormat.determineMessageFormatForSocialSpy(newMessage, sender, receiver);

                for (Player p : recipients.keySet()) {
                    if (recipients.get(p).equals(PmRecipientType.REAL_SENDER)) {
                        p.sendMessage(formatSender);
                    } else if (recipients.get(p).equals(PmRecipientType.REAL_RECIPIENT)) {
                        p.sendMessage(formatReceiver);
                    } else if (recipients.get(p).equals(PmRecipientType.SPY)) {
                        p.sendMessage(formatSpy);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Sends a Private Message from the specified sender to the last player they were messaging with.
     * @param sender The sender of the message.
     * @param message The message to send.
     * @return <code>true</code> if successful, else <code>false</code>.
     */
    public static Boolean sendPrivateReply(Player sender, String message) {
        if (PlayerDataManager.isMessagingPlayer(sender)) {
            Player receiver = Bukkit.getPlayer(PlayerDataManager.getMessagingWith(sender));

            if (!PlayerDataManager.isIgnoringPlayer(receiver, sender)) {
                String newMessage = PmMessage.determineMessageContents(message, sender);
                Hashtable<Player, PmRecipientType> recipients = PmRecipients.determineMessageRecipients(sender, receiver);

                String formatSender = PmFormat.determineMessageFormatForSender(newMessage, sender, receiver);
                String formatReceiver = PmFormat.determineMessageFormatForReceiver(newMessage, sender, receiver);
                String formatSpy = PmFormat.determineMessageFormatForSocialSpy(newMessage, sender, receiver);

                for (Player p : recipients.keySet()) {
                    if (recipients.get(p).equals(PmRecipientType.REAL_SENDER)) {
                        p.sendMessage(formatSender);
                    } else if (recipients.get(p).equals(PmRecipientType.REAL_RECIPIENT)) {
                        p.sendMessage(formatReceiver);
                    } else if (recipients.get(p).equals(PmRecipientType.SPY)) {
                        p.sendMessage(formatSpy);
                    }
                }
                return true;
            }
        }
        return false;
    }
}