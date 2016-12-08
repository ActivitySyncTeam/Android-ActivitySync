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

    void openUserDetailsScreen(int userId);

    void openSettingsScreen();

    void openChangePasswordScreen();

    void openEditAccountScreen(UserUpdate userUpdateDetails);

    void openParticipantsScreen(boolean isOrganizer);

    void openEventCreatorScreen();

    void openCommentsScreen(int eventId);

    void openFriendsScreen();
}
