package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.AdditionalInfo;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.AdditionalInfoBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.LevelBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import java.util.Date;

import rx.Scheduler;

public class EventDetailsPresenter extends Presenter<IEventDetailsView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final int eventId;
    private final IApiService apiService;

    private Event event;

    public EventDetailsPresenter(Scheduler uiThread, IEventDetailsView view, INavigator navigator, int eventId, IApiService apiService)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.eventId = eventId;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        createEvent(true, false, true);

        subscriptions.add(view.googleMapAsyncCompleted()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.setEventData(event);
                })
        );

        subscriptions.add(view.joinLeaveEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (event.getAdditionalInfo().isParticipant())
                    {
                        view.showLeaveConfirmationDialog();
                    }
                    else
                    {
                        view.showEnrollConfirmationDialog();
                    }
                })
        );

        subscriptions.add(view.cancelEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    view.showCancelConfirmationDialog();
                })
        );

        subscriptions.add(view.editEventClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEventUpdateScreen(eventId);
                })
        );

        subscriptions.add(view.joinEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    AdditionalInfo additionalInfo = new AdditionalInfoBuilder()
                            .setOrganizer(true)
                            .setParticipant(true)
                            .setCandidate(false)
                            .createAdditionalInfo();

                    view.showEnrollMessage();
                    view.setOrganizerParticipantView(new EventBuilder().setAdditionalInfo(additionalInfo).setIsActive(true).createEvent());
                    event.setAdditionalInfo(additionalInfo); // delete when api provided
                })
        );

        subscriptions.add(view.leaveEventConfirmClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    AdditionalInfo additionalInfo = new AdditionalInfoBuilder()
                            .setOrganizer(true)
                            .setParticipant(false)
                            .setCandidate(false)
                            .createAdditionalInfo();

                    view.showLeaveEventMessage();
                    view.setOrganizerParticipantView(new EventBuilder().setAdditionalInfo(additionalInfo).setIsActive(true).createEvent());
                    event.setAdditionalInfo(additionalInfo); // delete when api provided
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
                    navigator.openUserDetailsScreen("1");
                })
        );

        subscriptions.add(view.participantsDetailsClick()
                .observeOn(uiThread)
                .subscribe(o -> {

                    if (event != null)
                    {
                        navigator.openParticipantsScreen(event.getAdditionalInfo().isOrganizer());
                    }
                })
        );

        subscriptions.add(view.commentsClick()
                .observeOn(uiThread)
                .subscribe(o -> {
                    if (event != null)
                    {
                        navigator.openCommentsScreen(event.getEventId());
                    }
                })
        );
    }

    public void createEvent(boolean isActive, boolean isParticipant, boolean isOrganizer)      //for tests purposes, delete when api provided
    {
        event = new EventBuilder()
                .setId(eventId)
                .setOrganizer(new UserBuilder()
                        .setUsername("mzielu")
                        .createUser())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .setLatitude(50.061124)
                        .setLongitude(19.914123)
                        .createLocation())
                .setNumberOfPlayers(12)
                .setFreePlaces(7)
                .setDate(new Date("2016/11/01"))
                .setLevel(new LevelBuilder()
                        .setName("medium")
                        .createLevel())
                .setDescription("Very long text written in order to check if two lines of text here are displaying correctly. Yeah!")
                .setAdditionalInfo(new AdditionalInfoBuilder()
                        .setParticipant(isParticipant)
                        .setOrganizer(isOrganizer)
                        .createAdditionalInfo())
                .setIsActive(isActive)
                .createEvent();
    }
}
