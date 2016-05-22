package com.thestratagemmc.droolchat;

import com.google.common.io.Files;
import com.thestratagemmc.droolchat.channels.PrivateMessageChannel;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class PlayerChatter {
    static HashMap<UUID,ThemeUser> themeUsers = new HashMap<>();

    public static void loadThemeUsers(File file) throws Exception{
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        for (String string : lines){
            ThemeUser tu = ThemeUser.deserialize(string);
            themeUsers.put(tu.id, tu);
        }
    }

    public static void saveThemeUsers(File file) throws Exception{

    }
    private HashMap<UUID,PrivateMessageChannel> messageChannels = new HashMap<>();
    private UUID player;

    public PlayerChatter(UUID player) {
        this.player = player;
    }

    public PrivateMessageChannel getPrivateMessageChannel(UUID id){
        if (messageChannels.containsKey(id)) return messageChannels.get(id);
        PrivateMessageChannel pm = new PrivateMessageChannel(player, id);
        messageChannels.put(id, pm);
        return pm;
    }

    public ThemeUser getThemeUser(String name){
        if (themeUsers.containsKey(player)) return themeUsers.get(player);
        ThemeUser u = new ThemeUser(player, name);
        themeUsers.put(player, u);
        return u;
    }

}
