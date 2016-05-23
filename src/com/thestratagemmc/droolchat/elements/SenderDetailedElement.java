package com.thestratagemmc.droolchat.elements;

import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.Element;
import com.thestratagemmc.droolchat.ThemeUser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class SenderDetailedElement extends Element {

    ChatMessageSender sender;

    public SenderDetailedElement(ChatMessageSender sender){
        super();
        this.sender = sender;
    }

    @Override
    public TextComponent getComponent(ThemeUser user) { // no theming here, sorry
        return sender.getComponent();
    }

    @Override
    public void registerDefaultColors(HashMap<String, ChatColor> map) {
        //
    }
}
