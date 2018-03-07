package com.leighton;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.OutputStream;


public class UpdateHandler implements HttpHandler {

    private Controller rc;

    public UpdateHandler(Controller controller) {
        this.rc = controller;
    }
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        Headers resHeaders = exchange.getResponseHeaders();
        OutputStream os = exchange.getResponseBody();
        String query = exchange.getRequestURI().getQuery();
        Headers rqHeaders = exchange.getRequestHeaders();
        String response = new String();
        try {
            try {
                switch(method){
                    case("GET"): response = getProps();
                    break;
                    case("POST"): response =  postHandle(exchange);
                    break;
                }
                exchange.sendResponseHeaders(200, response.length());
            } catch (Throwable t) {
                t.printStackTrace();
                response = t.getStackTrace().toString();
                System.out.println(response);
                exchange.sendResponseHeaders(404, response.length());
            } finally {
                os.write(response.getBytes());
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String postHandle(HttpExchange exchange) {

        JsonObject props = Json.createReader(exchange.getRequestBody())
                .readObject();
        String response = new String();
        Commands command = Commands.lookupByName(props.getString("command"));

        switch(command){
            case mode:  modeSwitch(props);
            break;
            case addtimerevent :  addEvent(props);
            break;
            case removetimerevent :  removeEvent(props);
            break;
            case startrunback: startRunBack(props);
            break;
            case stoprunback: stopRunBack();
            break;
        }
        return getProps();
    }

    private void startRunBack(JsonObject props) {
        rc.getTimer().startRunback(Integer.parseInt(props.getString("length").trim()));
    }

    private void stopRunBack() {
        rc.getTimer().stopRunback();
    }

    private void removeEvent(JsonObject props) {
        rc.getTimer().removeEvent( props.getJsonNumber("removeIndex").intValue());
        rc.save();
    }

    private void addEvent(JsonObject props) {
        rc.getTimer().addEvent(props.getString("start"), Integer.parseInt(props.getString("length").trim()));
        rc.save();
    }

    private void modeSwitch(JsonObject props) {


        OperatingMode command = OperatingMode.valueOf(props.getString("mode").trim());
        if(rc.getOpMode() != command){
            rc.setMode(OperatingMode.valueOf(props.getString("mode").trim()));
            rc.save();
        }
    }

    public String getProps() {
        return rc.getDisplayProps().toString();
    }
}


