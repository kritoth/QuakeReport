package com.example.android.quakereport;

/**
 * Created by tians on 2019. 07. 27..
 */

public class Earthquake {

    private String location;
    private double magnitude;
    private String date;

    public Earthquake(String location, double magnitude, String date) {
        this.location = location;
        this.magnitude = magnitude;
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getDate() {
        return date;
    }
}
