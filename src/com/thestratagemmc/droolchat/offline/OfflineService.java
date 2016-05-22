package com.thestratagemmc.droolchat.offline;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * Created by 18AxMoreen on 5/22/2016.
 */
public class OfflineService {
    HashMap<String,UUID> userIds= new HashMap<>();
    private String serviceName;

    public UUID getUser(String id){
        if (userIds.containsKey(id)) return userIds.get(id);
        return null;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean hasUser(String id){
        return userIds.containsKey(id);
    }

    public void addUser(String id, UUID uuid){
        userIds.put(id, uuid);
    }

    public String save(){
        Set<String> out = new HashSet<>();
        for (Map.Entry<String,UUID> entry : userIds.entrySet()){
            out.add(entry.getKey()+ " "+entry.getValue());
        }
        return Joiner.on("\n").join(out);
    }
}
