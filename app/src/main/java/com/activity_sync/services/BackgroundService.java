package com.activity_sync.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.activity_sync.App;
import com.activity_sync.presentation.events.LocationPermissionGranted;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
import com.activity_sync.presentation.services.IPermanentStorage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BackgroundService extends Service
{
    @Inject
    IPermanentStorage permanentStorage;

    @Inject
    IApiService apiService;

    @Inject
    IErrorHandler errorHandler;

    private long timerTime = 30 * 1000;     //30 seconds

    private LocationService locationService;
    private NotificationService notificationService;
    private ExecutorService executor;
    private Timer timer;
    private TimerTask timerTask;
    private Runnable backgroundOperation = () -> {

        apiService.getNotifications()
                .observeOn(Schedulers.newThread())
                .subscribe(apiMessages -> {

                    if (apiMessages.size() > 0)
                    {
                        notificationService.handleMessage(apiMessages.get(0));
                    }

                }, this::handleError);
    };

    @Override
    public void onCreate()
    {
        super.onCreate();
        App.component(this).inject(this);
        Timber.d("BackgroundService Started");

        EventBus.getDefault().register(this);

        locationService = new LocationService(getApplicationContext(), permanentStorage);
        notificationService = new NotificationService(getApplicationContext(), (NotificationManager) getSystemService(NOTIFICATION_SERVICE));

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

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(LocationPermissionGranted event)
    {
        Timber.d("Location permission has been granted");
        locationService.start();
    }

    private void handleError(Throwable error)
    {
        errorHandler.handleError(error);
        error.printStackTrace();
        Timber.d(error.getMessage());
    }
}
