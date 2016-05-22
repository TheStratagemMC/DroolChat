package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class UnknownSender implements ChatMessageSender{
    @Override
    public TextComponent getComponent() {
        return new TextComponent("Unknown");
    }
}
