package com.thestratagemmc.droolchat.bot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class BotStore {

    HashMap<String,Bot> botMap = new HashMap<>();

    public Bot getBot(String name){
        if (botMap.containsKey(name)) return botMap.get(name);
        return null;
    }
    public void registerBot(Plugin plugin, String name, Bot bot){
        botMap.put(name, bot);
        Bukkit.getPluginManager().registerEvents(bot, plugin);
    }
    public boolean isBot(String name){
        return botMap.containsKey(name);
    }

}
