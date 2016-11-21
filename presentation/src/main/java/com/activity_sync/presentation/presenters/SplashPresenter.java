package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISplashView;

import rx.Scheduler;

public class SplashPresenter extends Presenter<ISplashView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IPermanentStorage permanentStorage;

    public SplashPresenter(ISplashView view, Scheduler uiThread, INavigator navigator, IPermanentStorage permanentStorage)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.permanentStorage = permanentStorage;
    }

    @Override
    public void start()
    {
        super.start();

        if (permanentStorage.retrieveBoolean(IPermanentStorage.IS_APP_OPENED_BEFORE, false))
        {
            navigator.openWelcomeScreen();
        }
        else
        {
            navigator.openIntroScreen();
        }

        view.finishSplashScreen();
    }
}
