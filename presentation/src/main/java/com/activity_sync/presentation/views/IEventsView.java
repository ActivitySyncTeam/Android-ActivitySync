package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;
import java.util.Collection;
import rx.Observable;

public interface IEventsView
{
    Observable<Event> selectedEvent();

    void addEvent(Event event);
    void addEventsList(Collection<Event> events);
    void showError();

    void eventSelected(Event event);
}
