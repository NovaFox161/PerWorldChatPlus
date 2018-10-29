package org.dreamexposure.perworldchatplus.plugin.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamexposure.novalib.api.NovaLibAPI;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.WorldDataManager;
import org.dreamexposure.perworldchatplus.api.utils.FileManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands.*;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.ChatListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.InventoryClickListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.JoinListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.QuitListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.utils.ChatColorInventory;

/**
 * Create by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PerWorldChatPlusPlugin extends JavaPlugin {
    public static PerWorldChatPlusPlugin plugin;

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        plugin = this;

        NovaLibAPI.getApi().hookBukkitPlugin(this);
        PerWorldChatPlusAPI.getApi().initAPIForBukkit(this);

        //Register things
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        getCommand("perworldchatplus").setExecutor(new PerWorldChatCommand());
        getCommand("perworldchat").setExecutor(new PerWorldChatCommand());
        getCommand("pwcp").setExecutor(new PerWorldChatCommand());
        getCommand("pwc").setExecutor(new PerWorldChatCommand());
        getCommand("globalchat").setExecutor(new GlobalChatCommand());
        getCommand("global").setExecutor(new GlobalChatCommand());
        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getCommand("privatemessage").setExecutor(new PrivateMessageCommand());
        getCommand("pm").setExecutor(new PrivateMessageCommand());
        getCommand("message").setExecutor(new PrivateMessageCommand());
        getCommand("msg").setExecutor(new PrivateMessageCommand());
        getCommand("whisper").setExecutor(new PrivateMessageCommand());
        getCommand("reply").setExecutor(new PrivateReplyCommand());
        getCommand("r").setExecutor(new PrivateReplyCommand());
        getCommand("socialspy").setExecutor(new SocialSpyCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());

        //Do file stuff
        FileManager.createSharesFile();

        //Make sure timed global didn't stay on somehow.
        PerWorldChatPlusAPI.getApi().getConfig().get().set("Global.TimedGlobal.On", false);
        PerWorldChatPlusAPI.getApi().getConfig().save();

        checkUpdatesOnStart();

        //Do some other stuff
        ChatColorInventory.createChatColorInventory();
        generateWorldDataFilesOnStart();
    }

    private void checkUpdatesOnStart() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            //TODO: Use my custom update API
        }, 20L);
    }

    private void generateWorldDataFilesOnStart() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            for (World world : Bukkit.getWorlds()) {
                WorldDataManager.createWorldDataFile(world.getName());
            }
        }, 20L * 2);
    }

    /**
     * A simple method for checking if the versions are compatible.
     * This is to help reduce updates needed when a patch comes out.
     *
     * @param targetVersion The version you are specifically looking for.
     * @return True if the versions are compatible, else false.
     */
    @SuppressWarnings("unused")
    public static Boolean checkVersionCompatibility(String targetVersion) {
        return targetVersion.equals(plugin.getDescription().getVersion()) || targetVersion.startsWith("6");
    }
}