package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IIntroBaseView;

import rx.Scheduler;

public class IntroBasePresenter extends Presenter<IIntroBaseView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public IntroBasePresenter(Scheduler uiThread, IIntroBaseView view, INavigator navigator) {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.skipButtonClicked()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openLoginScreen();
                })
        );

        subscriptions.add(view.doneButtonClicked()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openLoginScreen();
                })
        );
    }
}
