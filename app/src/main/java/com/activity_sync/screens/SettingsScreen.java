package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.presenters.SettingsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ISettingsView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class SettingsScreen extends ScreenWithMenu implements ISettingsView
{
    @Inject
    INavigator navigator;

    public SettingsScreen()
    {
        super(R.layout.settings_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_settings);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new SettingsPresenter(this, navigator, AndroidSchedulers.mainThread());
    }


}
