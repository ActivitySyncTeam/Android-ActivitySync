package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.Event;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface IActivitySyncApi
{
    @GET("/events/all")
    Observable<List<Event>> getEvents();

    @GET("/events/{id}")
    Observable<Event> getEvent(@Path("id") int id);
}
