package com.activity_sync.services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.activity_sync.App;
import com.activity_sync.presentation.services.IPermanentStorage;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import timber.log.Timber;

public class BackgroundService extends Service
{
    @Inject
    IPermanentStorage permanentStorage;

    private long timerTime = 30 * 1000;     //30 seconds

    private LocationService locationService;
    private ExecutorService executor;
    private Timer timer;
    private TimerTask timerTask;
    private Runnable backgroundOperation = () -> {

        Timber.d("Task which will be called every 30 seconds");

    };

    @Override
    public void onCreate()
    {
        super.onCreate();
        App.component(this).inject(this);
        Timber.d("BackgroundService Started");

        locationService = new LocationService(getApplicationContext(), permanentStorage);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationService.start();
        }

        executor = Executors.newCachedThreadPool();
        startRequestingServer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void startRequestingServer()
    {
        stopRequestingServer();

        timerTask = new TimerTask()
        {
            public void run()
            {
                executor.execute(backgroundOperation);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, timerTime);
    }

    private void stopRequestingServer()
    {
        if (timer == null)
        {
            return;
        }

        timerTask.cancel();
        timer.cancel();
        timer.purge();
        timer = null;
    }

    @Override
    public void onDestroy()
    {
        Timber.d("BackgroundService Ended");
        locationService.stop();
        stopRequestingServer();
    }
}
