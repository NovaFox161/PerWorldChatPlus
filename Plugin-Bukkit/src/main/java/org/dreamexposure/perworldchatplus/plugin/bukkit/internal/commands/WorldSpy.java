package org.dreamexposure.perworldchatplus.plugin.bukkit.internal.commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.dreamexposure.perworldchatplus.api.data.PlayerDataManager;
import org.dreamexposure.perworldchatplus.api.utils.MessageManager;

import java.util.List;

/**
 * Created by Nova Fox on 2/1/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 * <p>
 * This class handles worldSpy related functions because the base command class would be too messy with it there.
 * This is really only for that command and probably shouldn't be used for anything else.
 */
@SuppressWarnings("ConstantConditions")
class WorldSpy {
	@SuppressWarnings("Duplicates")
	static void worldSpy(Player player, String type) {
		if (type.equalsIgnoreCase("on")) {
			if (PlayerDataManager.getPlayerDataYml(player).getString("WorldSpy").equalsIgnoreCase("False")) {
				YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
				data.set("WorldSpy", true);
				PlayerDataManager.savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
				String msgOr = MessageManager.getMessages().get().getString("Command.WorldSpy.TurnOn");
				String worldListOr = "";
				if (PlayerDataManager.getPlayerDataYml(player).contains("WorldSpies")) {
					worldListOr = PlayerDataManager.getPlayerDataYml(player).getString("WorldSpies");
				}
				String msg = msgOr.replaceAll("%worlds%", worldListOr.replace("[", "").replace("]", ""));
				player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			} else {
				String msg = MessageManager.getMessages().get().getString("Command.WorldSpy.AlreadyOn");
				player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			}
		} else if (type.equalsIgnoreCase("off")) {
			if (PlayerDataManager.getPlayerDataYml(player).getString("WorldSpy").equalsIgnoreCase("True")) {
				YamlConfiguration data = PlayerDataManager.getPlayerDataYml(player);
				data.set("WorldSpy", false);
				PlayerDataManager.savePlayerData(data, PlayerDataManager.getPlayerDataFile(player));
				String msg = MessageManager.getMessages().get().getString("Command.WorldSpy.TurnOff");
				player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			} else {
				String msg = MessageManager.getMessages().get().getString("Command.WorldSpy.AlreadyOff");
				player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			}
		} else {
			YamlConfiguration playerData = PlayerDataManager.getPlayerDataYml(player);
			List<String> list = playerData.getStringList("WorldSpies");
			if (player.hasPermission("pwcp.worldspy." + type)) {
				if (list.contains(type.toLowerCase())) {
					list.remove(type.toLowerCase());
					playerData.set("WorldSpies", list);
					PlayerDataManager.savePlayerData(playerData, PlayerDataManager.getPlayerDataFile(player));
					String msgOr = MessageManager.getMessages().get().getString("Command.WorldSpy.RemoveWorld");
					String msg = msgOr.replaceAll("%world%", type);
					player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
				} else {
					list.add(type.toLowerCase());
					playerData.set("WorldSpies", list);
					PlayerDataManager.savePlayerData(playerData, PlayerDataManager.getPlayerDataFile(player));
					String msgOr = MessageManager.getMessages().get().getString("Command.WorldSpy.AddWorld");
					String msg = msgOr.replaceAll("%world%", type);
					player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
				}
			} else {
				String msg = MessageManager.getMessages().get().getString("Command.WorldSpy.NoPerm");
				player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
			}
		}
	}
}