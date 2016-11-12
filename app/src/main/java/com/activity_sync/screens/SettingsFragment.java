package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.activity_sync.R;

public class SettingsFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
    {
        addPreferencesFromResource(R.xml.settings);
    }
}
