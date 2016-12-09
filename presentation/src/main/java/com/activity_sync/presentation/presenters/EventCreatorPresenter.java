package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import rx.Scheduler;

public class EventCreatorPresenter extends EventEditorPresenterBase
{
    public EventCreatorPresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService)
    {
        super(uiThread, view, navigator, apiService);
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.createEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showConfirmationDialog();
                })
        );

        subscriptions.add(view.confirmCreationClickEvent()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventsScreen();
                })
        );
    }
}
