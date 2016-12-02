package com.activity_sync.presentation.services;

public interface INavigator
{
    void openDummyScreen();

    void openIntroScreen();

    void openLoginScreen();

    void openRegisterScreen();

    void openEventsScreen();

    void openEventDetailsScreen(int eventId);

    void openUserDetailsScreen(int userId);

    void openParticipantsScreen();

    void openEventCreatorScreen();
}
