package org.dreamexposure.perworldchatplus.api.utils;

import org.bukkit.ChatColor;
import org.dreamexposure.novalib.api.bukkit.file.CustomConfig;
import org.dreamexposure.perworldchatplus.api.PerWorldChatPlusAPI;

import java.util.LinkedHashMap;

/**
 * Created by Nova Fox on 1/30/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * This class handles message related functions, such as creating the message files and retrieving messages.
 */
public class MessageManager {
	/**
	 * Gets the prefix for messages send by PerWorldChatPlus.
	 *
	 * @return the prefix defined in the config.yml file.
	 */
	public static String getPrefix() {
		String prefix1 = PerWorldChatPlusAPI.getApi().getConfig().get().getString("Prefix");
		return ChatColor.translateAlternateColorCodes('&', prefix1) + " " + ChatColor.RESET;
	}
	
	/**
	 * Gets the no permission message.
	 *
	 * @return The no permission message when a player attempts a command (in color).
	 */
	public static String getNoPermMessage() {
		String noPermOr = getMessages().get().getString("Notification.NoPerm");
		return ChatColor.translateAlternateColorCodes('&', noPermOr);
	}
	
	/**
	 * Gets the message for when a non player attempts a player only command.
	 *
	 * @return the player only warning message.
	 */
	public static String getPlayerOnlyMessage() {
		String msgOr = getMessages().get().getString("Notification.PlayerOnly");
		return ChatColor.translateAlternateColorCodes('&', msgOr);
	}

	/**
	 * Gets the Messages yml configuration.
	 *
	 * @return the yml configuration of messages depending on the language.
	 */
	public static CustomConfig getMessages() {
		String fileName = PerWorldChatPlusAPI.getApi().getConfig().get().getString("Lang");

		return new CustomConfig(PerWorldChatPlusAPI.getApi().getPlugin(), PerWorldChatPlusAPI.getApi().getPlugin().getDataFolder() + "/Messages", fileName);
	}
	
	/**
	 * Gets the message from the messages yml from the given path.
	 *
	 * @param path The path the message resides at.
	 * @return The message with ChatColors translated.
	 */
	public static String getMessage(String path) {
		return ChatColor.translateAlternateColorCodes('&', getMessages().get().getString(path));
		
	}

	public static LinkedHashMap<String, Object> getEnglishMessageDefaults() {
		LinkedHashMap<String, Object> m = new LinkedHashMap<>();

		m.put("DO NOT DELETE", "PerWorldChatPlus and PerWorldChatPlusAPI are developed and managed by DreamExposure");

		m.put("Chat.Swear.Kick.Player", "&4Kicked automatically for swearing!");
		m.put("Chat.Swear.Kick.Announcement", "&6%player% &6was automatically kicked for swearing!");
		m.put("Chat.Ad.Kick.Player", "&4Kicked automatically for advertising!");
		m.put("Chat.Ad.Kick.Announcement", "&6%player% &6was automatically kicked for advertising!");
		m.put("Chat.Spam.Time.Warn", "&4You are sending messages too often!");
		m.put("Chat.Spam.Kick.Player", "&4Kicked automatically for spamming!");
		m.put("Chat.Spam.Kick.Announcement", "&6%player% &6was automatically kicked for spamming!");

		m.put("Command.Global.AddMessage", "&4You need to add in the message you want to broadcast!");
		m.put("Command.Spy.Enabled", "&2Global Chat Spy enabled! You can now see all messages!");
		m.put("Command.Spy.Disabled", "&4Global Chat Spy disabled! You will only see messages in your world!");
		m.put("Command.WorldSpy.TurnOn", "&2Spying on chat from worlds: &6%worlds%");
		m.put("Command.WorldSpy.TurnOff", "&4World spying off! You will only see messages in your world!");
		m.put("Command.WorldSpy.AlreadyOn", "&4World chat spy is already on! Use &6/pwc worldspy off &4to turn it off!");
		m.put("Command.WorldSpy.AlreadyOff", "&4World chat spy is already off! Use &6/pwc world spy on &4to turn it on!");
		m.put("Command.WorldSpy.RemoveWorld", "&4Removed &6%world% &4from spy list!");
		m.put("Command.WorldSpy.AddWorld", "&2Added &6%world% &2to spy list!");
		m.put("Command.WorldSpy.NoPerm", "&4You eo not have permission to spy on that world!");
		m.put("Command.SocialSpy.Enabled", "&2Socialspy enabled! You will now see all PMs!");
		m.put("Command.SocialSpy.Disabled", "&4Socialspy disabled! You will no longer see all PMs!");
		m.put("Command.Bypass.Enabled", "&2Global Chat Bypass enabled! All of your messages will be global until disabled!");
		m.put("Command.Bypass.Disabled", "&4Global Chat Bypass disabled! Your messages are per world until enabled!");
		m.put("Command.Alert.Added", "&6%word% &2has been added to your alert list!");
		m.put("Command.Alert.Removed", "&6%word% &4has been removed from your alert list!");
		m.put("Command.TimedGlobal.TurnedOn", "&2You have turned on Timed Global Chat for: &6%time% minutes&2!");
		m.put("Command.TimedGlobal.TurnedOff", "&4You have turned off timed Global Chat!");
		m.put("Command.TimedGlobal.TurningOff", "&4Turning off Timed Global Chat...");
		m.put("Command.TimedGlobal.Announce.On", "&2Chat is now global (in all worlds) for &6%time% minutes&2!");
		m.put("Command.TimedGlobal.Announce.Off", "&4Timed Global Chat is now off! Your messages may not be sent to all worlds!");
		m.put("Command.TimedGlobal.Disabled", "&4Timed Global Chat is disabled as defined in config.yml!");
		m.put("Command.TimedGlobal.AlreadyOn", "&4Timed Global Chat is already on! &2Use &6/pwc timedglobal &2to turn it off!");
		m.put("Command.TimedGlobal.AlreadyOff", "&4Timed Global Chat already off! &2Use &6/pwc timedglobal <time> &2to turn it on!");
		m.put("Command.TimedGlobal.TimeNotNumber", "&4ERROR time is not a number!");
		m.put("Command.Mute.Enable", "&4You have muted your chat! You will not see any chat message until you unmute or log off!");
		m.put("Command.Mute.Disable", "&2You have unmuted your chat! You will now see chat messages again!");
		m.put("Command.pm.args.few", "&4Too few args! You need &6/pm <to> <message>");
		m.put("Command.reply.args.few", "&4Too few args! You need &6/r <message>");
		m.put("Command.reply.no one", "&4You have no one to reply to!");

		m.put("Command.Set.Alias", "&5World alias for world: &6%world% &5 is now &6%alias%&5!");
		m.put("Command.Set.Color.Self", "&2You have changed your chat color to &6%color%&2!");
		m.put("Command.Set.Color.Other", "&2Color for player &5%Player% &2 is now &6%color%&1!");

		m.put("Command.ChatColor.Open", "&6Opened ChatColor GUI!");

		m.put("Command.Ignore.Self", "&4You cannot ignore yourself!");
		m.put("Command.Ignore.Start.Success", "&4You have ignored %player% &4and will no longer see their messages!");
		m.put("Command.Ignore.Start.Failure", "&4That player cannot be ignored or something went wrong!");
		m.put("Command.Ignore.Stop.Success", "&2You have stopped ignoring %player% &2and will now see their messages!");
		m.put("Command.Ignore.Stop.Failure", "&4Oops! Something went wrong and that operation did not work!");

		m.put("Mention.Notice", "&2You were mentioned by: &6%sender%");

		m.put("Notification.NoPerm", "&4You do not have permission to do that!");
		m.put("Notification.PlayerOnly", "&4Only players can use that command!");
		m.put("Notification.Args.Invalid", "&4Invalid arguments! &2Use: &6/pwc help &2for a list of commands!");
		m.put("Notification.Args.TooFew", "&4Too few arguments! &2Use: &6/pwc help &2for a list of commands!");
		m.put("Notification.Args.TooMany", "&4Too many arguments! &2Use: &6/pwc help &2for a list of commands!");
		m.put("Notification.Color.Invalid", "&4That is not a valid ChatColor!");
		m.put("Notifications.Player.Offline", "&4That player is not online or does not exist!");
		m.put("Notification.World.DoesNotExist", "&4The specified world does not exist or is spelled incorrectly!");

		return m;
	}
}