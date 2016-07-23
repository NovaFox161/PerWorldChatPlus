package com.cloudcraftgaming.perworldchatplus.data;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nova Fox on 6/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 *
 * This class contains everything regarding world specific data for chat.
 */
public class WorldDataManager {

    //File stuffs
    /**
     * Creates a default world data file for the specified world.
     * @param worldName The world to create a data file for.
     */
    public static void createWorldDataFile(String worldName) {
        File file = new File(Main.plugin.getDataFolder() + "/Data/WorldData/" + worldName + ".yml");
        if (!file.exists()) {
            Main.plugin.getLogger().info("Generating world data for world: " + worldName);

            YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
            data.addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
            data.addDefault("Alias", worldName);

            data.options().copyDefaults(true);
            saveWorldDataFile(data, file);

            data.options().copyDefaults(true);
            saveWorldDataFile(data, file);
        }
    }

    /**
     * Checks if the specified world has a data file.
     * @param worldName The world to check.
     * @return True if the world has a data file, else false.
     */
    public static boolean hasWorldData(String worldName) {
        File file = new File(Main.plugin.getDataFolder() + "/Data/WorldData/" + worldName + ".yml");
        return file.exists();
    }

    /**
     * Gets the world data file for the specified world.
     * @param worldName The world whose data file  you wish to get.
     * @return The world data file for the world.
     */
    public static File getWorldDataFile(String worldName) {
        return new File(Main.plugin.getDataFolder() + "/Data/WorldData/" + worldName + ".yml");
    }

    /**
     * Gets the world data yml for the specified world.
     * @param worldName The world whose data yml you wish to get.
     * @return The world data yml for the world.
     */
    public static YamlConfiguration getWorldDataYml(String worldName) {
        return YamlConfiguration.loadConfiguration(getWorldDataFile(worldName));
    }

    /**
     * Saves the specified data files.
     * @param dataYml The world data yml to save.
     * @param dataFile The world data file to save.
     */
    public static void saveWorldDataFile(YamlConfiguration dataYml, File dataFile) {
        try {
            dataYml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Getters
    /**
     * Gets the world's alias if one exists.
     * @param worldName The world you want the alias of.
     * @return The world's alias, if one does not exist, returns the world's name.
     */
    public static String getAlias(String worldName) {
        if (hasWorldData(worldName)) {
            String alias = getWorldDataYml(worldName).getString("Alias");
            return ChatColor.translateAlternateColorCodes('&', alias) + ChatColor.RESET;
        } else {
            return worldName;
        }
    }

    //Setters
    /**
     * Sets the world's alias for chat messages
     * @param worldName The world to set
     * @param newAlias The new alias (supports untranslated color codes).
     */
    public static void setAlias(String worldName, String newAlias) {
        if (hasWorldData(worldName)) {
            YamlConfiguration data = getWorldDataYml(worldName);
            data.set("Alias", newAlias);
            saveWorldDataFile(data, getWorldDataFile(worldName));
        } else {
            createWorldDataFile(worldName);

            YamlConfiguration data = getWorldDataYml(worldName);
            data.set("Alias", newAlias);
            saveWorldDataFile(data, getWorldDataFile(worldName));
        }
    }
}