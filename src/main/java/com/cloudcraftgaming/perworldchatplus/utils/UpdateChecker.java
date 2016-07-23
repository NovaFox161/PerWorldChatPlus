package com.cloudcraftgaming.perworldchatplus.utils;

import com.cloudcraftgaming.perworldchatplus.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by: NovaFox161
 * Website: www.cloudcraftgaming.com
 * For Project: PerWorldChatPlus
 */
public class UpdateChecker {
    private Main plugin;
    private URL filesFeed;
    private String version;
    private String link;

    /**
     * Constructor for UpdateChecker. This creates a new update checker object that will check for updates.
     *
     * @param plugin Reference to the plugin
     * @param url    Where to check for updates (Currently only supports dev bukkit rss feeds).
     */
    public UpdateChecker(Main plugin, String url) {
        this.plugin = plugin;
        try {
            this.filesFeed = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if there is a new update for PerWorldChatPLus
     *
     * @return Returns true if a new update is found, else false.
     */
    public boolean UpdateNeeded() {
        try {
            InputStream input = this.filesFeed.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            Node latestFile = document.getElementsByTagName("item").item(0);
            NodeList children = latestFile.getChildNodes();
            this.version = children.item(1).getTextContent().replaceAll("[a-zA-z ]", "");
            this.link = children.item(3).getTextContent();
            if (!this.plugin.getDescription().getVersion().equals(this.version)) {
                return true;
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Update URL is invalid! Report this to Shades161 on her Dev Bukkit Page!");
        }
        return false;
    }

    /**
     * Gets the version of the newest update.
     *
     * @return The version of the newest update.
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Gets the link to where the new update can be downloaded.
     *
     * @return The link to download the new update.
     */
    public String getLink() {
        return this.link;
    }
}