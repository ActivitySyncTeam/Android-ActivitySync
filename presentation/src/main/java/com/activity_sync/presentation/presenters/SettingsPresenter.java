package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ISettingsView;

import rx.Scheduler;

public class SettingsPresenter extends Presenter<ISettingsView>
{
    private final INavigator navigator;
    private Scheduler uiThread;

    public SettingsPresenter(ISettingsView view, INavigator navigator, Scheduler uiThread)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }
}
