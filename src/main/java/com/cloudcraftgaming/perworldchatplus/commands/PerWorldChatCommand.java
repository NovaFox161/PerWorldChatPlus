package com.cloudcraftgaming.perworldchatplus.commands;

import com.cloudcraftgaming.perworldchatplus.chat.TimedGlobalChatManager;
import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import com.cloudcraftgaming.perworldchatplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.cloudcraftgaming.perworldchatplus.Main.plugin;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 *
 * Just the base command. Nothing else to see here.
 */
public class PerWorldChatCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("pwc") || command.getName().equalsIgnoreCase("pwcp")
				|| command.getName().equalsIgnoreCase("PerWorldChat") || command.getName().equalsIgnoreCase("PerWorldChatPlus")) {
			String pr = MessageManager.getPrefix();
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length < 1) {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
					player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
				} else if (args.length == 1) {
					String type = args[0];
					if (type.equalsIgnoreCase("spy")) {
						if (!(player.hasPermission("pwcp.spy"))) {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						} else {
							if (PlayerDataManager.hasGlobalChatSpyEnabled(player)) {
								PlayerDataManager.setChatSpy(player, false);
								String msg = MessageManager.getMessageYml().getString("Command.Spy.Disabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							} else {
								PlayerDataManager.setChatSpy(player, true);
								String msg = MessageManager.getMessageYml().getString("Command.Spy.Enabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						}
					} else if (type.equalsIgnoreCase("bypass")) {
						if (!(player.hasPermission("pwcp.bypass"))) {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						} else {
							if (PlayerDataManager.hasGlobalBypassEnabled(player)) {
								PlayerDataManager.setGlobalBypass(player, false);
								String msg = MessageManager.getMessageYml().getString("Command.Bypass.Disabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							} else {
								PlayerDataManager.setGlobalBypass(player, true);
								String msg = MessageManager.getMessageYml().getString("Command.Bypass.Enabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						}
					} else if (type.equalsIgnoreCase("alert")) {
						if (!(player.hasPermission("pwcp.alert"))) {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						} else {
							String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
							player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
						}
					} else if (type.equalsIgnoreCase("timedGlobal")) {
						if (player.hasPermission("pwcp.timedglobal")) {
							if (plugin.getConfig().getString("Global.TimedGlobal.Allow").equalsIgnoreCase("True")) {
								if (plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")) {
									TimedGlobalChatManager.getManager().turnOffTimedGlobal(player);
								} else {
									String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.AlreadyOff");
									player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
								}
							} else {
								String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.Disabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("help")) {
						HelpCommand.helpCommand(player, "1");
					} else if (type.equalsIgnoreCase("worldSpy")) {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
						player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
					} else if (type.equalsIgnoreCase("mute")) {
						if (player.hasPermission("pwcp.mute")) {
							if (PlayerDataManager.hasChatMuted(player)) {
								PlayerDataManager.setChatMute(player, false);
								String msg = MessageManager.getMessageYml().getString("Command.Mute.Disable");
								player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
							} else {
								PlayerDataManager.setChatMute(player, true);
								String msg = MessageManager.getMessageYml().getString("Command.Mute.Enable");
								player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							player.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 2) {
					String type = args[0];
					if (type.equalsIgnoreCase("alert")) {
						if (!(player.hasPermission("pwcp.alert"))) {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						} else {
							String word = args[1];
							if (PlayerDataManager.hasAlertWord(player, word)) {
								PlayerDataManager.removeAlertWord(player, word);
								String msgOr = MessageManager.getMessageYml().getString("Command.Alert.Removed");
								String msg = msgOr.replaceAll("%word%", word);
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							} else {
								PlayerDataManager.addAlertWord(player, word);
								String msgOr = MessageManager.getMessageYml().getString("Command.Alert.Added");
								String msg = msgOr.replaceAll("%word%", word);
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						}
					} else if (type.equalsIgnoreCase("worldSpy")) {
						if (player.hasPermission("pwcp.worldspy")) {
							WorldSpy.worldSpy(player, args[1]);
						} else {
							player.sendMessage(MessageManager.getPrefix() + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("timedGlobal")) {
						if (player.hasPermission("pwcp.timedglobal")) {
							if (plugin.getConfig().getString("Global.TimedGlobal.Allow").equalsIgnoreCase("True")) {
								if (plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("False")) {
									String timeString = args[1];
									try {
										Integer time = Integer.valueOf(timeString);
										TimedGlobalChatManager.getManager().turnOnTimedGlobal(player, time);

									} catch (NumberFormatException e) {
										String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.TimeNotNumber");
										player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
									}
								} else {
									String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.AlreadyOn");
									player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
								}
							} else {
								String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.Disabled");
								player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							player.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("help")) {
						HelpCommand.helpCommand(sender, args[1]);
					} else if (type.equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length  > 4) {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
					player.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
				}
			} else {
				if (args.length < 1) {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.TooFew");
					sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
				} else if (args.length == 1) {
					String type = args[0];
					if (type.equalsIgnoreCase("Bypass")) {
						if (sender.hasPermission("pwcp.bypass")) {
							sender.sendMessage(pr + MessageManager.getPlayerOnlyMessage());
						} else {
							sender.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("worldSpy")) {
						sender.sendMessage(pr + MessageManager.getPlayerOnlyMessage());
					} else if (type.equalsIgnoreCase("Spy")) {
						if (sender.hasPermission("pwcp.spy")) {
							sender.sendMessage(pr + MessageManager.getPlayerOnlyMessage());
						} else {
							sender.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("Alert")) {
						if (sender.hasPermission("pwcp.alert")) {
							sender.sendMessage(pr + MessageManager.getPlayerOnlyMessage());
						} else {
							sender.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("timedGlobal")) {
						if (sender.hasPermission("pwcp.timedglobal")) {
							if (plugin.getConfig().getString("Global.TimedGlobal.Allow").equalsIgnoreCase("True")) {
								if (plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("True")) {
									TimedGlobalChatManager.getManager().turnOffTimedGlobal(sender);
								} else {
									String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.AlreadyOff");
									sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
								}
							} else {
								String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.Disabled");
								sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							sender.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("help")) {
						HelpCommand.helpCommand(sender, "1");
					} else if (type.equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 2) {
					String type = args[0];
					if (type.equalsIgnoreCase("timedGlobal")) {
						if (sender.hasPermission("pwcp.timedglobal")) {
							if (plugin.getConfig().getString("Global.TimedGlobal.Allow").equalsIgnoreCase("True")) {
								if (plugin.getConfig().getString("Global.TimedGlobal.On").equalsIgnoreCase("False")) {
									String timeString = args[1];
									try {
										Integer time = Integer.valueOf(timeString);
										TimedGlobalChatManager.getManager().turnOnTimedGlobal(sender, time);

									} catch (NumberFormatException e) {
										String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.TimeNotNumber");
										sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
									}
								} else {
									String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.AlreadyOn");
									sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
								}
							} else {
								String msg = MessageManager.getMessageYml().getString("Command.TimedGlobal.Disabled");
								sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
							}
						} else {
							sender.sendMessage(pr + MessageManager.getNoPermMessage());
						}
					} else if (type.equalsIgnoreCase("help")) {
						HelpCommand.helpCommand(sender, args[1]);
					} else if (type.equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 3) {
					if (args[0].equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else if (args.length == 4) {
					if (args[0].equalsIgnoreCase("set")) {
						SetCommand.setCommand(sender, args);
					} else {
						String msg = MessageManager.getMessageYml().getString("Notification.Args.Invalid");
						sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
					}
				} else {
					String msg = MessageManager.getMessageYml().getString("Notification.Args.TooMany");
					sender.sendMessage(pr + ChatColor.translateAlternateColorCodes('&', msg));
				}
			}
		}
		return false;
	}
}