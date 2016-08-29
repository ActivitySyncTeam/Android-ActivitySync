package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.views.IIntroLastView;

import rx.Scheduler;

public class IntroLastPresenter extends Presenter<IIntroLastView>
{
    private final Scheduler uiThread;

    public IntroLastPresenter(Scheduler uiThread, IIntroLastView view) {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start() {
        super.start();

        subscriptions.add(view.checkImageClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.endFlow();
                })
        );
    }
}
