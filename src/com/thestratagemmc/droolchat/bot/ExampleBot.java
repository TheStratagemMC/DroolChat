package com.thestratagemmc.droolchat.bot;

import com.thestratagemmc.droolchat.event.ChannelChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

/**
 * Created by 18AxMoreen on 5/22/2016.
 */
public class ExampleBot extends Bot {
    BotInfo info;
    @Override
    public BotInfo getInfo() {
        if (info == null) info = new BotInfo("ExampleBot", "I am an example", "0.0");
        return info;
    }

    @EventHandler
    public void chat(ChannelChatEvent event){
        if (event.getMessage().toLowerCase().contains("hi example bot")){
            event.respond(this, "Hi, "+event.getSender().getComponent().getText());
        }
    }

    @Override
    public String respondToPM(Player sender, String message) {
        return "Hi!";
    }
}
