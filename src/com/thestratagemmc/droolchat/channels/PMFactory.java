package com.thestratagemmc.droolchat.channels;

import com.thestratagemmc.droolchat.elements.MessageElement;
import com.thestratagemmc.droolchat.elements.MsgDirectionalElement;
import com.thestratagemmc.droolchat.elements.SenderDetailedElement;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Axel on 5/22/2016.
 */
public class PMFactory {
    public static PrivateMessageChannel getChannel(UUID to, UUID from){
        PrivateMessageChannel channel = new PrivateMessageChannel(from, to);
        channel.setElementOrder(new ArrayList(Arrays.asList(MsgDirectionalElement.class, SenderDetailedElement.class, MessageElement.class)));
        channel.setMessageColor(ChatColor.LIGHT_PURPLE);
        channel.setColor(ChatColor.LIGHT_PURPLE);
        return channel;
    }

    public static BotPrivateMessageChannel getBotChannel(Player with){
        BotPrivateMessageChannel channel = new BotPrivateMessageChannel(with);
        channel.setElementOrder(new ArrayList(Arrays.asList(MsgDirectionalElement.class, SenderDetailedElement.class, MessageElement.class)));
        channel.setMessageColor(ChatColor.LIGHT_PURPLE);
        channel.setColor(ChatColor.LIGHT_PURPLE);
        return channel;
    }
}
