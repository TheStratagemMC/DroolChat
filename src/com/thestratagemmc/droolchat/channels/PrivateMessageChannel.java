package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.Channel;
import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.Element;
import com.thestratagemmc.droolchat.elements.MsgDirectionalElement;
import com.thestratagemmc.droolchat.senders.PlayerSender;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class PrivateMessageChannel extends Channel {

    protected UUID from= null;
    protected UUID to = null;

    public PrivateMessageChannel(){}

    public PrivateMessageChannel(UUID from, UUID to){
        this.from = from;
        this.to = to;
    }

    public Element instantiate(Class<? extends Element> c, ChatMessageSender sender, String message, Player player){
        if (c.equals(MsgDirectionalElement.class)){
            if (player.getUniqueId().equals(from)) return new MsgDirectionalElement(this, "To");
            return new MsgDirectionalElement(this, "From");
        }
        return super.instantiate(c, sender, message, player);
    }

    public boolean hasBots(){
        return false;
    }

    public boolean isModerated(){
        return false;
    }

    public void sendMessage(ChatMessageSender sender, String message, Player origin){
        //going to have to instantiate the message twice, one for each player
        Player f = Bukkit.getPlayer(from);
        Player t = Bukkit.getPlayer(to);

        if (f != null) f.spigot().sendMessage(getMessageToSend(new PlayerSender(t), message, f));

        if (t == null) {
            if (f != null) f.sendMessage(ChatColor.YELLOW +"Player is not found.");
        }
        else {
            t.spigot().sendMessage(getMessageToSend(sender, message, t));
        }
    }
}
