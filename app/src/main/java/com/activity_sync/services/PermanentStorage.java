package com.activity_sync.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.activity_sync.presentation.services.IPermanentStorage;

public class PermanentStorage implements IPermanentStorage
{
        public static final String ACTIVITY_SYNC_PREFERENCES = "ActivitySyncPreferences";
        private final Context context;

        public PermanentStorage(Context context)
        {
            this.context = context;
        }

        @Override
        public void save(String key, boolean value)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }

        @Override
        public boolean retrieveBoolean(String key, boolean defaultValue)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(key, defaultValue);
        }

        @Override
        public void clear()
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
        }

}
