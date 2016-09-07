package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IDummyView;

import rx.Scheduler;

public class DummyPresenter extends Presenter<IDummyView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public DummyPresenter(Scheduler uiThread, IDummyView view, INavigator navigator)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.openDummyScreenClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openDummyScreen();
                })
        );

        subscriptions.add(view.displayMessageClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.displayMessage();
                })
        );
    }
}