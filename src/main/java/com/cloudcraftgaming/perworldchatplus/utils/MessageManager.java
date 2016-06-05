package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Nova Fox on 1/30/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * This class handles message related functions, such as creating the message files and retrieving messages.
 */
public class MessageManager {
    /**
     * Gets the prefix for messages send by PerWorldChatPlus.
     * @return the prefix defined in the config.yml file.
     *
     */
    public static String getPrefix() {
        String prefix1 = Main.plugin.getConfig().getString("Prefix");
        return ChatColor.translateAlternateColorCodes('&', prefix1) + " " + ChatColor.RESET;
    }

    /**
     * Gets the no permission message.
     * @return The no permission message when a player attempts a command (in color).
     */
    public static String getNoPermMessage() {
        String noPermOr = getMessageYml().getString("Notification.NoPerm");
        return ChatColor.translateAlternateColorCodes('&', noPermOr);
    }

    /**
     * Gets the message for when a non player attempts a player only command.
     * @return the player only warning message.
     */
    public static String getPlayerOnlyMessage() {
        String msgOr = getMessageYml().getString("Notification.PlayerOnly");
        return ChatColor.translateAlternateColorCodes('&', msgOr);
    }
    /**
     * Gets the messages file depending on the language.
     * @return the file in which messages are stored depending on the language set in the config.
     */
    public static File getMessageFile() {
        String fileName = Main.plugin.getConfig().getString("Lang");
        return new File(Main.plugin.getDataFolder() + "/Messages/" + fileName + ".yml");
    }

    /**
     * Gets the Messages yml configuration.
     * @return the yml configuration of messages depending on the language.
     */
    public static FileConfiguration getMessageYml() {
        File messageFile = getMessageFile();
        return YamlConfiguration.loadConfiguration(messageFile);
    }

    /**
     * Creates the English Messages File. (Generally only used on startup).
     */
    public static void createEnglishMessagesFile() {
        File enFile = new File(Main.plugin.getDataFolder() + "/Messages/En.yml");
        if (!(enFile.exists())) {
            Main.plugin.getLogger().info("Generating En.yml messages file...");
            YamlConfiguration en = YamlConfiguration.loadConfiguration(enFile);
            en.addDefault("DO NOT DELETE", "PerWorldChatPlus is developed and managed by Shades161");
            en.addDefault("Messages Version", Main.plugin.messageVersion);

            en.addDefault("Chat.Swear.Kick", "&4Kicked automatically for swearing!");

            en.addDefault("Command.Global.AddMessage", "&4You need to add in the message you want to broadcast!");
            en.addDefault("Command.Spy.Enabled", "&2Global Chat Spy enabled! You can now see all messages!");
            en.addDefault("Command.Spy.Disabled", "&4Global Chat Spy disabled! You will only see messages in your world!");
            en.addDefault("Command.WorldSpy.TurnOn", "&2Spying on chat from worlds: &6%worlds%");
            en.addDefault("Command.WorldSpy.TurnOff", "&4World spying off! You will only see messages in your world!");
            en.addDefault("Command.WorldSpy.AlreadyOn", "&4World chat spy is already on! Use &6/pwc worldspy off &4to turn it off!");
            en.addDefault("Command.WorldSpy.AlreadyOff", "&4World chat spy is already off! Use &6/pwc world spy on &4to turn it on!");
            en.addDefault("Command.WorldSpy.RemoveWorld", "&4Removed &6%world% &4from spy list!");
            en.addDefault("Command.WorldSpy.AddWorld", "&2Added &6%world% &2to spy list!");
            en.addDefault("Command.WorldSpy.NoPerm", "&4You eo not have permission to spy on that world!");
            en.addDefault("Command.Bypass.Enabled", "&2Global Chat Bypass enabled! All of your messages will be global until disabled!");
            en.addDefault("Command.Bypass.Disabled", "&4Global Chat Bypass disabled! Your messages are per world until enabled!");
            en.addDefault("Command.Alert.Added", "&6%word% &2has been added to your alert list!");
            en.addDefault("Command.Alert.Removed", "&6%word% &4has been removed from your alert list!");
            en.addDefault("Command.TimedGlobal.TurnedOn", "&2You have turned on Timed Global Chat for: &6%time% minutes&2!");
            en.addDefault("Command.TimedGlobal.TurnedOff", "&4You have turned off timed Global Chat!");
            en.addDefault("Command.TimedGlobal.TurningOff", "&4Turning off Timed Global Chat...");
            en.addDefault("Command.TimedGlobal.Announce.On", "&2Chat is now global (in all worlds) for &6%time% minutes&2!");
            en.addDefault("Command.TimedGlobal.Announce.Off", "&4Timed Global Chat is now off! Your messages may not be sent to all worlds!");
            en.addDefault("Command.TimedGlobal.Disabled", "&4Timed Global Chat is disabled as defined in config.yml!");
            en.addDefault("Command.TimedGlobal.AlreadyOn", "&4Timed Global Chat is already on! &2Use &6/pwc timedglobal &2to turn it off!");
            en.addDefault("Command.TimedGlobal.AlreadyOff", "&4Timed Global Chat already off! &2Use &6/pwc timedglobal <time> &2to turn it on!");
            en.addDefault("Command.TimedGlobal.TimeNotNumber", "&4ERROR time is not a number!");
            en.addDefault("Command.Mute.Enable", "&4You have muted your chat! You will not see any chat message until you unmute or log off!");
            en.addDefault("Command.Mute.Disable", "&2You have unmuted your chat! You will now see chat messages again!");

            en.addDefault("Mention.Notice", "&2You were mentioned by: &6%sender%");

            en.addDefault("Notification.NoPerm", "&4You do not have permission to do that!");
            en.addDefault("Notification.PlayerOnly", "&4Only players can use that command!");
            en.addDefault("Notification.Args.Invalid", "&4Invalid arguments! &2Use: &6/pwc help &2for a list of commands!");
            en.addDefault("Notification.Args.TooFew", "&4Too few arguments! &2Use: &6/pwc help &2for a list of commands!");
            en.addDefault("Notification.Args.TooMany", "&4Too many arguments! &2Use: &6/pwc help &2for a list of commands!");
            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);

            en.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(en, enFile);
        }
    }
}
