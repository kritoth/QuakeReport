package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tians on 2019. 07. 27..
 */

public class EarthquakeAdapter extends ArrayAdapter {

    public EarthquakeAdapter(@NonNull Context context, @NonNull ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quake_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currEarthquake = (Earthquake) getItem(position);

        // Find the TextView in the quake_item.xml layout with the ID magnitude
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the magnitude from the current EarthQukae object and
        // set this text on the name TextView
        magnitudeTextView.setText(Double.toString(currEarthquake.getMagnitude()));

        // Find the TextView in the quake_item.xml layout with the ID location_vicinity
        TextView locationVicinityTextView = (TextView) listItemView.findViewById(R.id.location_vicinity);
        // Get the location name from the current EarthQukae object and
        // set this text on the name TextView
        locationVicinityTextView.setText(currEarthquake.getLocation());

        // Find the TextView in the quake_item.xml layout with the ID location
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location);
        // Get the location name from the current EarthQukae object and
        // set this text on the name TextView
        locationTextView.setText(currEarthquake.getLocation());

        // Find the TextView in the quake_item.xml layout with the ID time
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Get the date from the current EarthQukae object and
        // set this text on the name TextView
        timeTextView.setText(formatTime(currEarthquake.getTimeInMillisec()));

        // Find the TextView in the quake_item.xml layout with the ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Get the date from the current EarthQukae object and
        // set this text on the name TextView
        dateTextView.setText(formatDate(currEarthquake.getTimeInMillisec()));

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private static String formatTime (long unixTime){
        Date time = new Date(unixTime);
        SimpleDateFormat formattedDate = new SimpleDateFormat("kk:mm:ss");
        return formattedDate.format(time);
    }

    private static String formatDate (long unixTime){
        Date date = new Date(unixTime);
        SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy:MM:dd");
        return formattedDate.format(date);
    }
}
