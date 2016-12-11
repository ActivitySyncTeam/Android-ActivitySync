package com.activity_sync.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class BackgroundService extends Service
{
    private long timerTime = 30 * 1000;

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
        Timber.d("BackgroundService Started");

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
        stopRequestingServer();
    }
}
