package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.User;

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

    void openAllUsersScreen();

    void openEditAccountScreen(User user);

    void openParticipantsScreen(boolean isOrganizer, int eventId);

    void openEventCreatorScreen();

    void openEventUpdateScreen(Event event);

    void openCommentsScreen(int eventId);

    void openFriendsScreen();

    void openMyProfileScreen();

    void startBackgroundService();

    void stopBackgroundService();
}
