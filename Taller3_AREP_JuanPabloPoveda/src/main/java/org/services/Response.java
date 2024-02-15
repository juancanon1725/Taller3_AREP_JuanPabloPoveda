package org.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Response {

    private String path;
    private String code;
    private String type;
    private String body;


    public String getResponse(){
        return getHeader() + getBody();
    }

    public String getHeader() {
        return "HTTP/1.1 "+getCode()+"\r\n" +
                "Content-type: "+getType()+"\r\n" +
                "\r\n";
    }

    public String getBody() {
        return body;
    }

    public void setBody(){
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(Paths.get(getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        body = new String(fileContent);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPath() {
        return "src/main/resources/public/"+path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
