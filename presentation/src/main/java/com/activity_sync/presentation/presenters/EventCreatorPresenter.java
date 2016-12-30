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
                    view.showCreateConfirmationDialog();
                })
        );

        subscriptions.add(view.confirmActionClickEvent()
                .observeOn(uiThread)
                .subscribe(o -> {

                    apiService.createEvent(createEventBody())
                            .observeOn(uiThread)
                            .subscribe(result -> {

                                navigator.openEventDetailsScreen(result.getEventID());

                            }, this::handleError);
                })
        );
    }
}
