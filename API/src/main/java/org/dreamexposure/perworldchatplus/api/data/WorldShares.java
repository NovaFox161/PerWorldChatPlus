package org.dreamexposure.perworldchatplus.api.data;

import org.bukkit.plugin.java.JavaPlugin;
import org.dreamexposure.novalib.api.bukkit.file.CustomConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author NovaFox161
 * Date Created: 10/29/2018
 * For Project: PerWorldChatPlus
 * Author Website: https://www.novamaday.com
 * Company Website: https://www.dreamexposure.org
 * Contact: nova@dreamexposure.org
 */
@SuppressWarnings("WeakerAccess")
public class WorldShares {
    private static WorldShares instance;
    
    private CustomConfig shares;
    
    private WorldShares() {
    }
    
    public static WorldShares get() {
        if (instance == null)
            instance = new WorldShares();
        
        return instance;
    }
    
    
    public void load(JavaPlugin plugin) {
        shares = new CustomConfig(plugin, "", "shares.yml");
        shares.update(sharesDefault());
    }
    
    public boolean isShared(String worldFrom, String worldTo) {
        if (worldFrom.equals(worldTo)) {
            return true;
        }
        String sharesListName = WorldShares.get().getWorldShareListName(worldFrom);
        List<String> sharesList = WorldShares.get().getWorldShareList(worldFrom);
        if (sharesList != null && sharesListName != null) {
            return sharesListName.equalsIgnoreCase(worldTo) || sharesList.contains(worldTo);
        }
        return false;
    }
    
    public String getWorldShareListName(String worldName) {
        for (String list : shares.get().getKeys(false)) {
            if (list.equalsIgnoreCase(worldName))
                return list;
            else if (shares.get().getStringList(list).contains(worldName))
                return list;
        }
        return null;
    }
    
    /**
     * Gets the shares list for the requested world.
     *
     * @param worldName The name of the world.
     * @return The shares list for the world (List of world names that share chat together).
     */
    public List<String> getWorldShareList(String worldName) {
        if (getWorldShareListName(worldName) != null) {
            return shares.get().getStringList(getWorldShareListName(worldName));
        }
        return null;
    }
    
    public CustomConfig getSharesFile() {
        return shares;
    }
    
    private LinkedHashMap<String, Object> sharesDefault() {
        LinkedHashMap<String, Object> s = new LinkedHashMap<>();
        
        s.put("DO NOT DELETE", "PerWorldChatPlus and PerWorldChatPlusAPI developed and managed by DreamExposure");
        
        List<String> sharesList = new ArrayList<>();
        sharesList.add("world_nether");
        sharesList.add("world_the_end");
        s.put("world", sharesList);
        
        return s;
    }
}