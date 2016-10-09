package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;

public class MyEventsPresenter extends EventsFragmentBasePresenter
{
    public MyEventsPresenter(IEventsFragmentView view, INavigator navigator, Scheduler uiThread)
    {
        super(view, navigator, uiThread);
    }

    @Override
    void loadEvents()
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

        view.addEventsList(events);
    }
}
