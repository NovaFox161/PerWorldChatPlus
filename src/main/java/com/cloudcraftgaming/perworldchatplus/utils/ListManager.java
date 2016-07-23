package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.Main;

import java.util.List;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For project: PerWorldChatPlus
 *
 * This class handles list related functions (Utility only really).
 */
public class ListManager {
	/**
	 * Gets the name of the list that contains the shared worlds.
	 * Only really a utility function and not really needed outside of PerWorldChatPlus.
	 * @param worldName name of the world the player was in when issued.
	 * @return returns a string list of worlds that have shared chats.
     */
	public static String getWorldShareListName(String worldName) {
		for (String list : Main.plugin.getConfig().getStringList("SharesList")) {
			if (list.equalsIgnoreCase(worldName)) {
				return list;
			} else if (FileManager.getSharesYml().getStringList(list).contains(worldName)) {
				return list;
			}
		}
		return null;
	}

	/**
	 * Gets the shares list for the requested world.
	 * @param worldName The name of the world.
	 * @return The shares list for the world (List of world names that share chat together).
     */
	public static List<String> getWorldShareList(String worldName) {
		if (getWorldShareListName(worldName) != null) {
			return FileManager.getSharesYml().getStringList(getWorldShareListName(worldName));
		}
		return null;
	}
}