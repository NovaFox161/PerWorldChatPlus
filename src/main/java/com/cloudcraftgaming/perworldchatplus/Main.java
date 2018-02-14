package com.cloudcraftgaming.perworldchatplus;

import com.cloudcraftgaming.perworldchatplus.api.data.WorldDataManager;
import com.cloudcraftgaming.perworldchatplus.internal.commands.*;
import com.cloudcraftgaming.perworldchatplus.internal.listeners.ChatListener;
import com.cloudcraftgaming.perworldchatplus.internal.listeners.InventoryClickListener;
import com.cloudcraftgaming.perworldchatplus.internal.listeners.JoinListener;
import com.cloudcraftgaming.perworldchatplus.internal.listeners.QuitListener;
import com.cloudcraftgaming.perworldchatplus.internal.utils.ChatColorInventory;
import com.cloudcraftgaming.perworldchatplus.internal.utils.FileManager;
import com.cloudcraftgaming.perworldchatplus.internal.utils.MessageManager;
import com.cloudcraftgaming.perworldchatplus.internal.utils.UpdateChecker;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Create by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class Main extends JavaPlugin {
	public static Main plugin;
	
	private static Chat chat = null;
	
	private boolean hasFactionsBool;
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		//Register things
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
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
		FileManager.createConfig();
		FileManager.createSharesFile();
		MessageManager.createEnglishMessagesFile();
		
		FileManager.checkFileVersion();
		
		//Make sure timed global didn't stay on somehow.
		getConfig().set("Global.TimedGlobal.On", false);
		saveConfig();
		
		checkUpdatesOnStart();
		
		//Do some other stuff
		ChatColorInventory.createChatColorInventory();
		generateWorldDataFilesOnStart();
		
		//Integrate dependencies
		setupChat();
		setupFactionsIntegration();
	}
	
	private void checkUpdatesOnStart() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				UpdateChecker.checkForUpdates();
			}
		}, 20L);
	}
	
	private void generateWorldDataFilesOnStart() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				for (World world : Bukkit.getWorlds()) {
					WorldDataManager.createWorldDataFile(world.getName());
				}
			}
		}, 20L * 2);
	}
	
	private void setupChat() {
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
			if (rsp == null) {
				//No chat provider registered.
				getServer().getLogger().warning("Vault installed BUT a chat provider is missing! Some of PerWorldChatPlus's functions may not work!" +
						" Sorry, this will be fixed in a future update!");
				return;
			}
			chat = rsp.getProvider();
		} else {
			getServer().getLogger().warning("Vault not installed/found! Some of PerWorldChatPlus's functions may not work!" +
					" Download Vault at: https://dev.bukkit.org/bukkit-plugins/vault/");
		}
	}
	
	private void setupFactionsIntegration() {
		if (getServer().getPluginManager().getPlugin("Factions") != null) {
			hasFactionsBool = true;
		}
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
		return targetVersion.equals(plugin.getDescription().getVersion()) || targetVersion.startsWith("5");
	}
	
	//Public methods for stuffs
	
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
}