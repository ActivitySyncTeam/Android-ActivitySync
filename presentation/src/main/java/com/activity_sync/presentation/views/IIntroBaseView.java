package com.activity_sync.presentation.views;

import rx.Observable;

public interface IIntroBaseView
{
    Observable skipButtonClicked();

    Observable doneButtonClicked();
}
