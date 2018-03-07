package com.leighton;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.*;

public class TimerControl {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    private ArrayList<TimerEvent> eventStore;
    private boolean timerOutputState;
    private Controller rc;
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
    private Future<?> runBackThread;
    private ArrayList<ScheduledFuture> threadList = new ArrayList<>();
    private LocalDateTime runBackFinish = LocalDateTime.now();

    public final static int MINS_IN_DAY = 1440;

    TimerControl(Controller rc) {
        eventStore = new ArrayList<TimerEvent>();
        scheduler.setRemoveOnCancelPolicy(true);
        this.rc = rc;
    }


    public JsonArray getJsonTable() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(TimerEvent event: eventStore){
            builder.add(event.getJson());
        }
        return builder.build();
    }

    public void loadEvents(JsonArray jsonTable) {
        try {
            eventStore.clear();
            for(int i = 0; i<jsonTable.size(); i++) {
                eventStore.add(new TimerEvent(jsonTable.get(i).asJsonObject().getString("onTime"),
                                                jsonTable.get(i).asJsonObject().getInt("length")));
            }

        } catch (Exception e) {
        }
    }

    public void addEvent(int start, int length) {
        this.eventStore.add(new TimerEvent(start, length));
        stopTimers(); startAllTimers();
        save();
    }

    public void addEvent(LocalTime start, int length) {
        this.eventStore.add(new TimerEvent(start, length));
        stopTimers(); startAllTimers();
        save();
    }

    public void addEvent(String start, int length) {
        this.eventStore.add(new TimerEvent(start, length));
        stopTimers(); startAllTimers();
        save();
    }


    public void removeEvent(int index) {
        if(index < 0 ) return;
        System.out.println("removeEvent called");
//        this.eventStore.remove(index);
        ScheduledFuture thread = threadList.get(index);
        int timeleft = MINS_IN_DAY - (int) thread.getDelay(TimeUnit.MINUTES);
        int length = eventStore.get(index).getLength();
        if(timeleft < length){
            System.out.print(timeleft + " : " + length);
            rc.timerSwitch(false);
        }

        thread.cancel(true);
        threadList.remove(index);
        eventStore.remove(index);
        initializeRelayState();
        System.out.println(threadList.size() + " :: " + eventStore.size());
        save();
    }

    public String getTimeNow() {
        String timeNow = TimerControl.dtf.format(LocalTime.now());
        return timeNow;
    }

    public int getRunBackFinishMins() {
        LocalDateTime timeNow = LocalDateTime.now();
        if (runBackFinish.isBefore(timeNow)) {
            return 0;
        }else {
            int finMins = runBackFinish.getMinute();
            int finHour = runBackFinish.getHour();
            int nowMin = timeNow.getMinute();
            int nowHour = timeNow.getHour();

            int nowComp = nowMin + nowHour*60;
            int finComp = finMins + finMins*60;

            int diff = finComp - nowComp;

            return diff;
        }
    }

    public void startRunback(int length) {
        runBackFinish = LocalDateTime.now().plusMinutes(length);
        runBackThread = scheduler.submit(new RunnableTimer(length,this.rc));
    }

    public void stopRunback(int length) {
        try {
            if(runBackThread.get() != null) {
                runBackThread.cancel(true);
                rc.timerSwitch(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void startAllTimers() {
                        int i = 0;
        for (TimerEvent event : this.eventStore) {
            threadList.add(i, scheduler.scheduleWithFixedDelay(new RunnableTimer(event.getLength(), this.rc),
                    event.getOffsetFromNow(), MINS_IN_DAY, TimeUnit.MINUTES));

            i++;
        }
        initializeRelayState();
    }

    private void initializeRelayState() {
        rc.timerSwitch(false);
        for(TimerEvent event:eventStore) {
            if (event.getOffsetEndTime() < event.getLength()) {
                scheduler.schedule(
                        new RunnableTimer(event.getLength() - event.getOffsetEndTime(), this.rc),
                        0, TimeUnit.MINUTES);
            }
        }
    }

    public void stopTimers(){
        for( int i = 0 ; i<threadList.size() ; i++ ) {
            threadList.get(i).cancel(true);
        }
        threadList.clear();
        rc.timerSwitch(false);
    }


    public boolean getTimerOutPutState() {
        return this.timerOutputState;
    }

    public void setTimerOutPutState(boolean state) {
        this.timerOutputState = state;
    }


    public void save() {
        ObjectOutputStream outputStream = null;
        try{
            Path path = Paths.get("timerEventSchedule");
            File file = new File(path.toUri());
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(eventStore);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }

    }


    public ArrayList<TimerEvent> getEventList() {
        return this.eventStore;
    }


    public void stopRunback() {
        if(getRunBackFinishMins() > 0 ) {
            runBackThread.cancel(true);
            rc.timerSwitch(false);
        }
    }
}
