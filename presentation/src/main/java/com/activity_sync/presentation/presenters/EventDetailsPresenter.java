package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import rx.Scheduler;

public class EventDetailsPresenter extends Presenter<IEventDetailsView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;

    public EventDetailsPresenter(Scheduler uiThread, IEventDetailsView view, INavigator navigator)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.joinEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventsScreen();
                })
        );

        subscriptions.add(view.organizerDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    //navigator.userProfileScreen(event.getOrganizer().getId());
                    view.organizerSelected();
                })
        );
    }
}
