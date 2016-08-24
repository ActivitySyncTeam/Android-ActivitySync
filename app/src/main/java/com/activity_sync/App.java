package com.activity_sync;

import android.content.Context;
import com.activity_sync.di.DiComponent;
import timber.log.Timber;

public class App extends AppBase
{
    private DiComponent component;

    private void buildComponentAndInject()
    {
        component = DiComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        buildComponentAndInject();
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

    public static DiComponent component(Context context)
    {
        return ((App) context.getApplicationContext()).component;
    }
}
