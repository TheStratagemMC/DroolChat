package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.Channel;
import com.thestratagemmc.droolchat.ChatMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


/**
 * Created by 18AxMoreen on 5/20/2016.
 */
public class PermissionsChannel extends Channel {
    private String speakPermission = "droolchat.channels.default";
    private String listenPermission = "droolchat.channels.default";

    public PermissionsChannel(){
        speakPermission = "droolchat.channels.default";
        listenPermission = "droolchat.channels.default";
    }
    public Channel load(ConfigurationSection section){
        speakPermission = section.getString("speakPermission");
        listenPermission = section.getString("listenPermission");

        return super.load(section);
    }

    public void sendMessage(ChatMessageSender sender, String message, Player origin){
        if (origin != null){
            if (!origin.hasPermission(speakPermission)) {
                origin.sendMessage(ChatColor.YELLOW + "No permissions to speak in "+this.getName());
                return;
            }
        }
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.hasPermission(listenPermission)){
                player.spigot().sendMessage(getMessageToSend(sender, message, player));
            }
        }
    }

    public boolean canSpeak(Player player){
        if (speakPermission == null) return true;
        return player.hasPermission(speakPermission);
    }

    public boolean canListen(Player player){

        if (listenPermission == null) return true;
        return player.hasPermission(listenPermission);
    }
}
