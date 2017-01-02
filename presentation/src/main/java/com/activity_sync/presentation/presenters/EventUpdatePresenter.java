package com.activity_sync.presentation.presenters;


import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import rx.Scheduler;

public class EventUpdatePresenter extends EventEditorPresenterBase
{
    private final Event event;

    public EventUpdatePresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService, Event event)
    {
        super(uiThread, view, navigator, apiService);
        this.event = event;
    }

    @Override
    public void start()
    {
        super.start();

        view.prepareUpdateLayout();

        loadEditedEvent();

        subscriptions.add(view.createEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showUpdateConfirmationDialog();
                })
        );

        subscriptions.add(view.confirmActionClickEvent()
                .observeOn(uiThread)
                .subscribe(o -> {

                    apiService.updateEvent(event.getEventId(), createEventBody())
                            .observeOn(uiThread)
                            .subscribe(result -> {

                                navigator.openEventDetailsScreen(result.getEventID());

                            }, this::handleError);
                })
        );
    }

    private void loadEditedEvent()
    {
        view.date(event.getDate());
        view.location(event.getLocation());
        view.level(event.getLevel());
        view.discipline(event.getDiscipline());
        view.playersNumber(String.valueOf(event.getNumberOfPlayers()));
        view.description(event.getDescription());
    }
}
