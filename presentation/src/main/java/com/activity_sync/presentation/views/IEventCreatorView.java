package com.activity_sync.presentation.views;

import rx.Observable;

public interface IEventCreatorView
{
    void openDisciplineSpinner(String[] disciplines);

    Observable noeldoClick();

    void openPickerScreen();
}
