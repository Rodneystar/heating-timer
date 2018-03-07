package com.leighton;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class ReactHandler implements HttpHandler {

    private Controller rc;

    public ReactHandler(Controller controller) {
        this.rc = controller;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream inS = exchange.getRequestBody();
        BufferedInputStream reader = new BufferedInputStream(inS);
        Headers headers = exchange.getRequestHeaders();
        OutputStream os = exchange.getResponseBody();
        String query = exchange.getRequestURI().getQuery();

        String response = genResponse();

        exchange.sendResponseHeaders(200, response.length());
        os.write(response.getBytes());
        os.close();

    }

    private String genResponse() {
        StringBuilder builder = new StringBuilder();
        try {
            URI uri = this.getClass().getResource("/index.html").toURI();
            File pageList = new File(uri);
            BufferedReader reader = new BufferedReader(new FileReader(pageList));

           String line = reader.readLine();
            while(line != null){
                builder.append(line + "\n\r");
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException f) {
            f.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private String getTable() {
        StringBuilder response = new StringBuilder("");
        String mode = rc.getOpMode().toString();
        ArrayList<TimerEvent> events = rc.getTimer().getEventList();
        String eventString[] = new String[events.size()];

        for(int i = 0; i< events.size(); i++){
            eventString[i] = events.get(i).getEventTime() + ", for "  + String.valueOf(events.get(i).getLength()) + " minutes." +
                    "<input type='button' value='remove' onclick='javascript:remove(\""+ String.valueOf(i)+"\");'>" + "\n";
        }
        response.append("<div id='wrapper'>"+ "\n");
        for(String e:eventString) {
            response.append(e+ "<br>"+ "\n");
        }
        response.append("<br> Mode: " + mode );
        response.append("</div>" + "\n");
        return response.toString();
    }

}