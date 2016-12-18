package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.List;

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
    void filterLayoutVisible(boolean isVisible);

    Observable enableLocationButtonClick();

    void postLocationPermissionsMessage();
    Observable<Location> locationFound();

    void prepareDisciplineSpinner(List<Discipline> disciplines);

    Observable dateLayoutClicked();
    void openDatePicker(DateTime dateTime);
    Observable<DateTime> newDateEvent();
    void setDate(DateTime dateTime);
}
