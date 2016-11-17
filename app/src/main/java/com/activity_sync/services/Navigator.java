package com.activity_sync.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.screens.*;

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
        this.startActivity(getIntent(DummyScreen.class));
    }

    @Override
    public void openIntroScreen()
    {
        this.startActivity(getIntent(IntroScreen.class));
    }

    @Override
    public void openWelcomeScreen()
    {
        this.startActivity(getIntent(LoginScreen.class));
    }

    @Override
    public void openEventsScreen()
    {
        this.startActivity(getIntent(EventsScreen.class));
    }

    @Override
    public void openEventDetailsScreen(int eventId)
    {
        this.startActivity(getIntent(EventDetailsScreen.class));
    }

    @Override
    public void openUserDetailsScreen(int userId)
    {
        this.startActivity(getIntent(UserDetailsScreen.class));
    }

    @Override
    public void openParticipantsScreen()
    {
        this.startActivity(getIntent(ParticipantsScreen.class));
    }

    private void startActivity(Intent intent)
    {
        if (context instanceof Activity == false)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private Intent getIntent(Class<? extends Activity> type)
    {
        return new Intent(context, type);
    }
}
