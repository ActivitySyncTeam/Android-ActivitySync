package com.activity_sync.services;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.activity_sync.presentation.events.LocationChangeEvent;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener
{
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 1000;       //5 minutes
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 3 * 1000;   //1 minute

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    private final Context context;
    private final IPermanentStorage permanentStorage;

    public LocationService(Context context, IPermanentStorage permanentStorage)
    {
        this.context = context;
        this.permanentStorage = permanentStorage;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        if (googleApiClient == null)
        {
            buildGoogleApiClient(context);
        }

        startLocationUpdates();
    }

    protected synchronized void buildGoogleApiClient(Context context)
    {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();

        createLocationRequest();
    }

    protected void createLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }

    protected void start()
    {
        if (googleApiClient == null)
        {
            buildGoogleApiClient(context);
            googleApiClient.connect();
        }
        else if (!googleApiClient.isConnected())
        {
            googleApiClient.connect();
        }
        else if (googleApiClient.isConnected())
        {
            startLocationUpdates();
        }
    }

    protected void stop()
    {
        if (googleApiClient.isConnected())
        {
            stopLocationUpdates();
            googleApiClient.disconnect();
        }
    }

    private void startLocationUpdates()
    {
        try
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
        catch (SecurityException securityException)
        {
            Timber.e("Permission: %s", ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION));
        }
    }

    private void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Timber.d("Connection failed: ConnectionResult.errorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Timber.d("Location changed: %s %s", location.getLatitude(), location.getLongitude());
        permanentStorage.saveFloat(IPermanentStorage.LAST_LATITUDE, (float) location.getLatitude());
        permanentStorage.saveFloat(IPermanentStorage.LAST_LONGITUDE, (float) location.getLongitude());

        EventBus.getDefault().post(new LocationChangeEvent());
    }
}
