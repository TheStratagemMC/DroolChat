package com.thestratagemmc.droolchat;

import com.google.common.base.Joiner;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */
public class ElementTheme {
    private static HashMap<Class<? extends Element>, ElementTheme> defaultThemes = new HashMap<>();
    private HashMap<String,ChatColor> colorMap = new HashMap<>();

    public ElementTheme(HashMap<String, ChatColor> colorMap) {
        this.colorMap = colorMap;
    }

    public ChatColor getColor(String name){
        if (!colorMap.containsKey(name)) return null;
        return colorMap.get(name);
    }

    public static ElementTheme getDefaultTheme(Class clazz){
        if (!defaultThemes.containsKey(clazz)) return null;
        return defaultThemes.get(clazz);
    }

    public String serialize(){
        Set<String> lines = new HashSet<>();
        for (Map.Entry<String,ChatColor> entry : colorMap.entrySet()){
            lines.add(entry.getKey()+":"+entry.getValue().getName());
        }
        return Joiner.on(" ").join(lines);
    }

    public static ElementTheme deserialize(String input){
        HashMap<String,ChatColor> map = new HashMap<>();
        for (String string : input.split(" ")){
            String[] minor = string.split(":");
            map.put(minor[0], ChatColor.valueOf(minor[1]));
        }

        return new ElementTheme(map);
    }
}
