package org.dreamexposure.perworldchatplus.api;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.dreamexposure.novalib.api.bukkit.file.CustomConfig;
import org.dreamexposure.perworldchatplus.api.data.WorldShares;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;

import java.util.LinkedHashMap;

@SuppressWarnings("unused")
public class PerWorldChatPlusAPI {
    private static PerWorldChatPlusAPI instance;

    private JavaPlugin plugin;
    
    private CustomConfig apiConfig;
    private CustomConfig pluginConfig;

    private static Chat chat = null;

    private PerWorldChatPlusAPI() {
    } //Prevent initialization

    /**
     * Gets the current loaded instance of the API.
     *
     * @return The  current loaded instance of the API.
     */
    public static PerWorldChatPlusAPI getApi() {
        if (instance == null)
            instance = new PerWorldChatPlusAPI();
        return instance;
    }

    /**
     * Initializes all parts of the API. This is automatically handled on server boot and SHOULD NOT be called by any plugins.
     */
    public void initAPIForBukkit(JavaPlugin _plugin) {
        plugin = _plugin;
    
        apiConfig = new CustomConfig(plugin, "/api/", "config.yml");
        apiConfig.update(getAPISettings());
        pluginConfig = new CustomConfig(plugin, "", "config.yml");
    
        WorldShares.get().load(plugin);

        MessageManager.getMessages().update(MessageManager.getEnglishMessageDefaults());
    
        plugin.getLogger().info("Checking for vault...");
        if (vaultCheck()) {
            plugin.getLogger().info("Vault Found! Checking for econ plugins...");
            if (chatCheck())
                plugin.getLogger().info("A Chat plugin was found! You are all set!");
            else
                plugin.getLogger().warning("A Chat was not found! Some features may be missing or completely broken!");
        } else
            plugin.getLogger().warning("Vault was not found! Some features may be missing or completely broken!");
    
        plugin.getLogger().info("Checking for PlaceholderAPI...");
        if (papiCheck())
            plugin.getLogger().info("PlaceholderAPI found! All supported placeholders will be used!");
        else
            plugin.getLogger().info("PlaceholderAPI not found! Some features may be missing or completely broken!");
    
        getPlugin().getLogger().info("Started PerWorldChatPlusAPI!");
    }

    /**
     * Shuts down the API gracefully. This is automatically handled on server shutdown and SHOULD NOT be called by any plugins.
     */
    public void shutdownAPI() {
        //Unhook anything that needs to be removed
        getPlugin().getLogger().info("Shutdown PerWorldChatPlusAPI!");
    }

    /**
     * Hook a Bukkit plugin into PerWorldChatPlus. This will be needed more in the future to handle certain functions!
     *
     * @param _bPlugin The plugin to hook.
     */
    public void hookBukkitPlugin(JavaPlugin _bPlugin) {
        plugin.getLogger().info("Plugin hooked: " + _bPlugin.getDescription().getName());
    }
    
    
    /**
     * Gets the BukkitPlugin for the API.
     *
     * @return The BukkitPlugin for the API.
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets whether or not PerWorldChatPlus is in Debug Mode.
     *
     * @return Whether or not PerWorldChatPlus is in Debug Mode.
     */
    public boolean debug() {
        return getApi().getPluginConfig().get().getBoolean("Console.Debug");
    }

    /**
     * Gets whether or not PerWorldChatPlus is in Verbose Mode.
     *
     * @return Whether or not PerWorldChatPlus is in Verbose Mode.
     */
    public boolean verbose() {
        return getApi().getPluginConfig().get().getBoolean("Console.Verbose");
    }

    /**
     * Gets Vault Chat to grab player chat data from.
     *
     * @return The vault chat.
     */
    public Chat getChat() {
        return chat;
    }
    
    public CustomConfig getApiConfig() {
        return apiConfig;
    }
    
    public CustomConfig getPluginConfig() {
        return pluginConfig;
    }
    
    public boolean vaultCheck() {
        return plugin.getServer().getPluginManager().getPlugin("Vault") != null;
    }
    
    public boolean chatCheck() {
        if (!vaultCheck())
            return false;
        
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp == null)
            return false;
        
        chat = rsp.getProvider();
        return chat != null;
    }
    
    public boolean papiCheck() {
        return plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
    
    private LinkedHashMap<String, Object> getAPISettings() {
        LinkedHashMap<String, Object> s = new LinkedHashMap<>();
        
        s.put("DO NOT DELETE", "PerWorldChatPlus and PerWorldChatPlusAPI developed and managed by DreamExposure");
        
        return s;
    }
}