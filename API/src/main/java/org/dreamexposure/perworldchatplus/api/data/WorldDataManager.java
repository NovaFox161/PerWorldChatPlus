package org.dreamexposure.perworldchatplus.api.data;

import org.bukkit.ChatColor;
import org.dreamexposure.novalib.api.bukkit.file.CustomConfig;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * Created by Nova Fox on 6/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus.
 * <p>
 * This class contains everything regarding world specific data for chat.
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
public class WorldDataManager {
	
	//File stuffs
	
	/**
	 * Creates a default world data file for the specified world.
	 *
	 * @param worldName The world to create a data file for.
	 */
	public static void createWorldDataFile(String worldName) {
		CustomConfig config = getWorldData(worldName);
		
		LinkedHashMap<String, Object> s = new LinkedHashMap<>();
		
		s.put("DO NOT DELETE", "PerWorldChatPlus is developed and managed by DreamExposure");
		s.put("Alias", worldName);
		
		config.update(s);
	}
	
	/**
	 * Checks if the specified world has a data file.
	 *
	 * @param worldName The world to check.
	 * @return True if the world has a data file, else false.
	 */
	public static boolean hasWorldData(String worldName) {
		File file = new File(PerWorldChatPlusAPI.getApi().getPlugin().getDataFolder() + "/Data/WorldData/" + worldName + ".yml");
		return file.exists();
	}
	
	/**
	 * Gets the world data yml for the specified world.
	 *
	 * @param worldName The world whose data yml you wish to get.
	 * @return The world data yml for the world.
	 */
	public static CustomConfig getWorldData(String worldName) {
		return new CustomConfig(PerWorldChatPlusAPI.getApi().getPlugin(), "/Data/WorldData", worldName + ".yml");
	}
	
	//Getters
	
	/**
	 * Gets the world's alias if one exists.
	 *
	 * @param worldName The world you want the alias of.
	 * @return The world's alias, if one does not exist, returns the world's name.
	 */
	public static String getAlias(String worldName) {
		if (hasWorldData(worldName)) {
			String alias = getWorldData(worldName).get().getString("Alias");
			return ChatColor.translateAlternateColorCodes('&', alias) + ChatColor.RESET;
		} else return worldName;
	}
	
	//Setters
	
	/**
	 * Sets the world's alias for chat messages
	 *
	 * @param worldName The world to set
	 * @param newAlias  The new alias (supports untranslated color codes).
	 */
	public static void setAlias(String worldName, String newAlias) {
		if (!hasWorldData(worldName))
			createWorldDataFile(worldName);
		
		CustomConfig config = getWorldData(worldName);
		config.get().set("Alias", newAlias);
		config.save();
	}
	
	public static String getChatFormat(String worldName) {
		if (PerWorldChatPlusAPI.getApi().getPluginConfig().get().contains("Format.Format." + worldName))
			return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Format.Format." + worldName);
		else
			return PerWorldChatPlusAPI.getApi().getPluginConfig().get().getString("Format.Format.Default");
	}
}