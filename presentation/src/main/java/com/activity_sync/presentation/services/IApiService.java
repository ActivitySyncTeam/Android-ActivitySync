package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.Event;
import java.util.List;
import rx.Observable;

public interface IApiService
{
    Observable<List<Event>> getEvents();

    Observable<Event> getEvent(int eventID);
}
