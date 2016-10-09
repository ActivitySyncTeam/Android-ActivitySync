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
    private Scheduler uiThread;

    public EventsPresenter(IEventsView view, INavigator navigator, Scheduler uiThread)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
    }

    @Override
    public void start()
    {
        super.start();
        loadEvents();

        subscriptions.add(view.refreshEvents()
                .subscribe(event -> {
                    loadEvents();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedEvent()
                .subscribe(event -> {
                    view.eventSelected(event);
                })
        );
    }

    private void loadEvents()
    {
        //API CALL WILL BE HERE

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

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(16)
                        .setName("Lebron")
                        .createUser())
                .setDate("10.10.2016")
                .setDescription("Basketball 4 life")
                .setId(1646)
                .createEvent());

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(18)
                        .setName("Dwayne")
                        .createUser())
                .setDate("11.10.2016")
                .setDescription("This is how we roll")
                .setId(12343)
                .createEvent());

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(18)
                        .setName("Robert")
                        .createUser())
                .setDate("11.10.2016")
                .setDescription("Football lovers")
                .setId(12343)
                .createEvent());

        view.addEventsList(events);
    }
}
