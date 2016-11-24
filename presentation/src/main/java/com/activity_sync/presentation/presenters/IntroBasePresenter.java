package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IIntroBaseView;

import rx.Scheduler;

public class IntroBasePresenter extends Presenter<IIntroBaseView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IPermanentStorage permanentStorage;

    public IntroBasePresenter(Scheduler uiThread, IIntroBaseView view, INavigator navigator, IPermanentStorage permanentStorage)
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

        permanentStorage.save(IPermanentStorage.IS_APP_OPENED_BEFORE, true);

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
