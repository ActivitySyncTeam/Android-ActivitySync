package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;

import rx.Scheduler;

public class EventsPresenter extends Presenter<IEventsView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    private final INavigator navigator;
    private Scheduler scheduler;

    public EventsPresenter(IEventsView view, INavigator navigator, Scheduler scheduler)
    {
        super(view);
        this.navigator = navigator;
        this.scheduler = scheduler;
    }

    @Override
    public void start()
    {
        super.start();

        this.loadEvents();

        this.subscriptions.add(this.view.selectedEvent()
                .subscribe(event -> {
                    view.eventSelected(event);
                })
        );
    }

    private void loadEvents()
    {
        //loadingEvents
    }
}
