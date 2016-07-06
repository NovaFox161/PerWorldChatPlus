package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova Fox on 7/5/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class ChatColorInventory {
    private static Inventory chatColorInv;

    //Functionals
    /**
     * Creates the ChatColor Inventory GUI. To be used on startup if enabled.
     */
    public static void createChatColorInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GREEN + "ChatColor");

        ItemStack reset = new ItemStack(Material.GLASS, 1);
        ItemMeta resetMeta = reset.getItemMeta();
        resetMeta.setDisplayName("RESET");
        List<String> resetLore = new ArrayList<>();
        resetLore.clear();
        resetLore.add(ChatColor.RESET + "Example");
        resetMeta.setLore(resetLore);
        reset.setItemMeta(resetMeta);
        inv.setItem(0, reset);

        ItemStack black = new ItemStack(Material.WOOL, 1, Short.valueOf("15"));
        ItemMeta blackMeta = black.getItemMeta();
        blackMeta.setDisplayName("BLACK");
        List<String> blackLore = new ArrayList<>();
        blackLore.clear();
        blackLore.add(ChatColor.BLACK + "Example");
        blackMeta.setLore(blackLore);
        black.setItemMeta(blackMeta);
        inv.setItem(1, black);

        ItemStack darkBlue = new ItemStack(Material.WOOL, 1, Short.valueOf("11"));
        ItemMeta darkBlueMeta = darkBlue.getItemMeta();
        darkBlueMeta.setDisplayName("DARK_BLUE");
        List<String> darkBlueLore = new ArrayList<>();
        darkBlueLore.clear();
        darkBlueLore.add(ChatColor.DARK_BLUE + "Example");
        darkBlueMeta.setLore(darkBlueLore);
        darkBlue.setItemMeta(darkBlueMeta);
        inv.setItem(2, darkBlue);

        ItemStack darkGreen = new ItemStack(Material.WOOL, 1, Short.valueOf("13"));
        ItemMeta darkGreenMeta = darkGreen.getItemMeta();
        darkGreenMeta.setDisplayName("DARK_GREEN");
        List<String> darkGreenLore = new ArrayList<>();
        darkGreenLore.clear();
        darkGreenLore.add(ChatColor.DARK_GREEN + "Example");
        darkGreenMeta.setLore(darkGreenLore);
        darkGreen.setItemMeta(darkGreenMeta);
        inv.setItem(3, darkGreen);

        ItemStack darkAqua = new ItemStack(Material.WOOL, 1, Short.valueOf("9"));
        ItemMeta darkAquaMeta = darkAqua.getItemMeta();
        darkAquaMeta.setDisplayName("DARK_AQUA");
        List<String> darkAquaLore = new ArrayList<>();
        darkAquaLore.clear();
        darkAquaLore.add(ChatColor.DARK_AQUA + "Example");
        darkAquaMeta.setLore(darkAquaLore);
        darkAqua.setItemMeta(darkAquaMeta);
        inv.setItem(4, darkAqua);

        ItemStack darkRed = new ItemStack(Material.WOOL, 1, Short.valueOf("14"));
        ItemMeta darkRedMeta = darkRed.getItemMeta();
        darkRedMeta.setDisplayName("DARK_RED");
        List<String> darkRedLore = new ArrayList<>();
        darkRedLore.clear();
        darkRedLore.add(ChatColor.DARK_RED + "Example");
        darkRedMeta.setLore(darkRedLore);
        darkRed.setItemMeta(darkRedMeta);
        inv.setItem(5, darkRed);

        ItemStack darkPurple = new ItemStack(Material.WOOL, 1, Short.valueOf("10"));
        ItemMeta darkPurpleMeta = darkPurple.getItemMeta();
        darkPurpleMeta.setDisplayName("DARK_PURPLE");
        List<String> darkPurpleLore = new ArrayList<>();
        darkPurpleLore.clear();
        darkPurpleLore.add(ChatColor.DARK_PURPLE + "Example");
        darkPurpleMeta.setLore(darkPurpleLore);
        darkPurple.setItemMeta(darkPurpleMeta);
        inv.setItem(6, darkPurple);

        ItemStack gold = new ItemStack(Material.WOOL, 1, Short.valueOf("1"));
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("GOLD");
        List<String> goldLore = new ArrayList<>();
        goldLore.clear();
        goldLore.add(ChatColor.GOLD + "Example");
        goldMeta.setLore(goldLore);
        gold.setItemMeta(goldMeta);
        inv.setItem(7, gold);

        ItemStack gray = new ItemStack(Material.WOOL, 1, Short.valueOf("8"));
        ItemMeta grayMeta = gray.getItemMeta();
        grayMeta.setDisplayName("GRAY");
        List<String> grayLore = new ArrayList<>();
        grayLore.clear();
        grayLore.add(ChatColor.GRAY + "Example");
        grayMeta.setLore(grayLore);
        gray.setItemMeta(grayMeta);
        inv.setItem(8, gray);

        ItemStack darkGray = new ItemStack(Material.WOOL, 1, Short.valueOf("7"));
        ItemMeta darkGrayMeta = darkGray.getItemMeta();
        darkGrayMeta.setDisplayName("DARK_GRAY");
        List<String> darkGrayLore = new ArrayList<>();
        darkGrayLore.clear();
        darkGrayLore.add(ChatColor.DARK_GRAY + "Example");
        darkGrayMeta.setLore(darkGrayLore);
        darkGray.setItemMeta(darkGrayMeta);
        inv.setItem(9, darkGray);

        ItemStack blue = new ItemStack(Material.WOOL, 1, Short.valueOf("3"));
        ItemMeta blueMeta = blue.getItemMeta();
        blueMeta.setDisplayName("BLUE");
        List<String> blueLore = new ArrayList<>();
        blueLore.clear();
        blueLore.add(ChatColor.BLUE + "Example");
        blueMeta.setLore(blueLore);
        blue.setItemMeta(blueMeta);
        inv.setItem(10, blue);

        ItemStack green = new ItemStack(Material.WOOL, 1, Short.valueOf("5"));
        ItemMeta greenMeta = green.getItemMeta();
        greenMeta.setDisplayName("GREEN");
        List<String> greenLore = new ArrayList<>();
        greenLore.clear();
        greenLore.add(ChatColor.GREEN + "Example");
        greenMeta.setLore(greenLore);
        green.setItemMeta(greenMeta);
        inv.setItem(11, green);

        ItemStack aqua = new ItemStack (Material.STAINED_GLASS, 1, Short.valueOf("3"));
        ItemMeta aquaMeta = aqua.getItemMeta();
        aquaMeta.setDisplayName("AQUA");
        List<String> aquaLore = new ArrayList<>();
        aquaLore.clear();
        aquaLore.add(ChatColor.AQUA + "Example");
        aquaMeta.setLore(aquaLore);
        aqua.setItemMeta(aquaMeta);
        inv.setItem(12, aqua);

        ItemStack red = new ItemStack(Material.WOOL, 1, Short.valueOf("6"));
        ItemMeta redMeta = red.getItemMeta();
        redMeta.setDisplayName("RED");
        List<String> redLore = new ArrayList<>();
        redLore.clear();
        redLore.add(ChatColor.RED + "Example");
        redMeta.setLore(redLore);
        red.setItemMeta(redMeta);
        inv.setItem(13, red);

        ItemStack lightPurple = new ItemStack(Material.WOOL, 1, Short.valueOf("2"));
        ItemMeta lightPurpleMeta = lightPurple.getItemMeta();
        lightPurpleMeta.setDisplayName("LIGHT_PURPLE");
        List<String> lightPurpleLore = new ArrayList<>();
        lightPurpleLore.clear();
        lightPurpleLore.add(ChatColor.LIGHT_PURPLE + "Example");
        lightPurpleMeta.setLore(lightPurpleLore);
        lightPurple.setItemMeta(lightPurpleMeta);
        inv.setItem(14, lightPurple);

        ItemStack yellow = new ItemStack(Material.WOOL, 1, Short.valueOf("4"));
        ItemMeta yellowMeta = yellow.getItemMeta();
        yellowMeta.setDisplayName("YELLOW");
        List<String> yellowLore = new ArrayList<>();
        yellowLore.clear();
        yellowLore.add(ChatColor.YELLOW + "Example");
        yellowMeta.setLore(yellowLore);
        yellow.setItemMeta(yellowMeta);
        inv.setItem(15, yellow);

        ItemStack white = new ItemStack(Material.WOOL, 1);
        ItemMeta whiteMeta = white.getItemMeta();
        whiteMeta.setDisplayName("WHITE");
        List<String> whiteLore = new ArrayList<>();
        whiteLore.clear();
        whiteLore.add(ChatColor.WHITE + "Example");
        whiteMeta.setLore(whiteLore);
        white.setItemMeta(whiteMeta);
        inv.setItem(16, white);

        ItemStack magic = new ItemStack(Material.BEDROCK);
        ItemMeta magicMeta = magic.getItemMeta();
        magicMeta.setDisplayName("MAGIC");
        List<String> magicLore = new ArrayList<>();
        magicLore.clear();
        magicLore.add(ChatColor.MAGIC + "Example");
        magicMeta.setLore(magicLore);
        magic.setItemMeta(magicMeta);
        inv.setItem(17, magic);

        ItemStack bold = new ItemStack(Material.OBSIDIAN, 1);
        ItemMeta boldMeta = bold.getItemMeta();
        boldMeta.setDisplayName("BOLD");
        List<String> boldLore = new ArrayList<>();
        boldLore.clear();
        boldLore.add(ChatColor.BOLD + "Example");
        boldMeta.setLore(boldLore);
        bold.setItemMeta(boldMeta);
        inv.setItem(18, bold);

        ItemStack strikethrough = new ItemStack(Material.REDSTONE, 1);
        ItemMeta strikethroughMeta = strikethrough.getItemMeta();
        strikethroughMeta.setDisplayName("STRIKETHROUGH");
        List<String> strikethroughLore = new ArrayList<>();
        strikethroughLore.clear();
        strikethroughLore.add(ChatColor.STRIKETHROUGH + "Example");
        strikethroughMeta.setLore(strikethroughLore);
        strikethrough.setItemMeta(strikethroughMeta);
        inv.setItem(19, strikethrough);

        ItemStack underline = new ItemStack(Material.STONE_SLAB2, 1);
        ItemMeta underlineMeta = underline.getItemMeta();
        underlineMeta.setDisplayName("UNDERLINE");
        List<String> underlineLore = new ArrayList<>();
        underlineLore.clear();
        underlineLore.add(ChatColor.UNDERLINE + "Example");
        underlineMeta.setLore(underlineLore);
        underline.setItemMeta(underlineMeta);
        inv.setItem(20, underline);

        ItemStack italic = new ItemStack(Material.ARROW, 1);
        ItemMeta italicMeta = italic.getItemMeta();
        italicMeta.setDisplayName("ITALIC");
        List<String> italicLore = new ArrayList<>();
        italicLore.clear();
        italicLore.add(ChatColor.ITALIC + "Example");
        italicMeta.setLore(italicLore);
        italic.setItemMeta(italicMeta);
        inv.setItem(21, italic);

        setChatColorInventory(inv);
    }
    /**
     * Sets the player's default chat color based on the clicked item.
     * @param player The player to set color for.
     * @param clickedStack The ItemStack they clicked.
     */
    public static void setChatColor(Player player, ItemStack clickedStack) {
        try {
            String colorNameOr = clickedStack.getItemMeta().getDisplayName();
            ChatColor color = ChatColor.valueOf(colorNameOr);
            PlayerDataManager.setChatColor(player, color);
            String msgOr = MessageManager.getMessageYml().getString("Command.Set.Color.Self");
            String msg = msgOr.replace("%color%", color + color.name());
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        } catch (IllegalArgumentException e) {
            String msg = MessageManager.getMessageYml().getString("Notification.Color.Invalid");
            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }

    //Booleans/Checkers
    /**
     * Checks if the specified inventory is the ChatColor GUI.
     * @param invToCheck The inventory to check.
     * @return True if they are the same, else false.
     */
    public static Boolean isChatColorInventory(Inventory invToCheck){
        return invToCheck.getName().equals(chatColorInv.getName());
    }

    //Getters
    /**
     * Gets the Inventory used for setting chat color via a GUI
     * @return The chatColor Inventory.
     */
    public static Inventory getChatColorInventory() {
        return chatColorInv;
    }

    //Setters
    /**
     * Sets the ChatColor GUI.
     * @param _chatColorInv The inventory to be the chat color inventory GUI.
     */
    private static void setChatColorInventory(Inventory _chatColorInv) {
        chatColorInv = _chatColorInv;
    }
}