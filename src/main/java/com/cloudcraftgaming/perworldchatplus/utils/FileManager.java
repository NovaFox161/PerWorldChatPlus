package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.cloudcraftgaming.perworldchatplus.Main.plugin;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * This class handles several file related functions and methods.
 */
public class FileManager {
	private static String conVersion = "6.0";
	static Double messageVersion = 2.2;
	
	//Functionals
	
	/**
	 * Creates the default config.yml file (Really only used on startup by PerWorldChatPlus).
	 */
	public static void createConfig() {
		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		if (!(file.exists())) {
			plugin.getLogger().info("Generating config.yml in folder /PerWorldChatPlus/");
			
			plugin.getConfig().addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
			plugin.getConfig().addDefault("Config Version", conVersion);
			plugin.getConfig().addDefault("Check for Updates", true);
			plugin.getConfig().addDefault("Download Updates", false);
			plugin.getConfig().addDefault("Lang", "En");
			plugin.getConfig().addDefault("Announce Dev Join", true);
			plugin.getConfig().addDefault("Prefix", "&5[PerWorldChat]");
			
			plugin.getConfig().addDefault("Global.Prefix", "&4[Global]");
			plugin.getConfig().addDefault("Global.Override", "!wc");
			plugin.getConfig().addDefault("Global.Always Global", false);
			plugin.getConfig().addDefault("Global.TimedGlobal.Allow", true);
			plugin.getConfig().addDefault("Global.TimedGlobal.Announce", true);
			plugin.getConfig().addDefault("Global.TimedGlobal.DefaultTime", 5);
			plugin.getConfig().addDefault("Global.TimedGlobal.On", false);
			plugin.getConfig().addDefault("Alert.Mention.OnName", true);
			plugin.getConfig().addDefault("Alert.Mention.RequirePermission", false);
			plugin.getConfig().addDefault("Alert.Mention.RequireAtSymbol", false);
			plugin.getConfig().addDefault("Alert.Mention.SendNotice", false);
			plugin.getConfig().addDefault("Alert.Mention.Sound", Sound.ENTITY_PLAYER_LEVELUP.name());
			
			plugin.getConfig().addDefault("Format.Enabled", true);
			plugin.getConfig().addDefault("Format.PerWorld", true);
			plugin.getConfig().addDefault("Format.Format.Default", "[%world%] %player% :&r %message%");
			plugin.getConfig().addDefault("Format.Format.Global", "&4[Global] [%world%] %player% :&r %message%");
			plugin.getConfig().addDefault("Format.Format.ExampleWorld", "This is a a format for 'ExampleWorld'");
			
			plugin.getConfig().addDefault("Chat.Color.Translate", true);
			plugin.getConfig().addDefault("Chat.Color.Auto", true);
			plugin.getConfig().addDefault("Chat.Color.Default", ChatColor.WHITE.name());
			plugin.getConfig().addDefault("Chat.Color.RequirePermission", true);
			plugin.getConfig().addDefault("Chat.Color.StripWithoutPermission", true);
			
			plugin.getConfig().addDefault("Chat.Swear.Block.Enabled", true);
			plugin.getConfig().addDefault("Chat.Swear.Block.EntireMessage", false);
			plugin.getConfig().addDefault("Chat.Swear.Replace", "***");
			plugin.getConfig().addDefault("Chat.Swear.Kick.Enabled", false);
			plugin.getConfig().addDefault("Chat.Swear.Kick.Announce", true);
			List<String> blockedSwears = plugin.getConfig().getStringList("Chat.Swear.Blocked");
			blockedSwears.add("Crap");
			blockedSwears.add("Shit");
			plugin.getConfig().set("Chat.Swear.Blocked", blockedSwears);
			plugin.getConfig().addDefault("Chat.Ad.Block.Enabled", true);
			plugin.getConfig().addDefault("Chat.Ad.Block.EntireMessage", false);
			plugin.getConfig().addDefault("Chat.Ad.Block.Ip-Addresses", true);
			plugin.getConfig().addDefault("Chat.Ad.Block.Websites", true);
			plugin.getConfig().addDefault("Chat.Ad.Replace", "***");
			plugin.getConfig().addDefault("Chat.Ad.Kick.Enabled", false);
			plugin.getConfig().addDefault("Chat.Ad.Kick.Announce", true);
			plugin.getConfig().addDefault("Chat.Spam.Block.Enabled", true);
			plugin.getConfig().addDefault("Chat.Spam.Block.EntireMessage", false);
			plugin.getConfig().addDefault("Chat.Spam.Replace", "***");
			plugin.getConfig().addDefault("Chat.Spam.Caps.Limit.Enabled", true);
			plugin.getConfig().addDefault("Chat.Spam.Caps.Limit.Percent", 75.0);
			plugin.getConfig().addDefault("Chat.Spam.Caps.Limit.ToLower", true);
			
			plugin.getConfig().addDefault("PM.Enabled", true);
			plugin.getConfig().addDefault("PM.Format.Spy", "&4[SPY][&sendername%] &6-> &4[%receivername%]:&r %message%");
			plugin.getConfig().addDefault("PM.Format.Default.From", "&4[You] &6-> [&4%receiver%]:&r %message%");
			plugin.getConfig().addDefault("PM.Format.Default.To", "&4[%sender%] &6-> [&4You]:&r %message%");
			plugin.getConfig().addDefault("PM.Format.ExampleWorld.From", "This is a a format for 'ExampleWorld' for the sender");
			plugin.getConfig().addDefault("PM.Format.ExampleWorld.To", "This is a a format for 'ExampleWorld' for the receiver");
			
			
			List<String> sharesList = plugin.getConfig().getStringList("SharesList");
			sharesList.add("world");
			plugin.getConfig().set("SharesList", sharesList);
			
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
			
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveConfig();
		}
	}
	
	/**
	 * This is a massive utility function that generates the default files used by PerWorldChatPlus (utility only, used on startup).
	 */
	public static void createSharesFile() {
		if (!(getSharesFile().exists())) {
			plugin.getLogger().info("Generating World Shares file...");
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
	
	/**
	 * A nice little function that compares the currently existing file versions with the new ones.
	 * If they are out of date, this will automatically disable the plugin.
	 */
	public static void checkFileVersion() {
		if (!(plugin.getConfig().getString("Config Version").equalsIgnoreCase(conVersion))) {
			plugin.getLogger().severe("Config.yml outdated! Plugin will not work properly! "
					+ "Delete the file and restart the server to update it!");
			plugin.getLogger().info("Shutting down plugin to prevent further errors....");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		} else if (!(MessageManager.getMessageYml().getDouble("Messages Version") == messageVersion)) {
			plugin.getLogger().severe("Your messages files are outdated! Plugin will not work properly! "
					+ "Delete the messages folder and restart the server to update it!");
			plugin.getLogger().info("Shutting down plugin to prevent further errors....");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
	}
	
	//Getters
	
	/**
	 * Gets the world shares File.
	 *
	 * @return The world shares file.
	 */
	private static File getSharesFile() {
		return new File(Main.plugin.getDataFolder() + "/shares.yml");
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