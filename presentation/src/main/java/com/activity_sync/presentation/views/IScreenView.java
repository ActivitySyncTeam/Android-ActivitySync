package com.activity_sync.presentation.views;


public interface IScreenView
{
    void displayDefaultError();

    void displayNetworkError();

    void showProgressBar();

    void hideProgressBar();
}
