package com.thestratagemmc.droolchat.offline;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thestratagemmc.droolchat.senders.OfflineSender;
import org.bukkit.craftbukkit.v1_9_R2.util.MojangNameLookup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 18AxMoreen on 5/22/2016.
 */
public class OfflineStore {
    private String apiKey;
    private HttpServer server;
    private int port = 99113;
    private HashMap<UUID,String> nameCache = new HashMap<>();
    private HashMap<String,OfflineService> serviceMap = new HashMap<>();

    public void run(){
        try {
            server = HttpServer.create(new InetSocketAddress(0), port);

            server.createContext("/message", new HttpHandler() {
                @Override
                public void handle(HttpExchange e) throws IOException {

                    String out = getResponse(e);
                    e.getResponseHeaders().add("Content-Type", "text/plain");
                }

                public String getResponse(HttpExchange e) {
                    String path = e.getRequestURI().getPath();
                    HashMap<String, String> args = new HashMap<>();
                    path = path.substring(path.indexOf("?"));
                    for (String string : path.split("&")) {
                        String[] d = string.split("=");
                        args.put(d[0], d[1]);
                    }

                    if (!args.containsKey("api-key")) return "No API key.";
                    if (!args.get("api-key").equals(apiKey)) return "Wrong API Key.";
                    if (!args.containsKey("service")) return "Not a registered service.";
                    if (!args.containsKey("user")) return "Not a user.";
                    if (!args.containsKey("message")) return "No message provided.";
                    if (!args.containsKey("channel")) return "No channel provided.";
                    if (!serviceMap.containsKey(args.get("service"))) return "Not a service.";
                    OfflineService service = serviceMap.get(args.get("service"));
                    if (!service.hasUser(args.get("user"))) return "You must verify your account first.";
                    UUID id = service.getUser(args.get("user"));
                    String name = getName(id);

                    OfflineSender sender = new OfflineSender(service.getServiceName(), name, id);
                    return "OK";
                }

            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getName(UUID id){
        if (nameCache.containsKey(id)) return nameCache.get(id);
        String name = MojangNameLookup.lookupName(id);
        nameCache.put(id, name);
        return name;
    }
}
