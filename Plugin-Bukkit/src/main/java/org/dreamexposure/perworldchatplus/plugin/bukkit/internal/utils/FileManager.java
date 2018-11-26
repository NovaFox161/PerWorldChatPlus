package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.utils;

import org.bukkit.ChatColor;

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
public class FileManager {
    public static LinkedHashMap<String, Object> getSettings() {
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
    
        s.put("Network.CrossServerChat.Enabled", false);
        
        s.put("Alert.Mention.OnName", true);
        s.put("Alert.Mention.RequirePermission", false);
        s.put("Alert.Mention.RequireAtSymbol", false);
        s.put("Alert.Mention.SendNotice", false);
        s.put("Alert.Mention.Sound", "ENTITY_PLAYER_LEVELUP");
        
        s.put("Chat.Handle", true);
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
        
        s.put("Format.Enabled", true); //Only applies if PWCP is natively handling chat.
        s.put("Format.PerWorld", false);
        s.put("Format.Format.Default", "[%world%] %player_name% :&r %message%");
        s.put("Format.Format.Global", "&4[Global] [%world%] %player_name% :&r %message%");
        s.put("Format.Format.ExampleWorld", "This is a a format for 'ExampleWorld'");
        
        s.put("PM.Enabled", true);
        s.put("PM.Format.Spy", "&4[SPY] [%otherplayer_displayname_{sender}%&r&4] &6-> &6[%otherplayer_displayname_{receiver}%&r&6]&6:&r %message%");
        s.put("PM.Format.Default.From", "&4[You&r&4] &6-> [&4%otherplayer_displayname_{receiver}%&r&6]:&r %message%");
        s.put("PM.Format.Default.To", "&4[%otherplayer_displayname_{sender}%&r&4] &6-> [&4You&r&6]:&r %message%");
        s.put("PM.Format.ExampleWorld.From", "This is a a format for 'ExampleWorld' for the sender");
        s.put("PM.Format.ExampleWorld.To", "This is a a format for 'ExampleWorld' for the receiver");
        
        return s;
    }
}
