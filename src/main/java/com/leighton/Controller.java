package com.leighton;

import javax.json.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {

    private OperatingMode opMode;

    public TimerControl getTimer() {
        return timer;
    }

    private TimerControl timer;
    private RelayControl rc;
    private JsonObject state;

    public Controller(){
        timer = new TimerControl(this);
        rc = new RelayControl();
        loadState();
        initializeController();
    }

    public JsonObject getDisplayProps(){
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("jsonTable", timer.getJsonTable())
            .add( "mode", this.opMode.name())
            .add( "start", timer.getTimeNow())
            .add( "length", 0)
            .add( "removeIndex", 0)
            .add( "command", "")
            .add("runBackFinish", timer.getRunBackFinishMins());


        return builder.build();
    }

    private void loadState() {
        try {
            URL stateUrl = this.getClass().getResource("/state");
            File file = new File(stateUrl.toURI());
            InputStream iS = new FileInputStream(file);
            JsonReader reader =  Json.createReader(iS);
            state = reader.readObject();
            if(state.isEmpty()){
                state = getPropModel();
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("broke getting state file, settng TIMED: " + e.getCause());
            this.state = getPropModel();
        }  catch (NullPointerException e) {
            this.state = getPropModel();
        } catch (URISyntaxException e) {
            this.state = getPropModel();
            e.printStackTrace();
        } catch( Throwable e) {
            System.out.println(e.getMessage());
            this.state = getPropModel();
        }

    }

    private void initializeController() {

        opMode = OperatingMode.valueOf(state.getString("mode"));
        this.setMode(opMode);
        timer.loadEvents(state.getJsonArray("jsonTable"));
        timer.startAllTimers();
    }

    public void save() {
        try{
            File file = new File(this.getClass().getResource("/state").toURI());

            JsonObject job = getDisplayProps();

            JsonWriter writer = Json.createWriter(new FileOutputStream(file));
            writer.writeObject(job);
            writer.close();
            timer.save();

        }catch (UnsupportedOperationException e) {
            System.out.println("save error: " + e.getCause());
        }catch (Throwable e){
            System.err.println(" save Error: " + e);
        }
    }

    public void timerSwitch(boolean turnOn){
        if(opMode == OperatingMode.TIMED){
            if(turnOn){
                rc.on();
            }
            else{
                rc.off();
            }
        }
    }

    public void setMode(OperatingMode opMode){
        this.opMode = opMode;
        if(this.opMode == OperatingMode.ON) {
            rc.on();

        } else if(this.opMode == OperatingMode.OFF) {
            rc.off();
        } else if(this.opMode == OperatingMode.TIMED) {
            timer.stopTimers();
            timer.startAllTimers();
        }
    }

    public OperatingMode getOpMode() {
        return opMode;
    }


    public JsonObject getPropModel() {

        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObject value = factory.createObjectBuilder()
                .add("jsonTable" , factory.createArrayBuilder()
                    .add(factory.createObjectBuilder()
                        .add("onTime", timer.getTimeNow())
                        .add( "length" , 60)))
                .add("mode" , "OFF")
                .add("start" , "00:01")
                .add("length" , 120)
                .add("removeIndex" , 0)
                .add("command" , "mode")
                .add( "option" , factory.createObjectBuilder()
                    .add("onTime", "00.30")
                    .add("length", 60)
                    .add("removeIndex", 0)
                    .add("modeOpt", "off"))
                .build();


        return value;
    }
}
