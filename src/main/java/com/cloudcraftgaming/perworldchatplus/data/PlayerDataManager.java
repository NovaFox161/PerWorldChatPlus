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
}
