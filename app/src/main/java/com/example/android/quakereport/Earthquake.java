package com.example.android.quakereport;

/**
 * Created by tians on 2019. 07. 27..
 */

public class Earthquake {

    private String location;
    private double magnitude;
    private long timeInMillisec;
    private String url;

    public Earthquake(String location, double magnitude, long date, String url) {
        this.location = location;
        this.magnitude = magnitude;
        this.timeInMillisec = date;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}
