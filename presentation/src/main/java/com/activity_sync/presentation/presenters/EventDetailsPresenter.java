package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.Event;
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
    private final int eventId;

    private Event event;

    public EventDetailsPresenter(Scheduler uiThread, IEventDetailsView view, INavigator navigator, int eventId)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.eventId = eventId;
    }

    @Override
    public void start()
    {
        super.start();

        event = new EventBuilder()
                .setId(eventId)
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
                .setIsActive(true)
                .setIsParticipant(false)
                .setIsOrganizer(false)
                .createEvent();

        view.setEventData(event);

        subscriptions.add(view.joinLeaveEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (event.isParticipant())
                    {
                        view.showLeaveConfirmationDialog();
                    }
                    else
                    {
                        view.showJoinConfirmationDialog();
                    }
                })
        );

        subscriptions.add(view.cancelEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showCancelConfirmationDialog();
                })
        );

        subscriptions.add(view.joinEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showJoinEventMessage();
                    view.setOrganizerParticipantView(new EventBuilder().setIsParticipant(true).setIsActive(true).setIsOrganizer(false).createEvent());

                    event.setParticipant(true);
                })
        );

        subscriptions.add(view.leaveEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showLeaveEventMessage();
                    view.setOrganizerParticipantView(new EventBuilder().setIsParticipant(false).setIsActive(true).setIsOrganizer(false).createEvent());

                    event.setParticipant(false);
                })
        );

        subscriptions.add(view.cancelEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventsScreen();
                })
        );

        subscriptions.add(view.organizerDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openUserDetailsScreen(1);
                })
        );

        subscriptions.add(view.participantsDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openParticipantsScreen();
                })
        );
    }
}
