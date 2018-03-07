package com.leighton;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class ResourceHandler implements HttpHandler {

    private Controller rc;

    public ResourceHandler(Controller controller) {
        this.rc = controller;
    }
    @Override
    public void handle(HttpExchange exchange) {
        InputStream inS = exchange.getRequestBody();
        BufferedInputStream reader = new BufferedInputStream(inS);
        String resFile = exchange.getRequestURI().getPath();
        Headers resHeaders = exchange.getResponseHeaders();
        OutputStream os;

        String query = exchange.getRequestURI().getQuery();

        String response = new String();

        try {
            try {
                response = genResponse(resFile);
                byte[] responseBs = response.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, 0);
//                ByteArrayOutputStream os = new ByteArrayOutputStream(exchange.getResponseBody());
                os  = exchange.getResponseBody();
                ByteArrayInputStream bis = new ByteArrayInputStream(responseBs);
                byte[] buffer = new byte[1000];
                int bytesRead = 0;
                while((bytesRead = bis.read(buffer)) != -1 ){
                    os.write(buffer,0,bytesRead);
                }
                os.close();
            } catch (Throwable t) {
                t.printStackTrace();
                response = "Could not find file: " + resFile;

            } finally {


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String genResponse(String resFile) throws IOException, URISyntaxException {


        StringBuilder builder = new StringBuilder();
            URI uri = this.getClass().getResource(resFile).toURI();
            File pageList = new File(uri);
        BufferedReader reader = new BufferedReader(new FileReader(pageList));

            String line = reader.readLine();
            while (line != null) {
                builder.append(line + "\n\r");
                line = reader.readLine();
            }
            return builder.toString();
    }

}


