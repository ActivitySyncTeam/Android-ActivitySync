package com.activity_sync;

import com.activity_sync.di.DiComponent;
import timber.log.Timber;

public class App extends AppBase
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        initializeLogging();
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    private void initializeLogging()
    {
        Timber.plant(new Timber.DebugTree());
    }
}
