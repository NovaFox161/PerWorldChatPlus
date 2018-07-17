package com.cloudcraftgaming.perworldchatplus.api;

import com.cloudcraftgaming.perworldchatplus.api.utils.MessageManager;
import com.novamaday.novalib.api.bukkit.file.CustomConfig;
import com.novamaday.novalib.api.packets.PacketManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("unused")
public class PerWorldChatPlusAPI {
    private static PerWorldChatPlusAPI instance;

    private JavaPlugin plugin;

    private CustomConfig config;

    private static Chat chat = null;

    private boolean hasFactionsBool;

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

        PacketManager.getManager().init(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3]);

        config = new CustomConfig(plugin, "", "config.yml");

        config.update(getSettings());

        MessageManager.getMessages().update(MessageManager.getEnglishMessageDefaults());

        //Integrate dependencies
        setupChat();
        setupFactionsIntegration();

        if (debug())
            getPlugin().getLogger().info("Started PerWorldChatPlusAPI!");
    }

    /**
     * Shuts down the API gracefully. This is automatically handled on server shutdown and SHOULD NOT be called by any plugins.
     */
    public void shutdownAPI() {
    }

    /**
     * Hook a Bukkit plugin into PerWorldChatPlus. This will be needed more in the future to handle certain functions!
     *
     * @param _bPlugin The plugin to hook.
     */
    public void hookBukkitPlugin(JavaPlugin _bPlugin) {
        plugin.getLogger().info("Plugin hooked: " + _bPlugin.getDescription().getName());
    }

    private LinkedHashMap<String, Object> getSettings() {
        LinkedHashMap<String, Object> s = new LinkedHashMap<>();

        s.put("DO NOT DELETE", "PerWorldChatPlus and PerWorldChatPlusAPI developed and managed by DreamExposure");
        s.put("Updates.Check", true);
        s.put("Updates.Download", false);

        s.put("Console.Debug", false);
        s.put("Console.Verbose", false);

        s.put("Lang", "En");

        s.put("Announce Dev Join", true);
        s.put("Prefix", "&5[PerWorldChat]");

        s.put("Global.Prefix", "&4[Global]");
        s.put("Global.Override", "!wc");
        s.put("Global.Always Global", false);
        s.put("Global.TimedGlobal.Allow", true);
        s.put("Global.TimedGlobal.Announce", true);
        s.put("Global.TimedGlobal.DefaultTime", 5);
        s.put("Global.TimedGlobal.On", false);
        s.put("Alert.Mention.OnName", true);
        s.put("Alert.Mention.RequirePermission", false);
        s.put("Alert.Mention.RequireAtSymbol", false);
        s.put("Alert.Mention.SendNotice", false);
        s.put("Alert.Mention.Sound", Sound.ENTITY_PLAYER_LEVELUP.name());

        s.put("Format.Enabled", true);
        s.put("Format.PerWorld", true);
        s.put("Format.Format.Default", "[%world%] %player% :&r %message%");
        s.put("Format.Format.Global", "&4[Global] [%world%] %player% :&r %message%");
        s.put("Format.Format.ExampleWorld", "This is a a format for 'ExampleWorld'");

        s.put("Chat.Color.Translate", true);
        s.put("Chat.Color.Auto", true);
        s.put("Chat.Color.Default", ChatColor.WHITE.name());
        s.put("Chat.Color.RequirePermission", true);
        s.put("Chat.Color.StripWithoutPermission", true);

        s.put("Chat.Swear.Block.Enabled", true);
        s.put("Chat.Swear.Block.EntireMessage", false);
        s.put("Chat.Swear.Replace", "***");
        s.put("Chat.Swear.Kick.Enabled", false);
        s.put("Chat.Swear.Kick.Announce", true);
        List<String> blockedSwears = new ArrayList<>();
        blockedSwears.add("Crap");
        blockedSwears.add("Shit");
        s.put("Chat.Swear.Blocked", blockedSwears);
        s.put("Chat.Ad.Block.Enabled", true);
        s.put("Chat.Ad.Block.EntireMessage", false);
        s.put("Chat.Ad.Block.Ip-Addresses", true);
        s.put("Chat.Ad.Block.Websites", true);
        s.put("Chat.Ad.Replace", "***");
        s.put("Chat.Ad.Kick.Enabled", false);
        s.put("Chat.Ad.Kick.Announce", true);
        s.put("Chat.Spam.Block.Enabled", true);
        s.put("Chat.Spam.Block.EntireMessage", false);
        s.put("Chat.Spam.Replace", "***");
        s.put("Chat.Spam.Kick.Enabled", false);
        s.put("Chat.Spam.Kick.Announce", true);
        s.put("Chat.Spam.Caps.Limit.Enabled", true);
        s.put("Chat.Spam.Caps.Limit.Percent", 75.0);
        s.put("Chat.Spam.Caps.Limit.ToLower", true);
        s.put("Chat.Spam.Time.Limit.Enabled", false);
        s.put("Chat.Spam.Time.Limit.MS", 1000);
        s.put("Chat.Spam.Time.Limit.Warn", true);
        s.put("Chat.Spam.Same.Limit.Enabled", false);

        s.put("PM.Enabled", true);
        s.put("PM.Format.Spy", "&4[SPY][&sendername%] &6-> &4[%receivername%]:&r %message%");
        s.put("PM.Format.Default.From", "&4[You] &6-> [&4%receiver%]:&r %message%");
        s.put("PM.Format.Default.To", "&4[%sender%] &6-> [&4You]:&r %message%");
        s.put("PM.Format.ExampleWorld.From", "This is a a format for 'ExampleWorld' for the sender");
        s.put("PM.Format.ExampleWorld.To", "This is a a format for 'ExampleWorld' for the receiver");


        List<String> sharesList = new ArrayList<>();
        sharesList.add("world");
        s.put("SharesList", sharesList);


        return s;
    }


    /**
     * Gets the CustomConfig for the API.
     *
     * @return The CustomConfig for the API.
     */
    public CustomConfig getConfig() {
        return config;
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
        return getApi().getConfig().get().getBoolean("Console.Debug");
    }

    /**
     * Gets whether or not PerWorldChatPlus is in Verbose Mode.
     *
     * @return Whether or not PerWorldChatPlus is in Verbose Mode.
     */
    public boolean verbose() {
        return getApi().getConfig().get().getBoolean("Console.Verbose");
    }

    /**
     * Gets Vault Chat to grab player chat data from.
     *
     * @return The vault chat.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Checks if the server is using factions for Factions Chat Integration.
     *
     * @return <code>true</code> if using factions, otherwise <code>false</code>.
     */
    public Boolean hasFactions() {
        return hasFactionsBool;
    }

    private void setupChat() {
        if (getPlugin().getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Chat> rsp = getPlugin().getServer().getServicesManager().getRegistration(Chat.class);
            if (rsp == null) {
                //No chat provider registered.
                getPlugin().getServer().getLogger().warning("Vault installed BUT a chat provider is missing! Some of PerWorldChatPlus's functions may not work! Sorry, this will be fixed in a future update!");
                return;
            }
            chat = rsp.getProvider();
        } else {
            getPlugin().getServer().getLogger().warning("Vault not installed/found! Some of PerWorldChatPlus's functions may not work! Download Vault at: https://dev.bukkit.org/bukkit-plugins/vault/");
        }
    }

    private void setupFactionsIntegration() {
        if (getPlugin().getServer().getPluginManager().getPlugin("Factions") != null) {
            hasFactionsBool = true;
        }
    }
}