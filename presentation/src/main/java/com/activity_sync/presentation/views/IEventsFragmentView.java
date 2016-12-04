package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;

import java.util.Collection;
import java.util.List;

import rx.Observable;

public interface IEventsFragmentView
{
    Observable<Event> selectedEvent();
    Observable refreshEvents();

    Observable searchDateClick();

    Observable<String> newDateEvent();

    void addEventsList(Collection<Event> events);
    void refreshingVisible(boolean isRefreshing);

    void setFragmentToolbarVisibility(int visibility);
    void openDatePicker();
    void setDate(String date);
    void prepareDisciplineSpinner(List<Discipline> disciplines);
    Discipline getDiscipline();

    boolean checkLocationPermissions();
    void askForPermission();
    Observable<Boolean> locationEnabled();
    Observable enableLocationButtonClick();

    void eventsListVisible(boolean isVisible);
}
