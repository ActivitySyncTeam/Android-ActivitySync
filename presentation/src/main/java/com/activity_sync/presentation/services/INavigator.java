package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.UserUpdate;

public interface INavigator
{
    void openDummyScreen();

    void openIntroScreen();

    void openLoginScreen();

    void openRegisterScreen();

    void openEventsScreen();

    void openEventDetailsScreen(int eventId);

    void openUserDetailsScreen(String userId);

    void openSettingsScreen();

    void openChangePasswordScreen();

    void openEditAccountScreen(UserUpdate userUpdateDetails);

    void openParticipantsScreen(boolean isOrganizer);

    void openEventCreatorScreen();

    void openEventUpdateScreen(int eventId);

    void openCommentsScreen(int eventId);

    void openFriendsScreen();

    void startBackgroundService();

    void stopBackgroundService();
}
