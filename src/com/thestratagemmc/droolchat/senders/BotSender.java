package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.bot.Bot;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class BotSender implements ChatMessageSender {
    Bot bot;

    public BotSender(Bot bot){
        this.bot = bot;
    }
    @Override
    public TextComponent getComponent() {
        return null;
    }
}
