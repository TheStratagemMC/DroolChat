package com.thestratagemmc.droolchat;

import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */

//to be overriden by players and bots
public interface ChatMessageSender {
    public TextComponent getComponent();
}
