package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
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

    /** URL to query the USGS dataset for earthquake information */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /**
     * To be called from the {@link}onCreateLoader(int id, Bundle args)
     * @param context to save the activity
     */
    public EarthquakeLoader(Context context) {
        super(context);
    }

    /**
     * Creates the http connection, fetches the JSON data and
     * @return the fetched list of {@link}Earthquake
     */
    @Override
    public List<Earthquake> loadInBackground() {

        // Create URL object
        URL url = QueryUtils.createUrl(USGS_REQUEST_URL);

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
