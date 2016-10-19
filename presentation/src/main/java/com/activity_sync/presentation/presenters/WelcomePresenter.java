package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IWelcomeView;

import rx.Scheduler;

public class WelcomePresenter extends Presenter<IWelcomeView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public WelcomePresenter(Scheduler uiThread, IWelcomeView view, INavigator navigator)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.loginBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openDummyScreen();
                })
        );

        subscriptions.add(view.registerBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openDummyScreen();
                })
        );
    }
}
