package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Location;

import java.util.List;

import rx.Observable;

public interface IEventCreatorView
{
    void openDisciplineSpinner(List<String> disciplines);

    void openLevelSpinner(List<String> disciplines);

    void openPlayersSpinner();

    String date();

    String location();

    void date(String date);

    void location(String location);

    String discipline();

    String level();

    String players();

    Observable createEventClick();

    Observable<Location> newLocationEvent();

    Observable locationErrorEvent();

    void openPickerScreen();

    void showPickerLocationErrorMessage();
}
