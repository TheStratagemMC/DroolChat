package com.thestratagemmc.droolchat.senders;

import com.thestratagemmc.droolchat.ChatMessageSender;
import com.thestratagemmc.droolchat.PlayerInfoGetter;
import com.thestratagemmc.droolchat.PlayerHoverAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class PlayerSender implements ChatMessageSender {
    private Player player;

    public PlayerSender(Player player){
        this.player = player;
    }
    @Override
    public TextComponent getComponent() {
        TextComponent c = new TextComponent(player.getDisplayName());
        ComponentBuilder cb = new ComponentBuilder(player.getName());
        for (PlayerInfoGetter getter : PlayerHoverAPI.getPlayerInfos()){
            cb.append(ChatColor.RESET+"\n"+getter.getInfo(player));
        }
        c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
        return c;
    }
}
