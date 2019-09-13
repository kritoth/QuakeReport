package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class QuakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference minMagnitudePref = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitudePref);

            Preference orderByPreference = findPreference((getString(R.string.settings_order_by_key)));
            bindPreferenceSummaryToValue(orderByPreference);
        }

        /**
         * To save the changed state of the preference right when it is clicked
         * @param preference to be changed
         * @param newValue to be saved into the preference
         * @return true if it succeeds
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            // If the preference is a ListPreference then find the item in the list and set its value to the preference
            // else set the value to the preference
            if(preference instanceof ListPreference){
                ListPreference listPreference = (ListPreference) preference;
                int indexOfValue = listPreference.findIndexOfValue(value);
                if(indexOfValue >= 0){
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[indexOfValue]);
                }
            }
            else {
                preference.setSummary(value);
            }
            return true;
        }

        /**
         *
         * @param preference
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = sharedPreferences.getString(getString(R.string.settings_min_magnitude_key), "");
            onPreferenceChange(preference, preferenceString);
        }

    }

}
