package com.activity_sync;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import timber.log.Timber;

public class AppBase extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Timber.d("Activity Sync");
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
