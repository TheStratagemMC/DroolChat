package com.thestratagemmc.droolchat;

import com.thestratagemmc.droolchat.event.ChannelChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Created by 18AxMoreen on 5/21/2016.
 *
 * Just seems cleaner to me.
 */
public class ChatBus  implements Listener {

    public static void sendChatMessage(final Channel channel, final ChatMessageSender sender, final String message, final Player origin){
        Bukkit.getScheduler().runTaskAsynchronously(DroolChat.getInstance(), new Runnable() {
            public void run() {
                Bukkit.getPluginManager().callEvent(new ChannelChatEvent(true, channel, message, sender, origin));
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void channel(final ChannelChatEvent event){
        if (!event.isCancelled()){
            Bukkit.getScheduler().runTask(DroolChat.getInstance(), new Runnable() {
                @Override
                public void run() {
                    event.getChannel().sendMessage(event.getSender(), event.getMessage(), event.getOrigin());
                }
            });
        }
    }
}
