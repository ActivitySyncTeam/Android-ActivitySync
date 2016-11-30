package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.Location;

import java.util.List;

import rx.Observable;

public interface IEventCreatorView
{
    void prepareDisciplineSpinner(List<Discipline> disciplines);

    void prepareLevelSpinner(List<Level> disciplines);

    void preparePlayersSpinner();

    String date();

    String location();

    void date(String date);

    void location(String location);

    Discipline discipline();

    Level level();

    String players();

    Observable createEventClick();

    Observable<Location> newLocationEvent();

    Observable locationErrorEvent();

    void openPickerScreen();

    void showPickerLocationErrorMessage();
}
