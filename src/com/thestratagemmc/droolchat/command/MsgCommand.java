package com.thestratagemmc.droolchat.command;

import com.google.common.base.Joiner;
import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.DroolChat;
import com.thestratagemmc.droolchat.PlayerChatter;
import com.thestratagemmc.droolchat.bot.*;
import com.thestratagemmc.droolchat.channels.BotPrivateMessageChannel;
import com.thestratagemmc.droolchat.channels.PMFactory;
import com.thestratagemmc.droolchat.channels.PrivateMessageChannel;
import com.thestratagemmc.droolchat.senders.BotSender;
import com.thestratagemmc.droolchat.senders.ConsoleSender;
import com.thestratagemmc.droolchat.senders.PlayerSender;
import com.thestratagemmc.droolchat.senders.UnknownSender;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class MsgCommand implements CommandExecutor {

    private HashMap<UUID,PrivateMessageChannel> nullChannelCache = new HashMap<>();
    private HashMap<UUID,BotPrivateMessageChannel> botChannelCache = new HashMap<>();

    private HashMap<UUID,UUID> lastConversation = new HashMap<>();
    private PrivateMessageChannel newChannel(UUID id){
        if (nullChannelCache.containsKey(id)) return nullChannelCache.get(id);
        PrivateMessageChannel channel = new PrivateMessageChannel(null, id);
        nullChannelCache.put(id, channel);
        return channel;
    }

    private BotPrivateMessageChannel botChannel(Player player){
        if (botChannelCache.containsKey(player.getUniqueId())) return botChannelCache.get(player.getUniqueId());
        BotPrivateMessageChannel channel = PMFactory.getBotChannel(player);
        botChannelCache.put(player.getUniqueId(), channel);
        return channel;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("reply")){
            if (!(sender instanceof Player)) return false;
            Player p = (Player)sender;
            if (!lastConversation.containsKey(p.getUniqueId())){
                p.sendMessage(ChatColor.YELLOW + "No one has chatted you.");
                return true;
            }

            Player to = Bukkit.getPlayer(lastConversation.get(p.getUniqueId()));
            p.performCommand("message "+to.getName()+" "+Joiner.on( " ").join(args));
            return true;
        }
        ChatMessageSender cm;
        boolean cachedChannels = false;
        boolean isBot = false;
        Player p = null;
        if (sender.equals(Bukkit.getConsoleSender())){
            cm = new ConsoleSender();
        }
        else if (sender instanceof Player){
            p = (Player)sender;
            cm = new PlayerSender(p);
            cachedChannels = true;
        }
        else cm = new UnknownSender();

        if (args.length == 0){
            // TODO: help message

            return true;
        }
        Player to = null;
        if (args.length >= 1){
            String name = args[0];
            //check isBot
            if (DroolChat.isBot(name)) isBot = true;
            to = Bukkit.getPlayer(name);
            if (to == null && !isBot){
                sender.sendMessage(ChatColor.YELLOW + "Player is not found.");
                return true;
            }
        }

        if (args.length == 1){
            //toDO: lock them in, args[0]
            return true;
        }


        PrivateMessageChannel pm;
        if (isBot){

                final Player player = (Player)sender;
                final Bot bot = DroolChat.getBot(args[0]);
                final String message = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));
                botChannel(player).sendMessage(new PlayerSender(player), message, player, bot);
                new BotResponse(new BotCall() {
                    @Override
                    public String call() {
                        return bot.respondToPM(player, message);
                    }
                }, new BotCallback() {
                    @Override
                    public void callback(String response) {
                        botChannel(player).sendMessage(new BotSender(bot), response, player);
                    }
                });
            return true;
        }
        if (cachedChannels){
            PlayerChatter chatter = DroolChat.getChatter(p.getUniqueId());
            pm = chatter.getPrivateMessageChannel(to.getUniqueId());
        }
        else pm = newChannel(to.getUniqueId());
        lastConversation.put(to.getUniqueId(), p.getUniqueId());
        String message = Joiner.on(" ").join(Arrays.copyOfRange(args, 1, args.length));

        pm.sendMessage(cm, message, null);

        return true;
    }
}
