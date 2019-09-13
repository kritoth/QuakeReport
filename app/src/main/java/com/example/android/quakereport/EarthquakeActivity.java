/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL to query the USGS dataset for earthquake information
     * to be followed by: format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?";

    TextView emptyView;
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        getLoaderManager().initLoader(1, null, this).forceLoad();
        Log.i(LOG_TAG, " initloader() initialized");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates the {@link}EarthQuakeLoader and sending it the query parameters as Strings
     * The query parameters are built up partly from {@link SharedPreferences} chosen by the user
     * and partly from some default values like format, eventtype and limit
     * @param id
     * @param args
     * @return {@link EarthquakeLoader(android.content.Context, String)}
     */
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, " onCreateLoader started");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // search for the users preference for magnitude
        String minMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        // search for the users preference for time
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("format", "geojson");
        builder.appendQueryParameter("eventtype", "earthquake");
        builder.appendQueryParameter("orderby", orderBy);
        builder.appendQueryParameter("minmag", minMagnitude);
        builder.appendQueryParameter("limit", "10");

        return new EarthquakeLoader(this, builder.toString());
    }

    /**
     * It is called by the {@link}loadInBackground() to update the UI with the fetched data
     * @param loader
     * @param earthquakes
     */
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.i(LOG_TAG, " onLoadFinished started");

        if (earthquakes == null || earthquakes.size()==0) {
            return;
        }
        updateUi(earthquakes);
    }

    /**
     * Resets the loader into a new empty {@link}EarthquakeAdapter
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i(LOG_TAG, " onLoaderReset started");
        new EarthquakeAdapter(this, new ArrayList<Earthquake>());
    }

    private void updateUi(List<Earthquake> earthquakes){
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Set the text of the emptyview to let it show something, if there are nothing to show
        emptyView = (TextView) findViewById(R.id.empty_view);
        if(earthquakes.isEmpty() || earthquakes == null) {
            emptyView.setText(R.string.empty_state_message);
        }

        // Create loader spinner and set it to GONE as default
        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadingSpinner.setVisibility(View.GONE);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        // On each item listen for click and initiate intent for opening the item's url
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEarthquakeWeb(((Earthquake) adapter.getItem(position)).getUrl());
            }
        });
    }

    /**
     * build the implicit {@link Intent} for any Action to show where the {@param url} is point to
     */
    private void openEarthquakeWeb (String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
