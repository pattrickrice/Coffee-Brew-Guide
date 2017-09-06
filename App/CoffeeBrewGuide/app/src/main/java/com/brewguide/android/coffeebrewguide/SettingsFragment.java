package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceFragmentCompat;

/***************************************************************************************************
 * Fragment that displays user preferences
 **************************************************************************************************/
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();


        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        //This line is required for the status bar color to stay consistent. Not sure why.
        getActivity().getWindow().setStatusBarColor(ContextCompat
                .getColor(context, R.color.colorPrimaryDark));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}
