package com.leighton;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ControlHandler implements HttpHandler {

    Controller rc;
    TimerControl timer;
    public ControlHandler(Controller controller) {
        this.rc = controller;
        this.timer = timer;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getRequestMethod();
        Headers headers = exchange.getResponseHeaders();

        BufferedReader stream = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));

        String query = stream.readLine();
        String response = "good";
        actionQuery(query);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();


    }

    synchronized void actionQuery(String q) {

        String commandString = q.substring(0, q.indexOf("="));
        String parmString = q.substring(q.indexOf("=") + 1, q.indexOf("&"));

        Commands command = Enum.valueOf(Commands.class, commandString);

        switch(command){
            case mode:
                OperatingMode parm = Enum.valueOf(OperatingMode.class, parmString);
                rc.setMode(parm);

                if(parm == OperatingMode.TIMED) {
                    timer.stopTimers();
                    timer.startAllTimers();
                }

                break;
            case addtimerevent:
                String[] times = parmString.split(",");
                String onTime = times[0];
                int delay = Integer.parseInt(times[1]);

                System.out.println("adding event: "+onTime + "   " + delay);

                this.timer.addEvent(onTime, delay);
                this.timer.stopTimers();
                this.timer.startAllTimers();
                break;
            case removetimerevent:
                int selIndex = Integer.parseInt(parmString);
                System.out.println(selIndex);


                timer.removeEvent(selIndex);
                this.timer.stopTimers();
                this.timer.startAllTimers();
                break;

        }
    }

}
