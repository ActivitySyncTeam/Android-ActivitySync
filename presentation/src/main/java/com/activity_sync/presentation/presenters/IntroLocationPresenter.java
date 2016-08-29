package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.views.IIntroLocationView;

import rx.Scheduler;

public class IntroLocationPresenter extends Presenter<IIntroLocationView>
{
    private final Scheduler uiThread;

    public IntroLocationPresenter(Scheduler uiThread, IIntroLocationView view)
    {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start() {
        super.start();

        subscriptions.add(view.allowPermissionClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.permissionFlow();
                })
        );
    }
}
