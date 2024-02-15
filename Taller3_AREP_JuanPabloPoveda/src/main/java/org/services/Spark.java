package org.services;

import org.HttpServer;
import java.io.IOException;
import java.util.HashMap;

public class Spark {

    private static Spark instance;

    private HashMap<String, String> services = new HashMap<>();

    private Spark(){}

    public static Spark getInstance() {
        if(instance == null){
            instance = new Spark();
        }
        return instance;
    }

    public void get(String path, Route route){
        Response response = new Response();
        Request request = new Request();

        String res = route.handle(request,response);
        services.put(path, res);
    }

    public String post(String path, Route route){
        Response response = new Response();
        Request request = new Request();
        services.put(path, route.handle(request,response));
        return route.handle(request,response);
    }

    public String getService(String path){
        return services.get(path);
    }
}
