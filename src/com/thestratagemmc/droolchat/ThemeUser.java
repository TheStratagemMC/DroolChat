package com.thestratagemmc.droolchat;

import com.google.common.base.Joiner;

import java.util.*;

/*
 * Created by 18AxMoreen on 5/13/2016.
 */
public class ThemeUser {
    public HashMap<Class<? extends Element>, ElementTheme> themeMap = new HashMap<>();
    public UUID id;
    public String username;

    public ThemeUser(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public ThemeUser(String username, UUID id, HashMap<Class<? extends Element>, ElementTheme> themeMap) {
        this.username = username;
        this.id = id;
        this.themeMap = themeMap;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String serialize(){
        Set<String> out = new HashSet<>();
        for (Map.Entry<Class<? extends Element>, ElementTheme> entry : themeMap.entrySet()){
            out.add(entry.getKey().getName()+"//"+entry.getValue().serialize());
        }
        return id.toString()+"~"+username+"~"+ Joiner.on("#").join(out);
    }

    public static ThemeUser deserialize(String input){
        String[] major = input.split("~");
        UUID id = UUID.fromString(major[0]);
        String username = major[1];
        String[] minor = major[2].split("#");
        HashMap<Class<? extends Element>, ElementTheme> map = new HashMap<>();

        for (String string : minor){
            String[] b = string.split("//");
            try{
                Class<? extends Element> cl = (Class<? extends Element>)Class.forName(b[0]);
                ElementTheme et = ElementTheme.deserialize(b[1]);
                map.put(cl, et);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return new ThemeUser(username, id, map);
    }
}
