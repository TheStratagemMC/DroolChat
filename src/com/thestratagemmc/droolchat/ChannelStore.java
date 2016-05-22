package com.thestratagemmc.droolchat;

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
        shortcutMap.put(channel.getName(), channel.getName());
        shortcutMap.put(channel.getNick(),channel.getName());
    }

    public Channel getChannel(String input){
        String lc = input.toLowerCase();
        if (shortcutMap.containsKey(lc)) return channelMap.get(shortcutMap.get(lc));
        if (channelMap.containsKey(lc)) return channelMap.get(lc);
        return null;
    }


    public Collection<Channel> getChannels(){ return channelMap.values(); }



}
