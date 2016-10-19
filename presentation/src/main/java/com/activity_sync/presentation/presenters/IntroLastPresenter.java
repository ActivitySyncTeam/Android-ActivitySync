package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IIntroLastView;
import rx.Scheduler;

public class IntroLastPresenter extends Presenter<IIntroLastView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public IntroLastPresenter(Scheduler uiThread, IIntroLastView view, INavigator navigator)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.checkImageClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openWelcomeScreen();
                })
        );
    }
}
