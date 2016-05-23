package com.thestratagemmc.droolchat.command;

import com.google.common.base.Joiner;
import com.thestratagemmc.droolchat.*;
import com.thestratagemmc.droolchat.elements.ChannelNameElement;
import com.thestratagemmc.droolchat.senders.ConsoleSender;
import com.thestratagemmc.droolchat.senders.PlayerSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.*;

/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class ChannelCommand implements CommandExecutor, Listener {

    private HashMap<UUID,String> focus = new HashMap<>();

    public void sendHeader(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "Channel=----------------------");
        sender.sendMessage(ChatColor.YELLOW+"Use /ch <channel> to focus.");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        boolean bypass = (sender instanceof ConsoleCommandSender);
        boolean player = sender instanceof Player;
        if (args.length == 0){
            sendHeader(sender);
            sender.sendMessage(ChatColor.YELLOW+"Your channels: ");
            Player p = null;
            if (player) p = (Player)sender;
            Set<TextComponent> output = new HashSet<>();
            for (Channel channel : DroolChat.getInstance().getChannelStore().getChannels()){
               // Bukkit.broadcastMessage(channel.getName());
                if (player) if (channel.canListen(p)) output.add(new ChannelNameElement(channel).getComponent(ThemeUserDb.getThemeUser(p)));
                else if (bypass) output.add(new ChannelNameElement(channel).getComponent(new ThemeUser(null, null)));
            }
            if (player) for (TextComponent comp : output) p.spigot().sendMessage(comp);
            else for (TextComponent comp : output)sender.sendMessage(comp.getText());
            return true;
        }
        if (args.length == 1){
            if (bypass) {
                sender.sendMessage(ChatColor.RED +"Can't focus channels from console, please use the shortcut directly.");
                return true;
            }
            if (!player){
                sender.sendMessage(ChatColor.RED + "Can't do this.");
                return true;
            }

            Player p = (Player)sender;
            Channel channel = DroolChat.getInstance().getChannelStore().getChannel(args[0]);
            if (channel == null){
                p.sendMessage(ChatColor.YELLOW + "Could not find channel '"+args[0]+"'");
                return true;
            }

            if (!channel.canSpeak(p)){
                p.sendMessage(ChatColor.RED + "No permissions to speak in "+channel.getName()+".");
                return true;
            }
            focus.put(p.getUniqueId(), channel.getName());
            p.sendMessage(ChatColor.YELLOW + "Now chatting in "+channel.getName()+".");
            return true;
        }

        sendHeader(sender);
        return true;
    }

    @EventHandler
    public void onCommand(ServerCommandEvent event){
        String real = (event.getCommand().contains("/") ? event.getCommand().substring(1) : event.getCommand());
        String[] args = real.split(" ");
        Channel channel = DroolChat.getInstance().getChannelStore().getChannel(args[0]);
        if (channel == null) return;

        boolean player = false;
        Player p = null;
        if (event.getSender() instanceof Player) {
            player = true;
            p = (Player)event.getSender();
        }

        ChatBus.sendChatMessage(channel, (player) ? new PlayerSender(p) : new ConsoleSender(), Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length)), p);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
     //  Bukkit.broadcastMessage(event.getMessage());
        String real = (event.getMessage().contains("/") ? event.getMessage().substring(1) : event.getMessage());
        String[] args = real.split(" ");
       // Bukkit.broadcastMessage(args[0]);
        Channel channel = DroolChat.getInstance().getChannelStore().getChannel(args[0]);
        if (channel == null) return;
        //Bukkit.broadcastMessage("vvvvv");
        boolean player =  true;
        Player p =event.getPlayer();
        ChatBus.sendChatMessage(channel, (player) ? new PlayerSender(p) : new ConsoleSender(), Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length)), p);
        event.setCancelled(true);
    }

    @EventHandler
    public void asyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if (focus.containsKey(event.getPlayer().getUniqueId())){
            Channel channel = DroolChat.getInstance().getChannelStore().getChannel(focus.get(event.getPlayer().getUniqueId()));
            if (channel != null) {
                ChatBus.sendChatMessage(channel, new PlayerSender(event.getPlayer()), event.getMessage(), event.getPlayer());
                event.setCancelled(true);
                return;
            }
        }
        Channel channel = DroolChat.getInstance().getChannelStore().getChannel("global"); //ehhhh about hard coding it in
        if (channel != null) {
            ChatBus.sendChatMessage(channel, new PlayerSender(event.getPlayer()), event.getMessage(), event.getPlayer());
            event.setCancelled(true);
            return;
        }
        channel = DroolChat.getInstance().getChannelStore().getChannel("local"); //ehhhh about hard coding it in
        if (channel != null) {
            ChatBus.sendChatMessage(channel, new PlayerSender(event.getPlayer()), event.getMessage(), event.getPlayer());
            event.setCancelled(true);
            return;
        }
    }
}
