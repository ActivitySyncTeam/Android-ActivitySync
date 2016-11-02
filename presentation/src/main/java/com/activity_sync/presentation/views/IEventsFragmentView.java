package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;
import java.util.Collection;
import rx.Observable;

public interface IEventsFragmentView
{
    Observable<Event> selectedEvent();
    Observable refreshEvents();
    void addEventsList(Collection<Event> events);
    void refreshingVisible(boolean isRefreshing);
}
