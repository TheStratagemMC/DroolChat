package com.thestratagemmc.droolchat.elements;

import com.thestratagemmc.droolchat.Channel;

/**
 * Created by 18AxMoreen on 5/13/2016.
 */
public class ChannelNickElement extends ChannelNameElement {
    public ChannelNickElement(Channel channel) {
        super(channel);
    }

    public String getName(){
        return channel.getNick();
    }
}
