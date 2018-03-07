package com.leighton;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeatingService {
    private boolean active;
    private Controller controller;
    public void start() {

        final ExecutorService ex = Executors.newFixedThreadPool(5);

        controller = new Controller();

        try {
            URI u = new URI("http://localhost:8000/");
            System.out.println(u.getPath());
            HttpServer myserver = HttpServer.create(new InetSocketAddress(8000), 0);
            myserver.createContext("/" , new ReactHandler(controller)); //change for diff front ends
            myserver.createContext("/add/" , new ControlHandler(controller));
            myserver.createContext("/testAdd/" , new AddHandler(controller));
            myserver.createContext("/res/" , new ResourceHandler(controller));
            myserver.createContext("/update/" , new UpdateHandler(controller));


            myserver.setExecutor(ex);

            myserver.start();
        }catch(Exception e){
            System.out.println("failed");
        }

    }

    public Controller getController() {
        return this.controller;
    }
}
