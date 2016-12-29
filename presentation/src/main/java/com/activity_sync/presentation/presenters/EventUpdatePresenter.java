package com.activity_sync.presentation.presenters;


import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LevelBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventCreatorView;

import java.util.Date;

import rx.Scheduler;

public class EventUpdatePresenter extends EventEditorPresenterBase
{
    private final int eventID;
    private Event event;

    public EventUpdatePresenter(Scheduler uiThread, IEventCreatorView view, INavigator navigator, IApiService apiService, int eventID)
    {
        super(uiThread, view, navigator, apiService);
        this.eventID = eventID;
    }

    @Override
    public void start()
    {
        super.start();

        event = createEvent();
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
                    navigator.openEventDetailsScreen(event.getEventId());
                })
        );
    }

    private void loadEditedEvent()
    {
        view.date(event.getDate().toString());
        view.location(event.getLocation());
        view.level(event.getLevel());
        view.discipline(event.getDiscipline());
        view.playersNumber(String.valueOf(event.getNumberOfPlayers()));
        view.description(event.getDescription());
    }

    Event createEvent()
    {
        return new EventBuilder()
                .setDate(new Date("2016/11/01"))
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .setLatitude(3213.3)
                        .setLongitude(32.23)
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .setId(2)
                        .createDiscipline())
                .setLevel(new LevelBuilder()
                        .setId(3)
                        .setName("niski")
                        .createLevel())
                .setNumberOfPlayers(12)
                .setDescription("testowy opis")
                .setId(1)
                .createEvent();
    }
}
