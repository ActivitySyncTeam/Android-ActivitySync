package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LevelBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.ParticipantsBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.EventDetailsPresenter;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class EventDetailsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IApiService apiService;

    @Mock
    IEventDetailsView view;

    @Mock
    CurrentUser currentUser;

    @Mock
    IErrorHandler errorHandler;

    PublishSubject joinLeaveEventClickEvent = PublishSubject.create();
    PublishSubject cancelEventClickEvent = PublishSubject.create();
    PublishSubject joinEventConfirmEvent = PublishSubject.create();
    PublishSubject leaveEventConfirmEvent = PublishSubject.create();
    PublishSubject ediEventClickEvent = PublishSubject.create();
    PublishSubject cancelEventConfirmEvent = PublishSubject.create();
    PublishSubject organizerDetailsClickEvent = PublishSubject.create();
    PublishSubject participantsClickEvent = PublishSubject.create();
    PublishSubject googleMapAsyncEvent = PublishSubject.create();
    PublishSubject commentsClickEvent = PublishSubject.create();

    private int eventId = 1;
    private int organizerId = 1;

    private Participants participants;

    @Before
    public void setup()
    {
        Mockito.when(view.joinLeaveEventClick()).thenReturn(joinLeaveEventClickEvent);
        Mockito.when(view.cancelEventClick()).thenReturn(cancelEventClickEvent);
        Mockito.when(view.editEventClick()).thenReturn(ediEventClickEvent);

        Mockito.when(view.joinEventConfirmClick()).thenReturn(joinEventConfirmEvent);
        Mockito.when(view.leaveEventConfirmClick()).thenReturn(leaveEventConfirmEvent);
        Mockito.when(view.cancelEventConfirmClick()).thenReturn(cancelEventConfirmEvent);
        Mockito.when(view.commentsClick()).thenReturn(commentsClickEvent);

        Mockito.when(view.organizerDetailsClick()).thenReturn(organizerDetailsClickEvent);
        Mockito.when(view.participantsDetailsClick()).thenReturn(participantsClickEvent);

        Mockito.when(view.googleMapAsyncCompleted()).thenReturn(googleMapAsyncEvent);

        Event event = create(false, false);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        participants = new ParticipantsBuilder().create();

        Mockito.when(apiService.cancelEventJoinRequest(any())).thenReturn(Observable.just(null));
        Mockito.when(apiService.leaveEvent(any())).thenReturn(Observable.just(null));
        Mockito.when(apiService.joinEvent(eventId)).thenReturn(Observable.just(participants));
        Mockito.when(apiService.joinEventAsAdmin(eventId)).thenReturn(Observable.just(participants));
        Mockito.when(apiService.deleteEvent(eventId)).thenReturn(Observable.just(null));
    }

    @Test
    public void eventDetailsPresenter_clickJoinEvent_displayJoinConfirmationDialog()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        joinLeaveEventClickEvent.onNext(this);
        Mockito.verify(view).showEnrollConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickCancelEvent_displayCancelConfirmationDialog()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        cancelEventClickEvent.onNext(this);
        Mockito.verify(view).showCancelConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickLeaveEvent_displayLeaveConfirmationDialog()
    {
        Event event = create(true, true);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        joinLeaveEventClickEvent.onNext(this);
        Mockito.verify(view).showLeaveConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickEditEvent_openEditEditor()
    {
        Event event = create(false, true);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        ediEventClickEvent.onNext(this);
        Mockito.verify(navigator).openEventUpdateScreen(event);
    }

    @Test
    public void eventDetailsPresenter_clickConfirmJoinEvent_showJoinMessage()
    {
        Event event = create(false, false);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        joinEventConfirmEvent.onNext(this);
        Mockito.verify(apiService).joinEvent(eventId);
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showEnrollMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmJoinEvent_showJoinMessageAsAdmin()
    {
        Event event = create(false, true);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        joinEventConfirmEvent.onNext(this);
        Mockito.verify(apiService).joinEventAsAdmin(eventId);
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showEnrollMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmLeaveEvent_showLeaveMessage()
    {
        Event event = create(false, false);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        leaveEventConfirmEvent.onNext(this);
        Mockito.verify(apiService).cancelEventJoinRequest(any());
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showLeaveEventMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmLeaveEvent_showLeaveMessageAsParticipant()
    {
        Event event = create(true, false);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        leaveEventConfirmEvent.onNext(this);
        Mockito.verify(apiService).leaveEvent(any());
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showLeaveEventMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmCancelEvent_openEventsScreen()
    {
        Event event = create(false, false);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        cancelEventConfirmEvent.onNext(this);
        Mockito.verify(apiService).deleteEvent(eventId);
        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void eventDetailsPresenter_clickOrganizerDetailsEvent_openMyProfileScreen()
    {
        Mockito.when(currentUser.userId()).thenReturn(organizerId);

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        organizerDetailsClickEvent.onNext(this);
        Mockito.verify(navigator).openMyProfileScreen();
    }

    @Test
    public void eventDetailsPresenter_clickOrganizerDetailsEvent_openDetailsScreen()
    {
        Mockito.when(currentUser.userId()).thenReturn(23423);

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        organizerDetailsClickEvent.onNext(this);
        Mockito.verify(navigator, never()).openMyProfileScreen();
        Mockito.verify(navigator).openUserDetailsScreen(organizerId);
    }

    @Test
    public void eventDetailsPresenter_clickParticipantsEvent_openParticipantsScreen()
    {
        Event event = create(true, true);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        participantsClickEvent.onNext(this);
        Mockito.verify(navigator).openParticipantsScreen(true, eventId);
    }

    @Test
    public void eventDetailsPresenter_clickComments_openCommentsScreen()
    {
        Event event = create(true, true);
        Mockito.when(apiService.getEventDetails(eventId)).thenReturn(Observable.just(event));

        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        googleMapAsyncEvent.onNext(this);

        commentsClickEvent.onNext(this);
        Mockito.verify(navigator).openCommentsScreen(eventId);
    }

    private EventDetailsPresenter createPresenter()
    {
        return new EventDetailsPresenter(Schedulers.immediate(), view, navigator, eventId, apiService, currentUser, errorHandler);
    }

    private Event create(boolean isParticipant, boolean isOrganizer)
    {
        return new EventBuilder()
                .setId(eventId)
                .setOrganizer(new UserBuilder()
                        .setUserId(organizerId)
                        .setUsername("mzielu")
                        .createUser())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setLocation(new LocationBuilder()
                        .setDescription("Park Jordana")
                        .setLatitude(50.061124)
                        .setLongitude(19.914123)
                        .createLocation())
                .setNumberOfPlayers(12)
                .setFreePlaces(7)
                .setDate("2017-12-12 23:23:00")
                .setLevel(new LevelBuilder()
                        .setName("medium")
                        .createLevel())
                .setDescription("Very long text written in order to check if two lines of text here are displaying correctly. Yeah!")
                .setParticipant(isParticipant)
                .setOrganizer(isOrganizer)
                .createEvent();
    }
}
