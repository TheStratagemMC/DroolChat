package com.thestratagemmc.droolchat;

import com.thestratagemmc.droolchat.elements.ChannelNameElement;
import com.thestratagemmc.droolchat.elements.ChannelNickElement;
import com.thestratagemmc.droolchat.elements.MessageElement;
import com.thestratagemmc.droolchat.elements.SenderDetailedElement;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */

/*
Instead of a formatted string, we keep the elements in an array to display them orderly

and then, we format the elements individually

ALL elements:
- Channel/Thread title
- Channel/Thread nick
- Sender Username
- Detailed Sender
- message

//- ServerIdentifier
//- ServerNickname


 */
public class Channel {
    boolean botsAllowed = true;
    //boolean crossServer = true;
    String title = "default";
    String description = "description";
    ChatColor messageColor = ChatColor.getByChar('7');
    String nick = "d";
    boolean moderated = true;
    ChatColor color;
    ArrayList<Class<? extends Element>> elementOrder = new ArrayList<Class<? extends Element>>(Arrays.asList(ChannelNameElement.class, SenderDetailedElement.class, MessageElement.class));
    Set<UUID> playersInChannel = new HashSet<>();

    public Channel(){

    }
    public Channel(String title, String description, String nick, ChatColor color){
        this.title = title;
        this.description = description;
        this.nick = nick;
        this.color = color;
    }

    public void loadElements(Collection<String> order){
        this.elementOrder.clear();
        for (String element : order){
            try{
                Class<? extends Element> cl = (Class<? extends Element>)Class.forName("com.thestratagemmc.droolchat.elements."+element);
                this.elementOrder.add(cl);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public Channel load(ConfigurationSection section){
        if (section.contains("title")) this.title = section.getString("title");
        if (section.contains("description")) this.description = section.getString("description");
        if (section.contains("nick")) this.nick = section.getString("nick");
        if (section.contains("color")) this.color = ChatColor.getByChar(section.getString("color").toCharArray()[0]);
        if (section.contains("message_color")) this.messageColor = ChatColor.getByChar(section.getString("message_color").toCharArray()[0]);
        if (section.contains("elements")){
            String string = section.getString("elements");
            loadElements(Arrays.asList(string.split(",")));
        }
        if (section.contains("bots")) this.botsAllowed = section.getBoolean("bots");
        return this;
    }

/*
origin may be null unless its a LocalChannel
 */
    public void sendMessage(ChatMessageSender sender, String message, Player origin){
        for (Player player : Bukkit.getOnlinePlayers()){
            if (playersInChannel.contains(player.getUniqueId())){
                player.spigot().sendMessage(getMessageToSend(sender, message, player));
            }
        }
    }

    public boolean canListen(Player player){
        return true;
    }
    public boolean canSpeak(Player player){
        return true;
    }


    public BaseComponent getMessageToSend(ChatMessageSender sender, String message, Player player){
        TextComponent output = new TextComponent("");
        ThemeUser user = ThemeUserDb.getThemeUser(player);
        output.addExtra(instantiate(elementOrder.get(0), sender, message, player).getComponent(user));
        for (int i = 1; i < elementOrder.size(); i++){
            output.addExtra(" ");
            output.addExtra(instantiate(elementOrder.get(i), sender, message, player).getComponent(user));
        }
        return output;
    }



    public Element instantiate(Class<? extends Element> c, ChatMessageSender sender, String message, Player player){
        if (c.equals(ChannelNameElement.class)) return new ChannelNameElement(this);
        if (c.equals(ChannelNickElement.class))return new ChannelNickElement(this);
        if (c.equals(MessageElement.class)) return new MessageElement(this, message);
        if (c.equals(SenderDetailedElement.class)) return new SenderDetailedElement(sender);

        //not found
        return new Element() {
            @Override
            public TextComponent getComponent(ThemeUser user) {
                return new TextComponent("ElementNotFoundError");
            }

            @Override
            public void registerDefaultColors(HashMap<String, ChatColor> map) {

            }
        };
    }

    public boolean hasBots(){
        return botsAllowed;
    }
    public String getDescription(){
        return description;
    }
    public boolean canTalk(Player player){
        return (playersInChannel.contains(player.getUniqueId()));
    }
    public boolean isModerated(){
        return moderated;
    }
    public String getNick(){
        return nick;
    }
    public int getSize(){
        return playersInChannel.size();
    }

    public String getName(){
        return title;
    }

    public ChatColor getColor(){
        return color;
    }
    public ChatColor getMessageColor(){
        return messageColor;
    }

    public void addPlayerToChannel(UUID id){
        playersInChannel.add(id);
    }


    public void setBotsAllowed(boolean botsAllowed) {
        this.botsAllowed = botsAllowed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMessageColor(ChatColor messageColor) {
        this.messageColor = messageColor;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setElementOrder(ArrayList<Class<? extends Element>> elementOrder) {
        this.elementOrder = elementOrder;
    }
}
