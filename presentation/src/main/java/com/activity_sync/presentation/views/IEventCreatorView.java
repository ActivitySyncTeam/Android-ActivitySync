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

    Discipline discipline();

    Level level();

    String players();

    String description();

    boolean isOrganizerEnrolled();

    void date(String date);

    void location(String location);

    void level(Level level);

    void discipline(Discipline discipline);

    void playersNumber(String players);

    void description(String description);

    Observable createEventClick();

    Observable<Location> newLocationEvent();

    Observable locationErrorEvent();

    Observable<String> newDateEvent();

    Observable openLocationPickerScreenClick();

    Observable openDatePickerClick();

    void openLocationPickerScreen();

    void openDatePicker();

    void showPickerLocationErrorMessage();

    void showCreateConfirmationDialog();

    void showUpdateConfirmationDialog();

    Observable confirmActionClickEvent();
}
