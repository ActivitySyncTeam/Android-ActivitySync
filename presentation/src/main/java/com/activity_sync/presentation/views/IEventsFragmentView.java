package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;

import java.util.Collection;

import rx.Observable;

public interface IEventsFragmentView
{
    void askForPermission();

    void eventsListVisible(boolean isVisible);
    void addEventsList(Collection<Event> events);
    void refreshingVisible(boolean isRefreshing);

    boolean checkLocationPermissions();

    int getViewPagerCurrentFragmentIndex();

    int getIndex();

    Observable enableLocationButtonClick();

    Observable refreshEvents();

    Observable<Boolean> locationEnabled();

    Observable<Event> selectedEvent();
}
