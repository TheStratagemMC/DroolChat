package com.thestratagemmc.droolchat;

import com.thestratagemmc.droolchat.channels.PrivateMessageChannel;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public interface Chatter {
    //store personal conversations and themeusers here
   /* HashMap<UUID,PrivateMessageChannel> personalMessages = new HashMap<>(); //non persistent! just cache the objects here as needed
    ThemeUser user;*/

    public PrivateMessageChannel getPrivateMessageChannel(UUID id);

}

//also to do, set up listeners for playerjoin to setup information and put them in appropriate channels
