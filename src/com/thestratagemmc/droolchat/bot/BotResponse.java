package com.thestratagemmc.droolchat.bot;

import com.thestratagemmc.droolchat.DroolChat;
import org.bukkit.Bukkit;

/**
 * Created by 18AxMoreen on 5/14/2016.
 */
public class BotResponse {
    BotCall call;
    BotCallback back;

    public BotResponse(BotCall call, BotCallback back){
        this.call = call;
        this.back = back;
    }


    public void run(){
        Bukkit.getScheduler().runTaskAsynchronously(DroolChat.getInstance(), new Runnable() {
            @Override
            public void run() {
                final String r = call.call();
                Bukkit.getScheduler().runTask(DroolChat.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        back.callback(r);
                    }
                });
            }
        });
    }

}
