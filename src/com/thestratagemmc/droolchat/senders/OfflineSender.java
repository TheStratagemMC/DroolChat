package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import javax.xml.soap.Text;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/22/2016.
 */
public class OfflineSender implements ChatMessageSender {

    private String service;
    private String name;
    private UUID id;

    public OfflineSender(String service, String name, UUID id) {
        this.service = service;
        this.name = name;
        this.id = id;
    }

    @Override
    public TextComponent getComponent() {
        TextComponent tc = new TextComponent(ChatColor.BLUE+name+" ");
        tc.addExtra(ChatColor.GRAY + "(OFFLINE)");

        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(name+", chatting from "+service+".").create()));
        return tc;
    }
}
