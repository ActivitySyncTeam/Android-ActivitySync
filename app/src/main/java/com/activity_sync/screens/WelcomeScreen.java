package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.WelcomePresenter;
import com.activity_sync.presentation.views.IWelcomeView;

public class WelcomeScreen extends Screen implements IWelcomeView
{
    public WelcomeScreen()
    {
        super(R.layout.welcome_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new WelcomePresenter(this);
    }
}
