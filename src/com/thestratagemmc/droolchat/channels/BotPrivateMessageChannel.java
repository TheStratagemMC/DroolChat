package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.Element;
import com.thestratagemmc.droolchat.ThemeUser;
import com.thestratagemmc.droolchat.ThemeUserDb;
import com.thestratagemmc.droolchat.bot.Bot;
import com.thestratagemmc.droolchat.elements.MsgDirectionalElement;
import com.thestratagemmc.droolchat.senders.PlayerSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
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

    public BaseComponent getMessageToSend(ChatMessageSender sender, String message, Player player){
        TextComponent output = new TextComponent("");
        ThemeUser user = ThemeUserDb.getThemeUser(player);
        output.addExtra(instantiate(elementOrder.get(0), sender, message, player).getComponent(user));
        for (int i = 1; i < elementOrder.size(); i++){
            output.addExtra(" ");
            Element el = instantiate(elementOrder.get(i), sender, message, player);
            if (el instanceof MsgDirectionalElement){
                Bukkit.broadcastMessage("penis");
                TextComponent tc = el.getComponent(user);
                if (sender instanceof PlayerSender) tc.setText("To");
                else tc.setText("From");
                output.addExtra(tc);
            }
            // Bukkit.broadcastMessage(el.getClass().getName());
            else output.addExtra(el.getComponent(user));
        }
        return output;
    }

    public void sendMessage(ChatMessageSender sender, String message, Player origin, Bot bot){
        Player p = Bukkit.getPlayer(player);
        if (p == null) return;
        p.spigot().sendMessage(this.getMessageToSend((sender instanceof PlayerSender) ? bot.getSender() : sender, message, origin));
    }
}
