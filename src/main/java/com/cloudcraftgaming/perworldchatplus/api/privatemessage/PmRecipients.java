package com.cloudcraftgaming.perworldchatplus.api.privatemessage;

import com.cloudcraftgaming.perworldchatplus.api.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Nova Fox on 10/10/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PmRecipients {
	/**
	 * Determines all of the players that are to receive the private message.
	 *
	 * @param sender   The sender of the private message.
	 * @param receiver The player the private message is to.
	 * @return A Hashtable of all private message receivers and the type they are.
	 */
	public static Hashtable<Player, PmRecipientType> determineMessageRecipients(Player sender, Player receiver) {
		Hashtable<Player, PmRecipientType> recipients = new Hashtable<>();

		recipients.put(sender, PmRecipientType.REAL_SENDER);
		recipients.put(receiver, PmRecipientType.REAL_RECIPIENT);

		for (Player p : getSpies(recipients)) {
			recipients.put(p, PmRecipientType.SPY);
		}

		return recipients;
	}

	/**
	 * Gets an ArrayList of players with social spy enabled that are to receive the pm.
	 *
	 * @param currentReceivers The current Hashtable of players this message is going to.
	 * @return An ArrayList of players with social spy enabled that are to receive the pm.
	 */
	public static ArrayList<Player> getSpies(Hashtable<Player, PmRecipientType> currentReceivers) {
		ArrayList<Player> spies = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!currentReceivers.containsKey(p)) {
				if (PlayerDataManager.hasSocialSpyEnabled(p)) {
					spies.add(p);
				}
			}
		}
		return spies;
	}
}