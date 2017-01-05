package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.Scheduler;
import timber.log.Timber;

abstract public class EventsFragmentBasePresenter extends Presenter<IEventsFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    protected final INavigator navigator;
    protected final Scheduler uiThread;
    protected final IApiService apiService;

    protected int currentPage = 1;
    protected boolean endAlreadyReached = false;

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

        view.eventsListVisible();
        loadEvents();

        subscriptions.add(view.refreshEvents()
                .subscribe(o -> {

                    currentPage = 1;
                    endAlreadyReached = false;

                    resolveRefresh();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedEvent()
                .subscribe(event -> {
                    navigator.openEventDetailsScreen(event.getEventId());
                })
        );

        subscriptions.add(view.pageDownReached()
                .observeOn(uiThread)
                .subscribe(result -> {

                    if (!endAlreadyReached)
                    {
                        currentPage++;
                        resolveRefresh();
                    }
                })
        );
    }

    abstract void loadEvents();

    abstract void resolveRefresh();

    protected void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
    }
}
