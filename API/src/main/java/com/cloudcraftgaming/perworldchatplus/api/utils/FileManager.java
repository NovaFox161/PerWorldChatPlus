package com.cloudcraftgaming.perworldchatplus.api.utils;

import com.cloudcraftgaming.perworldchatplus.api.PerWorldChatPlusAPI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * This class handles several file related functions and methods.
 */
public class FileManager {

    //Functionals

    /**
     * This is a massive utility function that generates the default files used by PerWorldChatPlus (utility only, used on startup).
     */
    public static void createSharesFile() {
        if (!(getSharesFile().exists())) {
            PerWorldChatPlusAPI.getApi().getPlugin().getLogger().info("Generating World Shares file...");
            YamlConfiguration shares = getSharesYml();
            List<String> list1 = shares.getStringList("world");
            list1.add("world_nether");
            list1.add("world_the_end");
            shares.set("world", list1);
            shares.options().copyDefaults(true);
            saveShares(shares);

            shares.options().copyDefaults(true);
            saveShares(shares);
        }
    }

    /**
     * Saves the shares files.
     *
     * @param shares The instance of the shares yml to save.
     */
    private static void saveShares(YamlConfiguration shares) {
        try {
            shares.save(getSharesFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Getters

    /**
     * Gets the world shares File.
     *
     * @return The world shares file.
     */
    private static File getSharesFile() {
        return new File(PerWorldChatPlusAPI.getApi().getPlugin().getDataFolder() + "/shares.yml");
    }

    /**
     * Gets the world shares YML.
     *
     * @return The world shares YML.
     */
    public static YamlConfiguration getSharesYml() {
        return YamlConfiguration.loadConfiguration(getSharesFile());
    }
}