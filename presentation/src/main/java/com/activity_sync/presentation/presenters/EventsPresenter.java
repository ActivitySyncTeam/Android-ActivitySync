package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;

import rx.Scheduler;

public class EventsPresenter extends Presenter<IEventsView>
{
    private final INavigator navigator;
    private Scheduler uiThread;

    public EventsPresenter(IEventsView view, INavigator navigator, Scheduler uiThread) {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.addNewEventClick()
                .subscribe(event -> {
                    navigator.openEventCreatorScreen();
                })
        );
    }
}
