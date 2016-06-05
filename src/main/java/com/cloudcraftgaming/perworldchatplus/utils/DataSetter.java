package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.chat.PlayerChatManager;
import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Nova Fox on 6/4/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 *
 * This class handles the majority of data setting. If setting PerWorldChatPlus data, it should be done through this class.
 */
public class DataSetter {
    /**
     * Sets the player's chat mute status. If true, they will not see any chat messages. False if they should see chat messages.
     * @param player The player who's chat mute setting is to be set.
     * @param mute True or False if should have chat muted.
     */
    public static void setChatMute(Player player, boolean mute) {
        Main.plugin.data.set("Players." + player.getUniqueId() + ".ChatMute", mute);
        Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
    }

    /**
     * Sets the player's chat spy status. If true, the player will receive all chat messages, regardless of world share settings.
     * @param player The player who's chat spy setting is to be set.
     * @param spy True or False if should have shat spy enabled.
     */
    public static void setChatSpy(Player player, boolean spy) {
        Main.plugin.data.set("Players." + player.getUniqueId() + ".Spy", spy);
        Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
    }

    /**
     * Sets the players Global Chat Bypass status. If true, the player's chat messages will be sent globally by default.
     * @param player The player who's Global Chat Bypass setting is to be set.
     * @param bypass True or False if should have Global Chat Bypass enabled.
     */
    public static void setGlobalBypass(Player player, boolean bypass) {
        Main.plugin.data.set("Players." + player.getUniqueId() + ".Bypass", bypass);
        Main.plugin.saveCustomConfig(Main.plugin.data, Main.plugin.dataFile);
    }

    /**
     * Adds the specified word to the specified player's alert list.
     * @param player The player who's alert list should be edited.
     * @param word The word to add to the player's alert list.
     */
    public static void addAlertWord(Player player, String word) {
        List<String> list = Main.plugin.alerts.getStringList("Alerts." + player.getUniqueId());
        list.add(word);
        Main.plugin.alerts.set("Alerts." + player.getUniqueId(), list);
        Main.plugin.saveCustomConfig(Main.plugin.alerts, Main.plugin.alertFile);
    }

    /**
     * Removed the specified word from the specified player's alert list.
     * @param player The player who's alert list should be edited.
     * @param word The word to remove from the player's alert list.
     */
    public static void removeAlertWord(Player player, String word) {
        if (PlayerChatManager.hasAlertWord(player, word)) {
            List<String> list = Main.plugin.alerts.getStringList("Alerts." + player.getUniqueId());
            list.remove(word);
            Main.plugin.alerts.set("Alerts." + player.getUniqueId(), list);
            Main.plugin.saveCustomConfig(Main.plugin.alerts, Main.plugin.alertFile);
        }
    }
}
