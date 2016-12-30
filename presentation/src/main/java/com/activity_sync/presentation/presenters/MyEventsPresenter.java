package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import java.util.ArrayList;
import java.util.List;

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
        //API CALL WILL BE HERE

        List<Event> events = new ArrayList<>();

        events.add(new EventBuilder()
                .setOrganizer(new UserBuilder()
                        .setUserId(12)
                        .setName("Marcin")
                        .setSurname("Zielinski")
                        .createUser())
                .setDate("2017-12-12 22:23:00")
                .setLocation(new LocationBuilder()
                        .setDescription("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setNumberOfPlayers(12)
                .setId(123)
                .createEvent());

        events.add(new EventBuilder()
                .setOrganizer(new UserBuilder()
                        .setUserId(12)
                        .setName("Marcin")
                        .setSurname("Zielinski")
                        .createUser())
                .setDate("2017-12-12 22:23:00")
                .setLocation(new LocationBuilder()
                        .setDescription("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Football")
                        .createDiscipline())
                .setNumberOfPlayers(10)
                .setId(123)
                .createEvent());

        view.addEventsList(events);
    }
}
