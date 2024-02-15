package org;

import java.io.IOException;

public class ThreadsTest extends Thread{

    private final String title;
    private String answer;

    public ThreadsTest(String title) {
        this.title = title;
    }

    @Override
    public void run(){
        try {
            answer = Cache.searchTitulo(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAnswer(){
        return answer;
    }

    public String getTitle(){
        return title;
    }
}
