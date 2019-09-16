package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tians on 2019. 08. 30..
 * Custom {@link}AsyncTaskLoader</E> for networking in the background-thread
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    /**
     * To be called from the {@link EarthquakeActivity#onCreateLoader(int, Bundle)}}
     * @param context to save the activity
     * @param url the String representing the url to be used for http request
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    /**
     * Creates the http connection, fetches the JSON data and
     * @return the fetched list of {@link}Earthquake
     */
    @Override
    public List<Earthquake> loadInBackground() {
        Log.i(LOG_TAG, " loadInBackground started");

        // Create URL object
        URL url = QueryUtils.createUrl(mUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with Http request: ", e);
        }
        // always prefer local over global variable
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(jsonResponse);

        return earthquakes;
    }
}
