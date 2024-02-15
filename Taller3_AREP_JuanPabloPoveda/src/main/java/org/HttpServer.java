package org;

import org.services.Service;
import org.services.Spark;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;


public class HttpServer {

    private static HttpServer _instance = new HttpServer();

    private static OutputStream outputStream = null;
    public static HttpServer getInstance(){
        return _instance;
    }

    public static void main(String[] args) throws IOException {
        Spark spark = Spark.getInstance();
        spark.get("/", ((request, response) -> {
            response.setType("application/json");
            return response.getResponse();
        }));
        HttpServer server = HttpServer.getInstance();
        server.run(args);
    }


    public static void run(String[] args) throws IOException {
        Spark spark = Spark.getInstance();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, title ="";

            String outputLine = "";
            boolean first_line = true;
            String request = "/simple";
            String verb = "GET";

            while ((inputLine = in.readLine()) != null) {
                if(inputLine.contains("info?title=")){
                    String[] array = inputLine.split("title=");
                    title = (array[1].split("HTTP")[0]).replace(" ", "");
                }
                if (first_line) {
                    request = inputLine.split(" ")[1];
                    verb = inputLine.split(" ")[0];
                    first_line = false;
                }

                if (!in.ready()) {
                    break;
                }
            }

            if (request.startsWith("/apps/")) {
                String path = request.substring(5);
                //outputLine = startService(request.substring(5));
                if (verb.equals("GET")) {
                    String res = spark.getService(path);
                    if(res == null){
                        spark.get(request.substring(5), ((requests, response) -> {
                            try{
                                String type = path.split("\\.")[1];
                                response.setType("text/"+type);
                                response.setCode("200 OK");
                                response.setPath(path);
                                response.setBody();
                                return response.getResponse();
                            }catch (Exception e){
                                response.setType("text/html");
                                response.setCode("404 Not Found");
                                response.setPath("error.html");
                                response.setBody();
                                return response.getResponse();
                            }

                        }));
                        res = spark.getService(path);
                        outputLine = res;
                    }

                }else if (verb.equals("POST")) {
                    outputLine = spark.post(path, ((requests, response) -> {
                        String paths = path.split("\\?")[0];
                        String query = path.split("\\?")[1];
                        response.setType("application/json");
                        response.setCode("201 Created");
                        response.setPath(paths);
                        response.setBody(query);
                        return response.getResponse();
                    }));

                }
            }
            else if(!title.equals("")){
                String response = HttpConnection.ResponseApi(title);
                outputLine ="HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<br>"
                        + "<table border=\" 1 \"> \n " + createTable(response)+

                        "    </table>";
            }else {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + getIndexResponse();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static String startService(String serviceName) {
        Service ser = Service.getInstance();
        try {
            String type = serviceName.split("\\.")[1];
            String header = ser.getHeader(type, "200 OK");
            String body = ser.getResponse("src/main/resources/" + serviceName);
            return header + body;
        }
        catch (RuntimeException e){
            String header = ser.getHeader("html", "404 Not Found");
            String body = ser.getResponse("src/main/resources/error.html");
            return header + body;
        }
    }

    /**
     * Entrega el index de la página principal
     * @return Index en formato de String del HTML del inicio de la Página
     */
    private static String getIndexResponse(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Buscador de peliculas</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Buscar una pelicula</h1>\n" +
                "<form action=\"/hello\">\n" +
                "    <label for=\"name\">Titulo de la pelicula a buscar:</label><br>\n" +
                "    <input type=\"text\" id=\"name\" name=\"name\" value=\"Batman\"><br><br>\n" +
                "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "</form>\n" +
                "<div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "<script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/info?title=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "</body>\n" +
                "</html>";
    }


    private static String createTable(String respuestaApi){
        String[] datos = respuestaApi.split(":");
        String tabla = "<table> \n";
        for (int i = 0;i<(datos.length);i++) {
            String[] respuestaTemporal = datos[i].split(",");
            tabla+="<td>" + Arrays.toString(Arrays.copyOf(respuestaTemporal, respuestaTemporal.length - 1)).replace("[","").replace("]","").replace("}","") + "</td>\n</tr>\n";
            tabla+="<tr>\n<td>" +  respuestaTemporal[respuestaTemporal.length-1].replace("{","").replace("[","") + "</td>\n";
        }
        tabla += "</table>";
        return tabla;

    }
}