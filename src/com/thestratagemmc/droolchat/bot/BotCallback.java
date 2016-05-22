package com.thestratagemmc.droolchat.bot;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */

/*
Designed for a bot to do work on another thread and come back with a response
 */
public interface BotCallback {
    public void callback(String response);
}
