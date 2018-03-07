package com.leighton;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddHandler implements HttpHandler {

    private Controller rc;
    private TimerControl timer;


    public AddHandler(Controller controller) {
        this.rc = controller;
        this.timer = timer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getRequestMethod();
        InputStream inS = exchange.getRequestBody();
        BufferedInputStream reader = new BufferedInputStream(inS);
        Headers headers = exchange.getRequestHeaders();
        OutputStream os = exchange.getResponseBody();


        String response = "response";

        exchange.sendResponseHeaders(200, response.length());
        os.write(response.getBytes());
        os.close();

    }
}
