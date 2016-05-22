package com.thestratagemmc.droolchat.elements;

import com.thestratagemmc.droolchat.Channel;
import com.thestratagemmc.droolchat.Element;
import com.thestratagemmc.droolchat.ThemeUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class MsgDirectionalElement extends Element {

    Channel channel;
    String word;
    public MsgDirectionalElement(Channel channel, String directionalWord){
        this.channel = channel;
        this.word = directionalWord;
    }

    @Override
    public TextComponent getComponent(ThemeUser user) {
        ChatColor color = getColor(user, channel.getName()+"_color");
        if (color == null) color = channel.getMessageColor();

        TextComponent component = new TextComponent(word);
        component.setColor(color);
        return component;
    }

    @Override
    public void registerDefaultColors(HashMap<String, ChatColor> map) {

    }
}
