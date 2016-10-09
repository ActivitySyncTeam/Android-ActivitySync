package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;
import java.util.Collection;
import rx.Observable;

public interface IEventsView
{
    Observable<Event> selectedEvent();
    Observable refreshEvents();
    void addEventsList(Collection<Event> events);
    void eventSelected(Event event);
    void refreshingVisible(boolean isRefreshing);
}
