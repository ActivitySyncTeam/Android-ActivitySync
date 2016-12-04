package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.Scheduler;

abstract public class EventsFragmentBasePresenter extends Presenter<IEventsFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    protected final INavigator navigator;
    protected final Scheduler uiThread;
    protected final IApiService apiService;

    public EventsFragmentBasePresenter(IEventsFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        view.eventsListVisible(true);
        loadEvents();

        subscriptions.add(view.refreshEvents()
                .subscribe(o -> {
                    loadEvents();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedEvent()
                .subscribe(event -> {
                    navigator.openEventDetailsScreen(event.getId());
                })
        );
    }

    abstract void loadEvents();
}
