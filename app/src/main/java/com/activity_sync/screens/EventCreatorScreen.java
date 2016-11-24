package com.activity_sync.screens;

import android.os.Bundle;

import com.activity_sync.App;
import com.activity_sync.R;
import com.activity_sync.presentation.presenters.EventCreatorPresenter;
import com.activity_sync.presentation.presenters.IPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;

public class EventCreatorScreen extends Screen implements IEventCreatorView
{
    @Inject
    INavigator navigator;

    public EventCreatorScreen()
    {
        super(R.layout.event_creator_screen);
    }

    @Override
    protected IPresenter createPresenter(Screen screen, Bundle savedInstanceState)
    {
        return new EventCreatorPresenter(AndroidSchedulers.mainThread(), this, navigator);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        App.component(this).inject(this);
        super.onCreate(savedInstanceState);
    }
}
