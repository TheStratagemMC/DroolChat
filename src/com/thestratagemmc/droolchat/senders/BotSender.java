package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.bot.Bot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
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
        TextComponent tc = new TextComponent(bot.getInfo().color + bot.getInfo().name);
        ComponentBuilder bc = new ComponentBuilder("Bot: "+bot.getInfo().name +"\n");
        bc.append(ChatColor.AQUA+"Description: "+bot.getInfo().description +ChatColor.RESET+"\n");
        bc.append(ChatColor.AQUA +"Version: "+bot.getInfo().version+ChatColor.RESET+"\n");
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, bc.create()));
        return tc;
    }
}
