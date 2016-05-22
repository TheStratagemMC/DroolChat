package com.thestratagemmc.droolchat.bot;

import com.thestratagemmc.droolchat.senders.BotSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public abstract class Bot implements Listener {
    private BotSender sender;

    public BotSender getSender(){
        if (sender == null){
            sender = new BotSender(this);
        }
        return sender;
    }
    public abstract BotInfo getInfo();
    public abstract String respondToPM(final Player sender, String message);
}
