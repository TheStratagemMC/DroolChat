package com.thestratagemmc.droolchat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */
public abstract class Element {
    private ElementTheme defaultTheme; //cache

    public abstract TextComponent getComponent(ThemeUser user);

    public abstract void registerDefaultColors(HashMap<String,ChatColor> map);

    public ChatColor getColor(ThemeUser user, String name){
        if (user.themeMap.containsKey(this.getClass())){
            ElementTheme theme  = user.themeMap.get(this.getClass());
            ChatColor c = theme.getColor(name);
            if (c != null) return c;
            // if it is, continue on because we want to get the default colors
        }
        ElementTheme theme;
        if (defaultTheme != null) theme = defaultTheme;
        else defaultTheme = theme = ElementTheme.getDefaultTheme(this.getClass());
        if (theme == null) return ChatColor.BLACK;

        return theme.getColor(name);
    }
}
