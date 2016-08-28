package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.views.IIntroSimpleView;
import rx.Scheduler;

public class IntroSimplePresenter extends Presenter<IIntroSimpleView>
{
    private final Scheduler uiThread;

    public IntroSimplePresenter(Scheduler uiThread, IIntroSimpleView view)
    {
        super(view);
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.testBtnClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.displayMessage();
                })
        );
    }
}
