package org.dreamexposure.perworldchatplus.plugin.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamexposure.novalib.api.NovaLibAPI;
import org.dreamexposure.novalib.api.bukkit.file.CustomConfig;
import org.dreamexposure.novalib.api.bukkit.update.UpdateChecker;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;
import org.dreamexposure.perworldchatplus.api.data.WorldDataManager;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands.*;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.ChatListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.InventoryClickListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.JoinListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.listeners.QuitListener;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.utils.ChatColorInventory;
import org.dreamexposure.perworldchatplus.plugin.bukkit.internal.utils.FileManager;

/**
 * Create by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class PerWorldChatPlusPlugin extends JavaPlugin {
    public static JavaPlugin plugin;
    
    private static PerWorldChatPlusPlugin pl;
    
    public CustomConfig config;

    @Override
    public void onDisable() {
        getLogger().info("===== PerWorldChatPlus =====");
        getLogger().info("Developed by DreamExposure");
        getLogger().info("Status: Disabling");
    
        PerWorldChatPlusAPI.getApi().shutdownAPI();
        getLogger().info("========================");
    }

    @Override
    public void onEnable() {
        plugin = this;
        pl = this;
    
        getLogger().info("===== PerWorldChatPlus =====");
        getLogger().info("Developed by DreamExposure");
        getLogger().info("Status: Enabling");

        NovaLibAPI.getApi().hookBukkitPlugin(this);
        PerWorldChatPlusAPI.getApi().initAPIForBukkit(this);
    
        getLogger().info("Registering event listeners...");
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    
        getLogger().info("Registering commands...");
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
        config = new CustomConfig(plugin, "", "config.yml");
    
        config.update(FileManager.getSettings());

        checkUpdatesOnStart();

        //Do some other stuff
        ChatColorInventory.createChatColorInventory();
        generateWorldDataFilesOnStart();
    
        getLogger().info("========================");
    }

    private void checkUpdatesOnStart() {
        if (config.get().getBoolean("Updates.Check")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> UpdateChecker.init(this, 26601).requestUpdateCheck().whenComplete((result, exception) -> {
                if (result.requiresUpdate()) {
                    this.getLogger().info(String.format("An update is available! PerWorldChatPlus %s may be downloaded on SpigotMC", result.getNewestVersion()));
                    return;
                }
            
                UpdateChecker.UpdateReason reason = result.getReason();
                if (reason == UpdateChecker.UpdateReason.UP_TO_DATE) {
                    getLogger().info(String.format("Your version of PerWorldChatPlus (%s) is up to date!", result.getNewestVersion()));
                } else if (reason == UpdateChecker.UpdateReason.UNRELEASED_VERSION) {
                    getLogger().info(String.format("Your version of PerWorldChatPlus (%s) is more recent than the one publicly available. Are you on a development build?", result.getNewestVersion()));
                } else {
                    getLogger().warning("Could not check for a new version of PerWorldChatPlus. Reason: " + reason);
                }
            }), 20L);
        }
    }

    private void generateWorldDataFilesOnStart() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            for (World world : Bukkit.getWorlds()) {
                WorldDataManager.createWorldDataFile(world.getName());
            }
        }, 20L * 2);
    }
    
    public static PerWorldChatPlusPlugin get() {
        return pl;
    }
}