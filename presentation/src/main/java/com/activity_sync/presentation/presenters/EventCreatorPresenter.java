package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import rx.Scheduler;

public class EventCreatorPresenter extends EventEditorPresenterBase
{
    public EventCreatorPresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService, IErrorHandler errorHandler)
    {
        super(uiThread, view, navigator, apiService, errorHandler);
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

                    if (view.location() != null)
                    {
                        view.showProgressBar();

                        apiService.createEvent(createEventBody())
                                .observeOn(uiThread)
                                .subscribe(result -> {

                                    view.hideProgressBar();
                                    navigator.openEventDetailsScreen(result.getEventID());

                                }, this::handleError);
                    }
                    else
                    {
                        view.showNoLocationChosenError();
                    }
                })
        );
    }
}
