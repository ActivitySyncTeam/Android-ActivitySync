package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import rx.Scheduler;

public class EventCreatorPresenter extends Presenter<IEventCreatorView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public EventCreatorPresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        view.openDisciplineSpinner(new String[]{
                "dupa",
                "elo",
                "test",
                "xd",
                "kappa"
        });

        subscriptions.add(view.noeldoClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.openPickerScreen();
                })
        );
    }
}
