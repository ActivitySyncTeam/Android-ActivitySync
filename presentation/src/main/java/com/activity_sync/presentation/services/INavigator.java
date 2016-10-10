package com.activity_sync.presentation.services;

public interface INavigator
{
    void openDummyScreen();

    void openIntroScreen();

    void openWelcomeScreen();

    void openEventsScreen();

    void openEventDetailsScreen(int eventId);
}
