package com.leighton;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalTime;

public class TimerEvent implements Serializable {

    private int event[] = new int[2];

    public TimerEvent(int minutesFrom0000, int length) throws IllegalArgumentException {
        if (minutesFrom0000 >= 1440 || 0 > minutesFrom0000 || 0 > length || length >= 1439)
            throw new IllegalArgumentException("minutes between 0 and 1439");

        this.event[0] = minutesFrom0000;
        this.event[1] = length;
    }

    public TimerEvent(LocalTime time, int length) throws IllegalArgumentException {
        this(time.toSecondOfDay() / 60, length);
    }

    public TimerEvent(String time, int length) throws DateTimeException {
        this(LocalTime.parse(time, TimerControl.dtf), length);
    }

    public int getLength() {
        return this.event[1];
    }

    public JsonObject getJson() {
         JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("onTime", getEventTime().toString())
                .add( "length", getLength());
        return builder.build();
    }

    public int getEndTime() {
        int et = getMinuteOfDay() + getLength();
        if(et >=1440) {
            et -= 1440;
        }
        return et;
    }

    public int getOffsetEndTime() {
        int et = getOffsetFromNow() + getLength();
        if(et >=1440) {
            et -= 1440;
        }
        return et;
    }


    public int getMinuteOfDay() {
        return this.event[0];
    }
    public LocalTime getEventTime() {
        return LocalTime.of(event[0]/60, event[0] % 60);
    }

    public int getOffsetFromNow() {
        LocalTime timeNow = LocalTime.parse(TimerControl.dtf.format(LocalTime.now()));

        LocalTime offsetTime = getEventTime()
                .minusHours(timeNow.getHour())
                .minusMinutes(timeNow.getMinute());
        return offsetTime.getHour() *60 + offsetTime.getMinute();

    }

}
