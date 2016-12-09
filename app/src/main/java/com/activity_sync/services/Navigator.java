package com.activity_sync.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.activity_sync.presentation.models.UserUpdate;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.screens.ChangePasswordScreen;
import com.activity_sync.screens.CommentsScreen;
import com.activity_sync.screens.CreateNewEventScreen;
import com.activity_sync.screens.DummyScreen;
import com.activity_sync.screens.EditAccountScreen;
import com.activity_sync.screens.EventDetailsScreen;
import com.activity_sync.screens.EventUpdateScreen;
import com.activity_sync.screens.EventsScreen;
import com.activity_sync.screens.IntroScreen;
import com.activity_sync.screens.LoginScreen;
import com.activity_sync.screens.ParticipantsScreen;
import com.activity_sync.screens.RegisterScreen;
import com.activity_sync.screens.SettingsScreen;
import com.activity_sync.screens.UserDetailsScreen;

public class Navigator implements INavigator
{
    private final Context context;

    public Navigator(Context context)
    {
        this.context = context;
    }

    @Override
    public void openDummyScreen()
    {
        startActivity(getIntent(DummyScreen.class));
    }

    @Override
    public void openIntroScreen()
    {
        startActivity(getIntent(IntroScreen.class));
    }

    @Override
    public void openLoginScreen()
    {
        this.startActivity(getIntent(LoginScreen.class));
    }

    @Override
    public void openRegisterScreen()
    {
        this.startActivity(getIntent(RegisterScreen.class));
    }

    @Override
    public void openEventsScreen()
    {
        startActivity(getIntent(EventsScreen.class));
    }

    @Override
    public void openEventDetailsScreen(int eventId)
    {
        Intent intent = new Intent(context, EventDetailsScreen.class);
        intent.putExtra(EventDetailsScreen.EVENT_ID, eventId);
        startActivity(intent);
    }

    @Override
    public void openUserDetailsScreen(int userId)
    {
        startActivity(getIntent(UserDetailsScreen.class));
    }

    @Override
    public void openParticipantsScreen(boolean isOrganizer)
    {
        Intent intent = new Intent(context, ParticipantsScreen.class);
        intent.putExtra(ParticipantsScreen.IS_ORGANIZER, isOrganizer);
        startActivity(intent);
    }

    @Override
    public void openEventCreatorScreen()
    {
        startActivity(getIntent(CreateNewEventScreen.class));
    }

    @Override
    public void openEventUpdateScreen(int eventId)
    {
        Intent intent = new Intent(context, EventUpdateScreen.class);
        intent.putExtra(EventUpdateScreen.EVENT_ID, eventId);
        startActivity(intent);
    }

    @Override
    public void openCommentsScreen(int eventId)
    {
        Intent intent = new Intent(context, CommentsScreen.class);
        intent.putExtra(CommentsScreen.EVENT_ID, eventId);
        startActivity(intent);
    }

    @Override
    public void openSettingsScreen()
    {
        this.startActivity(getIntent(SettingsScreen.class));
    }

    private void startActivity(Intent intent)
    {
        if (context instanceof Activity == false)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    public void openChangePasswordScreen()
    {
        startActivity(getIntent(ChangePasswordScreen.class));
    }

    @Override
    public void openEditAccountScreen(UserUpdate userUpdateDetails)
    {
        Intent intent = new Intent(context, EditAccountScreen.class);
        intent.putExtra(EditAccountScreen.USER_UPDATE_DETAILS, userUpdateDetails);
        startActivity(intent);
    }

    private Intent getIntent(Class<? extends Activity> type)
    {
        return new Intent(context, type);
    }
}
