package com.thestratagemmc.droolchat;

import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/20/2016.
 */
public class ChannelStore {
    private HashMap<String,Channel> channelMap = new HashMap<>();
    private HashMap<String,String> shortcutMap = new HashMap<>();

    public void registerChannel(Channel channel){
        channelMap.put(channel.getName(), channel);
        shortcutMap.put(channel.getName().toLowerCase(), channel.getName());
        shortcutMap.put(channel.getNick().toLowerCase(),channel.getName());
    }

    public Channel getChannel(String input){

        for (String key : shortcutMap.keySet()){
           // Bukkit.broadcastMessage(key);
            if (input.equalsIgnoreCase(key)){
                Channel c = channelMap.get(shortcutMap.get(key));
                //Bukkit.broadcastMessage(""+(c== null));
                return c;
            }
        }
        return null;
    }


    public Collection<Channel> getChannels(){ return channelMap.values(); }



}
