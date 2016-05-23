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
public class MessageElement extends Element {
    Channel channel;
    String message;

    public MessageElement(Channel channel, String message) {
        super();
        this.channel = channel;
        this.message = message;
    }

    @Override
    public TextComponent getComponent(ThemeUser user) {
        ChatColor color = getColor(user, channel.getName()+"_color");
        if (color == null) color = channel.getMessageColor();

        TextComponent component = new TextComponent(message);
        component.setColor(color);
        return component;
    }

    @Override
    public void registerDefaultColors(HashMap<String, ChatColor> map) {
        //nope nothing here
    }
}
