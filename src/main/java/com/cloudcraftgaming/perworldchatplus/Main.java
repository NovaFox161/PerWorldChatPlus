package com.cloudcraftgaming.perworldchatplus;

import com.cloudcraftgaming.perworldchatplus.commands.GlobalChatCommand;
import com.cloudcraftgaming.perworldchatplus.commands.PerWorldChat;
import com.cloudcraftgaming.perworldchatplus.listeners.ChatListener;
import com.cloudcraftgaming.perworldchatplus.listeners.JoinListener;
import com.cloudcraftgaming.perworldchatplus.utils.FileManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.utils.UpdateChecker;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Create by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class Main extends JavaPlugin {
	public static Main plugin;

	public Integer timedRequest;
	public String conVersion = "5.0";
	public Double messageVersion = 2.0;
	public UpdateChecker updateChecker;
	public File sharesFile = new File(this.getDataFolder() + "/shares.yml");
	public FileConfiguration shares = (YamlConfiguration.loadConfiguration(sharesFile));
	public File worldSpyFile = new File(this.getDataFolder() + "/Data/worldSpy.yml");
	public FileConfiguration worldSpyYml = (YamlConfiguration.loadConfiguration(worldSpyFile));
	
	public void onDisable() {
		//Make sure timed global is turned off.
		getConfig().set("Global.TimedGlobal.On", false);
		saveConfig();
	}
	public void onEnable() {
		plugin = this;
		timedRequest = 0;

		//Register listeners
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);

		//Register commands.
		getCommand("perworldchatplus").setExecutor(new PerWorldChat(this));
		getCommand("perworldchat").setExecutor(new PerWorldChat(this));
		getCommand("pwcp").setExecutor(new PerWorldChat(this));
		getCommand("pwc").setExecutor(new PerWorldChat(this));
		getCommand("globalchat").setExecutor(new GlobalChatCommand(this));
		getCommand("global").setExecutor(new GlobalChatCommand(this));


		//Create files
		FileManager.createConfig();
		FileManager.createFiles();
		MessageManager.createEnglishMessagesFile();

		//Check file versions
		FileManager.checkFileVersion();

		//Make sure timed global didn't stay on somehow.
		getConfig().set("Global.TimedGlobal.On", false);
		saveConfig();
		
		 //Check for plugin updates
		checkUpdatesOnStart();
	}
	private void checkUpdatesOnStart() {
		if (getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
			getLogger().info("Checking for updates...");
			this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/per-world-chat-plus/files.rss");
			if (this.updateChecker.UpdateNeeded()) {
				getLogger().info("A new update for PerWorldChatPlus is available! Version: " + updateChecker.getVersion());
				getLogger().info("Download it from: " + updateChecker.getLink());
			}
			else {
				getLogger().info("No updates found, will check again later.");
			}
		}
	}


	 public void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
		 try {
			 ymlConfig.save(ymlFile);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
	}
}