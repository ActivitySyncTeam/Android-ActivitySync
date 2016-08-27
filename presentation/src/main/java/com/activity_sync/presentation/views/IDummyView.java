package com.activity_sync.presentation.views;

import rx.Observable;

public interface IDummyView
{
    Observable displayMessageClick();

    Observable openDummyScreenClick();

    void displayMessage();
}
