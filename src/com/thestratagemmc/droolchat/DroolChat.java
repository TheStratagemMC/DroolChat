package com.thestratagemmc.droolchat;

import com.thestratagemmc.droolchat.bot.Bot;
import com.thestratagemmc.droolchat.bot.BotStore;

import com.thestratagemmc.droolchat.bot.ExampleBot;
import com.thestratagemmc.droolchat.command.ChannelCommand;
import com.thestratagemmc.droolchat.command.MsgCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class DroolChat extends JavaPlugin {

    private static DroolChat instance;
    private BotStore botStore;
    private ChannelStore channelStore;
    private File channelDir;
    private static HashMap<UUID,PlayerChatter> chatterMap = new HashMap<>();

    public void onEnable(){
        instance = this;
        botStore = new BotStore();
        channelStore = new ChannelStore();
        File pDir = getDataFolder();
        if (!pDir.exists()) pDir.mkdir();

        channelDir = new File(pDir, "channels");
        if (!channelDir.exists()) {
            channelDir.mkdir();
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/default_channels.yml")));
                File def = new File(channelDir, "default_channels.yml");
                def.createNewFile();
                System.out.println(def.exists());
                FileOutputStream fout = new FileOutputStream(def);
                String line;
                while ((line = reader.readLine()) != null){
                    fout.write((line +"\n").getBytes());
                }

                fout.close();
                reader.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        for (File file : channelDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".yml");
            }
        })){
            YamlConfiguration config = new YamlConfiguration();
            try{
                config.load(file);

                for (String key : config.getKeys(false)){
                    ConfigurationSection section = config.getConfigurationSection(key);
                    Channel channel;
                    if (section.getString("type") != null){
                        channel = (Channel)Class.forName("com.thestratagemmc.droolchat.channels."+section.getString("type")).newInstance();
                    }
                    else channel = new Channel();

                    channel.load(section);

                    channelStore.registerChannel(channel);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        File chatterDir = new File(getDataFolder(), "chatters");
        if (!chatterDir.exists()) chatterDir.mkdir();

        File themes = new File(chatterDir, "theme_users.yml");
        try{
            if (!themes.exists()) themes.createNewFile();
            PlayerChatter.loadThemeUsers(themes);
        }catch(Exception e){
            e.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new ChatBus(), this);
        final ChannelCommand command = new ChannelCommand();
        getServer().getPluginManager().registerEvents(command, this);
        getCommand("channel").setExecutor(command);
        final MsgCommand m = new MsgCommand();
        getCommand("reply").setExecutor(m);
        getCommand("message").setExecutor(m);

        registerBot(this, "ExampleBot", new ExampleBot());
    }

    public void onDisable(){
        File dir = getDataFolder();
        File chatterDir = new File(getDataFolder(), "chatters");

        try{
            PlayerChatter.saveThemeUsers(new File(chatterDir, "theme_users.yml"));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public ChannelStore getChannelStore(){ return channelStore; }
    public static DroolChat getInstance(){
        return instance;
    }

    public static PlayerChatter getChatter(UUID id){
      if (chatterMap.containsKey(id)) return chatterMap.get(id);
        PlayerChatter chatter = new PlayerChatter(id);
        chatterMap.put(id, chatter);
        return chatter;
    }

    public BotStore getBotStore(){
        return botStore;
    }

    public static Bot getBot(String name){
        return instance.getBotStore().getBot(name);
    }

    public static boolean isBot(String name){
        return instance.getBotStore().isBot(name);
    }

    public static void registerBot(Plugin plugin, String name, Bot bot){
        instance.getBotStore().registerBot(plugin, name, bot);
    }
}
