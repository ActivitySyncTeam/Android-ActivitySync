package com.activity_sync.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.activity_sync.R;
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

        addSlide(new IntroHappyScreen());
        addSlide(new IntroEasyOrganizingScreen());
        addSlide(new IntroSimpleScreen());
        addSlide(new IntroSimpleScreen());

        setColorTransitionsEnabled(true);
        setIndicatorColor(ContextCompat.getColor(this, R.color.intro_indicator_selected),
                ContextCompat.getColor(this, R.color.intro_indicator_unselected));
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
