package com.activity_sync.presentation.views;

import rx.Observable;

public interface IDummyView
{
    Observable displayMessageClick();

    Observable openIntroScreenClick();

    Observable showProgressClick();

    Observable hideProgressClick();

    void displayMessage();

    void showProgressBar();

    void hideProgressBar();
}
