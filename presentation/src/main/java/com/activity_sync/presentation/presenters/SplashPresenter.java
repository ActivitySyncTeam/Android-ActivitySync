package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.ISplashView;

import rx.Scheduler;

public class SplashPresenter extends Presenter<ISplashView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public SplashPresenter(ISplashView view, Scheduler uiThread, INavigator navigator)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start()
    {
        super.start();

        //will be more conditions here
        navigator.openIntroScreen();
    }
}
