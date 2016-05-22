package com.thestratagemmc.droolchat;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 18AxMoreen on 5/21/2016.
 */
public class PlayerHoverAPI {

    private static Set<PlayerInfoGetter> list = new HashSet<>();

    public static Collection<PlayerInfoGetter> getPlayerInfos(){
        return list;
    }

    public static void register(PlayerInfoGetter getter){
        list.add(getter);
    }
}
