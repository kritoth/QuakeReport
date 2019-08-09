package com.example.android.quakereport;

/**
 * Created by tians on 2019. 07. 27..
 */

public class Earthquake {

    private String location;
    private double magnitude;
    private long timeInMillisec;

    public Earthquake(String location, double magnitude, long date) {
        this.location = location;
        this.magnitude = magnitude;
        this.timeInMillisec = date;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTimeInMillisec() {
        return timeInMillisec;
    }
}
