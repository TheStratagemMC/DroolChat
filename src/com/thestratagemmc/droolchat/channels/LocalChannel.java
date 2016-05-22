package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.ChatMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class LocalChannel extends PermissionsChannel {
    private int distance = 250;

    @Override
    public void sendMessage(ChatMessageSender sender, String message, Player origin) {
        if (origin == null) return;
        for (Player player : Bukkit.getOnlinePlayers()){
            if (canListen(player) && origin.getLocation().distance(player.getLocation()) < distance){
                player.spigot().sendMessage(getMessageToSend(sender, message, player));
            }
        }
       // super.sendMessage(sender, message, origin);
    }
}
