package com.cloudcraftgaming.perworldchatplus.data;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
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


            data.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(data, file);

            data.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(data, file);
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
        Main.plugin.saveCustomConfig(dataYml, dataFile);
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
        if (PlayerDataManager.getPlayerDataYml(player).contains("Alerts")) {
            return PlayerDataManager.getPlayerDataYml(player).getStringList("Alerts").contains(word);
        }
        return false;
    }
    /**
     * Checks if the player has their chat muted (Not receiving any chat messages).
     * @param player The player to check.
     * @return True if the player has their chat muted, else false.
     */
    public static boolean hasChatMuted(Player player) {
        return PlayerDataManager.getPlayerDataYml(player).getString("ChatMute").equalsIgnoreCase("True");
    }
}
