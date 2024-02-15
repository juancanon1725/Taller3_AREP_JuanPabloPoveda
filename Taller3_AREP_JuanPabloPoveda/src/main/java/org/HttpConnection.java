package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpConnection {

    private static final String AGENTE = "Mozilla/5.0";

    // URL base para realizar la solicitud a la API de OMDB
    private static String GET_URL = "http://www.omdbapi.com/?t=";

    // Clave de API para autenticar la solicitud a la API de OMDB
    private static final String API_KEY = "&apikey=10e5237e";

    // Método para realizar la solicitud a la API de OMDB
    public static String ResponseApi(String title) throws IOException {
        String respuestaApi = "";


        if (!Objects.equals(title, "")) {
            // Construye la URL completa con el título y la clave de API
            GET_URL += title;
            GET_URL += API_KEY;


            URL obj = new URL(GET_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();


            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", AGENTE);


            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);


            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                // Lee la respuesta línea por línea y la almacena en el StringBuffer
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Convierte la respuesta en una cadena y la asigna a respuestaApi
                respuestaApi = response.toString();
            } else {
                // Si la respuesta no es exitosa, se registra un mensaje de error
                respuestaApi = "GET request not worked";
                System.out.println("GET request not worked");
            }
            System.out.println("GET DONE");
        }

        // Restablece la URL base para otras solicitudes
        GET_URL = "http://www.omdbapi.com/?t=";

        // Retorna la respuesta de la API de OMDB
        return respuestaApi;
    }
}