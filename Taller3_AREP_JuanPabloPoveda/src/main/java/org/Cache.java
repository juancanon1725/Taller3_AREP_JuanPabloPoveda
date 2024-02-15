package org;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache de la API
 */
public class Cache {
    public static ConcurrentHashMap<String,String> peliculas = new ConcurrentHashMap<>();
    public static String searchTitulo(String titulo) throws IOException {
        String valor="";
        if (peliculas.containsKey(titulo)){
            valor += peliculas.get(titulo);
        }else{
            valor += HttpConnection.ResponseApi(titulo);
            peliculas.put(titulo,valor);
        }
        return valor;
    }

}