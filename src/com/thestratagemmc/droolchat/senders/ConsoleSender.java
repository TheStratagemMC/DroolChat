package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class ConsoleSender implements ChatMessageSender {
    @Override
    public TextComponent getComponent() {
        TextComponent c = new TextComponent("CONSOLE");
        c.setColor(ChatColor.GRAY);
        c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Server's command console").create()));
        return c;
    }
}
