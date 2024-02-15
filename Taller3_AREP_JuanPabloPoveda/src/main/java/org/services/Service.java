package org.services;

import java.io.IOException;
import java.nio.file.*;

public class Service {

    public static Service instance;

    private Service(){}

    public static Service getInstance(){
        if(instance == null){
            instance = new Service();
        }
        return instance;
    }

    public String getHeader(String type, String code) {
        return "HTTP/1.1 "+code+"\r\n" +
                "Content-type: text/"+type+"\r\n" +
                "\r\n";
    }

    public String getResponse(String path) {
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(fileContent);
    }
}
