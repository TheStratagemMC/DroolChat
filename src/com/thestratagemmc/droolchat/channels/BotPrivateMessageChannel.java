package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.ChatMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class BotPrivateMessageChannel extends PrivateMessageChannel {
    private UUID player;

    public BotPrivateMessageChannel(Player player){
        this.player = player.getUniqueId();
    }

    public void sendMessage(ChatMessageSender sender, String message, Player origin){
        Player p = Bukkit.getPlayer(player);
        if (p == null) return;
        p.spigot().sendMessage(getMessageToSend(sender, message, p));
    }
}
