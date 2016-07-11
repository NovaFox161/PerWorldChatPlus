package com.cloudcraftgaming.perworldchatplus.data;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nova Fox on 6/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 */
public class PlayerDataManager {
    /**
     * Creates a default data file for the specified player if one does not exist.
     * @param player The player to create a data file for.
     */
    public static void createPlayerDataFile(Player player) {
        UUID uuid = player.getUniqueId();
        File file = new File(Main.plugin.getDataFolder() + "/Data/PlayerData/" + uuid + ".yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating player data file for player: " + player.getName());

            YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.addDefault("DO NOT DElETE", "PerWorldChatPlus is developed and managed by Shades161");
            data.addDefault("Name", player.getName());
            data.addDefault("Spy", false);
            data.addDefault("Bypass", false);
            data.addDefault("WorldSpy", false);
            data.addDefault("ChatMute", false);
            String chatColorString = Main.plugin.getConfig().getString("Chat.Color.Default");
            data.addDefault("ChatColor", ChatColor.valueOf(chatColorString).name());


            data.options().copyDefaults(true);
            savePlayerData(data, file);

            data.options().copyDefaults(true);
            savePlayerData(data, file);
        }
    }

    /**
     * Updates the specified player's data file.
     * @param player the player whose data file is to be updated.
     */
    public static void updatePlayerDataFile(Player player) {
        YamlConfiguration data = getPlayerDataYml(player);
        if (!data.getString("Name").equals(player.getName())) {
            data.set("Name", player.getName());
        }
        if (!data.getString("ChatMute").equalsIgnoreCase("False")) {
            data.set("ChatMute", false);
        }
        savePlayerData(data, getPlayerDataFile(player));
    }

    /**
     * Gets the data file for the specified player.
     * @param player The player who's data file you wish to get.
     * @return The specified player's data file.
     */
    public static File getPlayerDataFile(Player player) {
        UUID uuid = player.getUniqueId();
        return new File(Main.plugin.getDataFolder() + "/Data/PlayerData/" + uuid + ".yml");
    }

    /**
     * Gets the data file yml for the specified player.
     * @param player The player who's data file you wish to get.
     * @return The specified player's data file yml.
     */
    public static YamlConfiguration getPlayerDataYml(Player player) {
        return YamlConfiguration.loadConfiguration(getPlayerDataFile(player));
    }

    /**
     * Saves the player's data file.
     * @param dataYml The yml you wish to save.
     * @param dataFile The file you wish to save.
     */
    public static void savePlayerData(YamlConfiguration dataYml, File dataFile) {
        try {
            dataYml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Booleans/checkers

    /**
     * Checks if the player currently has a data file.
     * @param player The player to check
     * @return True if the player has a data file, else false.
     */
    public static boolean hasDataFile(Player player) {
        File file = new File(Main.plugin.getDataFolder() + "/Data/PlayerData/" + player.getUniqueId() + ".yml");
        return file.exists();
    }

    /**
     * Checks if the player currently has Global Chat Bypass enabled. (Sending all chat messages globally by default).
     * @param player The player to check.
     * @return True if the player currently has Global Chat Bypass enabled, else false.
     */
    public static boolean hasGlobalBypassEnabled(Player player) {
        return PlayerDataManager.getPlayerDataYml(player).getString("Bypass").equalsIgnoreCase("True");
    }

    /**
     * Checks if the player is currently spying on all chat (seeing chat for all worlds, ignoring sharing).
     * @param player The player to check.
     * @return True if the player is currently spying on chat, else false.
     */
    public static boolean hasGlobalChatSpyEnabled(Player player) {
        return PlayerDataManager.getPlayerDataYml(player).getString("Spy").equalsIgnoreCase("True");
    }

    /**
     * Checks if the specified player has WorldChatSpy Enabled.
     * Check PlayerChatManager#isSpyingOnWorld(Player player, String worldName) to see if they are spying on the particular world.
     * @param player The player to check.
     * @return True if the player currently has World Chat Spy enabled, else false.
     */
    public static boolean hasWorldChatSpyEnabled(Player player) {
        return PlayerDataManager.getPlayerDataYml(player).getString("WorldSpy").equalsIgnoreCase("True");
    }

    /**
     * Checks if the specified player is currently spying on the specific world.
     * @param player The player to check.
     * @param worldName The name of the world the chat message came from.
     * @return True if the player is currently spying on that world, else false.
     */
    public static boolean isSpyingOnWorld(Player player, String worldName) {
        if (hasWorldChatSpyEnabled(player)) {
            if (PlayerDataManager.getPlayerDataYml(player).contains("WorldSpies")) {
                return PlayerDataManager.getPlayerDataYml(player).getStringList("WorldSpies").contains(worldName.toLowerCase());
            }
        }
        return false;
    }

    /**
     * Checks if the player has the specified word defined in their alerts.
     * @param player The player to check.
     * @param word The alert word to check.
     * @return True if the player has the word as an alert word, else false.
     */
    public static boolean hasAlertWord(Player player, String word) {
        return PlayerDataManager.getPlayerDataYml(player).contains("Alerts") &&
                PlayerDataManager.getPlayerDataYml(player).getStringList("Alerts").contains(word);
    }

    /**
     * Checks if the player has their chat muted (Not receiving any chat messages).
     * @param player The player to check.
     * @return True if the player has their chat muted, else false.
     */
    public static boolean hasChatMuted(Player player) {
        return PlayerDataManager.getPlayerDataYml(player).getString("ChatMute").equalsIgnoreCase("True");
    }


    //Getters

    /**
     * Gets the player's chat color (Default White).
     * @param player The player to get.
     * @return The player's chat color.
     */
    public static ChatColor getChatColor(Player player) {
        return ChatColor.valueOf(getPlayerDataYml(player).getString("ChatColor"));
    }

    //Setters

    /**
     * Sets the player's chat mute status. If true, they will not see any chat messages. False if they should see chat messages.
     * @param player The player who's chat mute setting is to be set.
     * @param mute True or False if should have chat muted.
     */
    public static void setChatMute(Player player, boolean mute) {
        YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
        data.set("ChatMute", mute);
        savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
    }

    /**
     * Sets the player's chat spy status. If true, the player will receive all chat messages, regardless of world share settings.
     * @param player The player who's chat spy setting is to be set.
     * @param spy True or False if should have shat spy enabled.
     */
    public static void setChatSpy(Player player, boolean spy) {
        YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
        data.set("Spy", spy);
        savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
    }

    /**
     * Sets the players Global Chat Bypass status. If true, the player's chat messages will be sent globally by default.
     * @param player The player who's Global Chat Bypass setting is to be set.
     * @param bypass True or False if should have Global Chat Bypass enabled.
     */
    public static void setGlobalBypass(Player player, boolean bypass) {
        YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
        data.set("Bypass", bypass);
        savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
    }

    /**
     * Adds the specified word to the specified player's alert list.
     * @param player The player who's alert list should be edited.
     * @param word The word to add to the player's alert list.
     */
    public static void addAlertWord(Player player, String word) {
        YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
        List<String> list = data.getStringList("Alerts");
        list.add(word);
        data.set("Alerts", list);
        savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
    }

    /**
     * Removed the specified word from the specified player's alert list.
     * @param player The player who's alert list should be edited.
     * @param word The word to remove from the player's alert list.
     */
    public static void removeAlertWord(Player player, String word) {
        if (PlayerDataManager.hasAlertWord(player, word)) {
            YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
            List<String> list = data.getStringList("Alerts." + player.getUniqueId());
            list.remove(word);
            data.set("Alerts", list);
            savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
        }
    }

    /**
     * Sets the player's default chat color.
     * @param player The player to set.
     * @param color The new default color.
     */
    public static void setChatColor(Player player, ChatColor color) {
        YamlConfiguration data = getPlayerDataYml(player);
        data.set("ChatColor", color.name());
        savePlayerData(data, getPlayerDataFile(player));
    }
}