package com.thestratagemmc.droolchat.elements;

import com.thestratagemmc.droolchat.Channel;
import com.thestratagemmc.droolchat.Element;
import com.thestratagemmc.droolchat.ThemeUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */
public class ChannelNameElement extends Element {
    Channel channel;
    public ChannelNameElement(Channel channel){
        super();
        this.channel = channel;
    }

    public String getName(){
        return channel.getName();
    }
    @Override
    public TextComponent getComponent(ThemeUser user) {
        ChatColor brackets = getColor(user, channel.getName()+"_brackets");
        if (brackets == null) brackets = channel.getColor();

        ChatColor channelColor = getColor(user, channel.getName()+"_color");
        if (channelColor == null) channelColor = channel.getColor();
        TextComponent compo = new TextComponent(brackets+"["+channelColor+channel.getName()+brackets+"]");
       // compo.setColor(channel.getColor()); //don't have a choice with this one, unfortunately

        ComponentBuilder b = new ComponentBuilder(getColor(user, "hover_primary")+ChatColor.BOLD.toString() +"Channel: "+channel.getColor()+channel.getName());
        b.append(ChatColor.RESET+"\n");
        //b.append(getColor(user, "hover_secondary")+"Players: "+ getColor(user,"hover_bg")+channel.getSize()+"");
        b.append(ChatColor.RESET+"\n");
        b.append(getColor(user, "hover_secondary")+"Is moderated: "+getColor(user,"hover_bg")+channel.isModerated());
        b.append(ChatColor.RESET+"\n");
        b.append(getColor(user, "hover_secondary") +"Description: "+getColor(user, "hover_bg")+channel.getDescription());
        b.append(ChatColor.RESET+"\n");
        b.append(getColor(user, "hover_secondary") +"Bots enabled: "+getColor(user, "hover_bg")+channel.hasBots());
        compo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, b.create()));

        return compo;
    }

    @Override
    public void registerDefaultColors(HashMap<String, ChatColor> map) {
       // map.put("primary", channel.getColor());
       // map.put("brackets", ChatColor.BLUE);
        map.put("hover_primary", ChatColor.GOLD);
        map.put("hover_secondary", ChatColor.DARK_AQUA);
        map.put("hover_bg", ChatColor.getByChar('7'));
    }
}
