package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.views.IIntroWelcomeView;

import rx.Scheduler;
import timber.log.Timber;

public class IntroWelcomePresenter extends Presenter<IIntroWelcomeView>
{
    private final Scheduler uiThread;

    public IntroWelcomePresenter(Scheduler uiThread, IIntroWelcomeView view)
    {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.noEloClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    Timber.d("no elo clicked");
                })
        );
    }
}
