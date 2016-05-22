package com.thestratagemmc.droolchat;

import org.bukkit.entity.Player;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class ThemeUserDb {
    @Deprecated
    public static ThemeUser getThemeUser(Player player){
        return DroolChat.getInstance().getChatter(player.getUniqueId()).getThemeUser(player.getName());
    }
}
