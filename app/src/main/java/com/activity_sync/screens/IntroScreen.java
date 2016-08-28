package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.services.Navigator;
import com.github.paolorotolo.appintro.AppIntro2;

public class IntroScreen extends AppIntro2
{
    private INavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        navigator = new Navigator(this);

        addSlide(new IntroWelcomeScreen());
        addSlide(new IntroHappyScreen());
        addSlide(new IntroSimpleScreen());
        addSlide(new IntroSimpleScreen());

        setColorTransitionsEnabled(true);
    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        super.onDonePressed(currentFragment);
        navigator.openDummyScreen();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);
        navigator.openDummyScreen();
    }
}
