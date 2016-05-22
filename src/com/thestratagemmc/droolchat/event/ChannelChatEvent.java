package com.thestratagemmc.droolchat.event;

import com.thestratagemmc.droolchat.Channel;
import com.thestratagemmc.droolchat.ChatBus;
import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.bot.Bot;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class ChannelChatEvent extends Event implements Cancellable{
    private static HandlerList handlerList = new HandlerList();
    private Channel channel;
    private boolean cancelled = false;
    private String message;
    private ChatMessageSender sender;
    private Player origin;

    public ChannelChatEvent(boolean isAsync, Channel channel, String message, ChatMessageSender sender, Player origin) {
        super(true); //force this event to be async
        this.channel = channel;
        this.message = message;
        this.sender = sender;
        this.origin = origin;
    }

    public void respond(Bot bot, String message){
        ChatBus.sendChatMessage(channel, bot.getSender(), message, origin);
    }
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList(){
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getMessage() {
        return message;
    }

    public ChatMessageSender getSender() {
        return sender;
    }

    public Player getOrigin(){
        return origin;
    }
}
