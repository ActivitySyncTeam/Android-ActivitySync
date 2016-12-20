package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;

import retrofit2.http.GET;
import rx.Observable;

public interface IActivitySyncApi
{
    @GET("/api/disciplines")
    Observable<AvailableDisciplines> getAvailableDisciplines();

    @GET("/api/levels")
    Observable<AvailableLevels> getAvailableLevels();
}
