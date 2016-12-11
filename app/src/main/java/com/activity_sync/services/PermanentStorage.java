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
    public boolean retrieveBoolean(String key, boolean defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public int retrieveInteger(String key, int defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public String retrieveString(String key, String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public float retrieveFloat(String key, float defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public void saveBoolean(String key, boolean value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public void saveInteger(String key, int value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public void saveString(String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void saveFloat(String key, float value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    @Override
    public void clear()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVITY_SYNC_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

}
