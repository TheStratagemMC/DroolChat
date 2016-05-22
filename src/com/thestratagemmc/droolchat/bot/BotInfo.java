package com.thestratagemmc.droolchat.bot;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class BotInfo {
    public String name;
    public String description;
    public ChatColor color = ChatColor.RED;
    public String version;

    public BotInfo(String name, String description, String version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public BotInfo(String name, String description, ChatColor color, String version) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.version = version;
    }
}
