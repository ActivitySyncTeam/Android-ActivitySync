package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;

import java.util.ArrayList;
import java.util.List;

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
        List<Event> events = new ArrayList<>();

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(12)
                        .setName("Marcin")
                        .createUser())
                .setDate("08.10.2016")
                .setDescription("test description")
                .setId(123)
                .createEvent());

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(13)
                        .setName("Marcinek")
                        .createUser())
                .setDate("09.10.2016")
                .setDescription("test description 2")
                .setId(1233)
                .createEvent());

        view.addEventsList(events);
    }
}
