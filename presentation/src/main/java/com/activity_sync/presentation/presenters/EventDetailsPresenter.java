package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.PriceBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import java.util.Date;

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

        view.setEventData(new EventBuilder()
                .setUser(new UserBuilder()
                        .setUserDetails(new UserDetailsBuilder()
                                .setUserName("mzielu")
                                .createUserDetails())
                        .createUser())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .createLocation())
                .setMaxPlaces(12)
                .setOccupiedPlaces(7)
                .setDate(new Date("2016/11/01"))
                .setPrice(new PriceBuilder()
                        .setAmount(3.0f)
                        .setCurrency("zł")
                        .createPrice())
                .setDescription("Very long text written in order to check if two lines of text here are displaying correctly. Yeah!")
                .createEvent());

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
