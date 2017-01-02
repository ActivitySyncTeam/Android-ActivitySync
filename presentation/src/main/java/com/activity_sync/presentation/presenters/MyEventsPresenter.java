package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import rx.Scheduler;

public class MyEventsPresenter extends EventsFragmentBasePresenter
{
    public MyEventsPresenter(IEventsFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view, navigator, uiThread, apiService);
    }

    @Override
    public void start()
    {
        view.filterLayoutVisible(false);
        super.start();
    }

    @Override
    void loadEvents()
    {
        apiService.getMyEvents()
                .observeOn(uiThread)
                .subscribe(eventsCollection -> {

                    view.refreshingVisible(false);
                    view.addEventsList(eventsCollection.getEvents());

                }, this::handleError);
    }
}
