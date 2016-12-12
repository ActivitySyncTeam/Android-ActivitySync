package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;

import java.util.Collection;
import rx.Observable;

public interface IEventsFragmentView
{
    Observable<Event> selectedEvent();
    Observable refreshEvents();
    void addEventsList(Collection<Event> events);
    void refreshingVisible(boolean isRefreshing);

    boolean checkLocationPermissions();
    void askForPermission();

    Observable<Boolean> locationEnabled();

    void eventsListVisible();
    void noPermissionLayoutVisible();
    void searchingForCordsVisible();

    Observable enableLocationButtonClick();

    void postLocationPermissionsMessage();
    Observable<Location> locationFound();
}
