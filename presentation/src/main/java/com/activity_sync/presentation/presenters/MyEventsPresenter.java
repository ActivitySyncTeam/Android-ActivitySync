package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Scheduler;

public class MyEventsPresenter extends EventsFragmentBasePresenter
{
    public MyEventsPresenter(IEventsFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view, navigator, uiThread, apiService);
    }

    @Override
    void loadEvents()
    {
        //API CALL WILL BE HERE

        List<Event> events = new ArrayList<>();

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(12)
                        .setUserDetails(new UserDetailsBuilder()
                                .setFirstName("Marcin")
                                .setlastName("Zielinski")
                                .createUserDetails())
                        .createUser())
                .setDate(new Date("2016/10/15"))
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setMaxPlaces(12)
                .setId(123)
                .createEvent());

        events.add(new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(12)
                        .setUserDetails(new UserDetailsBuilder()
                                .setFirstName("Marcin")
                                .setlastName("Zielinski")
                                .createUserDetails())
                        .createUser())
                .setDate(new Date("2016/10/15"))
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Football")
                        .createDiscipline())
                .setMaxPlaces(10)
                .setId(123)
                .createEvent());

        view.addEventsList(events);
    }
}
