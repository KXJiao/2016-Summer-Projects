package cn.truthvision.stopsignlib;

import java.sql.Time;

/**
 * Created by TV_Laptop_01 on 8/9/2016.
 */
public class Violation {
    private Time time;
    private int hour;
    private int min;
    private int sec;
    private String desc;
    private int descval;

    public Violation(){
        time = new Time(0);
        hour = -1;
        min = -1;
        sec = -1;
        descval = -1;
        desc = "Do not use default constructor: it is for debugging purposes";
    }
    //Time is better version of Date
    public Violation(Time time, int vidHr, int vidMin, int vidSec, int descval){
        this.time = time; // Time is for the time the violation occured
        this.hour = vidHr; // These time units are for the time in the Video it occured
        this.min = vidMin;
        this.sec = vidSec;
        this.desc = findDesc(descval);
    }

    private String findDesc(int descval) {
        switch(descval){
            case 0: return "test";
            case 1: return "Did not stop, no attempt to stop";
            case 2: return "Did not stop, attempt to stop";
            case 3: return "Did not stop fully";
            case 4: return "akfjladsjfal '); DROP TABLE Videos; --";
            default: return "Other violation (possible error)";
        }
    }
    public String getDesc(){
        return desc;
    }

    public void setDesc(int val){
        this.descval = val;
        this.desc = findDesc(val);
    }


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getVidTime(){
        return hour + ":" + min + ":" + sec;
    }

    public void setVidTime(int hour, int min, int sec){
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }
    @Override
    public String toString(){
        return getTime() + ", @" + getVidTime() + " (" + desc + ")";
    }

}
